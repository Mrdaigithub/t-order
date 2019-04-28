package club.mrdaisite.torder.torderadmin.component;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * WebLogAspect
 *
 * @author dai
 * @date 2019/03/22
 */
@Component
@Aspect
@Order(1)
public class WebLogAspect {
    private ThreadLocal<Long> startTime = new ThreadLocal<>();

//    @Pointcut("execution(public * club.mrdaisite.torder.torderadmin.controller.*.*(..))")
//    public void webLog() {
//    }
//
//    @Before("webLog()")
//    public void doBefore(JoinPoint joinpoint) {
//        startTime.set(System.currentTimeMillis());
//    }
//
//    @Around("webLog()")
//    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
//        // 获取当前请求对象
//        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//        HttpServletRequest request = attributes.getRequest();
//        // 记录请求信息(通过logstash传入elasticsearch)
//        WebLog webLog = new WebLog();
//        Object result = joinPoint.proceed();
//        Signature signature = joinPoint.getSignature();
//        MethodSignature methodSignature = (MethodSignature) signature;
//        Method method = methodSignature.getMethod();
//        if (method.isAnnotationPresent(ApiOperation.class)) {
//            ApiOperation log = method.getAnnotation(ApiOperation.class);
//            webLog.setDescription(log.value());
//        }
//        long endTime = System.currentTimeMillis();
//        webLog.setBasePath(RequestUtil.getBasePath(request));
//        webLog.setUsername(request.getRemoteUser());
//        webLog.setIp(request.getRemoteHost());
//        webLog.setMethod(request.getMethod());
//        webLog.setParameter(getParameter(method, joinPoint.getArgs()));
//        webLog.setResult(result);
//        webLog.setSpendTime((int) (endTime - startTime.get()));
//        webLog.setStartTime(startTime.get());
//        webLog.setUri(request.getRequestURI());
//        webLog.setUrl(request.getRequestURL().toString());
//        Map<String, Object> logMap = new HashMap<>();
//        logMap.put("user", webLog.getUsername());
//        logMap.put("ip", webLog.getIp());
//        logMap.put("url", webLog.getUrl());
//        logMap.put("method", webLog.getMethod());
//        logMap.put("parameter", webLog.getParameter());
//        logMap.put("sendTime", webLog.getSpendTime());
//        logMap.put("description", webLog.getDescription());
//        LoggerUtil.logger.info(JSON.toJSONString(logMap));
//        return result;
//    }
//
//    /**
//     * 根据方法和传入的参数获取请求参数
//     */
//    private Object getParameter(Method method, Object[] args) {
//        List<Object> argList = new ArrayList<>();
//        Parameter[] parameters = method.getParameters();
//        for (int i = 0; i < parameters.length; i++) {
//            RequestBody requestBody = parameters[i].getAnnotation(RequestBody.class);
//            if (requestBody != null) {
//                argList.add(args[i]);
//            }
//            RequestParam requestParam = parameters[i].getAnnotation(RequestParam.class);
//            if (requestParam != null) {
//                Map<String, Object> map = new HashMap<>();
//                String key = parameters[i].getName();
//                if (!StringUtils.isEmpty(requestParam.value())) {
//                    key = requestParam.value();
//                }
//                map.put(key, args[i]);
//                argList.add(map);
//            }
//        }
//        if (argList.size() == 0) {
//            return null;
//        } else if (argList.size() == 1) {
//            return argList.get(0);
//        } else {
//            return argList;
//        }
//    }
}
