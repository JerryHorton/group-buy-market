package cn.cug.sxy.test.types.link.singletonModel.factory;

import cn.cug.sxy.test.types.link.singletonModel.logic.LogicLink01;
import cn.cug.sxy.test.types.link.singletonModel.logic.LogicLink02;
import cn.cug.sxy.types.design.framework.link.singletonModel.ILogicLink;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @version 1.0
 * @Date 2025/4/1 16:06
 * @Description
 * @Author jerryhotton
 */

@Service
public class DefaultSingletonLogicLinkFactory {

    @Resource
    private LogicLink01 logicLink01;

    @Resource
    private LogicLink02 logicLink02;

    public ILogicLink<String, DefaultSingletonLogicLinkFactory.DynamicContext, String> openLogicLink() {
        logicLink01.appendNext(logicLink02);
        return logicLink01;
    }


    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class DynamicContext {

        private String code;
        private String info;

    }

}
