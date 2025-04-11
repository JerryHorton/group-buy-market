package cn.cug.sxy.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @version 1.0
 * @Date 2025/3/31 21:45
 * @Description 营销支付锁单应答对象
 * @Author jerryhotton
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LockMarketPayOrderResponseDTO {

    /**
     * 预购订单ID
     */
    private String orderId;
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
     * 交易订单状态
     */
    private Integer tradeOrderStatus;

}
