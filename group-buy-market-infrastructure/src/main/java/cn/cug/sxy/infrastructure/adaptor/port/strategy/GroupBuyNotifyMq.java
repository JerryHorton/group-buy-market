package cn.cug.sxy.infrastructure.adaptor.port.strategy;

import cn.cug.sxy.domain.trade.adaptor.event.TeamSuccessEvent;
import cn.cug.sxy.infrastructure.event.GroupBuyEventPublisher;
import cn.cug.sxy.types.enums.NotifyTaskStatus;
import cn.cug.sxy.types.event.BaseEvent;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @version 1.0
 * @Date 2025/4/13 17:05
 * @Description MQ方式进行拼团回调
 * @Author jerryhotton
 */

@Component("GroupBuyNotify_MQ")
public class GroupBuyNotifyMq implements INotifyStrategy {

    @Resource
    private GroupBuyEventPublisher eventPublisher;

    @Resource
    private TeamSuccessEvent teamSuccessEvent;

    @Override
    public String notify(String target, String parameterJson) {
        BaseEvent.EventMessage<TeamSuccessEvent.TeamSuccessMessage> teamSuccessMessage = teamSuccessEvent.buildEventMessage(TeamSuccessEvent.TeamSuccessMessage.builder()
                .parameterJson(parameterJson)
                .build());
        eventPublisher.publish(target, teamSuccessMessage);
        return NotifyTaskStatus.SUCCESS.getCode();
    }

}
