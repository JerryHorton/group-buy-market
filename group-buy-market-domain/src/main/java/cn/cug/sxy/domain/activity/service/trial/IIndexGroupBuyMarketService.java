package cn.cug.sxy.domain.activity.service.trial;

import cn.cug.sxy.domain.activity.model.entity.MarketProductEntity;
import cn.cug.sxy.domain.activity.model.entity.TrialBalanceEntity;

/**
 * @version 1.0
 * @Date 2025/3/27 16:59
 * @Description 首页营销服务 接口
 * @Author jerryhotton
 */

public interface IIndexGroupBuyMarketService {

    TrialBalanceEntity indexMarketTrial(MarketProductEntity marketProductEntity) throws Exception;

}
