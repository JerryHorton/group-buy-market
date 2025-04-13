package cn.cug.sxy.trigger.http;

import cn.cug.sxy.api.IMarketIndexService;
import cn.cug.sxy.api.dto.GroupBuyMarketConfigRequestDTO;
import cn.cug.sxy.api.dto.GroupBuyMarketConfigResponseDTO;
import cn.cug.sxy.api.response.Response;
import cn.cug.sxy.domain.activity.model.entity.MarketProductEntity;
import cn.cug.sxy.domain.activity.model.entity.SCSkuActivityEntity;
import cn.cug.sxy.domain.activity.model.entity.TrialBalanceEntity;
import cn.cug.sxy.domain.activity.model.entity.UserGroupBuyOrderDetailEntity;
import cn.cug.sxy.domain.activity.model.valobj.TeamStatisticVO;
import cn.cug.sxy.domain.activity.service.index.IIndexGroupBuyMarketService;
import cn.cug.sxy.types.enums.ResponseCode;
import com.alibaba.fastjson2.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @version 1.0
 * @Date 2025/4/7 12:03
 * @Description 营销首页服务
 * @Author jerryhotton
 */

@Slf4j
@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1/gbm/index/")
public class MarketIndexController implements IMarketIndexService {

    @Resource
    private IIndexGroupBuyMarketService indexGroupBuyMarketService;

    @RequestMapping(value = "query_group_buy_market_config", method = RequestMethod.POST)
    @Override
    public Response<GroupBuyMarketConfigResponseDTO> queryGroupBuyMarketConfig(@RequestBody GroupBuyMarketConfigRequestDTO requestDTO) {
        try {
            log.info("查询营销配置开始, userId:{}, goodsId:{}, channel:{}, source:{}", requestDTO.getUserId(), requestDTO.getGoodsId(), requestDTO.getChannel(), requestDTO.getSource());

            if (StringUtils.isBlank(requestDTO.getUserId()) || StringUtils.isBlank(requestDTO.getGoodsId()) ||
                    StringUtils.isBlank(requestDTO.getChannel()) || StringUtils.isBlank(requestDTO.getSource())) {
                return Response.<GroupBuyMarketConfigResponseDTO>builder()
                        .code(ResponseCode.ILLEGAL_PARAMETER.getCode())
                        .info(ResponseCode.ILLEGAL_PARAMETER.getInfo())
                        .build();
            }
            // 1. 营销优惠试算
            TrialBalanceEntity trialBalanceEntity = indexGroupBuyMarketService.indexMarketTrial(MarketProductEntity.builder()
                    .userId(requestDTO.getUserId())
                    .goodsId(requestDTO.getGoodsId())
                    .source(requestDTO.getSource())
                    .channel(requestDTO.getChannel())
                    .build());

            // 2. 查询拼团组队
            List<UserGroupBuyOrderDetailEntity> userGroupBuyOrderDetailEntityList = indexGroupBuyMarketService.queryOngoingGroupOrderDetail(SCSkuActivityEntity.builder()
                    .source(requestDTO.getSource())
                    .channel(requestDTO.getChannel())
                    .goodsId(requestDTO.getGoodsId())
                    .build(), requestDTO.getUserId(), 1, 2);

            // 3. 统计拼团数据
            TeamStatisticVO teamStatisticVO = indexGroupBuyMarketService.queryTeamStatistic(SCSkuActivityEntity.builder()
                    .source(requestDTO.getSource())
                    .channel(requestDTO.getChannel())
                    .goodsId(requestDTO.getGoodsId())
                    .build());

            GroupBuyMarketConfigResponseDTO.Goods goods = GroupBuyMarketConfigResponseDTO.Goods.builder()
                    .goodsId(trialBalanceEntity.getGoodsId())
                    .originalPrice(trialBalanceEntity.getOriginalPrice())
                    .discountDeduction(trialBalanceEntity.getDiscountDeduction())
                    .payPrice(trialBalanceEntity.getFinalPrice())
                    .build();

            List<GroupBuyMarketConfigResponseDTO.Team> teams = new ArrayList<>();
            if (null != userGroupBuyOrderDetailEntityList && !userGroupBuyOrderDetailEntityList.isEmpty()) {
                for (UserGroupBuyOrderDetailEntity userGroupBuyOrderDetailEntity : userGroupBuyOrderDetailEntityList) {
                    GroupBuyMarketConfigResponseDTO.Team team = GroupBuyMarketConfigResponseDTO.Team.builder()
                            .userId(userGroupBuyOrderDetailEntity.getUserId())
                            .teamId(userGroupBuyOrderDetailEntity.getTeamId())
                            .activityId(userGroupBuyOrderDetailEntity.getActivityId())
                            .targetCount(userGroupBuyOrderDetailEntity.getTargetCount())
                            .completeCount(userGroupBuyOrderDetailEntity.getCompleteCount())
                            .lockCount(userGroupBuyOrderDetailEntity.getLockCount())
                            .validStartTime(userGroupBuyOrderDetailEntity.getValidStartTime())
                            .validEndTime(userGroupBuyOrderDetailEntity.getValidEndTime())
                            .validTimeCountdown(GroupBuyMarketConfigResponseDTO.Team.calculateValidTimeCountdown(new Date(), userGroupBuyOrderDetailEntity.getValidEndTime()))
                            .outTradeNo(userGroupBuyOrderDetailEntity.getOutTradeNo())
                            .build();
                    teams.add(team);
                }
            }

            GroupBuyMarketConfigResponseDTO.TeamStatistic teamStatistic = GroupBuyMarketConfigResponseDTO.TeamStatistic.builder()
                    .teamCreatedCount(teamStatisticVO.getTeamCreatedCount())
                    .teamCompletedCount(teamStatisticVO.getTeamCompletedCount())
                    .totalTeamUserCount(teamStatisticVO.getTotalTeamUserCount())
                    .build();

            Response<GroupBuyMarketConfigResponseDTO> response = Response.<GroupBuyMarketConfigResponseDTO>builder()
                    .code(ResponseCode.SUCCESS.getCode())
                    .info(ResponseCode.SUCCESS.getInfo())
                    .data(GroupBuyMarketConfigResponseDTO.builder()
                            .goods(goods)
                            .teamList(teams)
                            .teamStatistic(teamStatistic)
                            .build())
                    .build();

            log.info("查询拼团营销配置完成, userId:{} goodsId:{} response:{}", requestDTO.getUserId(), requestDTO.getGoodsId(), JSON.toJSONString(response));

            return response;
        } catch (Exception e) {
            log.error("查询拼团营销配置失败, userId:{} goodsId:{}", requestDTO.getUserId(), requestDTO.getGoodsId(), e);
            return Response.<GroupBuyMarketConfigResponseDTO>builder()
                    .code(ResponseCode.UN_ERROR.getCode())
                    .info(ResponseCode.UN_ERROR.getInfo())
                    .build();
        }
    }

}
