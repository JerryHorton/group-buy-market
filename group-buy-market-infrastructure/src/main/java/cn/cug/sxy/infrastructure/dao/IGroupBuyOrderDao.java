package cn.cug.sxy.infrastructure.dao;

import cn.cug.sxy.infrastructure.dao.po.GroupBuyOrder;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @version 1.0
 * @Date 2025/3/31 19:28
 * @Description 拼团订单 Dao
 * @Author jerryhotton
 */

@Mapper
public interface IGroupBuyOrderDao {

    GroupBuyOrder queryGroupBuyOrderByTeamId(String teamId);

    List<GroupBuyOrder> queryOngoingGroupBuyOrderByTeamIds(List<String> teamIds);

    List<GroupBuyOrder> queryCurrentGroupBuyTeams(GroupBuyOrder groupBuyOrderReq);

    Integer queryTeamCompletedCount(List<String> teamIds);

    Integer queryTotalTeamUserCount(List<String> teamIds);

    void insertGroupBuyOrder(GroupBuyOrder groupBuyOrder);

    int updateAddLockCount(String teamId);

    int updateAddCompleteCount(String teamId);

    int updateGroupBuyOrderStatus(GroupBuyOrder groupBuyOrder);

}
