package cn.cug.sxy.infrastructure.persistent.repository;

import cn.cug.sxy.domain.activity.model.valobj.DiscountTypeVO;
import cn.cug.sxy.domain.activity.model.valobj.GroupBuyActivityVO;
import cn.cug.sxy.domain.activity.model.valobj.SkuVO;
import cn.cug.sxy.domain.activity.repository.IActivityRepository;
import cn.cug.sxy.infrastructure.persistent.dao.IGroupBuyActivityDao;
import cn.cug.sxy.infrastructure.persistent.dao.IGroupBuyDiscountDao;
import cn.cug.sxy.infrastructure.persistent.dao.ISCSkuActivityDao;
import cn.cug.sxy.infrastructure.persistent.dao.ISkuDao;
import cn.cug.sxy.infrastructure.persistent.po.GroupBuyActivity;
import cn.cug.sxy.infrastructure.persistent.po.GroupBuyDiscount;
import cn.cug.sxy.infrastructure.persistent.po.SCSkuActivity;
import cn.cug.sxy.infrastructure.persistent.po.Sku;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 * @version 1.0
 * @Date 2025/3/28 17:02
 * @Description 活动 仓储实现
 * @Author jerryhotton
 */

@Repository
public class ActivityRepository implements IActivityRepository {

    @Resource
    private IGroupBuyActivityDao groupBuyActivityDao;

    @Resource
    private IGroupBuyDiscountDao groupBuyDiscountDao;

    @Resource
    private ISCSkuActivityDao sCSkuActivityDao;

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
        GroupBuyActivity groupBuyActivityRes = groupBuyActivityDao.queryValidGroupBuyActivityByActivityId(scSkuActivityRes.getActivityId());
        if (null == groupBuyActivityRes) {
            return null;
        }
        GroupBuyDiscount groupBuyDiscount = groupBuyDiscountDao.queryGroupBuyDiscountByDiscountId(groupBuyActivityRes.getDiscountId());
        if (null == groupBuyDiscount) {
            return null;
        }
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
                .tagId(groupBuyActivityRes.getTagId())
                .tagScope(groupBuyActivityRes.getTagScope())
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

}
