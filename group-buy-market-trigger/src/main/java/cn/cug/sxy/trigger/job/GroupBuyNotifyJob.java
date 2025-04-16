package cn.cug.sxy.trigger.job;

import cn.cug.sxy.domain.trade.service.ITradeSettlementOrderService;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @version 1.0
 * @Date 2025/4/5 16:31
 * @Description 拼团成功回调 任务补偿
 * @Author Sxy
 */

@Slf4j
@Service
public class GroupBuyNotifyJob {

    @Resource
    private ITradeSettlementOrderService tradeSettlementOrderService;

    @Resource
    private RedissonClient redissonClient;

    @Scheduled(cron = "0/15 * * * * ?")
    public void exec() {
        RLock lock = redissonClient.getLock("group_buy_market_group-buy-notify-job");
        try {
            boolean isLocked = lock.tryLock(3, 60, TimeUnit.MINUTES);
            if (!isLocked) {
                log.info("未获取到锁，跳过任务");
                return;
            }
            Map<String, Integer> notifyResultMap = tradeSettlementOrderService.execSettlementNotifyJob();
            log.info("定时任务，回调通知拼团完结任务 notifyResultMap:{}", JSON.toJSONString(notifyResultMap));
        } catch (Exception e) {
            log.error("定时任务，回调通知拼团完结任务失败", e);
        } finally {
            if (lock.isLocked() && lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }
    }

}
