/**
 * ${projectDescription}
 *
 * ${projectName}
 * ${packageName}
 * CustomPaginationRenderer.java
 *
 * @author ${author}
 * @version 1.0
 * @since ${since}
 */
package com.egov.template.config.pagination;


import org.egovframe.rte.ptl.mvc.tags.ui.pagination.AbstractPaginationRenderer;

/**
 * 페이징 렌더러
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
public class CustomPaginationRenderer extends AbstractPaginationRenderer {
    public CustomPaginationRenderer() {
		firstPageLabel 		= "<a href=\"#\" class=\"first\" onclick=\"{0}({1}); return false;\">처음 페이지로 이동</a>";
        previousPageLabel 	= "<a href=\"#\" class=\"prev\" onclick=\"{0}({1}); return false;\">이전 페이지로 이동</a>";
        currentPageLabel 	= "<a href=\"#\" class=\"on\">{0}</a>";
        otherPageLabel 		= "<a href=\"#\" onclick=\"{0}({1}); return false;\">{2}</a>";
        nextPageLabel	 	= "<a href=\"#\" class=\"next\" onclick=\"{0}({1}); return false;\">다음 페이지로 이동</a>";
        lastPageLabel 		= "<a href=\"#\" class=\"last\" onclick=\"{0}({1}); return false;\">마지막 페이지로 이동</a>";
	}
}
