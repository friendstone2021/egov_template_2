package com.egov.template.common.vo;

import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.util.List;
import java.util.Map;

/**
 * 실시간 데이터 VO 클래스<br>
 * <br>
 * <li>@author MJS</li>
 * <li>@date 2025. 02. 13.</li>
 */
@Data
@Alias("httpConnectVO")
public class HttpConnectVO {
	
	/** 요청할 api url */
	private String urlStr;
	
	/** S/W 관리 ID */
	private String id;
	
	/** S/W pid */
	private String pid;
	
	/** S/W 소유자 */
	private String owner;
	
	/** 서버 Main S/W 여부 */
	private String mainAp;
	
	/** CPU 정보 */
	private String cpu;
	
	/** Memory 정보 */
	private String mem;
	
	/** 명령줄(command line) */
	private String commandLine;
	
	/** Disk 정보 목록 */
	private List<Object> disk;
	
	/** Network 정보 목록 */
	private List<Object> network;
	
	/** S/W Listening port 목록 */
	private List<Object> port;
	
	/** 서버 Uptime(Seconds) */
	private String serverUptimeSec;
	
	/** 정보수집일시(YYYY-MM-DD’T’HH24:MI:SS.ZZZ) */
	private String lastUpdate;
	
	/** 등록일시(YYYY-MM-DD’T’HH24:MI:SS.ZZZ) */
	private String registDt;
	
	/** S/W 실행일시(YYYY-MM-DD’T’HH24:MI:SS.ZZZ) */
	private String lastStartTime;
	
	/** 호출 횟수 */
	private String callCount;	
	
	/** 성공 횟수 */
	private String successCount;
	
	/** 실패 횟수 */
	private String failureCount;
	
	/** 상세 오류 정보 목록(없으면 빈 배열) */
	private List<Object> errorDetails;
	
	private Map<String, Object> resultMap;
	
	private Map<String, Object> metadata;
	private Map<String, Object> metrics;
	private String methodStats;

	/** 기본URL */
	private String baseUrl;

	/** 전송대상 */
	private String sendSe;


	

	

}
