package com.egov.template.common.util;

import com.egov.template.common.constants.Constants;

import java.util.HashMap;
import java.util.Map;

/**
 * 파라미터 유틸
 * @author cjlee
 */
public class ParamUtil {

	/**
	 * 숫자형 치환 @param obj - null 체크 할 parameter 값, chr - obj가 null인 경우
	 * @param obj
	 * @param chr
	 * @return
	 */
	public static Integer getValidInt(Object obj, String chr) {
		
		int cnt;

		chr = "".equals(chr.trim()) ? "0" : !ParamUtil.isNumber(chr) ? "0" : chr;
		
		if ("".equals(ParamUtil.getValidStr(obj))) {
			cnt = Integer.parseInt(chr);
		} else {
			cnt = ParamUtil.isNumber(ParamUtil.getValidStr(obj)) ? Integer.parseInt(ParamUtil.getValidStr(obj)) : 0;
		}

		return cnt;
	}

	/**
	 * 문자 치환 @param obj - null 체크 할 parameter 값, str - obj가 null인 경우 치환
	 * @param o
	 * @param str
	 * @return
	 */
	public static String null2String(Object o, String str) {
		String returnStr;
		if (null == o) {
			returnStr = str;
		} else {
			returnStr = String.valueOf(o);
		}
		return returnStr;
	}

	/**
	 * 문자열이 숫자이면 true 반환
	 * @param str
	 * @return
	 */
	public static boolean isNumber(String str) {
		boolean check = true;
		for (int i = 0; i < str.length(); i++) {
			if (!Character.isDigit(str.charAt(i))) {
				check = false;
				break;
			}
		}
		return check;
	}

	/**
	 * Object 가 null 이면 "" 을 아니면 toString()을 반환
	 * @param o
	 * @return
	 */
	public static String getValidStr(Object o) {
		return o == null ? "" : o.toString();
	}
	
	/**
	 * Toast tui Grid용으로 페이징 정보 셋팅
	 * @param param
	 */
	public static void setToastGridPageInfo(Map<String, Object> param) {
		
		String perPageStr = null2String(param.get("perPage"), "10");
		String pageStr = null2String(param.get("page"), "1");
		
		int perPage = isNumber(perPageStr) ? Integer.parseInt(perPageStr) : 10;
		int page = isNumber(pageStr) ? Integer.parseInt(pageStr) : 1;
		
		param.put("firstIndex", (perPage * (page - 1)) + 1);
		param.put("lastIndex", perPage * page);
	}

	public static Map<String, Object> getResultMap(String key, String resultMsg){
		Map<String, Object> resultMap = Constants.RESULT_MAP.get(key);
		Map<String, Object> copyMap = new HashMap<>();

		for(Map.Entry<String, Object> map : resultMap.entrySet()){
			copyMap.put(map.getKey(), map.getValue());
		}
		copyMap.put("RESULT_MSG", resultMsg);

		return copyMap;
	}
}