package com.banne.template.aspect;

import com.banne.template.common.context.BaseContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@Aspect
@Component
@Slf4j
public class LogAspect {

    /**
     * 执行日志 AOP
     */
    @Around("execution(* com.banne.template.controller.*.*(..)) && @annotation(com.banne.template.annotation.Logging)")
    public Object doInterceptor(ProceedingJoinPoint point) throws Throwable {
        // 计时
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        // 获取请求路径
        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
        HttpServletRequest httpServletRequest = ((ServletRequestAttributes) requestAttributes).getRequest();
        // 生成请求唯一 id
        String requestId = UUID.randomUUID().toString();
        String url = httpServletRequest.getRequestURI();
        // 获取请求参数
        Object[] args = point.getArgs();
        String reqParam = "[" + StringUtils.join(args, ", ") + "]";
        // 获取当前操作的用户id
        Long currentUserId = BaseContext.getCurrentId();
        // 获取到操作的方法名称
        String methodName = point.getSignature().getName();
        // 输出请求日志
        log.info("监视重要的操作 request start，id: {}, path: {}, ip: {}, params: {}, userId: {},操作的方法: {}", requestId, url,
                httpServletRequest.getRemoteHost(), reqParam,currentUserId,methodName);

        // 执行原方法
        Object result = point.proceed();

        // 输出响应日志
        stopWatch.stop();
        long totalTimeMillis = stopWatch.getTotalTimeMillis();
        log.info("request end, id: {}, cost: {}ms", requestId, totalTimeMillis);
        return result;
    }
}
