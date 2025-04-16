package cn.cug.sxy.infrastructure.adaptor.port;

import cn.cug.sxy.domain.trade.adaptor.port.ISmallPayMallPort;
import cn.cug.sxy.domain.trade.model.entity.NotifyTaskEntity;
import cn.cug.sxy.infrastructure.adaptor.port.strategy.INotifyStrategy;
import cn.cug.sxy.infrastructure.redis.IRedisService;
import cn.cug.sxy.types.common.Constants;
import cn.cug.sxy.types.enums.NotifyTaskStatus;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RLock;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @version 1.0
 * @Date 2025/4/5 15:08
 * @Description 支付商城App出站端口 实现
 * @Author Sxy
 */

@Service
public class SmallPayMallAppPort implements ISmallPayMallPort {

    private final String notifyTaskKey = "GroupBuyNotify";

    private final Map<String, INotifyStrategy> notifyStrategyMap;

    public SmallPayMallAppPort(Map<String, INotifyStrategy> notifyStrategyMap) {
        this.notifyStrategyMap = notifyStrategyMap;
    }

    @Resource
    private IRedisService redisService;

    @Override
    public String groupBuyNotify(NotifyTaskEntity notifyTaskEntity) {
        RLock lock = redisService.getLock(notifyTaskEntity.lockKey());
        try {
            if (lock.tryLock(3, 0, TimeUnit.SECONDS)) {
                try {
                    // 二次校验是否已处理 或 无效的 notifyUrl 则直接返回成功
                    if (redisService.isExists(notifyTaskEntity.doneKey()) ||
                            StringUtils.isBlank(notifyTaskEntity.getNotifyTarget())) {
                        return NotifyTaskStatus.SUCCESS.getCode();
                    }
                    // 根据策略执行回调
                    String strategy = notifyTaskKey + Constants.UNDERLINE + notifyTaskEntity.getNotifyType();
                    String result = notifyStrategyMap.get(strategy).notify(notifyTaskEntity.getNotifyTarget(), notifyTaskEntity.getParameterJson());
                    // 标记回调完成
                    redisService.setValue(notifyTaskEntity.doneKey(), "1", 5 * 60 * 1000);
                    return result;
                } finally {
                    if (lock.isLocked() && lock.isHeldByCurrentThread()) {
                        lock.unlock();
                    }
                }
            }
            return NotifyTaskStatus.NULL.getCode();
        } catch (Exception e) {
            Thread.currentThread().interrupt();
            return NotifyTaskStatus.NULL.getCode();
        }
    }

}
