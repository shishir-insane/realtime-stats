/**
 * TimedLog.java
 * realtime-stats
 * 
 * @author Shishir Kumar
 */
package com.sk.sales.stats.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface TimedLog {
}