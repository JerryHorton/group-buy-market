package cn.cug.sxy.infrastructure.persistent.dao;

import cn.cug.sxy.infrastructure.persistent.po.GroupBuyOrderList;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @version 1.0
 * @Date 2025/3/31 19:30
 * @Description 用户拼单明细 Dao
 * @Author jerryhotton
 */

@Mapper
public interface IGroupBuyOrderListDao {

    GroupBuyOrderList queryUnpaidMarketPayOrderByOutTradeNo(GroupBuyOrderList groupBuyOrderListReq);

    List<GroupBuyOrderList> queryUserActivityParticipationOrderLists(GroupBuyOrderList groupBuyOrderListReq);

    void insertGroupBuyOrderList(GroupBuyOrderList groupBuyOrderList);

}
