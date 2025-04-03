package cn.cug.sxy.domain.trade.service;

import cn.cug.sxy.domain.trade.model.entity.MarketPayOrderEntity;
import cn.cug.sxy.domain.trade.model.entity.PayActivityEntity;
import cn.cug.sxy.domain.trade.model.entity.PayDiscountEntity;
import cn.cug.sxy.domain.trade.model.entity.UserEntity;
import cn.cug.sxy.domain.trade.model.valobj.GroupBuyProgressVO;

/**
 * @version 1.0
 * @Date 2025/3/31 19:45
 * @Description 拼团订单 服务接口
 * @Author jerryhotton
 */

public interface ITradeLockOrderService {

    MarketPayOrderEntity queryUnpaidMarketPayOrderByOutTradeNo(String userId, String outTradeNo);

    GroupBuyProgressVO queryGroupBuyProgress(String teamId);

    MarketPayOrderEntity lockMarketPayOrder(UserEntity userEntity, PayActivityEntity payActivityEntity, PayDiscountEntity payDiscountEntity)throws Exception ;

}
