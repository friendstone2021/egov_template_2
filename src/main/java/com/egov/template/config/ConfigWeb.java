/**
 * ${projectDescription}
 * <p>
 * ${projectName}
 * ${packageName}
 * ConfigWeb.java
 *
 * @author ${author}
 * @version 1.0
 * @since ${since}
 */
package com.egov.template.config;

import com.egov.template.config.pagination.CustomPaginationManager;
import org.egovframe.rte.ptl.mvc.tags.ui.pagination.PaginationManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.UrlBasedViewResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;
import org.springframework.web.servlet.view.tiles3.SimpleSpringPreparerFactory;
import org.springframework.web.servlet.view.tiles3.TilesConfigurer;
import org.springframework.web.servlet.view.tiles3.TilesView;
import org.springframework.web.servlet.view.tiles3.TilesViewResolver;

import java.util.List;

/**
 * 웹환경 설정
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
@Configuration
public class ConfigWeb {

    /**
	 * 컨트롤러에서 jsonView로 반환하기 위한 설정
	 *
	 * @return MappingJackson2JsonView
	 */
    @Bean(name = "jsonView")
    public MappingJackson2JsonView jsonView() {
        return new MappingJackson2JsonView();
    }

    /**
	 * 페이징 공통 컴포넌트
	 *
	 * @return PaginationManager
	 */
    @Bean(name = "paginationManager")
    PaginationManager paginationManager() {
        return new CustomPaginationManager();
    }

    /**
	 * tiles 설정
	 *
	 * @return TilesConfigurer
	 */
	@Bean
    TilesConfigurer tilesConfigurer() {
		final TilesConfigurer configurer = new TilesConfigurer();
		configurer.setDefinitions("/WEB-INF/config/tiles/tiles-config.xml");
		configurer.setCheckRefresh(true);
		configurer.setPreparerFactoryClass(SimpleSpringPreparerFactory.class);
		return configurer;
	}

	/**
	 * tilesViewResolver
	 *
	 * @return TilesViewResolver
	 */
	@Bean
    TilesViewResolver tilesViewResolver() {
		final TilesViewResolver tilesViewResolver = new TilesViewResolver();
		tilesViewResolver.setViewClass(TilesView.class);
		tilesViewResolver.setOrder(1);
		return tilesViewResolver;
	}

	/**
	 * viewResolver
	 *
	 * @return UrlBasedViewResolver
	 */
	@Bean
    UrlBasedViewResolver viewResolver() {
		final UrlBasedViewResolver resolver = new UrlBasedViewResolver();
		resolver.setViewClass(TilesView.class);
		resolver.setOrder(1);
		return resolver;
	}

}
