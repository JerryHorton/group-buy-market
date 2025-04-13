package cn.cug.sxy.domain.trade.model.aggregate;

import cn.cug.sxy.domain.trade.model.entity.GroupBuyOrderEntity;
import cn.cug.sxy.domain.trade.model.entity.TradePaySuccessEntity;
import cn.cug.sxy.domain.trade.model.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @version 1.0
 * @Date 2025/4/3 14:23
 * @Description
 * @Author Sxy
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TradePaySettlementAggregate {

    private UserEntity userEntity;

    private TradePaySuccessEntity tradePaySuccessEntity;

    private GroupBuyOrderEntity groupBuyOrderEntity;

}
