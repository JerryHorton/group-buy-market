package cn.cug.sxy.infrastructure.adaptor.repository;

import cn.cug.sxy.infrastructure.dcc.IDCCService;
import cn.cug.sxy.infrastructure.redis.IRedisService;
import cn.cug.sxy.types.common.Constants;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
import java.util.function.Supplier;

/**
 * @version 1.0
 * @Date 2025/4/17 09:54
 * @Description 仓储抽象类 - 提供特定功能
 * @Author jerryhotton
 */

@Slf4j
public abstract class AbstractRepository {

    @Resource
    private IRedisService redisService;

    @Resource
    private IDCCService dccService;

    /**
     * 通用缓存处理方法
     * 缓存未降级则优先从缓存获取，缓存不存在则从数据库获取并写入缓存
     *
     * @param cacheKey 缓存键
     * @param call     数据库查询函数
     * @param <T>      返回类型
     * @return 查询结果
     */
    public  <T> T getDataFromCacheOrDB(String cacheKey, Supplier<T> call) {
        // 判断是否开启缓存
        if (dccService.isCacheSwitch()) {
            // 从缓存获取
            T cacheResult = redisService.getValue(cacheKey);
            if (null != cacheResult) return cacheResult;
            // 缓存中没有则从数据库中获取
            T dbResult = call.get();
            if (null == dbResult) {
                // 数据库没有数据，将空值存入缓存，防止缓存穿透
                redisService.setValue(cacheKey, null, 60 * 1000);
                return null;
            }
            // 写入缓存
            redisService.setValue(cacheKey, dbResult);
            return dbResult;
        } else {
            log.warn("缓存降级 cacheKey:{}", cacheKey);
            return call.get();
        }
    }

    public String cacheKey(Class<?> clazz) {
        return "cacheKey" + Constants.UNDERLINE + clazz.getName();
    }

}
