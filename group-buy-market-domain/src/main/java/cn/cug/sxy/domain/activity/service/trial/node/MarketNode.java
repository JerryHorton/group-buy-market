package cn.cug.sxy.domain.activity.service.trial.node;

import cn.cug.sxy.domain.activity.model.entity.MarketProductEntity;
import cn.cug.sxy.domain.activity.model.entity.TrialBalanceEntity;
import cn.cug.sxy.domain.activity.model.valobj.GroupBuyActivityVO;
import cn.cug.sxy.domain.activity.model.valobj.SkuVO;
import cn.cug.sxy.domain.activity.service.discount.IDiscountCalculateService;
import cn.cug.sxy.domain.activity.service.trial.AbstractGroupBuyMarketSupport;
import cn.cug.sxy.domain.activity.service.trial.factory.DefaultActivityStrategyFactory;
import cn.cug.sxy.domain.activity.service.trial.thread.QueryGroupBuyActivityVOThreadTask;
import cn.cug.sxy.domain.activity.service.trial.thread.QuerySkuVOThreadTask;
import cn.cug.sxy.types.design.framework.tree.StrategyHandler;
import cn.cug.sxy.types.enums.ResponseCode;
import cn.cug.sxy.types.exception.AppException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Map;
import java.util.concurrent.FutureTask;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @version 1.0
 * @Date 2025/3/27 16:44
 * @Description 营销优惠节点
 * @Author jerryhotton
 */

@Slf4j
@Service
public class MarketNode extends AbstractGroupBuyMarketSupport<MarketProductEntity, DefaultActivityStrategyFactory.DynamicContext, TrialBalanceEntity> {

    @Resource
    private ThreadPoolExecutor threadPoolExecutor;

    @Resource
    private Map<String, IDiscountCalculateService> discountCalculateService;

    @Resource
    private EndNode endNode;

    @Override
    protected void multiThread(MarketProductEntity requestParameter, DefaultActivityStrategyFactory.DynamicContext dynamicContext) throws Exception {
        QueryGroupBuyActivityVOThreadTask queryGroupBuyActivityVOThreadTask = new QueryGroupBuyActivityVOThreadTask(requestParameter.getSource(), requestParameter.getChannel(), activityRepository);
        FutureTask<GroupBuyActivityVO> groupBuyActivityVOFutureTask = new FutureTask<>(queryGroupBuyActivityVOThreadTask);
        threadPoolExecutor.execute(groupBuyActivityVOFutureTask);

        QuerySkuVOThreadTask querySkuVOThreadTask = new QuerySkuVOThreadTask(requestParameter.getGoodsId(), activityRepository);
        FutureTask<SkuVO> skuVOFutureTask = new FutureTask<>(querySkuVOThreadTask);
        threadPoolExecutor.execute(skuVOFutureTask);

        dynamicContext.setGroupBuyActivityVO(groupBuyActivityVOFutureTask.get(timeout, TimeUnit.MINUTES));
        dynamicContext.setSkuVO(skuVOFutureTask.get(timeout, TimeUnit.MINUTES));
    }

    @Override
    protected TrialBalanceEntity doApply(MarketProductEntity requestParameter, DefaultActivityStrategyFactory.DynamicContext dynamicContext) throws Exception {
        GroupBuyActivityVO.GroupBuyDiscount groupBuyDiscount = dynamicContext.getGroupBuyActivityVO().getGroupBuyDiscount();
        SkuVO skuVO = dynamicContext.getSkuVO();
        String marketPlan = groupBuyDiscount.getMarketPlan();
        IDiscountCalculateService calculateService = discountCalculateService.get(marketPlan);
        if (null == calculateService) {
            throw new AppException(ResponseCode.E0001.getCode(), ResponseCode.E0001.getInfo());
        }
        BigDecimal deductionPrice = calculateService.calculate(requestParameter.getUserId(), skuVO.getOriginalPrice(), groupBuyDiscount);
        dynamicContext.setDeductionPrice(deductionPrice);
        return router(requestParameter, dynamicContext);
    }

    @Override
    public StrategyHandler<MarketProductEntity, DefaultActivityStrategyFactory.DynamicContext, TrialBalanceEntity> get(MarketProductEntity requestParameter, DefaultActivityStrategyFactory.DynamicContext dynamicContext) throws Exception {
        return endNode;
    }

}
