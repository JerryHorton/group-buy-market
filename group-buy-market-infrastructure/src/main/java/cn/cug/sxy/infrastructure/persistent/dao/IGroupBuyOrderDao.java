package cn.cug.sxy.infrastructure.persistent.dao;

import cn.cug.sxy.infrastructure.persistent.dao.po.GroupBuyOrder;
import org.apache.ibatis.annotations.Mapper;

/**
 * @version 1.0
 * @Date 2025/3/31 19:28
 * @Description 拼团订单 Dao
 * @Author jerryhotton
 */

@Mapper
public interface IGroupBuyOrderDao {

    GroupBuyOrder queryGroupBuyOrderByTeamId(String teamId);

    void insertGroupBuyOrder(GroupBuyOrder groupBuyOrder);

    int updateAddLockCount(String teamId);

    int updateAddCompleteCount(String teamId);

    int updateGroupBuyOrderStatus(GroupBuyOrder groupBuyOrder);

}
