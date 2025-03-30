package cn.cug.sxy.domain.tag.service;

/**
 * @version 1.0
 * @Date 2025/3/30 10:32
 * @Description 人群标签 服务接口
 * @Author jerryhotton
 */

public interface ITagService {

    /**
     * 执行人群标签批次任务
     *
     * @param tagId 人群ID
     * @param batchId 批次ID
     */
    void execTagBatchJob(String tagId, String batchId);

}
