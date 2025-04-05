package cn.cug.sxy.trigger.http;

import cn.cug.sxy.api.ITestApiClientService;
import cn.cug.sxy.api.dto.NotifyRequestDTO;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @version 1.0
 * @Date 2025/4/5 14:55
 * @Description 模拟拼团成功回调
 * @Author Sxy
 */

@Slf4j
@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1/test/")
public class TestApiClientController implements ITestApiClientService {

    @RequestMapping(value = "group_buy_notify", method = RequestMethod.POST)
    @Override
    public String groupBuyNotify(NotifyRequestDTO notifyRequestDTO) {
        log.info("模拟测试第三方服务接收拼团回调 {}", JSON.toJSONString(notifyRequestDTO));

        return "success";
    }

}
