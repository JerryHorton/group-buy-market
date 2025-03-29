package cn.cug.sxy.domain.activity.service.discount;

import cn.cug.sxy.domain.activity.model.valobj.DiscountTypeVO;
import cn.cug.sxy.domain.activity.model.valobj.GroupBuyActivityVO;
import cn.cug.sxy.domain.activity.repository.IActivityRepository;

import javax.annotation.Resource;
import java.math.BigDecimal;

/**
 * @version 1.0
 * @Date 2025/3/29 13:34
 * @Description 优惠计算抽象类
 * @Author jerryhotton
 */

public abstract class AbstractDiscountCalculateService implements IDiscountCalculateService {

    @Resource
    protected IActivityRepository activityRepository;

    @Override
    public BigDecimal calculate(String userId, BigDecimal originalPrice, GroupBuyActivityVO.GroupBuyDiscount groupBuyDiscount) {
        if (DiscountTypeVO.TAG.equals(groupBuyDiscount.getDiscountType())) {
            boolean isCrowdRange = filterTagId(userId, groupBuyDiscount.getTagId());
            if (!isCrowdRange) {
                return originalPrice;
            }
        }
        return doCalculate(originalPrice, groupBuyDiscount);
    }

    private boolean filterTagId(String userId, String tagId) {
        // todo 后续实现
        return true;
    }

    protected BigDecimal validPrice(BigDecimal deductionPrice) {
        if (deductionPrice.compareTo(BigDecimal.ZERO) <= 0) {
            return new BigDecimal("0.01");
        }
        return deductionPrice;
    }

    protected abstract BigDecimal doCalculate(BigDecimal originalPrice, GroupBuyActivityVO.GroupBuyDiscount groupBuyDiscount);

}
