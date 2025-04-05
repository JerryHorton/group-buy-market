package cn.cug.sxy.infrastructure.persistent.dao;

import cn.cug.sxy.infrastructure.persistent.dao.po.GroupBuyActivity;
import org.apache.ibatis.annotations.Mapper;

/**
 * @version 1.0
 * @Date 2025/3/27 14:43
 * @Description 拼团活动 Dao
 * @Author jerryhotton
 */

@Mapper
public interface IGroupBuyActivityDao {

    GroupBuyActivity queryGroupBuyActivityByActivityId(Long activityId);

}
