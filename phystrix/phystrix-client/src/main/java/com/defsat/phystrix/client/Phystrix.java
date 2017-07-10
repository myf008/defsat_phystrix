package com.defsat.phystrix.client;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD })
@Documented
@Inherited
public @interface Phystrix {

	String commandKey() ;
	String commandGroup() ;
	String fallBack() default "";
	String isolationStgy() default "SEMAPHORE";
	int maxRequest() default 500;
	int timeout() default 1000;
	int threadPoolSize() default 10;
	int requestThreshold() default 20;
	int errorThreshold() default 30;
	int circuitBreakTime() default 10000;
}
