package cn.cug.sxy.domain.activity.service.trial.node;

import cn.cug.sxy.domain.activity.model.entity.MarketProductEntity;
import cn.cug.sxy.domain.activity.model.entity.TrialBalanceEntity;
import cn.cug.sxy.domain.activity.model.valobj.GroupBuyActivityVO;
import cn.cug.sxy.domain.activity.model.valobj.SkuVO;
import cn.cug.sxy.domain.activity.model.valobj.TagScopeVO;
import cn.cug.sxy.domain.activity.service.trial.AbstractGroupBuyMarketSupport;
import cn.cug.sxy.domain.activity.service.trial.factory.DefaultActivityStrategyFactory;
import cn.cug.sxy.domain.activity.service.trial.thread.QueryGroupBuyActivityVOThreadTask;
import cn.cug.sxy.domain.activity.service.trial.thread.QuerySkuVOThreadTask;
import cn.cug.sxy.types.design.framework.tree.StrategyHandler;
import com.alibaba.fastjson2.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;
import java.util.concurrent.FutureTask;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @version 1.0
 * @Date 2025/3/30 16:42
 * @Description 人群标签节点
 * @Author jerryhotton
 */

@Slf4j
@Service
public class TagNode extends AbstractGroupBuyMarketSupport<MarketProductEntity, DefaultActivityStrategyFactory.DynamicContext, TrialBalanceEntity> {

    @Resource
    private ThreadPoolExecutor threadPoolExecutor;

    @Resource
    private MarketNode marketNode;

    @Resource
    private EndNode endNode;

    @Resource
    private ErrorNode errorNode;

    @Override
    protected void multiThread(MarketProductEntity requestParameter, DefaultActivityStrategyFactory.DynamicContext dynamicContext) throws Exception {
        QueryGroupBuyActivityVOThreadTask queryGroupBuyActivityVOThreadTask = new QueryGroupBuyActivityVOThreadTask(requestParameter.getSource(), requestParameter.getChannel(), requestParameter.getGoodsId(), activityRepository);
        FutureTask<GroupBuyActivityVO> groupBuyActivityVOFutureTask = new FutureTask<>(queryGroupBuyActivityVOThreadTask);
        threadPoolExecutor.execute(groupBuyActivityVOFutureTask);

        QuerySkuVOThreadTask querySkuVOThreadTask = new QuerySkuVOThreadTask(requestParameter.getGoodsId(), activityRepository);
        FutureTask<SkuVO> skuVOFutureTask = new FutureTask<>(querySkuVOThreadTask);
        threadPoolExecutor.execute(skuVOFutureTask);

        dynamicContext.setGroupBuyActivityVO(groupBuyActivityVOFutureTask.get(timeout, TimeUnit.MINUTES));
        dynamicContext.setSkuVO(skuVOFutureTask.get(timeout, TimeUnit.MINUTES));
        log.info("拼团商品查询试算服务-TagNode userId:{} 异步线程加载数据「GroupBuyActivityDiscountVO、SkuVO」完成", requestParameter.getUserId());
    }

    @Override
    protected TrialBalanceEntity doApply(MarketProductEntity requestParameter, DefaultActivityStrategyFactory.DynamicContext dynamicContext) throws Exception {
        log.info("拼团商品查询试算服务-TagNode userId:{} tagsConfig:{} 人群标签过滤", requestParameter.getUserId(), JSON.toJSONString(dynamicContext.getGroupBuyActivityVO().getTagsConfig()));
        // 获取上下文数据
        GroupBuyActivityVO groupBuyActivityVO = dynamicContext.getGroupBuyActivityVO();
        SkuVO skuVO = dynamicContext.getSkuVO();
        if (null == groupBuyActivityVO || null == skuVO) {
            return router(requestParameter, dynamicContext);
        }
        Map<String, String> tagsConfigMap = groupBuyActivityVO.getTagsConfig();
        // 人群标签配置为空，不做人群限制
        if (null == tagsConfigMap) {
            dynamicContext.setIsVisible(TagScopeVO.VISIBLE.getAllow());
            dynamicContext.setIsEnable(TagScopeVO.ENABLE.getAllow());
            return router(requestParameter, dynamicContext);
        }
        Map<String, Boolean> isWithin = activityRepository.IsWithinCrowdTagRangeAndScope(tagsConfigMap, requestParameter.getUserId());
        dynamicContext.setIsVisible(isWithin.get(TagScopeVO.VISIBLE.getCode()));
        dynamicContext.setIsEnable(isWithin.get(TagScopeVO.ENABLE.getCode()));
        return router(requestParameter, dynamicContext);
    }

    @Override
    public StrategyHandler<MarketProductEntity, DefaultActivityStrategyFactory.DynamicContext, TrialBalanceEntity> get(MarketProductEntity requestParameter, DefaultActivityStrategyFactory.DynamicContext dynamicContext) throws Exception {
        if (null == dynamicContext.getGroupBuyActivityVO() || null == dynamicContext.getSkuVO()) {
            return errorNode;
        }
        if (!dynamicContext.getIsVisible()) {
            return endNode;
        }
        return marketNode;
    }

}
