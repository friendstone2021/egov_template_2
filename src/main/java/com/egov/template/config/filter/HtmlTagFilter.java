/*
 * Project Name :
 * File Name : HtmlTagFilter.java
 * Author : energ
 * Created on 25. 3. 7.
 *
 *
 */
package com.egov.template.config.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import lombok.extern.slf4j.Slf4j;

/**
 * HtmlTagFilter
 */
@Slf4j
public class HtmlTagFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

//        log.debug(">> HtmlTagFilter.doFilter");
        servletRequest.getParameterMap().forEach((k,v)->{
            for(int i=0;i<v.length;i++){
                String newValue =  v[i].replaceAll("<.*?>","").replaceAll("</.*?>","");
                v[i] = newValue;
            }
        });

        filterChain.doFilter(servletRequest, servletResponse);

    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }

}
