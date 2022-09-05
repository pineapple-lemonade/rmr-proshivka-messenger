package ru.ruzavin.rmrproshivkamessenger.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LoggingAspect {
	@Pointcut(value = "execution(* ru.ruzavin.rmrproshivkamessenger.service.*.*(..))")
	public void loggingPointcut(){
		//this method returns void because it's just describe pointcut
	}

	@Around(value = "loggingPointcut()")
	public Object logMethod(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
		MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();
		Object[] args = proceedingJoinPoint.getArgs();

		Object result = proceedingJoinPoint.proceed(args);

		log.info("method with signature {} and args {} returned: {}", methodSignature, args, result);

		return result;
	}
}
