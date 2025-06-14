package cn.cug.sxy.domain.activity.model.valobj;

import lombok.*;

import java.util.Date;
import java.util.Map;

/**
 * @version 1.0
 * @Date 2025/3/28 17:05
 * @Description 拼团活动营销配置值对象
 * @Author jerryhotton
 */

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GroupBuyActivityVO {

    /**
     * 活动ID
     */
    private Long activityId;
    /**
     * 活动名称
     */
    private String activityName;
    /**
     * 来源
     */
    private String source;
    /**
     * 渠道
     */
    private String channel;
    /**
     * 商品ID
     */
    private String goodsId;
    /**
     * 折扣配置
     */
    private GroupBuyDiscount groupBuyDiscount;
    /**
     * 拼团方式（0自动成团、1达成目标拼团）
     */
    private Integer groupType;
    /**
     * 拼团次数限制
     */
    private Integer takeLimitCount;
    /**
     * 拼团目标人数
     */
    private Integer targetCount;
    /**
     * 拼团时长（分钟）
     */
    private Integer validTime;
    /**
     * 活动状态（0创建、1生效、2过期、3废弃）
     */
    private Integer status;
    /**
     * 活动开始时间
     */
    private Date startTime;
    /**
     * 活动结束时间
     */
    private Date endTime;
    /**
     * 人群标签配置
     */
    private Map<String, String> tagsConfig;

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class GroupBuyDiscount {

        /**
         * 折扣标题
         */
        private String discountName;
        /**
         * 折扣描述
         */
        private String discountDesc;
        /**
         * 折扣类型（0:base、1:tag）
         */
        private DiscountTypeVO discountType;
        /**
         * 营销优惠计划（ZJ:直减、MJ:满减、N元购）
         */
        private String marketPlan;
        /**
         * 营销优惠表达式
         */
        private String marketExpr;
        /**
         * 人群标签，特定优惠限定
         */
        private String tagId;

    }

}
