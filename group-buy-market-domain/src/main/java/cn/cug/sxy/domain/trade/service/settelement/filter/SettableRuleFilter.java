package cn.cug.sxy.domain.trade.service.settelement.filter;

import cn.cug.sxy.domain.trade.model.entity.GroupBuyTeamEntity;
import cn.cug.sxy.domain.trade.model.entity.MarketPayOrderEntity;
import cn.cug.sxy.domain.trade.model.entity.TradeSettlementRuleCommandEntity;
import cn.cug.sxy.domain.trade.model.entity.TradeSettlementRuleFilterBackEntity;
import cn.cug.sxy.domain.trade.repository.ITradeRepository;
import cn.cug.sxy.domain.trade.service.settelement.factory.TradeSettlementRuleFilterFactory;
import cn.cug.sxy.types.design.framework.link.multitonModel.handler.ILogicHandler;
import cn.cug.sxy.types.enums.ResponseCode;
import cn.cug.sxy.types.exception.AppException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @version 1.0
 * @Date 2025/4/4 14:33
 * @Description 拼团时间范围有效性过滤
 * @Author Sxy
 */

@Slf4j
@Service
public class SettableRuleFilter implements ILogicHandler<TradeSettlementRuleCommandEntity, TradeSettlementRuleFilterFactory.DynamicContext, TradeSettlementRuleFilterBackEntity> {

    @Resource
    private ITradeRepository repository;

    @Override
    public TradeSettlementRuleFilterBackEntity apply(TradeSettlementRuleCommandEntity requestParameter, TradeSettlementRuleFilterFactory.DynamicContext dynamicContext) throws Exception {
        log.info("结算规则过滤-有效时间校验, userId:{} outTradeNo:{}", requestParameter.getUserId(), requestParameter.getOutTradeNo());
        MarketPayOrderEntity marketPayOrderEntity = dynamicContext.getMarketPayOrderEntity();
        GroupBuyTeamEntity groupBuyTeamEntity = repository.queryGroupBuyTeamByTeamByTeamId(marketPayOrderEntity.getTeamId());

        Date outTradeTime = requestParameter.getOutTradeTime();
        if (outTradeTime.after(groupBuyTeamEntity.getValidEndTime())) {
            log.error("拼团交易-结算营销拼团订单失败, 拼团时间已过期, userId:{} outTradeNo:{}", requestParameter.getUserId(), requestParameter.getOutTradeNo());
            throw new AppException(ResponseCode.E0106);
        }

        dynamicContext.setGroupBuyTeamEntity(groupBuyTeamEntity);

        return next(requestParameter, dynamicContext);
    }

}
