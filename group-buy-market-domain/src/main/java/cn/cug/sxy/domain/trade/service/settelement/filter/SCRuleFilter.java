package cn.cug.sxy.domain.trade.service.settelement.filter;

import cn.cug.sxy.domain.trade.model.entity.TradeSettlementRuleCommandEntity;
import cn.cug.sxy.domain.trade.model.entity.TradeSettlementRuleFilterBackEntity;
import cn.cug.sxy.domain.trade.repository.ITradeRepository;
import cn.cug.sxy.domain.trade.service.settelement.factory.TradeSettlementRuleFilterFactory;
import cn.cug.sxy.types.design.framework.link.multitonModel.handler.ILogicHandler;
import cn.cug.sxy.types.enums.ResponseCode;
import cn.cug.sxy.types.exception.AppException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @version 1.0
 * @Date 2025/4/4 14:32
 * @Description
 * @Author Sxy
 */

@Slf4j
@Service
public class SCRuleFilter implements ILogicHandler<TradeSettlementRuleCommandEntity, TradeSettlementRuleFilterFactory.DynamicContext, TradeSettlementRuleFilterBackEntity> {

    @Resource
    private ITradeRepository repository;

    @Override
    public TradeSettlementRuleFilterBackEntity apply(TradeSettlementRuleCommandEntity requestParameter, TradeSettlementRuleFilterFactory.DynamicContext dynamicContext) throws Exception {
        log.info("结算规则过滤-渠道黑名单校验, userId:{} outTradeNo:{}", requestParameter.getUserId(), requestParameter.getOutTradeNo());

        Boolean intercept = repository.isSCBlackListIntercept(requestParameter.getSource(), requestParameter.getChannel());
        if (intercept) {
            log.error("source:{} channel:{} 渠道黑名单拦截", requestParameter.getSource(), requestParameter.getChannel());
            throw new AppException(ResponseCode.E0105);
        }

        return next(requestParameter, dynamicContext);
    }

}
