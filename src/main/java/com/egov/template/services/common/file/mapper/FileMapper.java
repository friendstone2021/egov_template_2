/**
 * ${projectDescription}
 * <p>
 * ${projectName}
 * ${packageName}
 * FileMapper.java
 *
 * @author ${author}
 * @version 1.0
 * @since ${since}
 */
package com.egov.template.services.common.file.mapper;

import com.egov.template.services.common.file.vo.TbCmFileDtlVO;
import com.egov.template.services.common.file.vo.TbCmFileGroupVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

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
public interface FileMapper {

    TbCmFileGroupVO selectFileGrp(TbCmFileGroupVO vo);

    TbCmFileDtlVO selectFileDtl(TbCmFileDtlVO vo);

    List<TbCmFileDtlVO> selectFileDtlList(TbCmFileDtlVO vo);

    void insertFileGrp(TbCmFileGroupVO vo);

    void updateFileGrp(TbCmFileGroupVO vo);

    void insertFileDtl(TbCmFileDtlVO vo);

    void updateFileDtl(TbCmFileDtlVO vo);

}
