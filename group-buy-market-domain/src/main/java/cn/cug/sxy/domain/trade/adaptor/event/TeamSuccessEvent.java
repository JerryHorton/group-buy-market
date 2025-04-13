package cn.cug.sxy.domain.trade.adaptor.event;

import cn.cug.sxy.types.event.BaseEvent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @version 1.0
 * @Date 2025/4/13 20:32
 * @Description 拼团完成 事件
 * @Author jerryhotton
 */

@Component
public class TeamSuccessEvent extends BaseEvent<TeamSuccessEvent.TeamSuccessMessage> {

    @Value("${spring.rabbitmq.topic.team_success}")
    private String topic;

    @Override
    public EventMessage<TeamSuccessMessage> buildEventMessage(TeamSuccessMessage data) {
        return EventMessage.<TeamSuccessMessage>builder()
                .id(RandomStringUtils.randomNumeric(16))
                .timestamp(new Date())
                .data(data)
                .build();
    }

    @Override
    protected String topic() {
        return topic;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class TeamSuccessMessage {

        private String parameterJson;

    }

}
