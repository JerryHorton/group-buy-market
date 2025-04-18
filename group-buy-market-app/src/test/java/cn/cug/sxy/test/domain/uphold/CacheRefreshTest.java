package cn.cug.sxy.test.domain.uphold;

import cn.cug.sxy.domain.activity.service.uphold.Test;
import cn.cug.sxy.domain.trade.model.entity.GroupBuyActivityEntity;
import cn.cug.sxy.infrastructure.redis.IRedisService;
import cn.cug.sxy.types.common.Constants;
import lombok.extern.slf4j.Slf4j;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * @version 1.0
 * @Date 2025/4/17 14:48
 * @Description
 * @Author jerryhotton
 */

@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class CacheRefreshTest {

    @Resource
    private Test test;

    @Resource
    private IRedisService redisService;

    @org.junit.Test
    public void test() {
        String cacheKey = "cacheKey" + Constants.UNDERLINE + GroupBuyActivityEntity.class.getName();
        redisService.setValue(cacheKey, "111");
        test.updateActivityConfig();
        log.info("测试成功");
    }

}
