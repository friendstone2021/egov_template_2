/**
 * ${projectDescription}
 *
 * ${projectName}
 * ${packageName}
 * ConfigTransaction.java
 *
 * @author ${author}
 * @version 1.0
 * @since ${since}
 */
package com.egov.template.config;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.interceptor.NameMatchTransactionAttributeSource;
import org.springframework.transaction.interceptor.RollbackRuleAttribute;
import org.springframework.transaction.interceptor.RuleBasedTransactionAttribute;
import org.springframework.transaction.interceptor.TransactionAttribute;
import org.springframework.transaction.interceptor.TransactionInterceptor;

/**
 * 트랜잭션 설정 
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
public class ConfigTransaction {

	@Value("${application.base.package}")
	private String basePackage;

	/**
     * 트랜잭션 매니저에 데이터 소스 설정
     * @param dataSource
     * @return DataSourceTransactionManager
     */
    @Bean
    DataSourceTransactionManager txManager(@Qualifier("dataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    /**
     * 트랜잭션 정책
     */
    @Bean
    TransactionInterceptor txAdvice(DataSourceTransactionManager txManagerDataSource) {

        RuleBasedTransactionAttribute txAttribute = new RuleBasedTransactionAttribute();
        txAttribute.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        txAttribute.setRollbackRules(Collections.singletonList(new RollbackRuleAttribute(Exception.class)));

        Map<String, TransactionAttribute> txMethods = new HashMap<>();
        txMethods.put("*", txAttribute);

        NameMatchTransactionAttributeSource txAttributeSource = new NameMatchTransactionAttributeSource();
        txAttributeSource.setNameMap(txMethods);

        TransactionInterceptor txAdvice = new TransactionInterceptor();
        txAdvice.setTransactionAttributeSource(txAttributeSource);
        txAdvice.setTransactionManager(txManagerDataSource);

        return txAdvice;
    }


    /**
	 * 트랜잭션 적용 범위
	 *
	 * @param txManager
	 * @return Advisor
	 */
	@Bean
	Advisor txAdvisor(@Qualifier("txManager") DataSourceTransactionManager txManager) {
		AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
		pointcut.setExpression("execution(* "+basePackage+"..service.*Service.*(..))");
		return new DefaultPointcutAdvisor(pointcut, txAdvice(txManager));
	}

}
