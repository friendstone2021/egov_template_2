/**
 * ${projectDescription}
 *
 * ${projectName}
 * ${packageName}
 * CustomPaginationManager.java
 *
 * @author ${author}
 * @version 1.0
 * @since ${since}
 */
package com.egov.template.config.pagination;

import org.egovframe.rte.ptl.mvc.tags.ui.pagination.PaginationManager;
import org.egovframe.rte.ptl.mvc.tags.ui.pagination.PaginationRenderer;

/**
 * 페이징 설정
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
public class CustomPaginationManager implements PaginationManager {
    @Override
    public PaginationRenderer getRendererType(String type) {
        return new CustomPaginationRenderer();
    }
}
