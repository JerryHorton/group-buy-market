package cn.cug.sxy.test.types.link.multitonModel.factory;

import cn.cug.sxy.test.types.link.multitonModel.logic.LogicHandler01;
import cn.cug.sxy.test.types.link.multitonModel.logic.LogicHandler02;
import cn.cug.sxy.types.design.framework.link.multitonModel.LinkArmory;
import cn.cug.sxy.types.design.framework.link.multitonModel.chain.BusinessLinkedList;
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
public class DefaultMultitonLogicLinkFactory {

    @Resource
    private LogicHandler01 logicHandler01;

    @Resource
    private LogicHandler02 logicHandler02;

    public BusinessLinkedList<String, DynamicContext, String> openLogicLink(String linkName) {
        return new LinkArmory<String, DynamicContext, String>(linkName)
                .addHandler(logicHandler01)
                .addHandler(logicHandler02)
                .getLogicLink();
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
