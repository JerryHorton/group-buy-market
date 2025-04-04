package cn.cug.sxy.domain.trade.service;

import cn.cug.sxy.domain.trade.model.entity.TradePaySettlementEntity;
import cn.cug.sxy.domain.trade.model.entity.TradePaySuccessEntity;

/**
 * @version 1.0
 * @Date 2025/4/3 11:42
 * @Description 拼团交易结算 服务接口
 * @Author Sxy
 */

public interface ITradeSettlementOrderService {

    /**
     * 结算营销拼团订单
     *
     * @param tradePaySuccessEntity 交易支付订单实体
     * @return 交易结算订单实体
     */
    TradePaySettlementEntity settlementMarketPayOrder(TradePaySuccessEntity tradePaySuccessEntity) throws Exception;

}
