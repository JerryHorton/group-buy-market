package cn.cug.sxy.domain.trade.model.entity;

import cn.cug.sxy.domain.trade.model.valobj.GroupBuyOrderStatusVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @version 1.0
 * @Date 2025/4/3 14:13
 * @Description 拼团 实体
 * @Author Sxy
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GroupBuyTeamEntity {

    /**
     * 拼单组队ID
     */
    private String teamId;
    /**
     * 活动ID
     */
    private Long activityId;
    /**
     * 目标参团数量
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
     * 状态（0-拼单中、1-完成、2-失败）
     */
    private GroupBuyOrderStatusVO status;
    /**
     * 拼团开始时间
     */
    private Date validStartTime;
    /**
     * 拼团结束时间
     */
    private Date validEndTime;

}
