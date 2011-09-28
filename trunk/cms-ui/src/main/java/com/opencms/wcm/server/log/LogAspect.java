package com.opencms.wcm.server.log;

import com.opencms.wcm.client.model.User;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Created by IntelliJ IDEA.
 * User: Lij
 * Date: 2010-12-4
 * Time: 11:27:02
 * To change this template use File | Settings | File Templates.
 */
@Component
@Aspect
public class LogAspect {

    private static Logger logger = LoggerFactory.getLogger(LogAspect.class);

    @Pointcut("execution(* com.opencms.wcm.server.WcmServiceImpl.login(..))")
    public void loginPT(){}

    @AfterReturning(value = "loginPT()", returning = "user")
    public void loginLog(User user) {
        logger.debug("{} 登录成功", user.getUsername());
    }

    @AfterThrowing(value = "loginPT()", throwing = "e")
    public void loginErr(Exception e) {
        logger.warn(e.getMessage());
    }

}
