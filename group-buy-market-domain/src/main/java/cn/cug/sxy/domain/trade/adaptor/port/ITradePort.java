package cn.cug.sxy.domain.trade.adaptor.port;

import cn.cug.sxy.domain.trade.model.entity.NotifyTaskEntity;

/**
 * @version 1.0
 * @Date 2025/4/5 15:05
 * @Description 交易接口服务 接口
 * @Author Sxy
 */

public interface ITradePort {

    String groupBuyNotify(NotifyTaskEntity notifyTaskEntity) throws Exception;

}
