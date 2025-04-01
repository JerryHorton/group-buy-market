package cn.cug.sxy.domain.trade.model.aggregate;

import cn.cug.sxy.domain.trade.model.entity.PayActivityEntity;
import cn.cug.sxy.domain.trade.model.entity.PayDiscountEntity;
import cn.cug.sxy.domain.trade.model.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @version 1.0
 * @Date 2025/3/31 20:32
 * @Description 拼团订单 聚合对象
 * @Author jerryhotton
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GroupBuyOrderAggregate {

    /**
     * 用户实体对象
     */
    private UserEntity userEntity;
    /**
     * 支付活动实体对象
     */
    private PayActivityEntity payActivityEntity;
    /**
     * 支付优惠实体对象
     */
    private PayDiscountEntity payDiscountEntity;

}
