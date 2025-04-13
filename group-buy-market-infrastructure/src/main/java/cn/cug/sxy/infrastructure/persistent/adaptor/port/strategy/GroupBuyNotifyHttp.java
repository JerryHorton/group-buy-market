package cn.cug.sxy.infrastructure.persistent.adaptor.port.strategy;

import cn.cug.sxy.infrastructure.persistent.gateway.GroupBuyNotifyService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @version 1.0
 * @Date 2025/4/13 17:05
 * @Description HTTP方式进行拼团回调
 * @Author jerryhotton
 */

@Component("GroupBuyNotify_HTTP")
public class GroupBuyNotifyHttp implements INotifyStrategy {

    @Resource
    private GroupBuyNotifyService groupBuyNotifyService;

    @Override
    public String notify(String target, String parameterJson) {
        return groupBuyNotifyService.groupBuyNotify(target, parameterJson);
    }

}
