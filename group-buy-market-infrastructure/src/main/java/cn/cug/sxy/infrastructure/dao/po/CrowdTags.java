package cn.cug.sxy.infrastructure.dao.po;

import lombok.Data;

import java.util.Date;

/**
 * @version 1.0
 * @Date 2025/3/30 10:20
 * @Description 人群标签
 * @Author jerryhotton
 */

@Data
public class CrowdTags {

    /**
     * 自增ID
     */
    private Long id;
    /**
     * 人群ID
     */
    private String tagId;
    /**
     * 人群名称
     */
    private String tagName;
    /**
     * 人群描述
     */
    private String tagDesc;
    /**
     * 人群标签统计量
     */
    private Integer statistics;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;

}
