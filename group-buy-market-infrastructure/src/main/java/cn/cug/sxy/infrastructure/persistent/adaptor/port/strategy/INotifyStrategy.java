package cn.cug.sxy.infrastructure.persistent.adaptor.port.strategy;

import cn.cug.sxy.types.enums.NotifyType;

/**
 * @version 1.0
 * @Date 2025/4/13 16:52
 * @Description 拼团回调策略接口
 * @Author jerryhotton
 */

public interface INotifyStrategy {

    String notify(String target, String parameterJson);

}
