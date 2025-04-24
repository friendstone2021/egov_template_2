/**
 * ${projectDescription}
 *
 * ${projectName}
 * ${packageName}
 * ConfigDatasource.java
 *
 * @author ${author}
 * @version 1.0
 * @since ${since}
 */
package com.egov.template.config;

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.zaxxer.hikari.HikariDataSource;

/**
 * 데이터소스 설정 
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
public class ConfigDatasource {

	/**
	 * 환경설정 파일에서 데이터소스 프로퍼티 로드 
	 * 
	 * @return DataSourceProperties
	 */
	@Bean
	@Primary
	@ConfigurationProperties("spring.datasource")
	DataSourceProperties dataSourceProperties() {
	    return new DataSourceProperties();
	}
	
	/**
	 * 데이터소스 프로퍼티에서 HikariDataSource 생성 
	 * 
	 * @return HikariDataSource
	 */
	@Bean
	@ConfigurationProperties("spring.datasource.hikari")
	public HikariDataSource dataSource(DataSourceProperties properties) {
		return properties.initializeDataSourceBuilder().type(HikariDataSource.class).build();
	}
}
