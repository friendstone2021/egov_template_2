/**
 * ${projectDescription}
 * <p>
 * ${projectName}
 * ${packageName}
 * ConfigSecurity.java
 *
 * @author ${author}
 * @version 1.0
 * @since ${since}
 */
package com.egov.template.config;

import com.egov.template.config.auth.CustomAuthenticationFilter;
import com.egov.template.config.auth.handler.CustomAuthenticationFailureHandler;
import com.egov.template.config.auth.handler.CustomAuthenticationSuccessHandler;
import com.egov.template.config.auth.manager.CustomAccessDecisionManager;
import com.egov.template.config.auth.manager.CustomAuthenticationManager;
import com.egov.template.config.filter.HtmlTagFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import javax.servlet.DispatcherType;

/**
 * 시큐리티 설정 
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
@EnableWebSecurity(debug = true)
public class ConfigSecurity {

    private final CustomAccessDecisionManager accessDecisionManager;

    @Autowired
    public ConfigSecurity(CustomAccessDecisionManager accessDecisionManager) {
        this.accessDecisionManager = accessDecisionManager;
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {


        http
                /* csrf (크로스 사이트 요청 위조) disable : https://velog.io/@woohobi/Spring-security-csrf%EB%9E%80 */
//            .csrf(AbstractHttpConfigurer::disable) // 메서드 참조 방식
//            .csrf(csrfConfig -> csrfConfig.disable()) // 람다 방식

                /* (iframe을 통한 ClickJacking 취약점을 막기 위한 X-Frame-Options 설정 해제) headers().frameOptions().sameOrigin : https://jwlim94.tistory.com/15 */
//            .headers(headerConfig -> headerConfig.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin)) // 메서드 참조 방식
                .headers(headerConfig -> headerConfig.frameOptions(frameOptionsConfig -> frameOptionsConfig.sameOrigin())) // 람다 방식

                /* cors (크로스도메인 설정)*/
                .cors(corsConfig -> corsConfig.configurationSource(corsConfigurationSource()))

                /* html tag 제거 필터 */
                .addFilterAfter(new HtmlTagFilter(), CorsFilter.class)

                /* 권한별 접근 허용 */
                .authorizeRequests(authorizeRequests -> {
                            authorizeRequests
                                    .requestMatchers(PathRequest.toH2Console()).permitAll()
                                    .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                                    .dispatcherTypeMatchers(DispatcherType.FORWARD).permitAll()
                                    .mvcMatchers(
                                            "/",
                                            "/public/**",
                                            "/user/userJoinForm.do",
                                            "/user/selectJoinUserIdDplct.do",
                                            "/user/insertUserJoin.do"
                                    ).permitAll()
                                    .mvcMatchers("/admin/**").hasRole("ADMIN")
                                    .anyRequest().authenticated()
                                    .accessDecisionManager(accessDecisionManager)
                                    .and()
                                    .addFilterBefore(customAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                            ;
                        }
                )

                /* 로그인 시 이동 페이지 */
                .formLogin(formLoginConfig ->
                        formLoginConfig
                                .loginPage("/loginPage.do")
                                .loginProcessingUrl("/actionLogin.do")
                                .defaultSuccessUrl("/mainPage.do", true)
                                .usernameParameter("username")
                                .passwordParameter("encryptPwd")
                                .permitAll()
                )

                /* 로그아웃 시 이동 페이지 */
                .logout(logoutConfig ->
                        logoutConfig
                                .logoutUrl("/actionLogout.do")
                                .logoutSuccessUrl("/sessionExpired.do")
                                .invalidateHttpSession(true)
                )

                /* 최대 세션 1개 허용, 세션 만료 시 로그인 페이지로 이동, 동일 사용자 로그인 시 기존 사용자 세션 종료 */
                .sessionManagement(sessionManagementConfig ->
                        sessionManagementConfig
                                .sessionFixation().changeSessionId()
                                .maximumSessions(1)
                                .expiredUrl("/sessionExpired.do")
                                .maxSessionsPreventsLogin(false)
                )

                /* 오류발생 및 예외처리 */
                .exceptionHandling(exceptionHandlingConfig ->
                        exceptionHandlingConfig.accessDeniedPage("/accessDenied.do")
                )
        ;

        return http.build();
    }

    /**
     * 커스텀 사용자 인증 필터
     *
     * @return CustomAuthenticationFilter
     */
    @Bean
    CustomAuthenticationFilter customAuthenticationFilter() {
        CustomAuthenticationFilter filter = new CustomAuthenticationFilter("/ua/ul/actionLogin.do");
        filter.setAuthenticationManager(customAuthenticationManager());
        filter.setAuthenticationFailureHandler(new CustomAuthenticationFailureHandler("/ua/ul/loginForm.do"));
        filter.setAuthenticationSuccessHandler(new CustomAuthenticationSuccessHandler("/mainPage.do"));
        return filter;
    }

    /**
     * 커스텀 사용자 인증 처리
     *
     * @return CustomAuthenticationManager
     */
    @Bean
    CustomAuthenticationManager customAuthenticationManager() {
        return new CustomAuthenticationManager();
    }

//	@Bean
//    AccessDecisionManager accessDecisionManager() {
//		return new CustomAccessDecisionManager();
//	}

    /**
     * 크로스 도메인 설정
     *
     * @return CorsConfigurationSource
     */
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.addAllowedOrigin("*");
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod(HttpMethod.POST);
        configuration.addAllowedMethod(HttpMethod.GET);
        configuration.addAllowedMethod(HttpMethod.OPTIONS);
        configuration.addAllowedMethod(HttpMethod.DELETE);
        configuration.addAllowedMethod(HttpMethod.PUT);
        configuration.addAllowedMethod(HttpMethod.PATCH);
        configuration.setAllowCredentials(true);
        configuration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }

    /**
     * 기본 패스워드 암호화 도구
     *
     * @return PasswordEncoder
     */
    @Bean
    BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
