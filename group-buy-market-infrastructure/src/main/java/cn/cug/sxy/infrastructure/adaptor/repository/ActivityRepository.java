package cn.cug.sxy.infrastructure.adaptor.repository;

import cn.cug.sxy.domain.activity.model.entity.SCSkuActivityEntity;
import cn.cug.sxy.domain.activity.model.entity.UserGroupBuyOrderDetailEntity;
import cn.cug.sxy.domain.activity.model.valobj.*;
import cn.cug.sxy.domain.activity.repository.IActivityRepository;
import cn.cug.sxy.infrastructure.dao.*;
import cn.cug.sxy.infrastructure.dao.po.*;
import cn.cug.sxy.infrastructure.dcc.IDCCService;
import cn.cug.sxy.infrastructure.redis.IRedisService;
import cn.cug.sxy.types.common.Constants;
import org.redisson.api.RBitSet;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @version 1.0
 * @Date 2025/3/28 17:02
 * @Description 活动 仓储实现
 * @Author jerryhotton
 */

@Repository
public class ActivityRepository implements IActivityRepository {

    @Resource
    private IRedisService redisService;

    @Resource
    private IDCCService dccService;

    @Resource
    private IGroupBuyActivityDao groupBuyActivityDao;

    @Resource
    private IGroupBuyDiscountDao groupBuyDiscountDao;

    @Resource
    private IGroupBuyOrderDao groupBuyOrderDao;

    @Resource
    private ISCSkuActivityDao sCSkuActivityDao;

    @Resource
    private IActivityTagConfigDao activityTagConfigDao;

    @Resource
    private IUserGroupBuyOrderDetailDao userGroupBuyOrderDetailDao;

    @Resource
    private ISkuDao skuDao;

    @Override
    public GroupBuyActivityVO queryGroupBuyActivityVO(String source, String channel, String goodsId) {
        SCSkuActivity scSkuActivityReq = new SCSkuActivity();
        scSkuActivityReq.setSource(source);
        scSkuActivityReq.setChannel(channel);
        scSkuActivityReq.setGoodsId(goodsId);
        SCSkuActivity scSkuActivityRes = sCSkuActivityDao.querySCSkuActivity(scSkuActivityReq);

        if (null == scSkuActivityRes) {
            return null;
        }

        GroupBuyActivity groupBuyActivityRes = groupBuyActivityDao.queryGroupBuyActivityByActivityId(scSkuActivityRes.getActivityId());
        if (null == groupBuyActivityRes) {
            return null;
        }

        GroupBuyDiscount groupBuyDiscount = groupBuyDiscountDao.queryGroupBuyDiscountByDiscountId(groupBuyActivityRes.getDiscountId());
        if (null == groupBuyDiscount) {
            return null;
        }

        List<ActivityTagsConfig> activityTagsConfigEntityList = activityTagConfigDao.queryActivityTagConfig(groupBuyActivityRes.getActivityId());
        Map<String, String> tagsConfigMap = activityTagsConfigEntityList.stream()
                .collect(Collectors.toMap(ActivityTagsConfig::getTagId, ActivityTagsConfig::getTagScope));

        return GroupBuyActivityVO.builder()
                .activityId(scSkuActivityRes.getActivityId())
                .activityName(groupBuyActivityRes.getActivityName())
                .source(scSkuActivityRes.getSource())
                .channel(scSkuActivityRes.getChannel())
                .goodsId(scSkuActivityRes.getGoodsId())
                .groupType(groupBuyActivityRes.getGroupType())
                .takeLimitCount(groupBuyActivityRes.getTakeLimitCount())
                .targetCount(groupBuyActivityRes.getTargetCount())
                .validTime(groupBuyActivityRes.getValidTime())
                .status(groupBuyActivityRes.getStatus())
                .startTime(groupBuyActivityRes.getStartTime())
                .endTime(groupBuyActivityRes.getEndTime())
                .tagsConfig(tagsConfigMap)
                .groupBuyDiscount(GroupBuyActivityVO.GroupBuyDiscount.builder()
                        .discountName(groupBuyDiscount.getDiscountName())
                        .discountDesc(groupBuyDiscount.getDiscountDesc())
                        .discountType(DiscountTypeVO.get(groupBuyDiscount.getDiscountType()))
                        .marketPlan(groupBuyDiscount.getMarketPlan())
                        .marketExpr(groupBuyDiscount.getMarketExpr())
                        .tagId(groupBuyDiscount.getTagId())
                        .build())
                .build();

    }

    @Override
    public SkuVO querySkuVOByGoodsId(String goodsId) {
        Sku sku = skuDao.querySkuByGoodsId(goodsId);
        if (null == sku) {
            return null;
        }
        return SkuVO.builder()
                .goodsId(sku.getGoodsId())
                .goodsName(sku.getGoodsName())
                .originalPrice(sku.getOriginalPrice())
                .build();
    }

    @Override
    public Map<String, Boolean> IsWithinCrowdTagRangeAndScope(Map<String, String> tagsConfigMap, String userId) {
        Map<String, Boolean> isWithin = new HashMap<String, Boolean>() {{
            put(TagScopeVO.VISIBLE.getCode(), false);
            put(TagScopeVO.ENABLE.getCode(), false);
        }};
        for (String tagId : tagsConfigMap.keySet()) {
            RBitSet bitSet = redisService.getBitSet(tagId);
            if (bitSet.get(redisService.getIndexFromUserId(userId))) {
                String tagScope = tagsConfigMap.get(tagId);
                String[] split = tagScope.split(Constants.SPLIT);
                isWithin.put(TagScopeVO.VISIBLE.getCode(), "1".equals(split[0]));
                isWithin.put(TagScopeVO.ENABLE.getCode(), split.length == 2 && "2".equals(split[1]));
                break;
            }
        }
        return isWithin;
    }

    @Override
    public List<UserGroupBuyOrderDetailEntity> queryOwnOngoingGroupOrderDetail(String userId, SCSkuActivityEntity skuActivityEntity, Integer ownerLimitCount) {
        UserGroupBuyOrderDetail userGroupBuyOrderDetailReq = new UserGroupBuyOrderDetail();
        userGroupBuyOrderDetailReq.setUserId(userId);
        userGroupBuyOrderDetailReq.setSource(skuActivityEntity.getSource());
        userGroupBuyOrderDetailReq.setChannel(skuActivityEntity.getChannel());
        userGroupBuyOrderDetailReq.setGoodsId(skuActivityEntity.getGoodsId());
        userGroupBuyOrderDetailReq.setCount(ownerLimitCount);
        List<UserGroupBuyOrderDetail> userGroupBuyOrderDetailList = userGroupBuyOrderDetailDao.queryOwnOngoingUserGroupBuyOrderDetail(userGroupBuyOrderDetailReq);
        return buildOngoingUserGroupBuyOrderDetailList(userGroupBuyOrderDetailList);
    }

    @Override
    public List<UserGroupBuyOrderDetailEntity> queryRandomOngoingGroupOrderDetail(String userId, SCSkuActivityEntity skuActivityEntity, Integer randomLimitCount) {
        UserGroupBuyOrderDetail userGroupBuyOrderDetailReq = new UserGroupBuyOrderDetail();
        userGroupBuyOrderDetailReq.setUserId(userId);
        userGroupBuyOrderDetailReq.setSource(skuActivityEntity.getSource());
        userGroupBuyOrderDetailReq.setChannel(skuActivityEntity.getChannel());
        userGroupBuyOrderDetailReq.setGoodsId(skuActivityEntity.getGoodsId());
        userGroupBuyOrderDetailReq.setCount(randomLimitCount * 2);
        List<UserGroupBuyOrderDetail> userGroupBuyOrderDetailList = userGroupBuyOrderDetailDao.queryRandomOngoingUserGroupBuyOrderDetail(userGroupBuyOrderDetailReq);
        return buildOngoingUserGroupBuyOrderDetailList(userGroupBuyOrderDetailList);
    }

    @Override
    public TeamStatisticVO queryTeamStatistic(SCSkuActivityEntity skuActivityEntity) {
        GroupBuyOrder groupBuyOrderReq = new GroupBuyOrder();
        groupBuyOrderReq.setSource(skuActivityEntity.getSource());
        groupBuyOrderReq.setChannel(skuActivityEntity.getChannel());
        groupBuyOrderReq.setGoodsId(skuActivityEntity.getGoodsId());
        // 查询当前商品的所有拼团队伍
        List<GroupBuyOrder> groupBuyOrderList = groupBuyOrderDao.queryCurrentGroupBuyTeams(groupBuyOrderReq);
        if (null == groupBuyOrderList || groupBuyOrderList.isEmpty()) {
            return new TeamStatisticVO(0, 0, 0);
        }
        // 获取所有拼团ID
        List<String> teamIds = groupBuyOrderList.stream()
                .map(GroupBuyOrder::getTeamId)
                .collect(Collectors.toList());
        // 统计数据
        Integer teamCreatedCount = teamIds.size();
        Integer teamCompletedCount = groupBuyOrderDao.queryTeamCompletedCount(teamIds);
        Integer totalTeamUserCount = groupBuyOrderDao.queryTotalTeamUserCount(teamIds);
        return TeamStatisticVO.builder()
                .teamCreatedCount(teamCreatedCount)
                .teamCompletedCount(teamCompletedCount)
                .totalTeamUserCount(totalTeamUserCount)
                .build();
    }

    @Override
    public boolean degradeSwitch() {
        return dccService.isDegradeSwitch();
    }

    @Override
    public boolean cutRange(String userId) {
        return dccService.isCutRange(userId);
    }

    private List<UserGroupBuyOrderDetailEntity> buildOngoingUserGroupBuyOrderDetailList(List<UserGroupBuyOrderDetail> userGroupBuyOrderDetailList) {
        if (null == userGroupBuyOrderDetailList) {
            return null;
        }
        // 获取拼团ID
        List<String> teamIds = userGroupBuyOrderDetailList.stream()
                .map(UserGroupBuyOrderDetail::getTeamId)
                .collect(Collectors.toList());
        // 查询未完成拼团的队伍明细
        List<GroupBuyOrder> groupBuyOrderList = groupBuyOrderDao.queryOngoingGroupBuyOrderByTeamIds(teamIds);
        Map<String, GroupBuyOrder> groupBuyOrderMap = groupBuyOrderList.stream()
                .collect(Collectors.toMap(GroupBuyOrder::getTeamId, groupBuyOrder -> groupBuyOrder));
        // 数据拼装
        List<UserGroupBuyOrderDetailEntity> userGroupBuyOrderDetailEntityList = new ArrayList<>();
        for (UserGroupBuyOrderDetail userGroupBuyOrderDetail : userGroupBuyOrderDetailList) {
            String teamId = userGroupBuyOrderDetail.getTeamId();
            GroupBuyOrder groupBuyOrder = groupBuyOrderMap.get(teamId);
            if (null == groupBuyOrder) {
                continue;
            }
            UserGroupBuyOrderDetailEntity userGroupBuyOrderDetailEntity = UserGroupBuyOrderDetailEntity.builder()
                    .userId(userGroupBuyOrderDetail.getUserId())
                    .teamId(groupBuyOrder.getTeamId())
                    .activityId(groupBuyOrder.getActivityId())
                    .targetCount(groupBuyOrder.getTargetCount())
                    .completeCount(groupBuyOrder.getCompleteCount())
                    .lockCount(groupBuyOrder.getLockCount())
                    .validStartTime(groupBuyOrder.getValidStartTime())
                    .validEndTime(groupBuyOrder.getValidEndTime())
                    .outTradeNo(userGroupBuyOrderDetail.getOutTradeNo())
                    .build();

            userGroupBuyOrderDetailEntityList.add(userGroupBuyOrderDetailEntity);
        }
        return userGroupBuyOrderDetailEntityList;
    }

}
