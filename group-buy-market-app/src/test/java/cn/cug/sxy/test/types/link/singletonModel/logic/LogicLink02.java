package cn.cug.sxy.test.types.link.singletonModel.logic;

import cn.cug.sxy.test.types.link.singletonModel.factory.DefaultSingletonLogicLinkFactory;
import cn.cug.sxy.types.design.framework.link.singletonModel.AbstractLogicLink;
import org.springframework.stereotype.Service;

/**
 * @version 1.0
 * @Date 2025/4/1 16:13
 * @Description
 * @Author jerryhotton
 */

@Service
public class LogicLink02 extends AbstractLogicLink<String, DefaultSingletonLogicLinkFactory.DynamicContext, String> {

    @Override
    public String apply(String requestParameter, DefaultSingletonLogicLinkFactory.DynamicContext dynamicContext) {
        dynamicContext.setCode("002");
        dynamicContext.setInfo("002");
        return "success";
    }

}
