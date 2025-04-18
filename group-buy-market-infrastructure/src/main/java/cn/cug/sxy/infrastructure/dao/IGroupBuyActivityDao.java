package cn.cug.sxy.infrastructure.dao;

import cn.cug.sxy.infrastructure.dao.po.GroupBuyActivity;
import cn.cug.sxy.types.annotation.CacheRefresh;
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

    @CacheRefresh
    void updateActivity(GroupBuyActivity groupBuyActivityReq);

}
