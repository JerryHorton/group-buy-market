package cn.cug.sxy.trigger.http;

import cn.cug.sxy.api.IMarketTradeService;
import cn.cug.sxy.api.dto.LockMarketPayOrderRequestDTO;
import cn.cug.sxy.api.dto.LockMarketPayOrderResponseDTO;
import cn.cug.sxy.api.dto.SettlementMarketPayOrderRequestDTO;
import cn.cug.sxy.api.dto.SettlementMarketPayOrderResponseDTO;
import cn.cug.sxy.api.response.Response;
import cn.cug.sxy.domain.activity.model.entity.MarketProductEntity;
import cn.cug.sxy.domain.activity.model.entity.TrialBalanceEntity;
import cn.cug.sxy.domain.activity.model.valobj.GroupBuyActivityVO;
import cn.cug.sxy.domain.activity.service.index.IIndexGroupBuyMarketService;
import cn.cug.sxy.domain.trade.model.entity.*;
import cn.cug.sxy.domain.trade.model.valobj.GroupBuyProgressVO;
import cn.cug.sxy.domain.trade.service.ITradeLockOrderService;
import cn.cug.sxy.domain.trade.service.ITradeSettlementOrderService;
import cn.cug.sxy.types.enums.ResponseCode;
import cn.cug.sxy.types.exception.AppException;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Objects;

/**
 * @version 1.0
 * @Date 2025/3/31 21:46
 * @Description 拼团交易
 * @Author jerryhotton
 */

@Slf4j
@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1/gbm/trade/")
public class MarketTradeController implements IMarketTradeService {

    @Resource
    private ITradeLockOrderService tradeOrderService;

    @Resource
    private IIndexGroupBuyMarketService indexGroupBuyMarketService;

    @Resource
    private ITradeSettlementOrderService tradeSettlementOrderService;

    @RequestMapping(value = "lock_market_pay_order", method = RequestMethod.POST)
    @Override
    public Response<LockMarketPayOrderResponseDTO> lockMarketPayOrder(@RequestBody LockMarketPayOrderRequestDTO requestDTO) throws Exception {
        try {
            // 参数
            String userId = requestDTO.getUserId();
            String source = requestDTO.getSource();
            String channel = requestDTO.getChannel();
            String goodsId = requestDTO.getGoodsId();
            Long activityId = requestDTO.getActivityId();
            String outTradeNo = requestDTO.getOutTradeNo();
            String teamId = requestDTO.getTeamId();
            String notifyUrl = requestDTO.getNotifyUrl();
            log.info("营销交易锁单:{} LockMarketPayOrderRequestDTO:{}", userId, JSON.toJSONString(requestDTO));
            if (StringUtils.isBlank(userId) || StringUtils.isBlank(source) ||
                    StringUtils.isBlank(channel) || StringUtils.isBlank(goodsId) ||
                    StringUtils.isBlank(goodsId) || StringUtils.isBlank(outTradeNo) ||
                    StringUtils.isBlank(notifyUrl) ||
                    null == activityId) {
                return Response.<LockMarketPayOrderResponseDTO>builder()
                        .code(ResponseCode.ILLEGAL_PARAMETER.getCode())
                        .info(ResponseCode.ILLEGAL_PARAMETER.getInfo())
                        .build();
            }
            // 查询 outTradeNo 是否已经存在交易记录
            MarketPayOrderEntity marketPayOrderEntity = tradeOrderService.queryUnpaidMarketPayOrderByOutTradeNo(userId, outTradeNo);
            if (null != marketPayOrderEntity) {
                LockMarketPayOrderResponseDTO lockMarketPayOrderResponseDTO = LockMarketPayOrderResponseDTO.builder()
                        .orderId(marketPayOrderEntity.getOrderId())
                        .discountedPrice(marketPayOrderEntity.getPayPrice())
                        .tradeOrderStatus(marketPayOrderEntity.getTradeOrderStatusVO().getCode())
                        .build();

                log.info("交易锁单记录(存在):{} marketPayOrderEntity:{}", userId, JSON.toJSONString(marketPayOrderEntity));
                return Response.<LockMarketPayOrderResponseDTO>builder()
                        .code(ResponseCode.SUCCESS.getCode())
                        .info(ResponseCode.SUCCESS.getInfo())
                        .data(lockMarketPayOrderResponseDTO)
                        .build();
            }
            // 判断拼团锁单是否完成了目标
            if (null != teamId) {
                GroupBuyProgressVO groupBuyProgressVO = tradeOrderService.queryGroupBuyProgress(teamId);
                if (null != groupBuyProgressVO && Objects.equals(groupBuyProgressVO.getTargetCount(), groupBuyProgressVO.getLockCount())) {
                    log.info("交易锁单拦截-拼单目标已达成，不可参与拼单 userId:{} teamId:{}", userId, teamId);
                    return Response.<LockMarketPayOrderResponseDTO>builder()
                            .code(ResponseCode.E0006.getCode())
                            .info(ResponseCode.E0006.getInfo())
                            .build();
                }
            }
            // 营销优惠试算
            TrialBalanceEntity trialBalanceEntity = indexGroupBuyMarketService.indexMarketTrial(MarketProductEntity.builder()
                    .userId(userId)
                    .source(source)
                    .channel(channel)
                    .goodsId(goodsId)
                    .build());
            GroupBuyActivityVO groupBuyActivityVO = trialBalanceEntity.getGroupBuyActivityVO();
            // 锁单
            marketPayOrderEntity = tradeOrderService.lockMarketPayOrder(
                    UserEntity.builder().userId(userId).build(),
                    PayActivityEntity.builder()
                            .teamId(teamId)
                            .activityId(activityId)
                            .activityName(groupBuyActivityVO.getActivityName())
                            .startTime(groupBuyActivityVO.getStartTime())
                            .endTime(groupBuyActivityVO.getEndTime())
                            .targetCount(groupBuyActivityVO.getTargetCount())
                            .validTime(groupBuyActivityVO.getValidTime())
                            .build(),
                    PayDiscountEntity.builder()
                            .source(source)
                            .channel(channel)
                            .goodsId(goodsId)
                            .goodsName(trialBalanceEntity.getGoodsName())
                            .originalPrice(trialBalanceEntity.getOriginalPrice())
                            .discountDeduction(trialBalanceEntity.getDiscountDeduction())
                            .outTradeNo(outTradeNo)
                            .notifyUrl(notifyUrl)
                            .build());
            log.info("交易锁单记录(新):{} marketPayOrderEntity:{}", userId, JSON.toJSONString(marketPayOrderEntity));
            // 返回结果
            return Response.<LockMarketPayOrderResponseDTO>builder()
                    .code(ResponseCode.SUCCESS.getCode())
                    .info(ResponseCode.SUCCESS.getInfo())
                    .data(LockMarketPayOrderResponseDTO.builder()
                            .orderId(marketPayOrderEntity.getOrderId())
                            .discountedPrice(marketPayOrderEntity.getPayPrice())
                            .tradeOrderStatus(marketPayOrderEntity.getTradeOrderStatusVO().getCode())
                            .build())
                    .build();
        } catch (AppException e) {
            log.error("营销交易锁单业务异常, userId:{} requestDTO:{}", requestDTO.getUserId(), JSON.toJSONString(requestDTO), e);
            return Response.<LockMarketPayOrderResponseDTO>builder()
                    .code(e.getCode())
                    .info(e.getInfo())
                    .build();
        } catch (Exception e) {
            log.error("营销交易锁单服务失败, userId:{} requestDTO:{}", requestDTO.getUserId(), JSON.toJSONString(requestDTO), e);
            return Response.<LockMarketPayOrderResponseDTO>builder()
                    .code(ResponseCode.UN_ERROR.getCode())
                    .info(ResponseCode.UN_ERROR.getInfo())
                    .build();
        }
    }


    @RequestMapping(value = "settlement_market_pay_order", method = RequestMethod.POST)
    @Override
    public Response<SettlementMarketPayOrderResponseDTO> settlementMarketPayOrder(@RequestBody SettlementMarketPayOrderRequestDTO requestDTO) {
        try {
            log.error("营销交易结算业务开始, userId:{} requestDTO:{}", requestDTO.getUserId(), JSON.toJSONString(requestDTO));

            // 获取请求参数
            String source = requestDTO.getSource();
            String channel = requestDTO.getChannel();
            String userId = requestDTO.getUserId();
            String outTradeNo = requestDTO.getOutTradeNo();
            Date outTradeTime = requestDTO.getOutTradeTime();
            if (StringUtils.isBlank(source) || StringUtils.isBlank(channel) ||
                    StringUtils.isBlank(userId) || StringUtils.isBlank(outTradeNo) ||
                    null == outTradeTime) {
                return Response.<SettlementMarketPayOrderResponseDTO>builder()
                        .code(ResponseCode.ILLEGAL_PARAMETER.getCode())
                        .info(ResponseCode.ILLEGAL_PARAMETER.getInfo())
                        .build();
            }
            // 结算服务
            TradePaySettlementEntity tradePaySettlementEntity = tradeSettlementOrderService.settlementMarketPayOrder(TradePaySuccessEntity.builder()
                    .source(source)
                    .channel(channel)
                    .userId(userId)
                    .outTradeNo(outTradeNo)
                    .outTradeTime(outTradeTime)
                    .build());
            log.error("营销交易结算业务完成, userId:{} tradePaySettlementEntity:{}", requestDTO.getUserId(), JSON.toJSONString(tradePaySettlementEntity));

            return Response.<SettlementMarketPayOrderResponseDTO>builder()
                    .code(ResponseCode.SUCCESS.getCode())
                    .info(ResponseCode.SUCCESS.getInfo())
                    .data(SettlementMarketPayOrderResponseDTO.builder()
                            .userId(tradePaySettlementEntity.getUserId())
                            .activityId(tradePaySettlementEntity.getActivityId())
                            .teamId(tradePaySettlementEntity.getTeamId())
                            .outTradeNo(tradePaySettlementEntity.getOutTradeNo())
                            .build())
                    .build();
        } catch (AppException e) {
            log.error("营销交易结算业务异常, userId:{} requestDTO:{}", requestDTO.getUserId(), JSON.toJSONString(requestDTO), e);
            return Response.<SettlementMarketPayOrderResponseDTO>builder()
                    .info(e.getCode())
                    .info(e.getInfo())
                    .build();
        } catch (Exception e) {
            log.error("营销交易结算业务失败, userId:{} requestDTO:{}", requestDTO.getUserId(), JSON.toJSONString(requestDTO), e);
            return Response.<SettlementMarketPayOrderResponseDTO>builder()
                    .info(ResponseCode.UN_ERROR.getCode())
                    .info(ResponseCode.UN_ERROR.getInfo())
                    .build();
        }
    }

}
