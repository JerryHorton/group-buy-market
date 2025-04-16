package cn.cug.sxy.infrastructure.dcc;

import cn.cug.sxy.types.annotation.DCCValue;
import cn.cug.sxy.types.common.Constants;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

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

    @DCCValue("scBlackList:SOURCE_002-CHANNEL_002")
    private String scBlackList;

    public Boolean isDegradeSwitch() {
        return "1".equals(degradeSwitch);
    }

    public Boolean isCutRange(String userId) {
        int hashCode = Math.abs(userId.hashCode());
        int range = Integer.parseInt(cutRange);
        int lastTwoDigits = hashCode % 100;
        return lastTwoDigits <= range;
    }

    @Override
    public Boolean isSCBlackListIntercept(String source, String channel) {
        List<String> list = Arrays.asList(scBlackList.split(Constants.SPLIT));
        return list.contains(source + Constants.HYPHEN + channel);
    }

}
