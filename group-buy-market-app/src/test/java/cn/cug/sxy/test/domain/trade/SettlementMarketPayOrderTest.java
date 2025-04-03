package cn.cug.sxy.test.domain.trade;

import cn.cug.sxy.domain.trade.model.entity.TradePaySettlementEntity;
import cn.cug.sxy.domain.trade.model.entity.TradePaySuccessEntity;
import cn.cug.sxy.domain.trade.service.ITradeSettlementOrderService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @version 1.0
 * @Date 2025/4/3 15:26
 * @Description 结算营销拼团订单 单元测试
 * @Author Sxy
 */

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class SettlementMarketPayOrderTest {

    @Resource
    private ITradeSettlementOrderService tradeSettlementOrderService;

    @Test
    public void test_settlementMarketPayOrder() {
        TradePaySettlementEntity tradePaySettlementEntity = tradeSettlementOrderService.settlementMarketPayOrder(TradePaySuccessEntity.builder()
                .source("SOURCE_001")
                .channel("CHANNEL_001")
                .userId("uer002")
                .outTradeNo("2010300909")
                .build());
        log.info("测试成功: 结算营销拼团订单成功, 结算信息: {}", tradePaySettlementEntity);
    }

}
