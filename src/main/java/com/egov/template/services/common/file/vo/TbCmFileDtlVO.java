/**
 * ${projectDescription}
 * <p>
 * ${projectName}
 * ${packageName}
 * CmTbFileDtl.java
 *
 * @author ${author}
 * @version 1.0
 * @since ${since}
 */
package com.egov.template.services.common.file.vo;

import com.egov.template.common.vo.SystemProperty;
import lombok.*;

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
@Setter
@Getter
@ToString
public class TbCmFileDtlVO extends SystemProperty {

    private String fileGroupId;

    private String fileId;

    private String filePathNm;

    private String strgFileNm;

    private String orgnlFileNm;

    private Long fileSz;

    private String fileExtnNm;

}
