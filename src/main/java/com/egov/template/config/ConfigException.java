/**
 * ${projectDescription}
 * <p>
 * ${projectName}
 * ${packageName}
 * ConfigException.java
 *
 * @author ${author}
 * @version 1.0
 * @since ${since}
 */
package com.egov.template.config;

import com.egov.template.config.error.AopExceptionTransfer;
import com.egov.template.config.error.ExcepHndlr;
import com.egov.template.config.error.OthersExcepHndlr;
import org.egovframe.rte.fdl.cmmn.aspect.ExceptionTransfer;
import org.egovframe.rte.fdl.cmmn.exception.handler.ExceptionHandler;
import org.egovframe.rte.fdl.cmmn.exception.manager.DefaultExceptionHandleManager;
import org.egovframe.rte.fdl.cmmn.exception.manager.ExceptionHandlerService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.util.AntPathMatcher;

/**
 * Exception 처리 설정
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
@EnableAspectJAutoProxy
public class ConfigException {

    /**
     * 디폴트 예외처리 핸들러
     *
     * @return ExcepHndlr
     */
    @Bean
    ExcepHndlr egovHandler() {
        return new ExcepHndlr();
    }

    /**
     * 기타 예외처리 핸들러
     *
     * @return OthersExcepHndlr
     */
    @Bean
    OthersExcepHndlr otherHandler() {
        return new OthersExcepHndlr();
    }

    /**
     * 디폴트 예외처리 핸들러 등록 및 적용범위
     *
     * @param antPathMatcher
     * @param egovHandler
     * @return DefaultExceptionHandleManager
     */
    @Bean
    DefaultExceptionHandleManager defaultExceptionHandleManager(AntPathMatcher antPathMatcher, ExcepHndlr egovHandler) {
        DefaultExceptionHandleManager defaultExceptionHandleManager = new DefaultExceptionHandleManager();
        defaultExceptionHandleManager.setReqExpMatcher(antPathMatcher);
        //defaultExceptionHandleManager.setPatterns(new String[]{"**service.impl.*"});
        defaultExceptionHandleManager.setPatterns(new String[]{"**"});
        defaultExceptionHandleManager.setHandlers(new ExceptionHandler[]{egovHandler});
        return defaultExceptionHandleManager;
    }

    /**
     * 기타 예외처리 핸들러 등록 및 적용범위
     *
     * @param antPathMatcher
     * @param othersExcepHndlr
     * @return DefaultExceptionHandleManager
     */
    @Bean
    DefaultExceptionHandleManager otherExceptionHandleManager(AntPathMatcher antPathMatcher, OthersExcepHndlr othersExcepHndlr) {
        DefaultExceptionHandleManager defaultExceptionHandleManager = new DefaultExceptionHandleManager();
        defaultExceptionHandleManager.setReqExpMatcher(antPathMatcher);
        defaultExceptionHandleManager.setPatterns(new String[]{"**service.impl.*"});
        defaultExceptionHandleManager.setHandlers(new ExceptionHandler[]{othersExcepHndlr});
        return defaultExceptionHandleManager;
    }

    /**
     * Exception 발생시 후처리를 위해 표준프레임워크 실행환경의 ExceptionTransfer를 활용하도록  설정
     *
     * @param defaultExceptionHandleManager
     * @param otherExceptionHandleManager
     * @return ExceptionTransfer
     */
    @Bean
    ExceptionTransfer exceptionTransfer(
            @Qualifier("defaultExceptionHandleManager") DefaultExceptionHandleManager defaultExceptionHandleManager,
            @Qualifier("otherExceptionHandleManager") DefaultExceptionHandleManager otherExceptionHandleManager) {
        ExceptionTransfer exceptionTransfer = new ExceptionTransfer();
        exceptionTransfer.setExceptionHandlerService(new ExceptionHandlerService[]{
                defaultExceptionHandleManager, otherExceptionHandleManager
        });
        return exceptionTransfer;
    }

    /**
     * 전달된 ExceptionTransfer 에 해당하는 후처리
     *
     * @param exceptionTransfer
     * @return AopExceptionTransfer
     */
    @Bean
    AopExceptionTransfer aopExceptionTransfer(ExceptionTransfer exceptionTransfer) {
        AopExceptionTransfer egovAopExceptionTransfer = new AopExceptionTransfer();
        egovAopExceptionTransfer.setExceptionTransfer(exceptionTransfer);
        return egovAopExceptionTransfer;
    }

}
