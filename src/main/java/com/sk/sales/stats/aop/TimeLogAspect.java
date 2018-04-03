/**
 * TimeLogAspect.java
 * realtime-stats
 *
 * @author Shishir Kumar
 */
package com.sk.sales.stats.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@Slf4j
public class TimeLogAspect {
    
    /**
     * Time.
     *
     * @param proceedingJoinPoint
     *            the proceeding join point
     * @return the object
     * @throws Throwable
     *             the throwable
     */
    @Around("@annotation(com.sk.sales.stats.aop.TimedLog) && execution(public * *(..))")
    public Object time(final ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        final long start = System.currentTimeMillis();
        Object value;
        try {
            log.info("Entered {}.{}", proceedingJoinPoint.getSignature().getDeclaringType().getSimpleName(),
                    proceedingJoinPoint.getSignature().getName());
            value = proceedingJoinPoint.proceed();
        } catch (final Throwable throwable) {
            throw throwable;
        } finally {
            final long duration = System.currentTimeMillis() - start;
            log.info("Exited {}.{} took {} ms", proceedingJoinPoint.getSignature().getDeclaringType().getSimpleName(),
                    proceedingJoinPoint.getSignature().getName(), duration);
        }
        return value;
    }
}