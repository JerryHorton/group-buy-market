package cn.cug.sxy.domain.activity.service.index.impl;

import cn.cug.sxy.domain.activity.model.entity.MarketProductEntity;
import cn.cug.sxy.domain.activity.model.entity.SCSkuActivityEntity;
import cn.cug.sxy.domain.activity.model.entity.TrialBalanceEntity;
import cn.cug.sxy.domain.activity.model.entity.UserGroupBuyOrderDetailEntity;
import cn.cug.sxy.domain.activity.model.valobj.TeamStatisticVO;
import cn.cug.sxy.domain.activity.repository.IActivityRepository;
import cn.cug.sxy.domain.activity.service.index.IIndexGroupBuyMarketService;
import cn.cug.sxy.domain.activity.service.trial.factory.ActivityStrategyFactory;
import cn.cug.sxy.types.design.framework.tree.StrategyHandler;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @version 1.0
 * @Date 2025/3/27 17:01
 * @Description 首页营销服务 实现
 * @Author jerryhotton
 */

@Service
public class IndexGroupBuyMarketService implements IIndexGroupBuyMarketService {

    @Resource
    private ActivityStrategyFactory activityStrategyFactory;

    @Resource
    private IActivityRepository repository;

    @Override
    public TrialBalanceEntity indexMarketTrial(MarketProductEntity marketProductEntity) throws Exception {
        StrategyHandler<MarketProductEntity, ActivityStrategyFactory.DynamicContext, TrialBalanceEntity> strategyHandler = activityStrategyFactory.strategyHandler();
        return strategyHandler.apply(marketProductEntity, new ActivityStrategyFactory.DynamicContext());
    }

    @Override
    public List<UserGroupBuyOrderDetailEntity> queryOngoingGroupOrderDetail(SCSkuActivityEntity skuActivityEntity, String userId, Integer ownerLimitCount, Integer randomLimitCount) throws Exception {
        List<UserGroupBuyOrderDetailEntity> resultList = new ArrayList<>();
        // 查询个人拼团数据
        if (0 != ownerLimitCount) {
            List<UserGroupBuyOrderDetailEntity> ownerList = repository.queryOwnOngoingGroupOrderDetail(userId, skuActivityEntity, ownerLimitCount);
            if (null != ownerList && !ownerList.isEmpty()) {
                resultList.addAll(ownerList);
            }
        }
        // 查询其他非个人拼团数据
        if (0 != randomLimitCount) {
            List<UserGroupBuyOrderDetailEntity> randomList = repository.queryRandomOngoingGroupOrderDetail(userId, skuActivityEntity, randomLimitCount);
            if (null != randomList && !randomList.isEmpty()) {
                resultList.addAll(randomList);
            }
        }

        return resultList;
    }

    @Override
    public TeamStatisticVO queryTeamStatistic(SCSkuActivityEntity skuActivityEntity) throws Exception {
        return repository.queryTeamStatistic(skuActivityEntity);
    }

}
