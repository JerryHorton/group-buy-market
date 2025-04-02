package cn.cug.sxy.domain.trade.model.valobj;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @version 1.0
 * @Date 2025/4/2 09:13
 * @Description 拼团活动状态枚举
 * @Author jerryhotton
 */

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum GroupBuyActivityStatusVO {

    CREATE(0, "创建"),
    ACTIVE(1, "生效"),
    EXPIRED(2, "过期"),
    DISABLED(3, "废弃");

    private Integer code;
    private String info;

    public static GroupBuyActivityStatusVO valueOf(Integer code) {
        switch (code) {
            case 0:
                return CREATE;
            case 1:
                return ACTIVE;
            case 2:
                return EXPIRED;
            case 3:
                return DISABLED;
            default:
                throw new RuntimeException("error GroupBuyActivityStatus code:" + code);
        }
    }

}
