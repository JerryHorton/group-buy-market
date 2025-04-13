package cn.cug.sxy.types.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @version 1.0
 * @Date 2025/4/13 16:52
 * @Description 回调类型
 * @Author jerryhotton
 */

@Getter
@AllArgsConstructor
public enum NotifyType {

    HTTP("HTTP", "HTTP回调方式"),
    MQ("MQ", "MQ回调方式"),

    ;

    private final String code;
    private final String info;

}
