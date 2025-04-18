package cn.cug.sxy.infrastructure.dcc;

import org.springframework.stereotype.Service;

/**
 * @version 1.0
 * @Date 2025/3/31 10:26
 * @Description 动态配置中心 服务接口
 * @Author jerryhotton
 */

@Service
public interface IDCCService {

    Boolean isDegradeSwitch();

    Boolean isCutRange(String userId);

    Boolean isSCBlackListIntercept(String source, String channel);

    boolean isCacheSwitch();

}
