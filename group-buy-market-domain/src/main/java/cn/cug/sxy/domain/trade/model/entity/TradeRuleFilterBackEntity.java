package cn.cug.sxy.domain.trade.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @version 1.0
 * @Date 2025/4/1 23:23
 * @Description 拼团交易过滤结果反馈 实体
 * @Author jerryhotton
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TradeRuleFilterBackEntity {

    /**
     * 用户参与活动的订单量
     */
    private Integer userParticipationCount;

}
