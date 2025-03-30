package cn.cug.sxy.domain.activity.service.trial.thread;

import cn.cug.sxy.domain.activity.model.valobj.GroupBuyActivityVO;
import cn.cug.sxy.domain.activity.repository.IActivityRepository;

import java.util.concurrent.Callable;

/**
 * @version 1.0
 * @Date 2025/3/28 19:27
 * @Description 查询营销配置任务
 * @Author jerryhotton
 */

public class QueryGroupBuyActivityVOThreadTask implements Callable<GroupBuyActivityVO> {

    private final String source;

    private final String channel;

    private final String groupId;

    private final IActivityRepository activityRepository;

    public QueryGroupBuyActivityVOThreadTask(String source, String channel, String groupId, IActivityRepository activityRepository) {
        this.source = source;
        this.channel = channel;
        this.groupId = groupId;
        this.activityRepository = activityRepository;
    }

    @Override
    public GroupBuyActivityVO call() throws Exception {
        return activityRepository.queryGroupBuyActivityVO(source, channel, groupId);
    }

}
