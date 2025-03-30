package cn.cug.sxy.domain.tag.repository;

import cn.cug.sxy.domain.tag.model.entity.CrowdTagsJobEntity;

/**
 * @version 1.0
 * @Date 2025/3/30 10:48
 * @Description 人群标签 仓储接口
 * @Author jerryhotton
 */

public interface ITagRepository {

    CrowdTagsJobEntity queryCrowdTagsJobEntity(String tagId, String batchId);

    void addCrowdTagsDetail(String tagId, String userId);

    void updateCrowdTagsStatistics(String tagId, int size);

}
