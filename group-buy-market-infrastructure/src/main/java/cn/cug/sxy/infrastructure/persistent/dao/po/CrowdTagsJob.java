package cn.cug.sxy.infrastructure.persistent.dao.po;

import lombok.Data;

import java.util.Date;

/**
 * @version 1.0
 * @Date 2025/3/30 10:22
 * @Description 人群标签构建任务
 * @Author jerryhotton
 */

@Data
public class CrowdTagsJob {

    /**
     * 自增ID
     */
    private Long id;
    /**
     * 标签ID
     */
    private String tagId;
    /**
     * 批次ID
     */
    private String batchId;
    /**
     * 标签类型（参与量、消费金额）
     */
    private Integer tagType;
    /**
     * 标签规则（限定类型 N次）
     */
    private String tagRule;
    /**
     * 统计数据，开始时间
     */
    private Date statStartTime;
    /**
     * 统计数据，结束时间
     */
    private Date statEndTime;
    /**
     * 状态；0初始、1计划（进入执行阶段）、2重置、3完成
     */
    private Integer status;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;


}
