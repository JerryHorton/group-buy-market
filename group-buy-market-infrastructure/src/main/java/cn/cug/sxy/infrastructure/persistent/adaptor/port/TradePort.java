package cn.cug.sxy.infrastructure.persistent.adaptor.port;

import cn.cug.sxy.domain.trade.adaptor.port.ITradePort;
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
 * @Description 交易接口服务 实现
 * @Author Sxy
 */

@Service
public class TradePort implements ITradePort {

    @Resource
    private GroupBuyNotifyService groupBuyNotifyService;

    @Resource
    private IRedisService redisService;

    @Override
    public String groupBuyNotify(NotifyTaskEntity notifyTaskEntity) throws Exception {
        RLock lock = redisService.getLock(notifyTaskEntity.lockKey());
        try {
            if (lock.tryLock(3, 0, TimeUnit.SECONDS)) {
                try {
                    // 无效的 notifyUrl 则直接返回成功
                    if (StringUtils.isBlank(notifyTaskEntity.getNotifyUrl())) {
                        return NotifyTaskHTTPStatus.SUCCESS.getCode();
                    }
                    return groupBuyNotifyService.groupBuyNotify(notifyTaskEntity.getNotifyUrl(), notifyTaskEntity.getParameterJson());
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
