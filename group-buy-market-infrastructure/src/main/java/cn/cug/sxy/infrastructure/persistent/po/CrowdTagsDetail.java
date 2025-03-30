package cn.cug.sxy.infrastructure.persistent.po;

import lombok.Data;

import java.util.Date;

/**
 * @version 1.0
 * @Date 2025/3/30 10:21
 * @Description 人群标签明细
 * @Author jerryhotton
 */

@Data
public class CrowdTagsDetail {

    /**
     * 自增ID
     */
    private Long id;
    /**
     * 人群ID
     */
    private String tagId;
    /**
     * 用户ID
     */
    private String userId;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;

}
