package cn.cug.sxy.domain.activity.model.valobj;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @version 1.0
 * @Date 2025/4/7 19:56
 * @Description 拼团统计信息 值对象
 * @Author jerryhotton
 */

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TeamStatisticVO {

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
