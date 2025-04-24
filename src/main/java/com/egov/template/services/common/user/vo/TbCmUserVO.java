/**
 * ${projectDescription}
 * <p>
 * ${projectName}
 * ${packageName}
 * CmTbUser.java
 *
 * @author ${author}
 * @version 1.0
 * @since ${since}
 */
package com.egov.template.services.common.user.vo;

import com.egov.template.common.vo.SystemProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Pattern;
import java.util.Date;

/**
 * ${programDescription}
 *
 * @author ${author}
 * @version 1.0
 *
 * <pre>
 * == 개정이력(Modification Information)==
 * 수정일                 수정자          수정내용
 * -------      -------   ----------------------------
 * ${since}   ${author}   최초 작성
 *
 * </pre>
 * @since ${since}
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class TbCmUserVO extends SystemProperty {

    /**
     * 사용자ID
     */
    private String userId;

    /**
     * 기관코드
     */
    private String instCd;

    /**
     * 부서코드
     */
    private String deptCd;

    /**
     * 비밀번호
     */
    private String userEnpswd;

    /**
     * 사용자이름
     */
    private String userNm;

    /**
     * 연락처
     */
    private String userTelno;

    /**
     * 이메일
     */
    @Pattern(regexp = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$")
    private String emlAddr;

    /**
     * 로그인실패횟수
     */
    private Integer lgnFailNmtm;

    /**
     * 최종접속일시
     */
    private Date lastCntnDt;

    /**
     * 삭제여부
     */
    private String delYn;

    /**
     * 비밀번호변경일시
     */
    private String pswdChgDt;

}
