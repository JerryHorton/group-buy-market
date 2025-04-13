package cn.cug.sxy.types.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @version 1.0
 * @Date 2025/4/5 15:24
 * @Description 回调任务状态
 * @Author Sxy
 */

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum NotifyTaskStatus {

    SUCCESS("success", "成功"),
    ERROR("error", "失败"),
    NULL(null, "空执行"),
    ;

    private String code;
    private String info;

}
