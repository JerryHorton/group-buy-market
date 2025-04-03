package cn.cug.sxy.domain.trade.model.valobj;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @version 1.0
 * @Date 2025/4/3 14:28
 * @Description 拼团状态
 * @Author Sxy
 */

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum GroupBuyOrderStatusVO {

    PENDING(0, "拼单中"),
    SUCCESS(1, "完成"),
    FAILED(2, "失败"),
    ;

    private Integer code;
    private String info;

    public static GroupBuyOrderStatusVO valueOf(Integer code) {
        switch (code) {
            case 0:
                return PENDING;
            case 1:
                return SUCCESS;
            case 2:
                return FAILED;
            default:
                throw new RuntimeException("error GroupBuyTeamStatus code:" + code);
        }
    }

}
