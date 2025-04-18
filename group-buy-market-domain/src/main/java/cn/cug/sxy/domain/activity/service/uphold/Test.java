package cn.cug.sxy.domain.activity.service.uphold;

import cn.cug.sxy.domain.activity.repository.IActivityRepository;
import cn.cug.sxy.domain.trade.model.entity.GroupBuyActivityEntity;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @version 1.0
 * @Date 2025/4/17 13:31
 * @Description
 * @Author jerryhotton
 */

@Component
public class Test {

    @Resource
    private IActivityRepository repository;

    public void updateActivityConfig() {
        GroupBuyActivityEntity groupBuyActivityEntity = GroupBuyActivityEntity.builder().activityId(20001L)
                .takeLimitCount(25)
                .build();
        repository.updateActivityConfig(groupBuyActivityEntity);
    }

}
