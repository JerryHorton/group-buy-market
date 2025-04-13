package cn.cug.sxy.infrastructure.persistent.adaptor.port;

import cn.cug.sxy.domain.trade.adaptor.port.ISmallPayMallPort;
import cn.cug.sxy.domain.trade.model.entity.NotifyTaskEntity;
import cn.cug.sxy.infrastructure.persistent.gateway.GroupBuyNotifyService;
import cn.cug.sxy.infrastructure.persistent.redis.IRedisService;
import cn.cug.sxy.types.enums.NotifyTaskHTTPStatus;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RLock;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @version 1.0
 * @Date 2025/4/5 15:08
 * @Description 支付商城App出站端口 实现
 * @Author Sxy
 */

@Service
public class SmallPayMallAppPort implements ISmallPayMallPort {

    @Resource
    private GroupBuyNotifyService groupBuyNotifyService;

    @Resource
    private IRedisService redisService;

    @Override
    public String groupBuyNotify(NotifyTaskEntity notifyTaskEntity) {
        RLock lock = redisService.getLock(notifyTaskEntity.lockKey());
        try {
            if (lock.tryLock(3, 0, TimeUnit.SECONDS)) {
                try {
                    // 二次校验是否已处理
                    if (redisService.isExists(notifyTaskEntity.doneKey())) {
                        return NotifyTaskHTTPStatus.SUCCESS.getCode();
                    }
                    // 无效的 notifyUrl 则直接返回成功
                    if (StringUtils.isBlank(notifyTaskEntity.getNotifyUrl())) {
                        return NotifyTaskHTTPStatus.SUCCESS.getCode();
                    }
                    String result = groupBuyNotifyService.groupBuyNotify(notifyTaskEntity.getNotifyUrl(), notifyTaskEntity.getParameterJson());
                    redisService.setValue(notifyTaskEntity.doneKey(), "1", 5 * 60 * 1000);
                    return result;
                } finally {
                    if (lock.isLocked() && lock.isHeldByCurrentThread()) {
                        lock.unlock();
                    }
                }
            }
            return NotifyTaskHTTPStatus.NULL.getCode();
        } catch (Exception e){
            Thread.currentThread().interrupt();
            return NotifyTaskHTTPStatus.NULL.getCode();
        }
    }

}
