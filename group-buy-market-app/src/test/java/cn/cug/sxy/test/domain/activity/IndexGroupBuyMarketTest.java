package cn.cug.sxy.test.domain.activity;

import cn.cug.sxy.domain.activity.model.entity.MarketProductEntity;
import cn.cug.sxy.domain.activity.model.entity.TrialBalanceEntity;
import cn.cug.sxy.domain.activity.service.trial.impl.IndexGroupBuyMarketService;
import com.alibaba.fastjson2.JSON;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @version 1.0
 * @Date 2025/3/28 21:10
 * @Description
 * @Author jerryhotton
 */

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class IndexGroupBuyMarketTest {

    @Resource
    private IndexGroupBuyMarketService indexGroupBuyMarketService;

    @Test
    public void test_indexMarketTrial() throws Exception {

        TrialBalanceEntity trialBalanceEntity = indexGroupBuyMarketService.indexMarketTrial(MarketProductEntity.builder()
                .userId("user001")
                .goodsId("10001")
                .source("SOURCE_001")
                .channel("CHANNEL_001")
                .build());
        log.info("测试成功,trialBalanceEntity:{}", JSON.toJSONString(trialBalanceEntity));
    }

}
