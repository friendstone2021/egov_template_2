/**
 * ${projectDescription}
 * <p>
 * ${projectName}
 * ${packageName}
 * UserService.java
 *
 * @author ${author}
 * @version 1.0
 * @since ${since}
 */
package com.egov.template.services.common.user.service;

import com.egov.template.services.common.auth.mapper.AuthMapper;
import com.egov.template.services.common.auth.vo.TbCmUserAuthrtVO;
import com.egov.template.services.common.user.mapper.UserMapper;
import com.egov.template.services.common.user.vo.TbCmUserVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

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
@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private AuthMapper authMapper;

    public boolean isUserIdDplct(String userId) {
        TbCmUserVO vo = new TbCmUserVO();
        vo.setUserId(userId);
        return userMapper.selectUser(vo) != null;
    }

    @Transactional
    public void insertUser(TbCmUserVO cmTbUser) {
        cmTbUser.setRegDt(LocalDateTime.now());
        cmTbUser.setRgtrId(cmTbUser.getUserId());
        cmTbUser.setUseYn("Y");
        userMapper.insertUser(cmTbUser);

        TbCmUserAuthrtVO tbCmUserAuthrtVO = new TbCmUserAuthrtVO();
        tbCmUserAuthrtVO.setUserId(cmTbUser.getUserId());
        tbCmUserAuthrtVO.setAuthrtCd("GUEST");
        tbCmUserAuthrtVO.setRegDt(LocalDateTime.now());
        tbCmUserAuthrtVO.setRgtrId(cmTbUser.getUserId());
        tbCmUserAuthrtVO.setUseYn("Y");
        authMapper.insertUserAuth(tbCmUserAuthrtVO);

    }
}
