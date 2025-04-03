package cn.cug.sxy.domain.trade.model.entity;

import cn.cug.sxy.domain.trade.model.valobj.TradeOrderStatusVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @version 1.0
 * @Date 2025/3/31 20:03
 * @Description 拼团预购单 实体
 * @Author jerryhotton
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MarketPayOrderEntity {

    /**
     * 预购订单ID
     */
    private String orderId;
    /**
     * 拼团ID
     */
    private String teamId;
    /**
     * 原始价格
     */
    private BigDecimal originalPrice;
    /**
     * 支付价格
     */
    private BigDecimal payPrice;
    /**
     * 折扣扣除的金额
     */
    private BigDecimal discountDeduction;
    /**
     * 交易订单状态枚举
     */
    private TradeOrderStatusVO tradeOrderStatusVO;

}
