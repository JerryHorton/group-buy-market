package cn.cug.sxy.test.types.link.singletonModel;

import cn.cug.sxy.test.types.link.singletonModel.factory.DefaultSingletonLogicLinkFactory;
import cn.cug.sxy.types.design.framework.link.singletonModel.ILogicLink;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @version 1.0
 * @Date 2025/4/1 16:19
 * @Description
 * @Author jerryhotton
 */

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class SingletonModelTest {

    @Resource
    private DefaultSingletonLogicLinkFactory logicLinkFactory;

    @Test
    public void test_logicLink() {
        ILogicLink<String, DefaultSingletonLogicLinkFactory.DynamicContext, String> logicLink = logicLinkFactory.openLogicLink();
        String res = logicLink.apply("11", new DefaultSingletonLogicLinkFactory.DynamicContext());
        log.info("测试成功 res:{}", res);
    }

}
