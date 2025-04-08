package cn.cug.sxy.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @version 1.0
 * @Date 2025/4/7 11:30
 * @Description 拼团营销配置查询响应对象
 * @Author jerryhotton
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GroupBuyMarketConfigResponseDTO {

    /**
     * 商品信息
     */
    private Goods goods;
    /**
     * 拼团队伍信息
     */
    private List<Team> teamList;
    /**
     * 组队统计
     */
    private TeamStatistic teamStatistic;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Goods {

        /**
         * 商品ID
         */
        private String goodsId;
        /**
         * 原始价格
         */
        private BigDecimal originalPrice;
        /**
         * 折扣金额
         */
        private BigDecimal discountDeduction;
        /**
         * 支付价格
         */
        private BigDecimal payPrice;

    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Team {

        /**
         * 用户ID
         */
        private String userId;
        /**
         * 拼单组队ID
         */
        private String teamId;
        /**
         * 活动ID
         */
        private Long activityId;
        /**
         * 拼团目标人数
         */
        private Integer targetCount;
        /**
         * 完成数量
         */
        private Integer completeCount;
        /**
         * 锁单数量
         */
        private Integer lockCount;
        /**
         * 拼团开始时间
         */
        private Date validStartTime;
        /**
         * 拼团结束时间
         */
        private Date validEndTime;
        /**
         * 拼团倒计时(字符串)
         */
        private String validTimeCountdown;
        /**
         * 外部交易单号-确保外部调用唯一幂等
         */
        private String outTradeNo;

        public static String calculateValidTimeCountdown(Date validStartTime, Date validEndTime) {
            if (null == validStartTime || null == validEndTime) {
                return "无效时间";
            }
            long diff = validEndTime.getTime() - validStartTime.getTime();
            if (diff < 0) {
                return "已结束";
            }
            long seconds = TimeUnit.MILLISECONDS.toSeconds(diff) % 60;
            long minutes = TimeUnit.MILLISECONDS.toMinutes(diff) % 60;
            long hours = TimeUnit.MILLISECONDS.toHours(diff) % 24;

            return String.format("%02d:%02d:%02d", hours, minutes, seconds);
        }

    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class TeamStatistic {

        /**
         * 当前商品开团总数
         */
        private Integer teamCreatedCount;
        /**
         * 当前商品成团总数
         */
        private Integer teamCompletedCount;
        /**
         * 当前商品总参团人数
         */
        private Integer totalTeamUserCount;

    }

}
