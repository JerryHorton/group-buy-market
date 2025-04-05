package cn.cug.sxy.trigger.job;

import cn.cug.sxy.domain.trade.service.ITradeSettlementOrderService;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

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

    @Scheduled(cron = "0/15 * * * * ?")
    public void exec() {
        try {
            Map<String, Integer> notifyResultMap = tradeSettlementOrderService.execSettlementNotifyJob();
            log.info("定时任务，回调通知拼团完结任务 notifyResultMap:{}", JSON.toJSONString(notifyResultMap));
        } catch (Exception e) {
            log.error("定时任务，回调通知拼团完结任务失败", e);
        }
    }

}
