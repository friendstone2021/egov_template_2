/**
 * ${projectDescription}
 * <p>
 * ${projectName}
 * ${packageName}
 * SystemProperty.java
 *
 * @author ${author}
 * @version 1.0
 * @since ${since}
 */
package com.egov.template.common.vo;

import lombok.Data;

import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

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
@MappedSuperclass
@Data
public class SystemProperty {

    private String useYn;

    private LocalDateTime regDt;

    private String rgtrId;

    private LocalDateTime mdfcnDt;

    private String mdfrId;
}
