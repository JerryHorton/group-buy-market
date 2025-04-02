package cn.cug.sxy.domain.activity.service.trial.node;

import cn.cug.sxy.domain.activity.model.entity.MarketProductEntity;
import cn.cug.sxy.domain.activity.model.entity.TrialBalanceEntity;
import cn.cug.sxy.domain.activity.model.valobj.GroupBuyActivityVO;
import cn.cug.sxy.domain.activity.model.valobj.SkuVO;
import cn.cug.sxy.domain.activity.service.trial.AbstractGroupBuyMarketSupport;
import cn.cug.sxy.domain.activity.service.trial.factory.ActivityStrategyFactory;
import cn.cug.sxy.types.design.framework.tree.StrategyHandler;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @version 1.0
 * @Date 2025/3/27 16:45
 * @Description 结束节点
 * @Author jerryhotton
 */

@Slf4j
@Service
public class EndNode extends AbstractGroupBuyMarketSupport<MarketProductEntity, ActivityStrategyFactory.DynamicContext, TrialBalanceEntity> {

    @Override
    protected TrialBalanceEntity doApply(MarketProductEntity requestParameter, ActivityStrategyFactory.DynamicContext dynamicContext) throws Exception {
        log.info("拼团商品查询试算服务-EndNode userId:{} requestParameter:{}", requestParameter.getUserId(), JSON.toJSONString(requestParameter));
        GroupBuyActivityVO groupBuyActivityVO = dynamicContext.getGroupBuyActivityVO();
        SkuVO skuVO = dynamicContext.getSkuVO();

        return TrialBalanceEntity.builder()
                .goodsId(groupBuyActivityVO.getGoodsId())
                .goodsName(skuVO.getGoodsName())
                .originalPrice(skuVO.getOriginalPrice())
                .finalPrice(dynamicContext.getFinalPrice())
                .discountDeduction(skuVO.getOriginalPrice().subtract(dynamicContext.getFinalPrice()))
                .targetCount(groupBuyActivityVO.getTargetCount())
                .startTime(groupBuyActivityVO.getStartTime())
                .endTime(groupBuyActivityVO.getEndTime())
                .isVisible(dynamicContext.getIsVisible())
                .isEnable(dynamicContext.getIsEnable())
                .groupBuyActivityVO(groupBuyActivityVO)
                .build();
    }

    @Override
    public StrategyHandler<MarketProductEntity, ActivityStrategyFactory.DynamicContext, TrialBalanceEntity> get(MarketProductEntity requestParameter, ActivityStrategyFactory.DynamicContext dynamicContext) throws Exception {
        return defaultStrategyHandler;
    }

}
