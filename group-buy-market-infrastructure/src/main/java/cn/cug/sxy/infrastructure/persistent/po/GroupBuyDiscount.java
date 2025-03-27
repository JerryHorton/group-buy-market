package cn.cug.sxy.infrastructure.persistent.po;

import lombok.Data;

import java.util.Date;

/**
 * @version 1.0
 * @Date 2025/3/27 14:41
 * @Description 折扣配置
 * @Author jerryhotton
 */

@Data
public class GroupBuyDiscount {

    /**
     * 自增ID
     */
    private Long id;
    /**
     * 折扣ID
     */
    private Integer discountId;
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
    private Byte discountType;
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
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;


}
