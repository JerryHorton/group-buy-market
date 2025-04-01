package cn.cug.sxy.domain.activity.service.discount;

import cn.cug.sxy.domain.activity.model.valobj.GroupBuyActivityVO;
import cn.cug.sxy.domain.activity.service.trial.factory.DefaultActivityStrategyFactory;

import java.math.BigDecimal;

/**
 * @version 1.0
 * @Date 2025/3/29 13:24
 * @Description 优惠计算 服务接口
 * @Author jerryhotton
 */

public interface IDiscountCalculateService {

    BigDecimal calculate(String userId, BigDecimal originalPrice, DefaultActivityStrategyFactory.DynamicContext dynamicContext);

}
