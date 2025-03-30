package cn.cug.sxy.test.domain.tag;

import cn.cug.sxy.domain.tag.service.ITagService;
import cn.cug.sxy.infrastructure.persistent.redis.IRedisService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.redisson.api.RBitSet;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @version 1.0
 * @Date 2025/3/30 11:33
 * @Description 单元测试
 * @Author jerryhotton
 */

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class execTagBatchJobTest {

    @Resource
    private ITagService tagService;

    @Resource
    private IRedisService redisService;

    @Test
    public void test_execTagBatchJob() {
        tagService.execTagBatchJob("RQ_KJHKL98UU78H66554GFDV", "0001");
    }

    @Test
    public void test_get_tag_bitmap() {
        RBitSet bitSet = redisService.getBitSet("RQ_KJHKL98UU78H66554GFDV");
        log.info("测试结果:{}", bitSet.get(redisService.getIndexFromUserId("user001")));
        log.info("测试结果:{}", bitSet.get(redisService.getIndexFromUserId("user003")));
    }

}
