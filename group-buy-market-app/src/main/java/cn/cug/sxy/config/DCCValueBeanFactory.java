package cn.cug.sxy.config;

import cn.cug.sxy.types.annotation.DCCValue;
import cn.cug.sxy.types.common.Constants;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RBucket;
import org.redisson.api.RTopic;
import org.redisson.api.RedissonClient;
import org.redisson.api.listener.MessageListener;
import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * @version 1.0
 * @Date 2025/3/31 10:37
 * @Description 基于 Redis 实现动态配置中心
 * @Author jerryhotton
 */

@Slf4j
@Configuration
public class DCCValueBeanFactory implements BeanPostProcessor {

    private static final String BASE_CONFIG_PATH = "group_buy_market_dcc_";

    private final Map<String, Object> dccObject = new HashMap<>();

    private final RedissonClient redissonClient;

    public DCCValueBeanFactory(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }

    @Bean("dccRedisTopic")
    public RTopic dccRedisTopicListener(RedissonClient redissonClient) {
        RTopic topic = redissonClient.getTopic("group_buy_market_dcc");
        topic.addListener(String.class, new MessageListener<String>() {
            @Override
            public void onMessage(CharSequence charSequence, String message) {
                String[] parts = message.split(Constants.COLON);

                String attribute = parts[0];
                String key = BASE_CONFIG_PATH + attribute;
                String value = parts[1];

                RBucket<Object> bucket = redissonClient.getBucket(key);
                boolean exists = bucket.isExists();
                if (!exists) return;
                bucket.set(value);
                Object objBean = dccObject.get(key);
                if (objBean == null) return;
                Class<?> objBeanClass = objBean.getClass();

                try {
                    Field field = objBeanClass.getDeclaredField(attribute);
                    field.setAccessible(true);
                    field.set(objBean, value);
                    field.setAccessible(false);
                    log.info("DCC 节点监听，动态设置值 {}:{}", key, value);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });

        return topic;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Class<?> targetBeanClass = bean.getClass();
        Object targetBeanObject = bean;

        if (AopUtils.isAopProxy(bean)) {
            targetBeanClass = AopUtils.getTargetClass(bean);
            targetBeanObject = AopProxyUtils.getSingletonTarget(bean);
        }

        Field[] fields = targetBeanClass.getDeclaredFields();

        for (Field field : fields) {
            if (!field.isAnnotationPresent(DCCValue.class)) {
                continue;
            }
            DCCValue dccValue = field.getAnnotation(DCCValue.class);
            String vale = dccValue.value();
            if (StringUtils.isBlank(vale)) {
                throw new RuntimeException(field.getName() + " @DCCValue is not config value config case 「degradeSwitch:1」");
            }

            String[] parts = vale.split(Constants.COLON);
            String key = BASE_CONFIG_PATH + parts[0];
            String defaultValue = parts.length == 2 ? parts[1] : null;
            if (StringUtils.isBlank(defaultValue)) {
                throw new RuntimeException("dcc config error " + key + " is not null - 请配置默认值!");
            }
            String setValue = defaultValue;

            try {
                RBucket<String> bucket = redissonClient.getBucket(key);
                boolean exists = bucket.isExists();
                if (!exists) {
                    bucket.set(defaultValue);
                } else {
                    setValue = bucket.get();
                }
                field.setAccessible(true);
                field.set(targetBeanObject, setValue);
                field.setAccessible(false);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            dccObject.put(key, targetBeanObject);
        }

        return bean;
    }
}
