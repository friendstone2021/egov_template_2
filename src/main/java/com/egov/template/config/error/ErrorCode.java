/**
 * ${projectDescription}
 *
 * ${projectName}
 * ${packageName}
 * ErrorCode.java
 *
 * @author ${author}
 * @version 1.0
 * @since ${since}
 */
package com.egov.template.config.error;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * 오류코드 ENUM
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
@Getter
public enum ErrorCode {

    BAD_REQUEST(HttpStatus.BAD_REQUEST, "400", "잘못된 요청입니다."),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "401", "해당 리소스에 대한 접근권한이 없습니다."),
    FORBIDDEN(HttpStatus.FORBIDDEN,"403","해당 리소스에 대한 접근이 금지되었습니다."),
    NOT_FOUND(HttpStatus.NOT_FOUND, "404", "페이지가 존재하지 않습니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "500", "서버 에러가 발생했습니다."),
    NOT_IMPLEMENTED(HttpStatus.NOT_IMPLEMENTED, "501", "해당 서비스는 제공하지 않습니다."),
    BAD_GATEWAY(HttpStatus.BAD_GATEWAY, "502", "게이트웨이가 잘못된 응답을 받았습니다."),
    SERVICE_UNAVAILABLE(HttpStatus.SERVICE_UNAVAILABLE, "503", "현재 서버에서 서비스를 제공할 수 없습니다.");

    private final String message;

    private final String code;
    private final HttpStatus status;

    ErrorCode(final HttpStatus status, final String code, final String message) {

        this.status = status;
        this.code = code;
        this.message = message;

    }

}
