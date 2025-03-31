package cn.cug.sxy.api;

import cn.cug.sxy.api.response.Response;

/**
 * @version 1.0
 * @Date 2025/3/31 11:36
 * @Description DCC 动态配置中心 外部接口
 * @Author jerryhotton
 */

public interface IDCCService {

    Response<Boolean> updateConfig(String key, String value);

}
