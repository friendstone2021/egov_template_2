package com.egov.template.common.constants;

import java.util.HashMap;
import java.util.Map;

public final class Constants {


	// session key.
	public static final String SESSION_KEY_USER = "SESSION_USER_INFO";

	// USER STATE
	public static final String MEMBER_STATE_NORMAL = "P";	// 정상(승인)
	public static final String MEMBER_STATE_LEAVE  = "D";		// 탈퇴 (삭제)
	public static final String MEMBER_STATE_BLOCK  = "B";	// 블럭 (비밀번호 오류)

	// RESULT CODE
	public static final String SUCCESS_CODE = "S";
	public static final String ERROR_CODE = "E";
	public static final String WARNING_CODE = "W";

	// ################################################################ [ FILE UPLOAD PATH ]
	public static final String UPLOAD_ROOT_WEB_PATH		= "Upload";

	// ################################################################ [ FILE UPLOAD ]
	public static final String FILE_UPLOAD_FIELD_NAME						= "attach";

	// ################################################################ [ FILE UPLOAD RETURN ]
	public static final String FILE_UPLOAD_SUCCESS_CODE			= "0";
	public static final String FILE_UPLOAD_ERROR_CODE			= "1";
	public static final String FILE_UPLOAD_MIMETYPE_CODE		= "1";
	public static final String FILE_UPLOAD_SIZE_CODE				= "2";
	public static final String FILE_UPLOAD_DIRECTORY_CODE		= "3";
	public static final String FILE_UPLOAD_TOTALSIZE_CODE		= "4";

	public static final String FILE_DELETE_SUCCESS_CODE			= "0";
	public static final String FILE_DELETE_ERROR_CODE				= "1";
	public static final String FILE_DELETE_DIRECTORY_CODE		= "2";
	public static final String FILE_DELETE_DATA_CODE				= "3";

	public static final String AUTHOR_CODE_ROLE_ADMIN = "ROLE_ADMIN";			//사용자 권한_시스템관리자
	public static final String AUTHOR_CODE_ROLE_GNOFCE = "ROLE_GNOFCE";			//사용자 권한_업무책임자
	public static final String AUTHOR_CODE_ROLE_TEMPORARY = "ROLE_TEMPORARY";		//사용자 권한_임시권한
	public static final String AUTHOR_CODE_ROLE_ANONYMOUS = "ROLE_ANONYMOUS";		//사용자 권한_익명사용자
	public static final String AUTHOR_CODE_ROLE_USER = "ROLE_USER";					//사용자 권한_대민
	public static final String INSERT_USER_SYSTEM = "SYSTEM";				//insert 시, REG_ID or MDFCN_ID 를 SYSTEM으로

	public static final String AUTHOR_CODE_ROLE_GUEST = "ROLE_GUEST";

	public static final String USER_REG_STS_REQST = "REQST";					//신청
	public static final String USER_REG_STS_CONFM = "CONFM";					//승인
	public static final String USER_REG_STS_RETURN = "RETURN";					//반려

	public static final String LNG_SE_SYSTEM = "SYSTEM";					//로그인 구분
	public static Map<String, Map<String, Object>> RESULT_MAP = new HashMap<>(); //결과 맵
	static {

		Map<String, Object> SUCCESS_MAP = new HashMap<>();
		SUCCESS_MAP.put("RESULT_CD","SUCC");
		SUCCESS_MAP.put("RESULT",true);

		Map<String, Object> FAIL_MAP = new HashMap<>();
		FAIL_MAP.put("RESULT_CD","FAIL");
		FAIL_MAP.put("RESULT",false);

		RESULT_MAP.put("SUCCESS", SUCCESS_MAP);
		RESULT_MAP.put("FAIL", FAIL_MAP);


	}





}
