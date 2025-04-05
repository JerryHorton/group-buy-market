package cn.cug.sxy.infrastructure.persistent.dao;

import cn.cug.sxy.infrastructure.persistent.dao.po.CrowdTagsJob;
import org.apache.ibatis.annotations.Mapper;

/**
 * @version 1.0
 * @Date 2025/3/30 10:23
 * @Description 人群标签构建任务 Dao
 * @Author jerryhotton
 */

@Mapper
public interface ICrowdTagsJobDao {

    CrowdTagsJob queryCrowdTagsJob(CrowdTagsJob crowdTagsJobReq);

}
