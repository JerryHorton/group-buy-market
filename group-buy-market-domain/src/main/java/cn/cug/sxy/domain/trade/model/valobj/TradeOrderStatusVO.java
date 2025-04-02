package cn.cug.sxy.domain.trade.model.valobj;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @version 1.0
 * @Date 2025/3/31 20:06
 * @Description 交易订单状态枚举
 * @Author jerryhotton
 */

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum TradeOrderStatusVO {

    CREATE(0, "初始创建"),
    COMPLETE(1, "消费完成"),
    CLOSE(2, "超时关单"),
    ;

    private Integer code;
    private String info;

    public static TradeOrderStatusVO valueOf(Integer code) {
        switch (code) {
            case 0:
                return CREATE;
            case 1:
                return COMPLETE;
            case 2:
                return CLOSE;
            default:
                throw new RuntimeException("error TradeOrderStatus code:" + code);
        }
    }

}
