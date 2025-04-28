/**
 * ${projectDescription}
 * <p>
 * ${projectName}
 * ${packageName}
 * CustomAuthenticationManager.java
 *
 * @author ${author}
 * @version 1.0
 * @since ${since}
 */
package com.egov.template.config.auth.manager;

import com.egov.template.services.common.auth.mapper.AuthMapper;
import com.egov.template.services.common.user.mapper.UserMapper;
import com.egov.template.services.common.user.vo.TbCmUserVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;
import java.util.List;

/**
 * ${programDescription}
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
public class CustomAuthenticationManager implements AuthenticationManager {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private AuthMapper authMapper;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        UsernamePasswordAuthenticationToken authToken;

        String username = authentication.getPrincipal().toString();
        String password = authentication.getCredentials().toString();

//        password = EgovFileScrty.encryptPassword(password, username);

        TbCmUserVO userIdParam = new TbCmUserVO();
        userIdParam.setUserId(username);
        TbCmUserVO cmTbUser = userMapper.selectUser(userIdParam);
        if (cmTbUser != null) {
            if (!password.equals(cmTbUser.getEnpswd())) {
                throw new BadCredentialsException("invalidIdPass");
            } else {
                cmTbUser.setLastCntnDt(new Date());
                userMapper.updateUser(cmTbUser);

                List<String> userAuthList = authMapper.selectUserAuthList(cmTbUser.getUserId());

                if (userAuthList.isEmpty()) {
                    throw new BadCredentialsException("invalidRoute");
                } else {
                    String[] arrUserAuth = userAuthList.toArray(new String[0]);

                    UserDetails userDetails = User.builder().username(username)
                            .password(password)
                            .roles(arrUserAuth)
                            .build();

                    cmTbUser.setEnpswd(null);

                    authToken = new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());
                    authToken.setDetails(cmTbUser);
                }

            }
        } else {
            throw new BadCredentialsException("invalidIdPass");
        }

        return authToken;
    }

}
