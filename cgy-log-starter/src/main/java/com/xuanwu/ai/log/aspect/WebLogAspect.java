package com.xuanwu.ai.log.aspect;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.ui.Model;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

@Aspect
@Slf4j
public class WebLogAspect {

    private final String httpStartTime = "HTTP_START_TIME";

    @Pointcut("execution(public com.dashuf.merlin.web.base.views.ResponseVo com.dashuf..*Controller.*(..))")
    public void webLog() {
    }

    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {
        try {
            if (!log.isDebugEnabled()) {
                return;
            }
            // 接收到请求，记录请求内容
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (attributes == null) {
                return;
            }
            HttpServletRequest request = attributes.getRequest();
            request.setAttribute(httpStartTime, System.currentTimeMillis());

            StringBuilder sb = new StringBuilder(1000);
            sb.append("\n-----------------------------开始调用:-----------------------------\n");
            sb.append("Controller: ").append(joinPoint.getSignature().getDeclaringType()).append("\n");
            sb.append("Method    : ").append(joinPoint.getSignature().getName()).append("\n");
            sb.append("Header    : {").append("\n");
            Enumeration<String> requestHeader = request.getHeaderNames();
            while (requestHeader.hasMoreElements()) {
                String headerKey = requestHeader.nextElement();
                // 打印所有Header值
                sb.append("    ").append(headerKey).append(" : ").append(request.getHeader(headerKey)).append("\n");
            }
            sb.append("}").append("\n");
            sb.append("Params    : ").append(getParams(joinPoint)).append("\n");
            sb.append("URI       : ").append(request.getMethod()).append(" ").append(request.getRequestURI()).append("\n");
            sb.append("-----------------------------开始调用:-----------------------------");
            // 记录下请求内容
            log.debug(sb.toString());
        } catch (Exception e) {
            log.warn("切面处理前错误", e);
        }
    }

    @AfterReturning(returning = "ret", pointcut = "webLog()")
    public void doAfterReturning(JoinPoint joinPoint, Object ret) throws Throwable {
        try {
            if (!log.isDebugEnabled()) {
                return;
            }
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (attributes == null) {
                return;
            }
            HttpServletRequest request = attributes.getRequest();
            long ms = System.currentTimeMillis() - (long) request.getAttribute(httpStartTime);
            // 处理完请求，返回内容
            StringBuilder sb = new StringBuilder(50);
            sb.append("\n-----------------------------结束调用:-----------------------------\n");
            sb.append("Controller: ").append(joinPoint.getSignature().getDeclaringType()).append("\n");
            sb.append("Method    : ").append(joinPoint.getSignature().getName()).append("\n");
            sb.append("CostTime  : ").append(ms).append("ms\n");
            sb.append("-----------------------------结束调用:-----------------------------");
            log.debug(sb.toString());
        } catch (Exception e) {
            log.warn("切面处理后错误", e);
        }

    }

    private StringBuilder getParams(JoinPoint joinPoint) {
        StringBuilder params = new StringBuilder();
        for (Object arg : joinPoint.getArgs()) {
            try {
                if (!(arg instanceof ServletRequest)
                        && !(arg instanceof ServletResponse)
                        && !(arg instanceof MultipartFile)
                        && !(arg instanceof ModelAndView)
                        && !(arg instanceof Model)
                ) {
                    params.append(", ");
                    params.append(JSON.toJSONString(arg));
                }
            } catch (Exception e) {
                log.warn("未知类型转换错误:" + arg.getClass().getSimpleName());
            }
        }
        return params;
    }

}
