package cn.cug.sxy.domain.activity.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @version 1.0
 * @Date 2025/4/7 13:31
 * @Description 用户拼团订单明细
 * @Author jerryhotton
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserGroupBuyOrderDetailEntity {

    /**
     * 用户ID
     */
    private String userId;
    /**
     * 拼单组队ID
     */
    private String teamId;
    /**
     * 活动ID
     */
    private Long activityId;
    /**
     * 目标数量
     */
    private Integer targetCount;
    /**
     * 完成数量
     */
    private Integer completeCount;
    /**
     * 锁单数量
     */
    private Integer lockCount;
    /**
     * 拼团开始时间 - 参与拼团时间
     */
    private Date validStartTime;
    /**
     * 拼团结束时间 - 拼团有效时长
     */
    private Date validEndTime;
    /**
     * 外部交易单号-确保外部调用唯一幂等
     */
    private String outTradeNo;

}
