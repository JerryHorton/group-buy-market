package cn.cug.sxy.infrastructure.persistent.dao.po;

import lombok.Data;

import java.util.Date;

/**
 * @version 1.0
 * @Date 2025/4/3 11:38
 * @Description 通知回调任务
 * @Author Sxy
 */

@Data
public class NotifyTask {

    /**
     * 自增ID
     */
    private Long id;
    /**
     * 活动ID
     */
    private Long activityId;
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
     * 回调状态【0初始、1完成、2重试、3失败】
     */
    private Integer notifyStatus;
    /**
     * 参数对象
     */
    private String parameterJson;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;

}
