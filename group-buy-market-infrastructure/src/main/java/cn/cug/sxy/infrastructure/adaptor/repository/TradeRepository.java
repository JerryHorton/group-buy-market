package cn.cug.sxy.infrastructure.adaptor.repository;

import cn.cug.sxy.domain.trade.model.aggregate.GroupBuyOrderAggregate;
import cn.cug.sxy.domain.trade.model.aggregate.TradePaySettlementAggregate;
import cn.cug.sxy.domain.trade.model.entity.*;
import cn.cug.sxy.domain.trade.model.valobj.*;
import cn.cug.sxy.domain.trade.adaptor.repository.ITradeRepository;
import cn.cug.sxy.infrastructure.dao.IGroupBuyActivityDao;
import cn.cug.sxy.infrastructure.dao.IGroupBuyOrderDao;
import cn.cug.sxy.infrastructure.dao.IUserGroupBuyOrderDetailDao;
import cn.cug.sxy.infrastructure.dao.INotifyTaskDao;
import cn.cug.sxy.infrastructure.dcc.IDCCService;
import cn.cug.sxy.infrastructure.dao.po.GroupBuyActivity;
import cn.cug.sxy.infrastructure.dao.po.GroupBuyOrder;
import cn.cug.sxy.infrastructure.dao.po.UserGroupBuyOrderDetail;
import cn.cug.sxy.infrastructure.dao.po.NotifyTask;
import cn.cug.sxy.infrastructure.redis.IRedisService;
import cn.cug.sxy.types.common.Constants;
import cn.cug.sxy.types.enums.ResponseCode;
import cn.cug.sxy.types.exception.AppException;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @version 1.0
 * @Date 2025/3/31 20:20
 * @Description 拼团交易 仓储实现
 * @Author jerryhotton
 */

@Slf4j
@Repository
public class TradeRepository implements ITradeRepository {

    @Resource
    private IUserGroupBuyOrderDetailDao userGroupBuyOrderDetailDao;

    @Resource
    private IGroupBuyOrderDao groupBuyOrderDao;

    @Resource
    private IGroupBuyActivityDao groupBuyActivityDao;

    @Resource
    private INotifyTaskDao notifyTaskDao;

    @Resource
    private IDCCService dccService;

    @Resource
    private IRedisService redisService;

    @Override
    public MarketPayOrderEntity queryUnpaidMarketPayOrderByOutTradeNo(String outTradeNo) {
        UserGroupBuyOrderDetail userGroupBuyOrderDetailReq = new UserGroupBuyOrderDetail();
        userGroupBuyOrderDetailReq.setOutTradeNo(outTradeNo);
        UserGroupBuyOrderDetail userGroupBuyOrderDetailRes = userGroupBuyOrderDetailDao.queryUnpaidMarketPayOrderByOutTradeNo(userGroupBuyOrderDetailReq);
        if (null == userGroupBuyOrderDetailRes) {
            return null;
        }
        return MarketPayOrderEntity.builder()
                .teamId(userGroupBuyOrderDetailRes.getTeamId())
                .orderId(userGroupBuyOrderDetailRes.getOrderId())
                .originalPrice(userGroupBuyOrderDetailRes.getOriginalPrice())
                .payPrice(userGroupBuyOrderDetailRes.getPayPrice())
                .discountDeduction(userGroupBuyOrderDetailRes.getDiscountDeduction())
                .tradeOrderStatusVO(TradeOrderStatusVO.valueOf(userGroupBuyOrderDetailRes.getStatus()))
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
        UserGroupBuyOrderDetail userGroupBuyOrderDetailReq = new UserGroupBuyOrderDetail();
        userGroupBuyOrderDetailReq.setUserId(userId);
        userGroupBuyOrderDetailReq.setActivityId(activityId);
        List<UserGroupBuyOrderDetail> userGroupBuyOrderDetails = userGroupBuyOrderDetailDao.queryUserActivityParticipationOrderLists(userGroupBuyOrderDetailReq);
        return userGroupBuyOrderDetails.size();
    }

    @Override
    public GroupBuyOrderEntity queryGroupBuyTeamByTeamByTeamId(String teamId) {
        GroupBuyOrder groupBuyOrder = groupBuyOrderDao.queryGroupBuyOrderByTeamId(teamId);
        return GroupBuyOrderEntity.builder()
                .teamId(groupBuyOrder.getTeamId())
                .activityId(groupBuyOrder.getActivityId())
                .targetCount(groupBuyOrder.getTargetCount())
                .lockCount(groupBuyOrder.getLockCount())
                .completeCount(groupBuyOrder.getCompleteCount())
                .status(GroupBuyOrderStatusVO.valueOf(groupBuyOrder.getStatus()))
                .validStartTime(groupBuyOrder.getValidStartTime())
                .validEndTime(groupBuyOrder.getValidEndTime())
                .notifyType(groupBuyOrder.getNotifyType())
                .notifyTarget(groupBuyOrder.getNotifyTarget())
                .build();
    }

    @Override
    public List<NotifyTaskEntity> queryUnExecuteSuccessNotifyTaskList() {
        return queryUnExecuteSuccessNotifyTaskList(null);
    }

    @Override
    public List<NotifyTaskEntity> queryUnExecuteSuccessNotifyTaskList(String teamId) {
        List<NotifyTask> notifyTaskList = notifyTaskDao.queryUnExecuteSuccessNotifyTaskList(teamId);
        List<NotifyTaskEntity> notifyTaskEntityList = new ArrayList<>();
        for (NotifyTask notifyTask : notifyTaskList) {
            notifyTaskEntityList.add(NotifyTaskEntity.builder()
                    .teamId(notifyTask.getTeamId())
                    .notifyType(notifyTask.getNotifyType())
                    .notifyTarget(notifyTask.getNotifyTarget())
                    .parameterJson(notifyTask.getParameterJson())
                    .notifyCount(notifyTask.getNotifyCount())
                    .build());
        }
        return notifyTaskEntityList;
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
            // 生成拼团ID
            teamId = RandomStringUtils.randomAlphabetic(8);
            // 日期处理
            Date currentDate = new Date();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(currentDate);
            calendar.add(Calendar.MINUTE, payActivityEntity.getValidTime());

            // 构建拼团订单
            GroupBuyOrder groupBuyOrder = GroupBuyOrder.builder()
                    .teamId(teamId)
                    .activityId(payActivityEntity.getActivityId())
                    .goodsId(payDiscountEntity.getGoodsId())
                    .source(payDiscountEntity.getSource())
                    .channel(payDiscountEntity.getChannel())
                    .originalPrice(payDiscountEntity.getOriginalPrice())
                    .discountDeduction(payDiscountEntity.getDiscountDeduction())
                    .payPrice(payDiscountEntity.getOriginalPrice().subtract(payDiscountEntity.getDiscountDeduction()))
                    .targetCount(payActivityEntity.getTargetCount())
                    .completeCount(0)
                    .lockCount(1)
                    .validStartTime(currentDate)
                    .validEndTime(calendar.getTime())
                    .notifyType(payDiscountEntity.getNotifyType())
                    .notifyTarget(payDiscountEntity.getNotifyTarget())
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
        UserGroupBuyOrderDetail userGroupBuyOrderDetail = UserGroupBuyOrderDetail.builder()
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
            userGroupBuyOrderDetailDao.insertGroupBuyOrderList(userGroupBuyOrderDetail);
        } catch (DuplicateKeyException e) {
            throw new AppException(ResponseCode.INDEX_EXCEPTION.getCode(), ResponseCode.INDEX_EXCEPTION.getInfo(), e);
        } catch (Exception e) {
            throw new AppException("写入拼团记录失败", e);
        }
        return MarketPayOrderEntity.builder()
                .orderId(orderId)
                .originalPrice(payDiscountEntity.getOriginalPrice())
                .payPrice(payDiscountEntity.getOriginalPrice().subtract(payDiscountEntity.getDiscountDeduction()))
                .discountDeduction(payDiscountEntity.getDiscountDeduction())
                .tradeOrderStatusVO(TradeOrderStatusVO.CREATE)
                .build();
    }

    @Transactional(timeout = 500)
    @Override
    public NotifyTaskEntity settlementMarketPayOrder(TradePaySettlementAggregate tradePaySettlementAggregate) {
        UserEntity userEntity = tradePaySettlementAggregate.getUserEntity();
        GroupBuyOrderEntity groupBuyOrderEntity = tradePaySettlementAggregate.getGroupBuyOrderEntity();
        TradePaySuccessEntity tradePaySuccessEntity = tradePaySettlementAggregate.getTradePaySuccessEntity();

        // 1. 更新用户拼团明细状态
        UserGroupBuyOrderDetail userGroupBuyOrderDetailReq = new UserGroupBuyOrderDetail();
        userGroupBuyOrderDetailReq.setOutTradeNo(tradePaySuccessEntity.getOutTradeNo());
        userGroupBuyOrderDetailReq.setOutTradeTime(tradePaySuccessEntity.getOutTradeTime());
        userGroupBuyOrderDetailReq.setStatus(TradeOrderStatusVO.COMPLETE.getCode());
        userGroupBuyOrderDetailDao.updateUserGroupBuyOrderDetailStatus(userGroupBuyOrderDetailReq);

        // 2. 更新拼团达成数量
        int updateOrderListCount = groupBuyOrderDao.updateAddCompleteCount(groupBuyOrderEntity.getTeamId());
        if (1 != updateOrderListCount) {
            throw new AppException(ResponseCode.UPDATE_ZERO);
        }

        // 3. 更新拼团完成状态
        if (groupBuyOrderEntity.getCompleteCount() + 1 == groupBuyOrderEntity.getTargetCount()) {
            GroupBuyOrder groupBuyOrder = new GroupBuyOrder();
            groupBuyOrder.setTeamId(groupBuyOrderEntity.getTeamId());
            groupBuyOrder.setStatus(GroupBuyOrderStatusVO.SUCCESS.getCode());
            int updateOrderCount = groupBuyOrderDao.updateGroupBuyOrderStatus(groupBuyOrder);
            if (1 != updateOrderCount) {
                throw new AppException(ResponseCode.UPDATE_ZERO);
            }

            // 3.1 查询拼团交易完成外部单号列表
            List<String> outTradeNoList = userGroupBuyOrderDetailDao.queryGroupBuyOrderListOutTradeNo(groupBuyOrderEntity.getTeamId());

            // 3.2 拼团完成写入回调任务记录
            NotifyTask notifyTask = new NotifyTask();
            notifyTask.setActivityId(groupBuyOrderEntity.getActivityId());
            notifyTask.setTeamId(groupBuyOrderEntity.getTeamId());
            notifyTask.setNotifyCount(0);
            notifyTask.setNotifyStatus(NotifyTaskStatusVO.INIT.getCode());
            notifyTask.setNotifyType(groupBuyOrderEntity.getNotifyType());
            notifyTask.setNotifyTarget(groupBuyOrderEntity.getNotifyTarget());
            notifyTask.setParameterJson(JSON.toJSONString(new HashMap<String, Object>() {{
                put("teamId", groupBuyOrderEntity.getTeamId());
                put("outTradeNoList", outTradeNoList);
            }}));
            notifyTaskDao.insertNotifyTask(notifyTask);

            return NotifyTaskEntity.builder()
                    .teamId(notifyTask.getTeamId())
                    .notifyType(notifyTask.getNotifyType())
                    .notifyTarget(notifyTask.getNotifyTarget())
                    .notifyCount(notifyTask.getNotifyCount())
                    .parameterJson(notifyTask.getParameterJson())
                    .build();
        }
        return null;
    }

    @Override
    public Boolean isSCBlackListIntercept(String source, String channel) {
        return dccService.isSCBlackListIntercept(source, channel);
    }

    @Override
    public int updateNotifyTaskStatusSuccess(String teamId) {
        NotifyTask notifyTaskReq = new NotifyTask();
        notifyTaskReq.setTeamId(teamId);
        notifyTaskReq.setNotifyStatus(NotifyTaskStatusVO.SUCCESS.getCode());
        return notifyTaskDao.updateNotifyTaskStatus(notifyTaskReq);
    }

    @Override
    public int updateNotifyTaskStatusRetry(String teamId) {
        NotifyTask notifyTaskReq = new NotifyTask();
        notifyTaskReq.setTeamId(teamId);
        notifyTaskReq.setNotifyStatus(NotifyTaskStatusVO.RETRY.getCode());
        return notifyTaskDao.updateNotifyTaskStatus(notifyTaskReq);
    }

    @Override
    public int updateNotifyTaskStatusError(String teamId) {
        NotifyTask notifyTaskReq = new NotifyTask();
        notifyTaskReq.setTeamId(teamId);
        notifyTaskReq.setNotifyStatus(NotifyTaskStatusVO.ERROR.getCode());
        return notifyTaskDao.updateNotifyTaskStatus(notifyTaskReq);
    }

    @Override
    public boolean occupyTeamStock(String teamStockKey, String recoveryTeamStockKey, Integer targetCount, Integer validTime) {
        // 1. 获取恢复量
        Long recoveryCount = redisService.getAtomicLong(recoveryTeamStockKey);
        recoveryCount = recoveryCount == null ? 0L : recoveryCount;
        // 2. 获取占用号
        long occupyNo = redisService.incr(teamStockKey) + 1;
        // 3. 判断是否超额（总库存 + 恢复补偿）
        if (occupyNo > targetCount + recoveryCount) {
            redisService.setAtomicLong(teamStockKey, targetCount);
            return false;
        }
        // 4. 尝试为该编号加锁，防止并发冲突
        String occupyLockKey = teamStockKey + Constants.UNDERLINE + occupyNo;
        boolean isLocked = redisService.setNx(occupyLockKey, validTime + 60, TimeUnit.MINUTES);
        if (!isLocked) {
            // 加锁失败，记录为恢复量
            redisService.incr(recoveryTeamStockKey);
            return false;
        }
        // 成功占用
        return true;
    }

    @Override
    public void recoveryTeamStock(String recoveryTeamStockKey) {
        // 首次组队拼团 无 teamId，不做处理
        if (StringUtils.isBlank(recoveryTeamStockKey)) return;
        redisService.incr(recoveryTeamStockKey);
    }

}
