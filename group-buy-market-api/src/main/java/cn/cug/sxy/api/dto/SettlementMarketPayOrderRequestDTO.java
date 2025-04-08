package cn.cug.sxy.api.dto;

import lombok.Data;

import java.util.Date;

/**
 * @version 1.0
 * @Date 2025/4/7 10:47
 * @Description 营销订单结算 请求对象
 * @Author jerryhotton
 */

@Data
public class SettlementMarketPayOrderRequestDTO {

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
