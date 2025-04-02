package cn.cug.sxy.infrastructure.persistent.repository;

import cn.cug.sxy.domain.trade.model.aggregate.GroupBuyOrderAggregate;
import cn.cug.sxy.domain.trade.model.entity.*;
import cn.cug.sxy.domain.trade.model.valobj.GroupBuyActivityStatusVO;
import cn.cug.sxy.domain.trade.model.valobj.GroupBuyProgressVO;
import cn.cug.sxy.domain.trade.model.valobj.TradeOrderStatusVO;
import cn.cug.sxy.domain.trade.repository.ITradeRepository;
import cn.cug.sxy.infrastructure.persistent.dao.IGroupBuyActivityDao;
import cn.cug.sxy.infrastructure.persistent.dao.IGroupBuyOrderDao;
import cn.cug.sxy.infrastructure.persistent.dao.IGroupBuyOrderListDao;
import cn.cug.sxy.infrastructure.persistent.po.GroupBuyActivity;
import cn.cug.sxy.infrastructure.persistent.po.GroupBuyOrder;
import cn.cug.sxy.infrastructure.persistent.po.GroupBuyOrderList;
import cn.cug.sxy.types.common.Constants;
import cn.cug.sxy.types.enums.ResponseCode;
import cn.cug.sxy.types.exception.AppException;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @version 1.0
 * @Date 2025/3/31 20:20
 * @Description 拼团交易 仓储实现
 * @Author jerryhotton
 */

@Repository
public class TradeRepository implements ITradeRepository {

    @Resource
    private IGroupBuyOrderListDao groupBuyOrderListDao;

    @Resource
    private IGroupBuyOrderDao groupBuyOrderDao;

    @Resource
    private IGroupBuyActivityDao groupBuyActivityDao;

    @Override
    public MarketPayOrderEntity queryUnpaidMarketPayOrderByOutTradeNo(String userId, String outTradeNo) {
        GroupBuyOrderList groupBuyOrderListReq = new GroupBuyOrderList();
        groupBuyOrderListReq.setUserId(userId);
        groupBuyOrderListReq.setOutTradeNo(outTradeNo);
        GroupBuyOrderList groupBuyOrderListRes = groupBuyOrderListDao.queryUnpaidMarketPayOrderByOutTradeNo(groupBuyOrderListReq);
        if (null == groupBuyOrderListRes) {
            return null;
        }
        return MarketPayOrderEntity.builder()
                .orderId(groupBuyOrderListRes.getOrderId())
                .originalPrice(groupBuyOrderListRes.getOriginalPrice())
                .payPrice(groupBuyOrderListRes.getPayPrice())
                .discountDeduction(groupBuyOrderListRes.getDiscountDeduction())
                .tradeOrderStatusVO(TradeOrderStatusVO.valueOf(groupBuyOrderListRes.getStatus()))
                .build();
    }

    @Override
    public GroupBuyProgressVO queryGroupBuyProgress(String teamId) {
        GroupBuyOrder groupBuyOrder = groupBuyOrderDao.queryGroupBuyOrderByTeamId(teamId);
        if (null == groupBuyOrder) {
            return null;
        }
        return GroupBuyProgressVO.builder()
                .targetCount(groupBuyOrder.getTargetCount())
                .lockCount(groupBuyOrder.getLockCount())
                .completeCount(groupBuyOrder.getCompleteCount())
                .build();
    }

    @Override
    public GroupBuyActivityEntity queryGroupBuyActivityEntity(Long activityId) {
        GroupBuyActivity groupBuyActivity = groupBuyActivityDao.queryGroupBuyActivityByActivityId(activityId);
        if (null == groupBuyActivity) {
            return null;
        }
        return GroupBuyActivityEntity.builder()
                .activityId(groupBuyActivity.getActivityId())
                .activityName(groupBuyActivity.getActivityName())
                .discountId(groupBuyActivity.getDiscountId())
                .groupType(groupBuyActivity.getGroupType())
                .takeLimitCount(groupBuyActivity.getTakeLimitCount())
                .targetCount(groupBuyActivity.getTargetCount())
                .validTime(groupBuyActivity.getValidTime())
                .status(GroupBuyActivityStatusVO.valueOf(groupBuyActivity.getStatus()))
                .startTime(groupBuyActivity.getStartTime())
                .endTime(groupBuyActivity.getEndTime())
                .build();
    }

    @Override
    public Integer queryActivityParticipationCount(String userId, Long activityId) {
        GroupBuyOrderList groupBuyOrderListReq = new GroupBuyOrderList();
        groupBuyOrderListReq.setUserId(userId);
        groupBuyOrderListReq.setActivityId(activityId);
        List<GroupBuyOrderList> groupBuyOrderLists = groupBuyOrderListDao.queryUserActivityParticipationOrderLists(groupBuyOrderListReq);
        return groupBuyOrderLists.size();
    }

    @Transactional(timeout = 500)
    @Override
    public MarketPayOrderEntity lockMarketPayOrder(GroupBuyOrderAggregate groupBuyOrderAggregate) {
        // 聚合对象信息
        UserEntity userEntity = groupBuyOrderAggregate.getUserEntity();
        PayActivityEntity payActivityEntity = groupBuyOrderAggregate.getPayActivityEntity();
        PayDiscountEntity payDiscountEntity = groupBuyOrderAggregate.getPayDiscountEntity();
        Integer userParticipationCount = groupBuyOrderAggregate.getUserParticipationCount();
        // 判断是否有团 - teamId 为空 - 新团、为不空 - 老团
        String teamId = payActivityEntity.getTeamId();
        if (StringUtils.isBlank(teamId)) {
            teamId = RandomStringUtils.randomAlphabetic(8);
            // 构建拼团订单
            GroupBuyOrder groupBuyOrder = GroupBuyOrder.builder()
                    .teamId(teamId)
                    .activityId(payActivityEntity.getActivityId())
                    .source(payDiscountEntity.getSource())
                    .channel(payDiscountEntity.getChannel())
                    .originalPrice(payDiscountEntity.getOriginalPrice())
                    .discountDeduction(payDiscountEntity.getDiscountDeduction())
                    .payPrice(payDiscountEntity.getOriginalPrice().subtract(payDiscountEntity.getDiscountDeduction()))
                    .targetCount(payActivityEntity.getTargetCount())
                    .completeCount(0)
                    .lockCount(1)
                    .build();
            // 写入记录
            groupBuyOrderDao.insertGroupBuyOrder(groupBuyOrder);
        } else {
            // 更新记录 - 如果更新记录不等于1，则表示拼团已满，抛出异常
            int updateCount = groupBuyOrderDao.updateAddLockCount(teamId);
            if (1 != updateCount) {
                throw new AppException(ResponseCode.E0006.getCode(), ResponseCode.E0006.getInfo());
            }
        }
        // 构建用户拼单明细
        String orderId = RandomStringUtils.randomNumeric(12);
        GroupBuyOrderList groupBuyOrderList = GroupBuyOrderList.builder()
                .userId(userEntity.getUserId())
                .teamId(teamId)
                .orderId(orderId)
                .activityId(payActivityEntity.getActivityId())
                .startTime(payActivityEntity.getStartTime())
                .endTime(payActivityEntity.getEndTime())
                .goodsId(payDiscountEntity.getGoodsId())
                .source(payDiscountEntity.getSource())
                .channel(payDiscountEntity.getChannel())
                .originalPrice(payDiscountEntity.getOriginalPrice())
                .payPrice(payDiscountEntity.getOriginalPrice().subtract(payDiscountEntity.getDiscountDeduction()))
                .discountDeduction(payDiscountEntity.getDiscountDeduction())
                .status(TradeOrderStatusVO.CREATE.getCode())
                .outTradeNo(payDiscountEntity.getOutTradeNo())
                .bizId(payActivityEntity.getActivityId() + Constants.UNDERLINE + userEntity.getUserId() + Constants.UNDERLINE + (userParticipationCount + 1))
                .build();
        try {
            // 写入拼团记录
            groupBuyOrderListDao.insertGroupBuyOrderList(groupBuyOrderList);
        } catch (DuplicateKeyException e) {
            throw new AppException(ResponseCode.INDEX_EXCEPTION.getCode(), ResponseCode.INDEX_EXCEPTION.getInfo(), e);
        }
        return MarketPayOrderEntity.builder()
                .orderId(orderId)
                .originalPrice(payDiscountEntity.getOriginalPrice())
                .payPrice(payDiscountEntity.getOriginalPrice().subtract(payDiscountEntity.getDiscountDeduction()))
                .discountDeduction(payDiscountEntity.getDiscountDeduction())
                .tradeOrderStatusVO(TradeOrderStatusVO.CREATE)
                .build();
    }

}
