package cn.cug.sxy.domain.trade.model.entity;

import cn.cug.sxy.domain.trade.model.valobj.GroupBuyActivityStatusVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @version 1.0
 * @Date 2025/4/1 23:52
 * @Description 拼团活动 实体
 * @Author jerryhotton
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GroupBuyActivityEntity {

    /**
     * 活动ID
     */
    private Long activityId;
    /**
     * 活动名称
     */
    private String activityName;
    /**
     * 折扣ID
     */
    private String discountId;
    /**
     * 拼团方式（0:自动成团、1:达成目标拼团）
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
     * 活动状态（0:创建、1:生效、2:过期、3:废弃）
     */
    private GroupBuyActivityStatusVO status;
    /**
     * 活动开始时间
     */
    private Date startTime;
    /**
     * 活动结束时间
     */
    private Date endTime;

}
