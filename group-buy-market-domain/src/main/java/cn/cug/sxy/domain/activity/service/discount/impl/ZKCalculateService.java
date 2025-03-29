package cn.cug.sxy.domain.activity.service.discount.impl;

import cn.cug.sxy.domain.activity.model.valobj.GroupBuyActivityVO;
import cn.cug.sxy.domain.activity.service.discount.AbstractDiscountCalculateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * @version 1.0
 * @Date 2025/3/29 13:31
 * @Description 折扣优惠 服务实现
 * @Author jerryhotton
 */

@Slf4j
@Service(value = "ZK")
public class ZKCalculateService extends AbstractDiscountCalculateService {

    @Override
    protected BigDecimal doCalculate(BigDecimal originalPrice, GroupBuyActivityVO.GroupBuyDiscount groupBuyDiscount) {
        log.info("优惠策略折扣计算:{}", groupBuyDiscount.getDiscountType().getInfo());
        String marketExpr = groupBuyDiscount.getMarketExpr();
        BigDecimal discount = new BigDecimal(marketExpr);
        return originalPrice.multiply(discount);
    }

}
