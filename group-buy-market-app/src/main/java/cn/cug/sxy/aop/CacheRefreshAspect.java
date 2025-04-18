package cn.cug.sxy.aop;

import cn.cug.sxy.infrastructure.redis.IRedisService;
import cn.cug.sxy.types.annotation.CacheRefresh;
import cn.cug.sxy.types.common.Constants;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @version 1.0
 * @Date 2025/4/17 14:15
 * @Description redis缓存刷新切面
 * @Author jerryhotton
 */

@Aspect
@Component
public class CacheRefreshAspect {

    @Resource
    private IRedisService redisService;

    @Pointcut("@annotation(cn.cug.sxy.types.annotation.CacheRefresh)")
    public void aopPoint() {
    }

    @Around("aopPoint() && @annotation(cacheRefresh)")
    public Object refresh(ProceedingJoinPoint jp, CacheRefresh cacheRefresh) throws Throwable {
        Class<?> aClass = cacheRefresh.type();
        String cacheKey = cacheKey(aClass);
        redisService.remove(cacheKey);
        return jp.proceed();
    }

    private String cacheKey(Class<?> aClass) {
        return "cacheKey" + Constants.UNDERLINE + aClass.getName();
    }

}
