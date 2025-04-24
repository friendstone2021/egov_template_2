/**
 * ${projectDescription}
 *
 * ${projectName}
 * ${packageName}
 * CommonExceptionHandler.java
 *
 * @author ${author}
 * @version 1.0
 * @since ${since}
 */
package com.egov.template.config.error;

import com.egov.template.common.util.EgovStringUtil;
import com.egov.template.config.error.exception.CommonException;
import com.egov.template.config.error.exception.NoResourceFoundException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.egovframe.rte.fdl.cmmn.exception.BaseException;
import org.egovframe.rte.fdl.cmmn.exception.EgovBizException;
import org.egovframe.rte.fdl.cmmn.exception.FdlException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.BadRequestException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * 공통 예외처리 핸들러
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
@Slf4j
@ControllerAdvice
public class CommonControllerAdvice {

    @ExceptionHandler({MethodArgumentNotValidException.class})
	public ResponseEntity<ErrorResponse> validException(MethodArgumentNotValidException ex){
		ErrorResponse response = new ErrorResponse(ErrorCode.BAD_REQUEST, "유효성 검사 실패");
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler({BindException.class})
	public ResponseEntity<ErrorResponse> validException(BindException ex){
		ErrorResponse response = new ErrorResponse(ErrorCode.BAD_REQUEST, "유효성 검사 실패");
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}

	//에러로깅 공통 합수
    private ModelAndView handleExceptionInternal(Exception e, ErrorCode errorCode) {
        // Redirect to the error page
        ModelAndView mv = new ModelAndView();
        mv.addObject("errorCode", errorCode.getCode());
        mv.addObject("errorMessage", errorCode.getMessage());
        mv.setViewName("redirect:/cmmn/errorPage.do");
        return mv;
    }


    /** General Exception */
    @ExceptionHandler(Exception.class)
    public ModelAndView handleGeneralException(Exception e) {
        return handleExceptionInternal(e, ErrorCode.INTERNAL_SERVER_ERROR);
    }

    /** Runtime Exception */
    @ExceptionHandler(RuntimeException.class)
    public ModelAndView handleRuntimeException(RuntimeException e) {
        return handleExceptionInternal(e, ErrorCode.INTERNAL_SERVER_ERROR);
    }

    /** EgovBizException */
    @ExceptionHandler(EgovBizException.class)
    public ModelAndView handleEgovBizException(EgovBizException e) {
        return handleExceptionInternal(e, ErrorCode.INTERNAL_SERVER_ERROR);
    }

    /** BaseException */
    @ExceptionHandler(BaseException.class)
    public ModelAndView handleBaseException(BaseException e) {
        return handleExceptionInternal(e, ErrorCode.INTERNAL_SERVER_ERROR);
    }

    /** FdlException */
    @ExceptionHandler(FdlException.class)
    public ModelAndView handleFdlException(FdlException e) {
        return handleExceptionInternal(e, ErrorCode.INTERNAL_SERVER_ERROR);
    }

    /** SQLException */
    @ExceptionHandler(SQLException.class)
    public ModelAndView handleSQLException(SQLException e) {
        return handleExceptionInternal(e, ErrorCode.INTERNAL_SERVER_ERROR);
    }

    /** IOException */
    @ExceptionHandler(IOException.class)
    public ModelAndView handleIOException(IOException e) {
        return handleExceptionInternal(e, ErrorCode.INTERNAL_SERVER_ERROR);
    }
}
