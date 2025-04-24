/**
 * ${projectDescription}
 * <p>
 * ${projectName}
 * ${packageName}
 * UserMapper.java
 *
 * @author ${author}
 * @version 1.0
 * @since ${since}
 */
package com.egov.template.services.common.user.mapper;

import com.egov.template.services.common.user.vo.TbCmUserVO;
import org.apache.ibatis.annotations.Mapper;

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
public interface UserMapper {

    TbCmUserVO selectUser(TbCmUserVO vo);

    void insertUser(TbCmUserVO vo);

    void updateUser(TbCmUserVO vo);

}
