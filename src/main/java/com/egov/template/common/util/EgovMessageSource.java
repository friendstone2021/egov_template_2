package com.egov.template.common.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

/**
 * 메시지 리소스 사용을 위한 MessageSource 인터페이스 및 ReloadableResourceBundleMessageSource 클래스의 구현체
 * @author 공통서비스 개발팀 이문준
 * @since 2009.06.01
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 *   2009.03.11  이문준          최초 생성
 *   2017.07.21  장동한 			args, locale 설정
 *
 * </pre>
 */

@Component
public class EgovMessageSource extends ReloadableResourceBundleMessageSource implements MessageSource {

	@Autowired
	private ReloadableResourceBundleMessageSource messageSource;
	
	/**
	 * 정의된 메세지 조회
	 * @param code - 메세지 코드
	 * @return String
	 */	
	public String getMessage(String code) {
		return messageSource.getMessage(code, null, Locale.getDefault());
	}
	
	/**
	 * 정의된 메세지 조회
	 * @param code - 메세지 코드
	 * @param locale - 로케일
	 * @return String
	 */	
	public String getMessage(String code, Locale locale) {
		return messageSource.getMessage(code, null, locale);
	}
	
	/**
	 * 정의된 메세지 조회
	 * @param code - 메세지 코드
	 * @param args - 매개변수
	 * @return String
	 */	
	public String getMessageArgs(String code, Object[] args) {
		return messageSource.getMessage(code, args, Locale.getDefault());
	}
	
	/**
	 * 정의된 메세지 조회
	 * @param code - 메세지 코드
	 * @param args - 매개변수
	 * @param locale - 로케일
	 * @return String
	 */	
	public String getMessageArgsLocale(String code, Object[] args, Locale locale) {
		return messageSource.getMessage(code, args, locale);
	}

}
