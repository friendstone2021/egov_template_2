/*
 * Project Name : 새만금 분양정보시스템
 * File Name      : PageingStringUtil.java
 * Created on 2024. 07. 30.
 *
 */
package com.egov.template.common.util;

import org.egovframe.rte.ptl.mvc.tags.ui.pagination.DefaultPaginationManager;
import org.egovframe.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import org.egovframe.rte.ptl.mvc.tags.ui.pagination.PaginationManager;
import org.egovframe.rte.ptl.mvc.tags.ui.pagination.PaginationRenderer;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * ajax용 페이징 유틸<br>
 * <br>
 * <li>@author SDH</li>
 * <li>@date 2023. 4. 27.</li>
 */
public class PagingStringUtil {

	public static String pagingString(HttpServletRequest reqeust, PaginationInfo paginationInfo, String type, String jsFunction) {

		PaginationManager paginationManager;

		// WebApplicationContext에 id 'paginationManager'로 정의된 해당 Manager를 찾는다.
		WebApplicationContext ctx = RequestContextUtils.findWebApplicationContext(reqeust, reqeust.getServletContext());

		if (ctx.containsBean("paginationManager")) {
			paginationManager = (PaginationManager) ctx.getBean("paginationManager");
		} else {
			//bean 정의가 없다면 DefaultPaginationManager를 사용. 빈설정이 없으면 기본 적인 페이징 리스트라도 보여주기 위함.
			paginationManager = new DefaultPaginationManager();
		}

		PaginationRenderer paginationRenderer = paginationManager.getRendererType(type);

		String contents = paginationRenderer.renderPagination(paginationInfo, jsFunction);

		return contents;
	}
}
