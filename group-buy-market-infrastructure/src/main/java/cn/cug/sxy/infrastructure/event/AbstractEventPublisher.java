package cn.cug.sxy.infrastructure.event;

import cn.cug.sxy.types.event.BaseEvent;
import com.alibaba.fastjson2.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

/**
 * @version 1.0
 * @Date 2025/4/10 13:44
 * @Description 消息发送者 抽象类
 * @Author jerryhotton
 */

@Slf4j
@Component
public abstract class AbstractEventPublisher {

    protected final RabbitTemplate rabbitTemplate;

    public AbstractEventPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void publish(String topic, BaseEvent.EventMessage<?> eventMessage) {
        try {
            String messageJson = JSON.toJSONString(eventMessage);
            this.doPublish(topic, messageJson);
            log.info("发送MQ消息 topic:{} message:{}", topic, messageJson);
        } catch (Exception e) {
            log.error("发送MQ消息失败 topic:{} message:{}", topic, JSON.toJSONString(eventMessage), e);
            throw e;
        }
    }

    public void publish(String topic, String message) {
        try {
            this.doPublish(topic, message);
            log.info("发送MQ消息 topic:{} message:{}", topic, message);
        } catch (Exception e) {
            log.error("发送MQ消息失败 topic:{} message:{}", topic, JSON.toJSONString(message), e);
            throw e;
        }
    }

    protected abstract void doPublish(String topic, String message);

}
