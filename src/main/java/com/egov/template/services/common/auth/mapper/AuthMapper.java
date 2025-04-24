/**
 * ${projectDescription}
 * <p>
 * ${projectName}
 * ${packageName}
 * AuthMapper.java
 *
 * @author ${author}
 * @version 1.0
 * @since ${since}
 */
package com.egov.template.services.common.auth.mapper;

import com.egov.template.services.common.auth.vo.TbCmAuthrtVO;
import com.egov.template.services.common.auth.vo.TbCmUserAuthrtVO;
import org.apache.ibatis.annotations.Mapper;

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
@Mapper
public interface AuthMapper {

    void insertAuth(TbCmAuthrtVO vo);

    List<TbCmAuthrtVO> selectAuthList(TbCmAuthrtVO vo);

    TbCmAuthrtVO selectAuth(String authrtCd);

    void updateAuth(TbCmAuthrtVO vo);

    void deleteAuth(String authrtCd);

    void deactiveAuth(Map<String,Object> paramMap);

    int countAuth(String authrtCd);

    int checkAuthrtCdDuplicate(String authrtCd);

    List<String> selectUserAuthList(String userId);

    void insertUserAuth(TbCmUserAuthrtVO vo);
}
