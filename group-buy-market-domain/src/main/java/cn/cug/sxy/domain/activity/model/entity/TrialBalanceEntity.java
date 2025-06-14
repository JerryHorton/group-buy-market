package cn.cug.sxy.domain.activity.model.entity;

import cn.cug.sxy.domain.activity.model.valobj.GroupBuyActivityVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @version 1.0
 * @Date 2025/3/27 16:11
 * @Description 试算结果 实体（给用户展示拼团可获得的优惠信息）
 * @Author jerryhotton
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TrialBalanceEntity {

    /**
     * 商品ID
     */
    private String goodsId;
    /**
     * 商品名称
     */
    private String goodsName;
    /**
     * 原始价格
     */
    private BigDecimal originalPrice;
    /**
     * 折扣扣除金额
     */
    private BigDecimal discountDeduction;
    /**
     * 最终价格
     */
    private BigDecimal finalPrice;
    /**
     * 拼团目标数量
     */
    private Integer targetCount;
    /**
     * 拼团开始时间
     */
    private Date startTime;
    /**
     * 拼团结束时间
     */
    private Date endTime;
    /**
     * 是否可见拼团
     */
    private Boolean isVisible;
    /**
     * 是否可参与拼团
     */
    private Boolean isEnable;
    /**
     * 拼团活动值对象
     */
    private GroupBuyActivityVO groupBuyActivityVO;

}
