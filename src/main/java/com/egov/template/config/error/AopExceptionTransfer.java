package com.egov.template.config.error;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.egovframe.rte.fdl.cmmn.aspect.ExceptionTransfer;
import org.springframework.beans.factory.annotation.Value;

@Aspect
public class AopExceptionTransfer {

	private ExceptionTransfer exceptionTransfer;

	public void setExceptionTransfer(ExceptionTransfer exceptionTransfer) {
		this.exceptionTransfer = exceptionTransfer;
	}

	@Pointcut("execution(* com.egov.template.*.service.*Service.*(..))")
	private void exceptionTransferService() {}

	@AfterThrowing(pointcut="exceptionTransferService()", throwing="ex")
	public void doAfterThrowingExceptionTransferService(JoinPoint thisJoinPoint, Exception ex) throws Exception {
		exceptionTransfer.transfer(thisJoinPoint, ex);
	}

}
