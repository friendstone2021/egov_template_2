/**
 * ${projectDescription}
 * <p>
 * ${projectName}
 * ${packageName}
 * AuthService.java
 *
 * @author ${author}
 * @version 1.0
 * @since ${since}
 */
package com.egov.template.services.common.auth.service;

import com.egov.template.services.common.auth.mapper.AuthMapper;
import com.egov.template.services.common.auth.vo.TbCmAuthrtVO;
import com.egov.template.services.common.auth.vo.TbCmUserAuthrtVO;
import com.egov.template.services.common.user.vo.TbCmUserVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ${programDescription}
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
@Slf4j
@Service
public class AuthService {

    @Autowired
    AuthMapper authMapper;

    public void insertAuth(TbCmAuthrtVO cmTbAuth) {
        TbCmUserVO user = (TbCmUserVO) SecurityContextHolder.getContext().getAuthentication().getDetails();
        cmTbAuth.setRgtrId(user.getUserId());
        cmTbAuth.setRegDt(LocalDateTime.now());
        authMapper.insertAuth(cmTbAuth);
    }

    public List<TbCmAuthrtVO> selectAuthList(TbCmAuthrtVO cmTbAuth) {
        return authMapper.selectAuthList(cmTbAuth);
    }

    public TbCmAuthrtVO selectAuth(String authrtCd) {
        return authMapper.selectAuth(authrtCd);
    }

    public void updateAuth(TbCmAuthrtVO cmTbAuth) {
        TbCmUserVO user = (TbCmUserVO) SecurityContextHolder.getContext().getAuthentication().getDetails();
        cmTbAuth.setMdfrId(user.getUserId());
        cmTbAuth.setMdfcnDt(LocalDateTime.now());
        authMapper.updateAuth(cmTbAuth);
    }

    public void deactivateAuth(String authrtCd) {
        TbCmUserVO user = (TbCmUserVO) SecurityContextHolder.getContext().getAuthentication().getDetails();
        Map<String,Object> param = new HashMap<>();
        param.put("mdfrId",user.getUserId());
        param.put("authrtCd",authrtCd);
        authMapper.deactiveAuth(param);
    }

    public int countAuth(String authrtCd) {
        return authMapper.countAuth(authrtCd);
    }

    public boolean checkAuthrtCdDuplicate(String authrtCd) {
        return authMapper.countAuth(authrtCd) > 0;
    }

    public List<String> selectUserAuthList(String userId){
        return authMapper.selectUserAuthList(userId);
    }

    public void insertUserAuth(TbCmUserAuthrtVO cmTbUserAuth) {
        TbCmUserVO user = (TbCmUserVO) SecurityContextHolder.getContext().getAuthentication().getDetails();
        cmTbUserAuth.setRgtrId(user.getUserId());
        cmTbUserAuth.setRegDt(LocalDateTime.now());
        cmTbUserAuth.setUseYn("Y");
        authMapper.insertUserAuth(cmTbUserAuth);
    }

}
