package cn.cug.sxy.domain.activity.service.index;

import cn.cug.sxy.domain.activity.model.entity.MarketProductEntity;
import cn.cug.sxy.domain.activity.model.entity.SCSkuActivityEntity;
import cn.cug.sxy.domain.activity.model.entity.TrialBalanceEntity;
import cn.cug.sxy.domain.activity.model.entity.UserGroupBuyOrderDetailEntity;
import cn.cug.sxy.domain.activity.model.valobj.TeamStatisticVO;

import java.util.List;

/**
 * @version 1.0
 * @Date 2025/3/27 16:59
 * @Description 首页营销服务 接口
 * @Author jerryhotton
 */

public interface IIndexGroupBuyMarketService {

    TrialBalanceEntity indexMarketTrial(MarketProductEntity marketProductEntity) throws Exception;

    List<UserGroupBuyOrderDetailEntity> queryOngoingGroupOrderDetail(SCSkuActivityEntity skuActivityEntity, String userId, Integer ownerLimitCount, Integer randomLimitCount) throws Exception;

    TeamStatisticVO queryTeamStatistic(SCSkuActivityEntity skuActivityEntity) throws Exception;

}
