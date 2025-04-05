package cn.cug.sxy.api;

import cn.cug.sxy.api.dto.NotifyRequestDTO;

/**
 * @version 1.0
 * @Date 2025/4/5 14:53
 * @Description 回调服务接口测试
 * @Author Sxy
 */

public interface ITestApiClientService {

    /**
     * 模拟回调案例
     *
     * @param notifyRequestDTO 通知回调参数
     * @return success 成功，error 失败
     */
    String groupBuyNotify(NotifyRequestDTO notifyRequestDTO);

}
