package com.egov.template.common.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @Class Name : ComDefaultVO.java
 * @Description : ComDefaultVO class
 * @Modification Information
 * @
 * @  수정일         수정자                   수정내용
 * @ -------    --------    ---------------------------
 * @ 2009.02.01    조재영         최초 생성
 *
 *  @author 공통서비스 개발팀 조재영
 *  @since 2009.02.01
 *  @version 1.0
 *  @see
 *
 */
@Data
@SuppressWarnings("serial")
public class DashBoardVO implements Serializable {
	
	/** 순번 */
	private int rnum = 0;
	
	/** 상호명 */
	private String conmNm;
	
	private String cntrctNu;
	
	/** 계약일 */
	private String cntrctYmd;
	
	/** 중분류 코드 */
	private String lndseMclsfCd;
	
	/** 중분류 코드명 */
	private String lndseMclsfNm;
	
	/** 공급 면적 */
	private String tmpDstrbtnArea;
	
	/** 대분류 코드명 */
	private String lndseLclsfNm;
	
	/** 총 면적 */
	private String totArea;
	
	/** 총 면적 비율(Percent 공식 대입) */
	private String areaRatio;
	
	/** 공구 번호 */
	private String cnstrctznInfo;
	
	/** 분양 면적 */
	private String cfmtnArea;
	
	/** 분양율 */
	private String lcRatio;
	
	/** 잔여용지 */
	private String remingArea;
	
	/** 일반-가동중 */
	private String gBizStsR;
	
	/** 일반-건설중 */
	private String gBizStsB;

	/** 일반-미착공 */
	private String gBizStsN;
	
	/** 일반-MOU */
	private String gMouCount;
	
	/** 일반-입주계약 */
	private String gCtrtCount;
	
	/** 일반-협의중 */
	private String gDscsCount;
	
	/** 일반-심사완료 */
	private String gJudgCount;
	
	/** 임대-가동중 */
	private String LsBizStsR;
	
	/** 임대-건설중 */
	private String LsBizStsB;

	/** 임대-미착공 */
	private String LsBizStsN;
	
	/** 임대-MOU */
	private String LsMouCount;
	
	/** 임대-입주계약 */
	private String LsCtrtCount;
	
	/** 임대-협의중 */
	private String LsDscsCount;
	
	/** 임대-심사완료 */
	private String LsJudgCount;
	
	/** 등록 아이디 */
	private String regId;
	
	/** 등록 일시 */
	private String reg_dt;
	
	/** 수정 일시 */
	private String mdfcnDt;
	
	/** 수정 아이디 */
	private String mdfcnId;
	
	/** ALL 필지 수 */
	private String lscaACnt;
	
	/** 분양대상 HA */
	private String tmpDstrbtnHa;
	
	/** 분양면적 HA */
	private String cfmtnHa;
	
	/** 분양면적 percent */
	private String percent;
	
	/** B 필지수 */
	private String lscaBCnt;
	
	/** 입주대상 개수 */
	private String cmCnt;
	
	/** 입주 개수 */
	private String moinCnt;
	
	/** 진행중 개수 */
	private String mopsCnt;
	
	/** 대기 개수 */
	private String mowtCnt;
	
	/** 미입주 개수 */
	private String nocpCnt;
	
	/** 미분양 개수 */
	private String unsdLscaCnt;
	
	/** 미분양 면적 */
	private String unsdCfmtnArea;
	
	/** 미분양 HA */
	private String unsdCfmtnHa;
	
	/**	입주현황	*/
	private String moinStsSe;
	
	/** 납부금액(억원) */
	private String ohmPayAmt;
	
	/** 고지금액(억원) */
	private String ohmInfrmAmt;
	
	/** 고지금액 */
	private String infrmAmt;
	
	/** 납부금액 */
	private String payAmt;
	
	/** 납부일자 */
	private String payYmd;
	
	/** 연체발생일수 */
	private String ovdDtcnt;
	
	/** 연체이자금액 */
	private String ovdAmt;
	
	/** 연체이자금리 */
	private String ovdRt;
	
	/** 연체율 */
	private String ovdPercent;
	
	/** 미납금액 */
	private String nPayAmt;
	
	/** 미납율 */
	private String nPayPercent;
	
	/** 완납금액 */
	private String fullPayAmt;
	
	/** 완납율 */
	private String fullPercent;
	
	/** 완납일자 */
	private String fullPayYmd;
	
	/** 연체/미납/완납 구분 코드 */
	private String recordType;
	
	private String bbsInnb;
	
	private String bbsSe;
	
	private String bbsSj;
	
	private String bbsCn;
	
	private String regDt;
	
	
	/**	사업비 매출액 현황	*/
	
	/** 총 매출액  */
	private String totalAmt;
	
	/** 연도별  */
	private String yearStatic;
	
	/** 총 건수  */
	private String totalCnt;
	
}
