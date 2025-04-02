package cn.cug.sxy.domain.trade.repository;

import cn.cug.sxy.domain.trade.model.aggregate.GroupBuyOrderAggregate;
import cn.cug.sxy.domain.trade.model.entity.GroupBuyActivityEntity;
import cn.cug.sxy.domain.trade.model.entity.MarketPayOrderEntity;
import cn.cug.sxy.domain.trade.model.valobj.GroupBuyProgressVO;

/**
 * @version 1.0
 * @Date 2025/3/31 20:20
 * @Description 拼团交易 仓储接口
 * @Author jerryhotton
 */

public interface ITradeRepository {

    MarketPayOrderEntity queryUnpaidMarketPayOrderByOutTradeNo(String userId, String outTradeNo);

    GroupBuyProgressVO queryGroupBuyProgress(String teamId);

    GroupBuyActivityEntity queryGroupBuyActivityEntity(Long activityId);

    Integer queryActivityParticipationCount(String userId, Long activityId);

    MarketPayOrderEntity lockMarketPayOrder(GroupBuyOrderAggregate groupBuyOrderAggregate);

}
