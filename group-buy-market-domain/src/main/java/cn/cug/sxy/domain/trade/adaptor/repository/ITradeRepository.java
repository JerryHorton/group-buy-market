package cn.cug.sxy.domain.trade.adaptor.repository;

import cn.cug.sxy.domain.trade.model.aggregate.GroupBuyOrderAggregate;
import cn.cug.sxy.domain.trade.model.aggregate.TradePaySettlementAggregate;
import cn.cug.sxy.domain.trade.model.entity.GroupBuyActivityEntity;
import cn.cug.sxy.domain.trade.model.entity.GroupBuyOrderEntity;
import cn.cug.sxy.domain.trade.model.entity.MarketPayOrderEntity;
import cn.cug.sxy.domain.trade.model.entity.NotifyTaskEntity;
import cn.cug.sxy.domain.trade.model.valobj.GroupBuyProgressVO;

import java.util.List;

/**
 * @version 1.0
 * @Date 2025/3/31 20:20
 * @Description 拼团交易 仓储接口
 * @Author jerryhotton
 */

public interface ITradeRepository {

    MarketPayOrderEntity queryUnpaidMarketPayOrderByOutTradeNo(String outTradeNo);

    GroupBuyProgressVO queryGroupBuyProgress(String teamId);

    GroupBuyActivityEntity queryGroupBuyActivityEntity(Long activityId);

    Integer queryActivityParticipationCount(String userId, Long activityId);

    GroupBuyOrderEntity queryGroupBuyTeamByTeamByTeamId(String teamId);

    List<NotifyTaskEntity> queryUnExecuteSuccessNotifyTaskList();

    List<NotifyTaskEntity> queryUnExecuteSuccessNotifyTaskList(String teamId);

    MarketPayOrderEntity lockMarketPayOrder(GroupBuyOrderAggregate groupBuyOrderAggregate);

    NotifyTaskEntity settlementMarketPayOrder(TradePaySettlementAggregate tradePaySettlementAggregate);

    Boolean isSCBlackListIntercept(String source, String channel);

    int updateNotifyTaskStatusSuccess(String teamId);

    int updateNotifyTaskStatusRetry(String teamId);

    int updateNotifyTaskStatusError(String teamId);

    boolean occupyTeamStock(String teamStockKey, String recoveryTeamStockKey, Integer targetCount, Integer validTime);

    void recoveryTeamStock(String recoveryTeamStockKey);

}
