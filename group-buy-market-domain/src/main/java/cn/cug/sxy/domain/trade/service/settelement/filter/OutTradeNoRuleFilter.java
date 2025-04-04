package cn.cug.sxy.domain.trade.service.settelement.filter;

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

/**
 * @version 1.0
 * @Date 2025/4/4 14:32
 * @Description 外部单号有效性过滤
 * @Author Sxy
 */

@Slf4j
@Service
public class OutTradeNoRuleFilter implements ILogicHandler<TradeSettlementRuleCommandEntity, TradeSettlementRuleFilterFactory.DynamicContext, TradeSettlementRuleFilterBackEntity> {

    @Resource
    private ITradeRepository repository;

    @Override
    public TradeSettlementRuleFilterBackEntity apply(TradeSettlementRuleCommandEntity requestParameter, TradeSettlementRuleFilterFactory.DynamicContext dynamicContext) throws Exception {
        log.info("结算规则过滤-外部单号校验, userId:{} outTradeNo:{}", requestParameter.getUserId(), requestParameter.getOutTradeNo());
        // 1. 查询用户拼团单信息
        MarketPayOrderEntity marketPayOrderEntity = repository.queryUnpaidMarketPayOrderByOutTradeNo(requestParameter.getOutTradeNo());
        if (null == marketPayOrderEntity) {
            log.error("拼团交易-结算营销拼团订单失败, 未查询到拼团订单信息, userId:{} outTradeNo:{}", requestParameter.getUserId(), requestParameter.getOutTradeNo());
            throw new AppException(ResponseCode.E0104);
        }

        // 2. 设置拼团订单信息
        dynamicContext.setMarketPayOrderEntity(marketPayOrderEntity);

        return next(requestParameter, dynamicContext);
    }

}
