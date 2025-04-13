package cn.cug.sxy.test;

import cn.cug.sxy.infrastructure.event.GroupBuyAbstractEventPublisher;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.concurrent.CountDownLatch;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class ApiTest {

    @Resource
    private GroupBuyAbstractEventPublisher publisher;

    @Value("${spring.rabbitmq.config.producer.topic_team_success.routing_key}")
    private String routingKey;

    @Test
    public void test() throws InterruptedException {
        publisher.publish(routingKey, "订单结算：ORD-20231234");
        publisher.publish(routingKey, "订单结算：ORD-20231235");
        publisher.publish(routingKey, "订单结算：ORD-20231236");
        publisher.publish(routingKey, "订单结算：ORD-20231237");
        publisher.publish(routingKey, "订单结算：ORD-20231238");

        new CountDownLatch(1).await();
    }

}
