package cn.cug.sxy.infrastructure.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @version 1.0
 * @Date 2025/4/10 13:44
 * @Description 消息发送
 * @Author jerryhotton
 */

@Slf4j
@Component
public class GroupBuyAbstractEventPublisher extends AbstractEventPublisher {

    public GroupBuyAbstractEventPublisher(RabbitTemplate rabbitTemplate) {
        super(rabbitTemplate);
    }

    @Value("${spring.rabbitmq.config.producer.exchange}")
    private String exchange;

    @Override
    protected void doPublish(String topic, String message) {
        rabbitTemplate.convertAndSend(exchange, topic, message, msg -> {
            // 持久化消息配置
            msg.getMessageProperties().setDeliveryMode(MessageDeliveryMode.PERSISTENT);
            return msg;
        });
    }
}
