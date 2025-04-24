/**
 * ${projectDescription}
 * <p>
 * ${projectName}
 * ${packageName}
 * CustomAccessDecisionManager.java
 *
 * @author ${author}
 * @version 1.0
 * @since ${since}
 */
package com.egov.template.config.auth.manager;

import com.egov.template.services.common.menu.mapper.MenuMapper;
import lombok.extern.slf4j.Slf4j;
import org.egovframe.rte.psl.dataaccess.util.EgovMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.FilterInvocation;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

/**
 * 요청된 URL에 대해 현재 사용자가 필요한 권한을 가지고 있는지 확인하는 역할
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
@Slf4j
@Service
public class CustomAccessDecisionManager implements AccessDecisionManager {

    @Autowired
    private MenuMapper menuMapper;

    private List<EgovMap> authMenuList;

    @Override
    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException {

        if (authMenuList == null) {
            authMenuList = menuMapper.selectAuthMenuList();
        }

        // configAttributes: 요청 URL에 필요한 권한들
        // authentication: 현재 사용자의 권한 정보
        if (configAttributes == null || configAttributes.isEmpty()) {
            return;
        }

        // 현재 사용자의 권한 목록 확인
        Collection<? extends GrantedAuthority> userAuthorities = authentication.getAuthorities();

        for (ConfigAttribute attribute : configAttributes) {

            String authConfig = attribute.toString();

            if ("permitAll".equals(authConfig)) {
                return;
            } else if ("authenticated".equals(authConfig)) {
                FilterInvocation fi = (FilterInvocation) object;
                String requestUrl = fi.getRequestUrl();

                boolean isPermit = false;
                for (EgovMap authMenu : authMenuList) {
                    String menuUrl = authMenu.get("menuUrl").toString();
                    String authorCode = "ROLE_" + authMenu.get("authrtCd").toString();

                    if (menuUrl.equals(requestUrl)) {
                        for (GrantedAuthority authority : userAuthorities) {
                            if (authority.getAuthority().equals(authorCode)) {
                                isPermit = true;
                                break;
                            }
                        }
                    }
                }

                if (!isPermit) {
                    throw new AccessDeniedException("접근 권한이 없습니다.");
                }
            }
        }
    }

    @Override
    public boolean supports(ConfigAttribute attribute) {
        // 모든 ConfigAttribute를 지원
        return true;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        // 모든 Security 클래스 지원
        return true;
    }

}
