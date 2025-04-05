package cn.cug.sxy.infrastructure.persistent.dao;

import cn.cug.sxy.infrastructure.persistent.dao.po.CrowdTagsDetail;
import org.apache.ibatis.annotations.Mapper;

/**
 * @version 1.0
 * @Date 2025/3/30 10:24
 * @Description 人群标签明细 Dap
 * @Author jerryhotton
 */

@Mapper
public interface ICrowdTagsDetailDao {

    void insertCrowdTagsDetail(CrowdTagsDetail crowdTagsDetail);

}
