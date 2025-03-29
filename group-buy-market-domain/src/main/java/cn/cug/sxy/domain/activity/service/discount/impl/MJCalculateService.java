package cn.cug.sxy.domain.activity.service.discount.impl;

import cn.cug.sxy.domain.activity.model.valobj.GroupBuyActivityVO;
import cn.cug.sxy.domain.activity.service.discount.AbstractDiscountCalculateService;
import cn.cug.sxy.types.common.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * @version 1.0
 * @Date 2025/3/29 13:28
 * @Description 满减优惠 服务实现
 * @Author jerryhotton
 */

@Slf4j
@Service(value = "MJ")
public class MJCalculateService extends AbstractDiscountCalculateService {

    @Override
    protected BigDecimal doCalculate(BigDecimal originalPrice, GroupBuyActivityVO.GroupBuyDiscount groupBuyDiscount) {
        log.info("优惠策略折扣计算:{}", groupBuyDiscount.getDiscountType().getInfo());
        String marketExpr = groupBuyDiscount.getMarketExpr();
        String[] parts = marketExpr.split(Constants.SPLIT);
        BigDecimal threshold = new BigDecimal(parts[0]);
        BigDecimal deduction = new BigDecimal(parts[1]);
        if (threshold.compareTo(originalPrice) > 0) {
            return originalPrice;
        }
        BigDecimal deductionPrice = originalPrice.subtract(deduction);

        return validPrice(deductionPrice);
    }

}
