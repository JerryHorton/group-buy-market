package cn.cug.sxy.test.types.link.multitonModel.logic;

import cn.cug.sxy.test.types.link.multitonModel.factory.DefaultMultitonLogicLinkFactory;
import cn.cug.sxy.types.design.framework.link.multitonModel.handler.ILogicHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @version 1.0
 * @Date 2025/4/1 21:16
 * @Description
 * @Author jerryhotton
 */

@Slf4j
@Service
public class LogicHandler02 implements ILogicHandler<String, DefaultMultitonLogicLinkFactory.DynamicContext, String> {

    @Override
    public String apply(String requestParameter, DefaultMultitonLogicLinkFactory.DynamicContext dynamicContext) throws Exception {
        log.info("handler02");
        return "success";
    }

}
