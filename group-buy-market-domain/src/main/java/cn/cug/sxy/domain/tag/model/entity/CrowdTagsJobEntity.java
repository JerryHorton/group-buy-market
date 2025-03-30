package cn.cug.sxy.domain.tag.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @version 1.0
 * @Date 2025/3/30 10:52
 * @Description 人群标签任务 实体
 * @Author jerryhotton
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CrowdTagsJobEntity {

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

}
