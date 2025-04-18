package cn.cug.sxy.domain.activity.repository;

import cn.cug.sxy.domain.activity.model.entity.SCSkuActivityEntity;
import cn.cug.sxy.domain.activity.model.entity.UserGroupBuyOrderDetailEntity;
import cn.cug.sxy.domain.activity.model.valobj.GroupBuyActivityVO;
import cn.cug.sxy.domain.activity.model.valobj.SkuVO;
import cn.cug.sxy.domain.activity.model.valobj.TeamStatisticVO;
import cn.cug.sxy.domain.trade.model.entity.GroupBuyActivityEntity;

import java.util.List;
import java.util.Map;

/**
 * @version 1.0
 * @Date 2025/3/28 17:00
 * @Description 活动 仓储接口
 * @Author jerryhotton
 */

public interface IActivityRepository {

    GroupBuyActivityVO queryGroupBuyActivityVO(String source, String channel, String goodsId);

    SkuVO querySkuVOByGoodsId(String goodsId);

    Map<String, Boolean> IsWithinCrowdTagRangeAndScope(Map<String, String> tagsConfigMap, String userId);

    List<UserGroupBuyOrderDetailEntity> queryOwnOngoingGroupOrderDetail(String userId, SCSkuActivityEntity skuActivityEntity, Integer ownerLimitCount);

    List<UserGroupBuyOrderDetailEntity> queryRandomOngoingGroupOrderDetail(String userId, SCSkuActivityEntity skuActivityEntity, Integer randomLimitCount);

    TeamStatisticVO queryTeamStatistic(SCSkuActivityEntity skuActivityEntity);

    boolean degradeSwitch();

    boolean cutRange(String userId);

    void updateActivityConfig(GroupBuyActivityEntity groupBuyActivityEntity);

}
