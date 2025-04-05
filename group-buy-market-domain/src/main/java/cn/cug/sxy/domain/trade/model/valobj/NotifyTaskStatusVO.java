package cn.cug.sxy.domain.trade.model.valobj;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @version 1.0
 * @Date 2025/4/3 14:59
 * @Description 回调任务状态
 * @Author Sxy
 */

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum NotifyTaskStatusVO {

    INIT(0, "初始"),
    SUCCESS(1, "完成"),
    RETRY(2, "重试"),
    ERROR(3, "失败"),
    ;

    private Integer code;
    private String info;

    public static NotifyTaskStatusVO valueOf(Integer code) {
        switch (code) {
            case 0:
                return INIT;
            case 1:
                return SUCCESS;
            case 2:
                return RETRY;
            case 3:
                return ERROR;
            default:
                throw new RuntimeException("error NotifyTaskStatus code:" + code);
        }
    }

}
