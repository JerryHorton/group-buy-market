package cn.cug.sxy.domain.activity.model.valobj;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @version 1.0
 * @Date 2025/3/29 13:47
 * @Description 折扣优惠类型 枚举
 * @Author jerryhotton
 */

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum DiscountTypeVO {

    BASE(0, "基础优惠"),
    TAG(1, "人群标签优惠"),
    ;

    private Integer code;
    private String info;

    public static DiscountTypeVO get(Integer code) {
        switch (code) {
            case 0:
                return BASE;
            case 1:
                return TAG;
            default:
                throw new RuntimeException("error code:" + code);
        }
    }

}
