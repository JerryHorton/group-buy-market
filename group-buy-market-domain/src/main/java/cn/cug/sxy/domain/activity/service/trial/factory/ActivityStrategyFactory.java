package cn.cug.sxy.domain.activity.service.trial.factory;

import cn.cug.sxy.domain.activity.model.entity.MarketProductEntity;
import cn.cug.sxy.domain.activity.model.entity.TrialBalanceEntity;
import cn.cug.sxy.domain.activity.model.valobj.GroupBuyActivityVO;
import cn.cug.sxy.domain.activity.model.valobj.SkuVO;
import cn.cug.sxy.domain.activity.service.trial.node.RootNode;
import cn.cug.sxy.types.design.framework.tree.StrategyHandler;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * @version 1.0
 * @Date 2025/3/27 16:25
 * @Description 活动策略工厂
 * @Author jerryhotton
 */

@Service
public class ActivityStrategyFactory {

    private final RootNode rootNode;

    public ActivityStrategyFactory(RootNode rootNode) {
        this.rootNode = rootNode;
    }

    public StrategyHandler<MarketProductEntity, DynamicContext, TrialBalanceEntity> strategyHandler() {
        return rootNode;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class DynamicContext {

        /**
         * 拼团活动营销配置值对象
         */
        private GroupBuyActivityVO groupBuyActivityVO;
        /**
         * 商品信息值对象
         */
        private SkuVO skuVO;
        /**
         * 优惠后的最终价格
         */
        private BigDecimal finalPrice;
        /**
         * 是否可见拼团
         */
        private Boolean isVisible;
        /**
         * 是否可参与拼团
         */
        private Boolean isEnable;

    }

}
