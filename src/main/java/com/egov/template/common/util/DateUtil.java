package com.egov.template.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 일자, 시간과 관련된 다수의 기능
 * <p>
 * 일자패턴 사용시의 문법
 * 
 * <pre>
 *  Symbol   Meaning                 Presentation        Example
 *  ------   -------                 ------------        -------
 *  G        era designator          (Text)              AD
 *  y        year                    (Number)            1996
 *  M        month in year           (Text &amp; Number)     July &amp; 07
 *  d        day in month            (Number)            10
 *  h        hour in am/pm (1&tilde;12)    (Number)            12
 *  H        hour in day (0&tilde;23)      (Number)            0
 *  m        minute in hour          (Number)            30
 *  s        second in minute        (Number)            55
 *  S        millisecond             (Number)            978
 *  E        day in week             (Text)              Tuesday
 *  D        day in year             (Number)            189
 *  F        day of week in month    (Number)            2 (2nd Wed in July)
 *  w        week in year            (Number)            27
 *  W        week in month           (Number)            2
 *  a        am/pm marker            (Text)              PM
 *  k        hour in day (1&tilde;24)      (Number)            24
 *  K        hour in am/pm (0&tilde;11)    (Number)            0
 *  z        time zone               (Text)              Pacific Standard Time
 *  '        escape for text         (Delimiter)
 *  ''       single quote            (Literal)           '
 * 
 *  [예시]
 *  Format Pattern                         Result
 *  --------------                         -------
 *  &quot;yyyyMMdd&quot;                        -&gt;&gt;  19960710
 *  &quot;yyyy-MM-dd&quot;                      -&gt;&gt;  1996-07-10
 *  &quot;HHmmss&quot;                          -&gt;&gt;  210856
 *  &quot;HH:mm:ss&quot;                        -&gt;&gt;  21:08:56
 *  &quot;hh:mm:ss&quot;                        -&gt;&gt;  09:08:56
 *  &quot;yyyy.MM.dd hh:mm:ss&quot;             -&gt;&gt;  1996.07.10 15:08:56
 *  &quot;EEE, MMM d, ''yy&quot;                -&gt;&gt;  Wed, July 10, '96
 *  &quot;h:mm a&quot;                          -&gt;&gt;  12:08 PM
 *  &quot;hh 'o''clock' a, zzzz&quot;           -&gt;&gt;  12 o'clock PM, Pacific Daylight Time
 *  &quot;K:mm a, z&quot;                       -&gt;&gt;  0:00 PM, PST
 *  &quot;yyyyy.MMMMM.dd GGG hh:mm aaa&quot;    -&gt;&gt;  1996.July.10 AD 12:08 PM
 * 
 * </pre>
 * 
 * 기타 자세한 것은 <a href=
 * "http://java.sun.com/j2se/1.3/docs/api/java/text/SimpleDateFormat.html"
 * >SimpleDateFormat</a> Class API Document 를 참조할것
 */
public class DateUtil {

	private static final Logger LOGGER = LoggerFactory.getLogger(DateUtil.class);

	private static final String TIME_PATTERN = "HH:mm";

	// ~ Methods
	// ================================================================

	/**
	 * Return default datePattern (yyyy-MM-dd)
	 * 
	 * @return a string representing the date pattern on the UI
	 */
	public static String getDatePattern()  throws Exception {
		// Locale locale = LocaleContextHolder.getLocale();
		String datePattern=null;
		// defaultDatePattern =
		// ResourceBundle.getBundle(Constants.BUNDLE_KEY, locale)
		// .getString("date.format");
		datePattern = "yyyy-MM-dd";

		return datePattern;
	}

	public static String getTimePattern() throws Exception  {
		return DateUtil.getDatePattern() + " HH:mm:ss";
	}

	public static String getTime2Pattern() throws Exception  {
		return DateUtil.getDatePattern() + " HH:mm";
	}

	/**
	 * This method attempts to convert an Oracle-formatted date in the form
	 * dd-MMM-yyyy to mm/dd/yyyy.
	 * 
	 * @param aDate
	 *            date from database as a string
	 * @return formatted string for the ui
	 */
	public static final String getDate(Date aDate) throws Exception  {
		SimpleDateFormat df = null;
		String returnValue = "";

		if (aDate != null) {
			df = new SimpleDateFormat(getDatePattern());
			returnValue = df.format(aDate);
		}

		return (returnValue);
	}

	/**
	 * This method generates a string representation of a date/time in the
	 * format you specify on input
	 * 
	 * @param aMask
	 *            the date pattern the string is in
	 * @param strDate
	 *            a string representation of a date
	 * @return a converted Date object
	 * @see SimpleDateFormat
	 * @throws ParseException
	 */
	public static final Date convertStringToDate(String aMask, String strDate)
			 throws Exception  {
		SimpleDateFormat df = null;
		Date date = null;
		df = new SimpleDateFormat(aMask);

		// if (log.isDebugEnabled()) {
		// log.debug("converting '" + strDate + "' to date with mask '"
		// + aMask + "'");
		// }

		date = df.parse(strDate);

		return (date);
	}
	
	public static final String convertFormat(String aMask1,String aMask2,String strDate)  throws Exception  {
		
		SimpleDateFormat df1 = null;
		
		SimpleDateFormat df2 = null;
		
		df1 = new SimpleDateFormat(aMask1);
		
		String date = "";
		
		Date diaryDate = null;
		
		df2 = new SimpleDateFormat(aMask2);
		
		diaryDate = df1.parse(strDate);
		
		date = df2.format(diaryDate);
		
		return date;
	}
	
	public static final String getBeforeDay(String aMask1,String strDate)  throws Exception  {
		
		SimpleDateFormat df1 = null;	
		
		df1 = new SimpleDateFormat(aMask1);
		
		String date = "";
		
		Date diaryDate = null;
		
		diaryDate = df1.parse(strDate);
		
		Calendar cal = new GregorianCalendar(Locale.KOREA);			

		cal.setTime(diaryDate);

		cal.add(Calendar.DATE, -1);
		
		date = df1.format(cal.getTime());
			
		return date;
	}

	
	
	/**
	 * This method returns the current date time in the format: MM/dd/yyyy HH:MM
	 * a
	 * 
	 * @param theTime
	 *            the current time
	 * @return the current date/time
	 */
	public static String getTimeNow(Date theTime) {
		return getDateTime(TIME_PATTERN, theTime);
	}

	/**
	 * This method returns the current date in the format: MM/dd/yyyy
	 * 
	 * @return the current date
	 * @throws ParseException
	 */
	public static Calendar getToday() throws Exception  {
		Date today = new Date();
		SimpleDateFormat df = new SimpleDateFormat(getDatePattern());

		// This seems like quite a hack (date -> string -> date),
		// but it works ;-)
		String todayAsString = df.format(today);
		Calendar cal = new GregorianCalendar();
		cal.setTime(convertStringToDate(todayAsString));

		return cal;
	}

	/**
	 * This method generates a string representation of a date's date/time in
	 * the format you specify on input
	 * 
	 * @param aMask
	 *            the date pattern the string is in
	 * @param aDate
	 *            a date object
	 * @return a formatted string representation of the date
	 * 
	 * @see SimpleDateFormat
	 */
	public static final String getDateTime(String aMask, Date aDate) {
		SimpleDateFormat df = null;
		String returnValue = "";

		if (aDate == null) {
			LOGGER.error("aDate is null!");
		} else {
			df = new SimpleDateFormat(aMask);
			returnValue = df.format(aDate);
		}

		return (returnValue);
	}

	/**
	 * This method generates a string representation of a date based on the
	 * System Property 'dateFormat' in the format you specify on input
	 * 
	 * @param aDate
	 *            A date to convert
	 * @return a string representation of the date
	 */
	public static final String convertDateToString(Date aDate) throws Exception  {
		return getDateTime(getDatePattern(), aDate);
	}

	/**
	 * This method converts a String to a date using the datePattern
	 * 
	 * @param strDate
	 *            the date to convert (in format MM/dd/yyyy)
	 * @return a date object
	 * 
	 * @throws ParseException
	 */
	public static Date convertStringToDate(String strDate)
			 throws Exception  {
		Date aDate = null;

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("converting date with pattern: " + getDatePattern());
		}

		aDate = convertStringToDate(getDatePattern(), strDate);

		return aDate;
	}

	/**
	 * 문자열의 값이 일자값인지 검증
	 * 
	 * @param textDate
	 *            일자값을 가진 8자리 문자열 예) '20010806'
	 * @return 일자값이면 true, 아니면 false
	 */
	public static boolean isDate(String textDate)  throws Exception {
		dateCheck(textDate);
		return true;
	}

	/**
	 * 내부적인 Date Value Check용 임
	 * 
	 * @param textDate
	 */
	private static void dateCheck(String textDate) throws Exception {
		if (textDate.length() != 8)
			throw new RuntimeException("[" + textDate + "] is not date value");

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

		sdf.setLenient(false);
		Date dt = sdf.parse(textDate);
	}

	/**
	 * 일자값을 가진 8자리 문자열로 Calendar 객체를 생성
	 * 
	 * @param textDate
	 *            일자값을 가진 8자리 문자열 예) '20010806'
	 * @return Calendar 객체
	 */
	public static Calendar getCalendar(String textDate) throws RuntimeException {
		// dateCheck(textDate);
		int year = Integer.parseInt(textDate.substring(0, 4));
		int month = Integer.parseInt(textDate.substring(4, 6));
		int date = Integer.parseInt(textDate.substring(6, 8));

		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Asia/Seoul"));

		if (textDate.length() == 14) {
			int hour = Integer.parseInt(textDate.substring(8, 10));
			int minute = Integer.parseInt(textDate.substring(10, 12));
			int second = Integer.parseInt(textDate.substring(12, 14));
			cal.set(year, month - 1, date, hour, minute, second);
		} else {
			cal.set(year, month - 1, date);
		}

		return cal;
	}

	/**
	 * 일자값을 가진 8자리 문자열로 Date 객체를 생성
	 * 
	 * @param textDate
	 *            일자값을 가진 8자리 문자열 예) '20010806'
	 * @return Date 객체
	 */
	public static Date getDate(String textDate) throws RuntimeException {
		return getCalendar(textDate).getTime();
	}

	/**
	 * 주어진 Date 객체를 이용하여 주어진 패턴 날짜형의 문자열을 구함.
	 * 
	 * @param date
	 *            원하는 일자의 Date 객체
	 * @param pattern
	 *            원하는 일자 패턴
	 * @return 주어진 패턴의 일자
	 */
	public static String getDateString(Date date, String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(date);
	}

	/**
	 * 주어진 Date 객체를 이용하여 기본날짜형('yyyyMMdd')의 문자열을 구함.
	 * 
	 * @param date
	 *            원하는 일자의 Date 객체
	 * @return 주어진 패턴의 일자
	 */
	public static String getDateString(Date date) throws RuntimeException {
		return DateUtil.getDateString(date, "yyyyMMdd");
	}

	/**
	 * 주어진 일자를 이용하여 주어진 패턴 날짜형의 문자열을 구함.
	 * 
	 * @param textDate
	 *            일자값을 가진 8자리 문자열 예) '20010806'
	 * @param pattern
	 *            원하는 일자 패턴
	 * @return 주어진 패턴의 일자
	 */
	public static String getDateString(String textDate, String pattern)
			throws RuntimeException {
		String date = null;
		if (textDate == null || "".equals(textDate)) 
		{
			date = "";
		} 
		else 
		{
			date = DateUtil.getDateString(getDate(textDate), pattern);			
		}
		return date;
	}

	/**
	 * 주어진 패턴 날짜형 시스템일자를 구함
	 * 
	 * @param pattern
	 *            원하는 일자 패턴
	 * @return 시스템 일자
	 */
	public static String getSysDate(String pattern) {
		return getDateString(new Date(), pattern);
	}

	/**
	 * 기본패턴('yyyyMMdd') 날짜형 시스템일자를 구함
	 * 
	 * @param pattern
	 *            원하는 일자 패턴
	 * @return 기본형('yyyyMMdd')의 시스템 일자
	 */
	public static String getSysDate() {
		return getSysDate("yyyyMMdd");
	}

	/**
	 * 기본패턴('HHmmss') 날짜형 시스템시간을 구함
	 * 
	 * @param pattern
	 *            원하는 일자 패턴
	 * @return 기본형('HHmmss')의 시스템 시간
	 */
	public static String getTime() {
		return getSysDate("HHmmss");
	}

	/**
	 * 지정한 분리자를 이용한 시스템일자를 구함
	 * 
	 * @param delmt
	 *            원하는 분리자 문자 예) ':', '/' ...
	 * @return 분리자가 삽입된 시스템 시간
	 */
	public static String getTime(char delmt) {
		return getSysDate("HH" + delmt + "mm" + delmt + "ss");
	}

	/**
	 * 지정된 일자로 부터 일정기간 이후의 일자를 구함
	 * 
	 * @param fromDate
	 *            시작일자
	 * @param termDays
	 *            원하는 기간
	 * @return 일정기간 이후의 일자 ('yyyyMMdd')
	 */
	public static String getDateAdd(String fromDate, int termDays)
			throws RuntimeException {
		Calendar cal = getCalendar(fromDate);
		cal.add(Calendar.DATE, termDays);
		return getDateString(cal.getTime(), "yyyyMMdd");
	}

	/**
	 * 지정된 일자로 부터 일정달 이후의 일자를 구함
	 * 
	 * @param fromDate
	 *            시작일자
	 * @param termMonth
	 *            원하는 달
	 * @return 일정기간 이후의 일자 ('yyyyMMdd')
	 */
	public static String getMonthAdd(String fromDate, int termMonth)
			throws RuntimeException {
		Calendar cal = getCalendar(fromDate);
		cal.add(Calendar.MONTH, termMonth);
		return getDateString(cal.getTime(), "yyyyMMdd");
	}

	/**
	 * 시작일로부터 종료일까지의 일수를 구함
	 * 
	 * @param fromDate
	 *            시작일자
	 * @param toDate
	 *            종료일자
	 * @param both
	 *            양편넣기 여부
	 * @return 시작일자로 부터 종료일까지의 일수
	 */
	public static int getDiffDays(Date fromDate, Date toDate, boolean both) {
		long diffDays = toDate.getTime() - fromDate.getTime();
		long days = diffDays / (24 * 60 * 60 * 1000);
		if (both) {
			if (days >= 0)
			{
				days += 1;
			}
			else
			{
				days -= 1;
			}
		}
		return new Long(days).intValue();
	}

	/**
	 * 시작일로부터 종료일까지의 일수를 한편넣기로 계산함.
	 * 
	 * @param fromDate
	 *            시작일자
	 * @param toDate
	 *            종료일자
	 * @return 시작일자로 부터 종료일까지의 일수
	 */
	public static int getDiffDays(Date fromDate, Date toDate) {
		return getDiffDays(fromDate, toDate, false);
	}

	/**
	 * 시작일로부터 종료일까지의 일수를 구함
	 * 
	 * @param fromDate
	 *            시작일자
	 * @param toDate
	 *            종료일자
	 * @param both
	 *            양편넣기 여부
	 * @return 시작일자로 부터 종료일까지의 일수
	 */
	public static int getDiffDays(String fromDate, String toDate, boolean both) throws RuntimeException {
		
		if(toDate==null)
		{
			return 0;
		}
		return DateUtil.getDiffDays(getDate(fromDate), getDate(toDate), both);
	}

	/**
	 * 시작일로부터 종료일까지의 일수를 한편넣기로 계산함.
	 * 
	 * @param fromDate
	 *            시작일자
	 * @param toDate
	 *            종료일자
	 * @return 시작일자로 부터 종료일까지의 일수
	 */
	public static int getDiffDays(String fromDate, String toDate)
			throws ParseException,RuntimeException {
		return getDiffDays(getDate(fromDate), getDate(toDate), false);
	}


	public static long getDiffTime(String pattern, String receiveTime,
			String currentTime) throws ParseException,RuntimeException {
		DateFormat sdFormat = new SimpleDateFormat(pattern);
		Date receiveDate = sdFormat.parse(receiveTime);
		Date currentDate = sdFormat.parse(currentTime);
		long diffDays = currentDate.getTime() - receiveDate.getTime();
		diffDays = diffDays / 1000;

		return diffDays;
	}

	public static long getDiffTime(String pattern, String receiveTime,
			String currentTime, int timeOut) throws ParseException,RuntimeException {
		DateFormat sdFormat = new SimpleDateFormat(pattern);
		Date receiveDate = sdFormat.parse(receiveTime);
		Date currentDate = sdFormat.parse(currentTime);
		long diffDays = currentDate.getTime() - receiveDate.getTime();
		diffDays = diffDays / 1000;

		return timeOut - diffDays;
	}

	public static int getMyAge(String ssn) {

		String today = ""; // 시스템 날짜
		String birthyear = ""; // 생일
		String birthday = "";
		int myAge = 0; // 만 나이

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy", Locale.KOREA);

		today = formatter.format(new Date()); // 시스템 날짜를 가져와서 yyyy 형태로 변환

		if (ssn.charAt(6) == '1' || ssn.charAt(6) == '2') {
			birthyear = "19" + ssn.substring(0, 2); // 주민번호 7번째 자리수가 1 또는 2이면
														// 1900년대 출생
			birthday = "19" + ssn.substring(2, 4); // 주민번호 7번째 자리수가 1 또는 2이면
		} else {
			birthday = "20" + ssn.substring(2, 4); // 주민번호 7번째 자리수가 1 또는 2가
														// 아니면 2000년대 출생
		}

		myAge = Integer.parseInt(today) - Integer.parseInt(birthday) + 1; // 현재년도
		return myAge;

	}
	
	/**
	 * 날짜로 요일 구하기
	 * @param date - 요일 구할 날짜
	 */
	public static String getDayOfweek(String date)  throws Exception {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		String[] week = { "일", "월", "화", "수", "목", "금", "토" };
		Calendar cal = Calendar.getInstance();
		Date getDate;
		getDate = format.parse(date);
		cal.setTime(getDate);
		int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
		return week[w];
	}
}