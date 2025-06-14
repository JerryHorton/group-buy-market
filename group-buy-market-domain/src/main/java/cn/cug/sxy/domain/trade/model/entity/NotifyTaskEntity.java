package cn.cug.sxy.domain.trade.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @version 1.0
 * @Date 2025/4/5 15:06
 * @Description 回调任务实体
 * @Author Sxy
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NotifyTaskEntity {

    /**
     * 拼单组队ID
     */
    private String teamId;
    /**
     * 回调类型（HTTP、MQ）
     */
    private String notifyType;
    /**
     * 回调目标（url or topic）
     */
    private String notifyTarget;
    /**
     * 回调次数
     */
    private Integer notifyCount;
    /**
     * 参数对象
     */
    private String parameterJson;

    public String lockKey() {
        return "notify_job_lock_key_" + this.teamId;
    }

    public String doneKey() {
        return "notify_job_done_key_" + this.teamId;
    }

}
