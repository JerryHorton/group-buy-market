package cn.cug.sxy.domain.trade.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @version 1.0
 * @Date 2025/4/1 23:22
 * @Description 拼团结算命令 实体
 * @Author jerryhotton
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TradeSettlementRuleCommandEntity {

    /**
     * 渠道
     */
    private String source;
    /**
     * 来源
     */
    private String channel;
    /**
     * 用户ID
     */
    private String userId;
    /**
     * 外部交易单号
     */
    private String outTradeNo;
    /**
     * 外部交易时间
     */
    private Date outTradeTime;

}
