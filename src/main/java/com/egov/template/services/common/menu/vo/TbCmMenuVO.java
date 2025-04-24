/**
 * ${projectDescription}
 * <p>
 * ${projectName}
 * ${packageName}
 * CmTbMenu.java
 *
 * @author ${author}
 * @version 1.0
 * @since ${since}
 */
package com.egov.template.services.common.menu.vo;

import com.egov.template.common.vo.SystemProperty;
import lombok.*;

/**
 * ${programDescription}
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
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Setter
@Getter
@ToString
public class TbCmMenuVO extends SystemProperty {
    private String menuId;

    private String upMenuId;

    private String menuNm;

    private Integer menuSeq;

    private String menuUrl;

    private String menuIndctYn;
}
