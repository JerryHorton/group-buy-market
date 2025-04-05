package cn.cug.sxy.config;

import okhttp3.OkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @version 1.0
 * @Date 2025/4/5 15:03
 * @Description http 框架
 * @Author Sxy
 */

@Configuration
public class OKHttpClientConfig {

    @Bean
    public OkHttpClient okHttpClient() {
        return new OkHttpClient();
    }

}
