package com.abc.common.core.threadlocal;

import com.abc.common.exception.GlobalException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ThreadLocalTempHelp {

    public static void execute(ThreadLocal<Object> tempThreadLocal, Object tempVar, Runnable task) {
        tempThreadLocal.set(tempVar);
        try {
            task.run();
        } catch (Exception e) {
            log.error("执行临时变量域方法错误：{}", e.getMessage(), e);
            throw new GlobalException("执行临时变量域方法错误");
        }
        tempThreadLocal.remove();
    }

}
