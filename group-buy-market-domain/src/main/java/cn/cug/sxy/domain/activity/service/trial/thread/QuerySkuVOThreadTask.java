package cn.cug.sxy.domain.activity.service.trial.thread;

import cn.cug.sxy.domain.activity.model.valobj.SkuVO;
import cn.cug.sxy.domain.activity.repository.IActivityRepository;

import java.util.concurrent.Callable;

/**
 * @version 1.0
 * @Date 2025/3/28 20:34
 * @Description 查询商品信息任务
 * @Author jerryhotton
 */

public class QuerySkuVOThreadTask implements Callable<SkuVO> {

    private final String goodsId;

    private final IActivityRepository activityRepository;

    public QuerySkuVOThreadTask(String goodsId, IActivityRepository activityRepository) {
        this.goodsId = goodsId;
        this.activityRepository = activityRepository;
    }

    @Override
    public SkuVO call() throws Exception {
        return activityRepository.querySkuVOByGoodsId(goodsId);
    }

}
