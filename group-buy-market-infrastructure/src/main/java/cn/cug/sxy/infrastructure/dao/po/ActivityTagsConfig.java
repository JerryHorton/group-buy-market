package cn.cug.sxy.infrastructure.dao.po;

import lombok.Data;

import java.util.Date;

/**
 * @version 1.0
 * @Date 2025/3/30 19:36
 * @Description 活动人群标签配置
 * @Author jerryhotton
 */

@Data
public class ActivityTagsConfig {

    /**
     * 自增ID
     */
    private Long id;
    /**
     * 活动ID
     */
    private Long activityId;
    /**
     * 人群标签规则标识
     */
    private String tagId;
    /**
     * 人群标签规则范围（多选；1可见限制、2参与限制）
     */
    private String tagScope;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;

}
