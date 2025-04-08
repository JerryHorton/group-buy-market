package cn.cug.sxy.infrastructure.persistent.dao;

import cn.cug.sxy.infrastructure.persistent.dao.po.UserGroupBuyOrderDetail;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @version 1.0
 * @Date 2025/3/31 19:30
 * @Description 用户拼单明细 Dao
 * @Author jerryhotton
 */

@Mapper
public interface IUserGroupBuyOrderDetailDao {

    UserGroupBuyOrderDetail queryUnpaidMarketPayOrderByOutTradeNo(UserGroupBuyOrderDetail userGroupBuyOrderDetailReq);

    List<UserGroupBuyOrderDetail> queryUserActivityParticipationOrderLists(UserGroupBuyOrderDetail userGroupBuyOrderDetailReq);

    List<String> queryGroupBuyOrderListOutTradeNo(String teamId);

    List<UserGroupBuyOrderDetail> queryOwnOngoingUserGroupBuyOrderDetail(UserGroupBuyOrderDetail userGroupBuyOrderDetailReq);

    List<UserGroupBuyOrderDetail> queryRandomOngoingUserGroupBuyOrderDetail(UserGroupBuyOrderDetail userGroupBuyOrderDetailReq);

    void insertGroupBuyOrderList(UserGroupBuyOrderDetail userGroupBuyOrderDetail);

    void updateUserGroupBuyOrderDetailStatus(UserGroupBuyOrderDetail userGroupBuyOrderDetailReq);

}
