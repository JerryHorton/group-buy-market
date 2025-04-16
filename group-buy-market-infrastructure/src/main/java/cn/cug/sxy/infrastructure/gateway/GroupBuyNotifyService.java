package cn.cug.sxy.infrastructure.gateway;

import cn.cug.sxy.types.enums.ResponseCode;
import cn.cug.sxy.types.exception.AppException;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @version 1.0
 * @Date 2025/4/5 15:12
 * @Description 支付商城服务API
 * @Author Sxy
 */

@Slf4j
@Service
public class GroupBuyNotifyService {

    @Resource
    private OkHttpClient okHttpClient;

    /**
     * 拼团通知回调请求
     *
     * @param apiUrl               回调地址
     * @param notifyRequestDTOJSON 请求参数（JSON 格式）
     * @return 回调接口的响应结果字符串
     */
    public String groupBuyNotify(String apiUrl, String notifyRequestDTOJSON) {
        // 构建请求体
        RequestBody body = RequestBody.create(
                notifyRequestDTOJSON,
                MediaType.get("application/json")
        );
        // 构建 HTTP 请求
        Request request = new Request.Builder()
                .url(apiUrl)
                .post(body)
                .header("Content-Type", "application/json")
                .build();

        try (Response response = okHttpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                log.warn("拼团回调请求失败，状态码：{}，url：{}", response.code(), apiUrl);
                throw new AppException(ResponseCode.HTTP_EXCEPTION);
            }

            ResponseBody responseBody = response.body();
            return responseBody != null ? responseBody.string() : "";
        } catch (Exception e) {
            log.error("拼团回调 HTTP 接口服务异常：{}", apiUrl, e);
            throw new AppException(ResponseCode.HTTP_EXCEPTION);
        }
    }

}
