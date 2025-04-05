package cn.cug.sxy.infrastructure.persistent.dao;

import cn.cug.sxy.infrastructure.persistent.dao.po.ActivityTagsConfig;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @version 1.0
 * @Date 2025/3/30 19:37
 * @Description 活动人群标签配置 Dap
 * @Author jerryhotton
 */

@Mapper
public interface IActivityTagConfigDao {

    List<ActivityTagsConfig> queryActivityTagConfig(Long activityId);

}
