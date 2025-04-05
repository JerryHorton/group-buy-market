package cn.cug.sxy.infrastructure.persistent.adaptor.repository;

import cn.cug.sxy.domain.activity.model.valobj.DiscountTypeVO;
import cn.cug.sxy.domain.activity.model.valobj.GroupBuyActivityVO;
import cn.cug.sxy.domain.activity.model.valobj.SkuVO;
import cn.cug.sxy.domain.activity.model.valobj.TagScopeVO;
import cn.cug.sxy.domain.activity.repository.IActivityRepository;
import cn.cug.sxy.infrastructure.persistent.dao.*;
import cn.cug.sxy.infrastructure.persistent.dcc.IDCCService;
import cn.cug.sxy.infrastructure.persistent.dao.po.*;
import cn.cug.sxy.infrastructure.persistent.redis.IRedisService;
import cn.cug.sxy.types.common.Constants;
import org.redisson.api.RBitSet;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    private ISCSkuActivityDao sCSkuActivityDao;

    @Resource
    private IActivityTagConfigDao activityTagConfigDao;

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
    public boolean degradeSwitch() {
        return dccService.isDegradeSwitch();
    }

    @Override
    public boolean cutRange(String userId) {
        return dccService.isCutRange(userId);
    }

}
