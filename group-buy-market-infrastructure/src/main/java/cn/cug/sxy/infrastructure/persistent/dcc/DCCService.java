package cn.cug.sxy.infrastructure.persistent.dcc;

import cn.cug.sxy.types.annotation.DCCValue;
import org.springframework.stereotype.Service;

/**
 * @version 1.0
 * @Date 2025/3/31 10:26
 * @Description 动态配置中心 服务实现
 * @Author jerryhotton
 */

@Service
public class DCCService implements IDCCService {

    @DCCValue("degradeSwitch:0")
    private String degradeSwitch;

    @DCCValue("cutRange:100")
    private String cutRange;

    public Boolean isDegradeSwitch() {
        return "1".equals(degradeSwitch);
    }

    public Boolean isCutRange(String userId) {
        int hashCode = Math.abs(userId.hashCode());
        int range = Integer.parseInt(cutRange);
        int lastTwoDigits = hashCode % 100;
        return lastTwoDigits <= range;
    }

}
