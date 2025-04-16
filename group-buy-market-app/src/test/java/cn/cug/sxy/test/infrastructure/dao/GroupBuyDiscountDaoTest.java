package cn.cug.sxy.test.infrastructure.dao;

import cn.cug.sxy.infrastructure.dao.IGroupBuyDiscountDao;
import cn.cug.sxy.infrastructure.dao.po.GroupBuyDiscount;
import com.alibaba.fastjson2.JSON;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * @version 1.0
 * @Date 2025/3/27 14:54
 * @Description 单元测试
 * @Author jerryhotton
 */

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class GroupBuyDiscountDaoTest {

    @Resource
    private IGroupBuyDiscountDao groupBuyDiscountDao;

    @Test
    public void test_queryGroupBuyDiscount(){
        List<GroupBuyDiscount> groupBuyDiscounts = groupBuyDiscountDao.queryGroupBuyDiscount();
        log.info("测试结果:{}", JSON.toJSONString(groupBuyDiscounts));
    }

}
