package cn.cug.sxy.test.types.link.multitonModel;

import cn.cug.sxy.test.types.link.multitonModel.factory.DefaultMultitonLogicLinkFactory;
import cn.cug.sxy.types.design.framework.link.multitonModel.chain.BusinessLinkedList;
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
public class MultitonModelTest {

    @Resource
    private DefaultMultitonLogicLinkFactory logicLinkFactory;

    @Test
    public void test_logicLink() throws Exception {
        BusinessLinkedList<String, DefaultMultitonLogicLinkFactory.DynamicContext, String> businessLinkedList = logicLinkFactory.openLogicLink("link01");
        String res = businessLinkedList.apply("001", new DefaultMultitonLogicLinkFactory.DynamicContext());
        log.info("测试成功 res:{}", res);
    }

}
