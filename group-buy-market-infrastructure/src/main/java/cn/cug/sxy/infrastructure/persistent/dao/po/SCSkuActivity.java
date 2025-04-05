package cn.cug.sxy.infrastructure.persistent.dao.po;

import lombok.Data;

import java.util.Date;

/**
 * @version 1.0
 * @Date 2025/3/30 14:01
 * @Description 渠道商品活动配置
 * @Author jerryhotton
 */

@Data
public class SCSkuActivity {

    /**
     * 自增ID
     */
    private Long id;
    /**
     * 渠道
     */
    private String source;
    /**
     * 来源
     */
    private String channel;
    /**
     * 活动ID
     */
    private Long activityId;
    /**
     * 商品ID
     */
    private String goodsId;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;

}
