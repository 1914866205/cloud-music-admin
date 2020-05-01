package com.soft1851.music.admin.aspect;

import com.soft1851.music.admin.annotation.ControllerWebLog;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description TODO
 * @Author 涛涛
 * @Date 2020/4/10 11:17
 * @Version 1.0
 **/

/**
 * @Aspect 把该类注成一个切面类     切面=切点+增强
 * @Component 一旦注上@Component 该类就被托管成一个单例的bean
 * @Order(100) 指定切面的优先级，参数越小，优先级最高  如@Order(1)优先级最高
 * @Slf4j 打日志
 */
@Aspect
@Component
@Order(100)
@Slf4j
public class WebLogAspect {
    /**
     * ThreadLocal可以让我们拥有当前线程的变量，可以通过get()和set()来对
     * 这个局部变量进行操作，不会和其他线程的局部变量进行冲突，实现了先出的数据隔离
     * 此处用它进行日志信息在这个方法之间的共享
     */
    private ThreadLocal<Map<String, Object>> threadLocal = new ThreadLocal<>();


    /**
     * 定义切点
     * 该包下的所有方法运行都会被切到
     */
    @Pointcut("execution(public * com.soft1851.music.admin.controller..*.*(.. ))")
    public void webLog() {

    }

    //这个webLog()是上面的方法   在方法被调用之前执行增强
    //符合这个条件并且这个方法还要带@ControllerWebLog注解
    @Before(value = "webLog() && @annotation(controllerWebLog)")
    public void doBefore(JoinPoint joinPoint, ControllerWebLog controllerWebLog) {
        //接收请求
        RequestAttributes at = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes) at;
        log.info("这里是前置增强");
        //通过以下连接点和注解获取到相关信息
        assert sra != null;
        HttpServletRequest request = sra.getRequest();
        log.info("请求的URI" + request.getRequestURL());
        log.info("请求的URL" + request.getRequestURL());
        log.info("请求头的User-Agent:" + request.getHeader("User-Agent"));
        log.info("请求方法：" + request.getMethod());
        log.info("请求地址" + request.getRemoteAddr());
        log.info("连接点对象通过反射获得类名和方法名" + joinPoint.getSignature().getDeclaringTypeName() + "."
                + joinPoint.getSignature().getName());
        log.info("AOP拦截获得参数"+ Arrays.toString(joinPoint.getArgs()));
//        log.info("执行方法："+controllerWebLog.name());
        //定义一个map用来记录日志信息，并且put入threadLocal
        Map<String, Object> map = new HashMap<>();
        map.put("uri",request.getRequestURI());
        map.put("url",request.getRequestURL());
        map.put("user-agent",request.getHeader("User-Agent"));
        map.put("request-method",request.getMethod());
        map.put("class-method", joinPoint.getSignature().getDeclaringTypeName() + "."
                + joinPoint.getSignature().getName());
        map.put("arguments", Arrays.toString(joinPoint.getArgs()));
//        map.put("execute-method", controllerWebLog.name());
        threadLocal.set(map);
    }


    //这个webLog()是上面的方法 在方法被调用之后执行增强
    //符合这个条件并且这个方法还要带@ControllerWebLog注解
    @After(value = "webLog() && @annotation(controllerWebLog)")
    public void doAfter(JoinPoint joinPoint, ControllerWebLog controllerWebLog) {
        log.info("这里是后置增强");
    }


    //这个webLog()是上面的方法    在方法成功执行之后执行增强
    // 符合这个条件并且这个方法还要带@ControllerWebLog注解
    @AfterReturning(value = "webLog() && @annotation(controllerWebLog)",returning = "ret")
    public void doAfterReturning(ControllerWebLog controllerWebLog, Object ret) {
        log.info("这里是后置执行成功后增强");
        //从当前线程变量取出数据
        Map<String, Object> threadInfo = threadLocal.get();
        //将请求的目标方法getHello()的去执行的返回结果存入线程对象
        threadInfo.put("result", ret);
        if (controllerWebLog.isSaved()){
            //真实场景可做定时器插入数据库操作，此处模拟
            log.info("日志已存入数据库");
        }
        //处理完请求，返回内容
        log.info("RESPONSE:"+ret);
    }

    //这个webLog()是上面的方法    在方法抛出指定异常后执行增强
    // 符合这个条件并且这个方法还要带@ControllerWebLog注解
    @AfterThrowing(value = "webLog()",throwing="throwable")
    public void doAfterTrowing(Throwable throwable) {
        log.info("这里是抛出异常后增强");
        RequestAttributes at = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes) at;
        //通过以下连接点和注解获取到相关信息
        assert sra != null;
        HttpServletRequest request = sra.getRequest();
        log.error("啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊接口调用异常啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊");
        log.error(request.getRequestURI(),throwable);
    }

    /**
     * 通过环绕增强回去目标方法执行时间，可用于分析性能
     * 可通过连接点入参和反射机制，在这里调用目标方法getHello()，并返回结果
     */
    //这个webLog()是上面的方法    在方法调用的前后执行自定义的增强行为
    // 符合这个条件并且这个方法还要带@ControllerWebLog注解
    @Around(value = "webLog()")
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint)throws Throwable {
        log.info("这里是环绕增强");
        //得到开始时间
//        long startTime = System.currentTimeMillis();
        //执行连接点的目标方法getHello()
        Object ob = proceedingJoinPoint.proceed();
//        Map<String, Object> threadInfo = threadLocal.get();
        //计算出方法的真实执行时间，可以在目标方法中加入线程休眠体会效果
//        Long takeTime = System.currentTimeMillis() - startTime;
        //存入线程变量
//        threadInfo.put("takeTime", takeTime);
//        log.info("耗时："+takeTime);
//        threadLocal.set(threadInfo);
        return ob;
    }


}
