/**
 * ${projectDescription}
 *
 * ${projectName}
 * ${packageName}
 * ConfigMessage.java
 *
 * @author ${author}
 * @version 1.0
 * @since ${since}
 */
package com.egov.template.config;

import org.egovframe.rte.fdl.cmmn.trace.LeaveaTrace;
import org.egovframe.rte.fdl.cmmn.trace.handler.DefaultTraceHandler;
import org.egovframe.rte.fdl.cmmn.trace.handler.TraceHandler;
import org.egovframe.rte.fdl.cmmn.trace.manager.DefaultTraceHandleManager;
import org.egovframe.rte.fdl.cmmn.trace.manager.TraceHandlerService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.util.AntPathMatcher;

/**
 * 시스템 공통 메시지 설정 
 * @author ${author}
 * @since ${since}
 * @version 1.0
 * 
 * <pre>
 * == 개정이력(Modification Information)==
 * 수정일                 수정자          수정내용
 * -------      -------   ----------------------------
 * ${since}   ${author}   최초 작성
 * 
 * </pre>
 */
@Configuration
public class ConfigMessage {

	/**
	 * 시스템 공통 메시지 설정 
	 *
	 * @return ReloadableResourceBundleMessageSource
	 */
	@Bean
	ReloadableResourceBundleMessageSource messageSource() {
		ReloadableResourceBundleMessageSource reloadableResourceBundleMessageSource = new ReloadableResourceBundleMessageSource();
		reloadableResourceBundleMessageSource.setBasenames(
				"classpath:/message/message-common",
				"classpath:/org/egovframe/rte/fdl/property/messages/properties");
		reloadableResourceBundleMessageSource.setDefaultEncoding("UTF-8");
		reloadableResourceBundleMessageSource.setCacheSeconds(60);
		return reloadableResourceBundleMessageSource;
	}

	/**
	 * 시스템 공통 메시지 접근
	 *
	 * @return MessageSourceAccessor
	 */
	@Bean
	MessageSourceAccessor messageSourceAccessor() {
		return new MessageSourceAccessor(this.messageSource());
	}

	/**
	 * [LeaveaTrace 설정] traceHandlerService 등록. TraceHandler 설정
	 *
	 * @return DefaultTraceHandleManager
	 */
	@Bean
	DefaultTraceHandleManager traceHandlerService() {
		DefaultTraceHandleManager defaultTraceHandleManager = new DefaultTraceHandleManager();
		defaultTraceHandleManager.setReqExpMatcher(antPathMatcher());
		defaultTraceHandleManager.setPatterns(new String[]{"*"});
		defaultTraceHandleManager.setHandlers(new TraceHandler[]{defaultTraceHandler()});
		return defaultTraceHandleManager;
	}
	
	/**
	 * AntPathMatcher 등록.  Ant 경로 패턴 경로와 일치하는지 여부를 확인
	 *
	 * @return AntPathMatcher
	 */
	@Bean
	AntPathMatcher antPathMatcher() {
		return new AntPathMatcher();
	}

	/**
	 * [LeaveaTrace 설정] defaultTraceHandler 등록
	 *
	 * @return DefaultTraceHandler
	 */
	@Bean
	DefaultTraceHandler defaultTraceHandler() {
		return new DefaultTraceHandler();
	}

	/**
	 * [LeaveaTrace 설정] LeaveaTrace 등록
	 *
	 * @return LeaveaTrace
	 */
	@Bean
	LeaveaTrace leaveaTrace() {
		LeaveaTrace leaveaTrace = new LeaveaTrace();
		leaveaTrace.setTraceHandlerServices(new TraceHandlerService[]{traceHandlerService()});
		return leaveaTrace;
	}
	
}
