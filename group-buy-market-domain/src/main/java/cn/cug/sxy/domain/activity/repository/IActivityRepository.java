package cn.cug.sxy.domain.activity.repository;

import cn.cug.sxy.domain.activity.model.valobj.GroupBuyActivityVO;
import cn.cug.sxy.domain.activity.model.valobj.SkuVO;

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

}
