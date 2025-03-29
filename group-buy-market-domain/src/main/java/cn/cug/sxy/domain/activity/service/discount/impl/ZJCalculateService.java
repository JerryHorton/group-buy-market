package cn.cug.sxy.domain.activity.service.discount.impl;

import cn.cug.sxy.domain.activity.model.valobj.GroupBuyActivityVO;
import cn.cug.sxy.domain.activity.service.discount.AbstractDiscountCalculateService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * @version 1.0
 * @Date 2025/3/29 13:29
 * @Description 直减优惠 服务实现
 * @Author jerryhotton
 */

@Service(value = "ZJ")
public class ZJCalculateService extends AbstractDiscountCalculateService {

    @Override
    protected BigDecimal doCalculate(BigDecimal originalPrice, GroupBuyActivityVO.GroupBuyDiscount groupBuyDiscount) {
        String marketExpr = groupBuyDiscount.getMarketExpr();
        BigDecimal deduction = new BigDecimal(marketExpr);
        BigDecimal deductionPrice = originalPrice.subtract(deduction);

        return validPrice(deductionPrice);
    }

}
