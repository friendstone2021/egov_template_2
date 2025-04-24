/**
 * ${projectDescription}
 * <p>
 * ${projectName}
 * ${packageName}
 * MenuMapper.java
 *
 * @author ${author}
 * @version 1.0
 * @since ${since}
 */
package com.egov.template.services.common.menu.mapper;


import org.apache.ibatis.annotations.Mapper;
import org.egovframe.rte.psl.dataaccess.util.EgovMap;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
@Mapper
public interface MenuMapper {

    /**
     * 메뉴 목록 조회
     *
     * @return
     * @throws Exception
     */
    List<EgovMap> selectCmmnMenuList(HashMap<String, String> param);

    /**
     * 메뉴관리 목록 조회
     *
     * @return
     * @throws Exception
     */
    List<EgovMap> selectCmmnMenuManageList();

    /**
     * 메뉴 아이디 생성
     *
     * @return
     * @throws Exception
     */
    String selectCmmnMenuId();


    /**
     * 권한별 메뉴 url 목록
     *
     * @return
     * @throws Exception
     */
    List<EgovMap> selectAuthMenuList();

    /**
     * 메뉴 일괄 삭제여부 처리
     *
     * @param param
     */
    void deleteCmmnMenuList(HashMap<String, Object> param);

    /**
     * 권한역할매핑관리 일괄 삭제
     *
     * @param param
     */
    void deleteCmmnAuthorRoleMapngManageList(HashMap<String, Object> param);

    /**
     * 역할관리 일괄 삭제
     *
     * @param param
     */
    void deleteCmmnRoleManageList(HashMap<String, Object> param);

    /**
     * 메뉴 저장
     *
     * @param map
     */
    void processCmmnMenu(Map<String, Object> map);

    /**
     * 메뉴 권한 저장
     *
     * @param map
     */
    void processCmmnMenuAuthorCode(Map<String, Object> map);

    /***
     * 서비스 권한 저장
     */
    void processCmmnMenuServiceAuthorCode(Map<String, Object> map);

    /**
     * 역할 관리 저장
     *
     * @param map
     */
    void processCmmnRoleManage(Map<String, Object> map);

    /**
     * 권한역할매핑 관리 저장
     *
     * @param map
     */
    void processCmmnAuthorRoleMapngManage(Map<String, Object> map);

    /**
     * 메뉴 권한 코드 삭제
     *
     * @param map
     */
    void deleteCmmnMenuAuthorCode(Map<String, Object> map);

    /**
     * 권한 역할 매핑 관리 삭제
     *
     * @param map
     */
    void deleteCmmnAuthorRoleMapngManage(Map<String, Object> map);

    /**
     * 매뉴 상세 조회
     *
     * @param srvcUrl
     */
    HashMap<String, Object> selectMenuInfoByUrl(String srvcUrl);

}
