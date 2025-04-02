package cn.cug.sxy.domain.activity.service.discount;

import cn.cug.sxy.domain.activity.model.valobj.DiscountTypeVO;
import cn.cug.sxy.domain.activity.model.valobj.GroupBuyActivityVO;
import cn.cug.sxy.domain.activity.service.trial.factory.ActivityStrategyFactory;

import java.math.BigDecimal;

/**
 * @version 1.0
 * @Date 2025/3/29 13:34
 * @Description 优惠计算抽象类
 * @Author jerryhotton
 */

public abstract class AbstractDiscountCalculateService implements IDiscountCalculateService {

    @Override
    public BigDecimal calculate(String userId, BigDecimal originalPrice, ActivityStrategyFactory.DynamicContext dynamicContext) {
        GroupBuyActivityVO.GroupBuyDiscount groupBuyDiscount = dynamicContext.getGroupBuyActivityVO().getGroupBuyDiscount();
        // 若为人群标签型优惠
        if (DiscountTypeVO.TAG.equals(groupBuyDiscount.getDiscountType())) {
            boolean isCrowdRange = filterTagId(dynamicContext);
            if (!isCrowdRange) {
                return originalPrice;
            }
        }
        return doCalculate(originalPrice, groupBuyDiscount);
    }

    private boolean filterTagId(ActivityStrategyFactory.DynamicContext dynamicContext) {
        return dynamicContext.getIsVisible();
    }

    protected BigDecimal validPrice(BigDecimal deductionPrice) {
        if (deductionPrice.compareTo(BigDecimal.ZERO) <= 0) {
            return new BigDecimal("0.01");
        }
        return deductionPrice;
    }

    protected abstract BigDecimal doCalculate(BigDecimal originalPrice, GroupBuyActivityVO.GroupBuyDiscount groupBuyDiscount);

}
