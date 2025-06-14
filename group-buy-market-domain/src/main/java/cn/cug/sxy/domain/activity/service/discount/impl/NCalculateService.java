package cn.cug.sxy.domain.activity.service.discount.impl;

import cn.cug.sxy.domain.activity.model.valobj.GroupBuyActivityVO;
import cn.cug.sxy.domain.activity.service.discount.AbstractDiscountCalculateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * @version 1.0
 * @Date 2025/3/29 13:31
 * @Description N元购优惠 服务实现
 * @Author jerryhotton
 */

@Slf4j
@Service(value = "N")
public class NCalculateService extends AbstractDiscountCalculateService {

    @Override
    protected BigDecimal doCalculate(BigDecimal originalPrice, GroupBuyActivityVO.GroupBuyDiscount groupBuyDiscount) {
        log.info("优惠策略计算 - N元购优惠:{}", groupBuyDiscount.getDiscountType().getInfo());
        String marketExpr = groupBuyDiscount.getMarketExpr();
        BigDecimal discountedPrice = new BigDecimal(marketExpr);

        return validPrice(discountedPrice);
    }

}
