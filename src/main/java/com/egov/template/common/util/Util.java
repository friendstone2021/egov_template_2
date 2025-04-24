package com.egov.template.common.util;

import org.egovframe.rte.psl.dataaccess.util.EgovMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.util.UrlPathHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Map.Entry;
import java.util.regex.Pattern;

/**
 * 공통유틸
 * 
 * @author cjlee
 */
public class Util {

	private static final Logger LOGGER = LoggerFactory.getLogger(Util.class);
	
	static String[] code = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H",
			"I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V" };

	/**
	 * Map -> VO
	 * @param map
	 * @param objClass
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public Object convertMapToVO(Map map, Object objClass)  throws Exception {
		String keyAttribute = null;
		String methodString = null;
		Iterator itr = map.keySet().iterator();
		while (itr.hasNext()) {
			keyAttribute = (String) itr.next();
			methodString = "set" + keyAttribute.substring(0, 1).toUpperCase() + keyAttribute.substring(1);
			Method[] methods = objClass.getClass().getDeclaredMethods();
			for (int i = 0; i <= methods.length - 1; i++) {
				if (methodString.equals(methods[i].getName())) {
					String returnType = methods[i].getGenericParameterTypes()[0].toString();
					if ("int".equals(returnType)) {
						if (map.get(keyAttribute) != null) {
							methods[i].invoke(objClass,
									Integer.valueOf(map.get(keyAttribute).toString()).intValue());
						}
					} else {
						methods[i].invoke(objClass, map.get(keyAttribute));
					}
				}
			}
		}
		return objClass;
	}

	/**
	 * Map -> List
	 * @param mapList
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<LinkedHashMap<String, String>> convertMapToList(List<EgovMap> mapList) throws Exception  {
		
		String keyAttribute = null;

		List<LinkedHashMap<String, String>> list = new ArrayList<LinkedHashMap<String, String>>();

		for (int i = 0; i < mapList.size(); i++) {
			Map map = mapList.get(i);
			Iterator itr = map.keySet().iterator();
			LinkedHashMap linkedHashMap = new LinkedHashMap();
			while (itr.hasNext()) {
				keyAttribute = (String) itr.next();
				linkedHashMap.put(keyAttribute, map.get(keyAttribute));
			}
			list.add(linkedHashMap);
		}
		return list;
	}

	/**
	 * 10진수 --> 32 진수 답변형 게시판 작성시.. 답변일경우 원글 thread + 자기번호 thread select* from table
	 * order by thread DESC Depth 계산 (length(thread) /5 -1)
	 * @param thread
	 * @return
	 */
	public String getThread(int thread) {
		StringBuffer threadCode = new StringBuffer();

		if (thread < 32) {
			return "0000" + code[thread];
		} else {
			int divide = (int) (thread / 32);
			while (divide > 0) {
				int rest = (int) (thread % 32);
				threadCode.append(code[rest]);
				divide = (int) (thread / 32);
				thread = divide;
			}
			int count = 5 - threadCode.length();
			for (int j = 0; j < count; j++)
				threadCode.append("0");
			threadCode = threadCode.reverse();
		}
		return threadCode.toString();
	}

	/**
	 * 벡터를 받아 구분자 구분지어 스트링으로 리턴.
	 * 
	 * @param java.util.Vector
	 * @parma String delemeter
	 * @return String
	 **/
	@SuppressWarnings("rawtypes")
	public String addToken(java.util.Vector v, String delemeter) throws Exception {
		StringBuffer strBuffer = new StringBuffer();
		if (v == null)
			return strBuffer.toString();
		for (int i = 0; i < v.size(); i++) {
			strBuffer.append(v.elementAt(i));
			strBuffer.append(delemeter);
		}
		return strBuffer.toString();
	}

	/**
	 * 스트링을 받아 구분자로 나누어 벡터에 담아 리턴.
	 * 
	 * @param String
	 *            buffer
	 * @parma String delemeter
	 * @return Vector
	 **/
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public java.util.Vector getToken(String buffer, String delemeter) throws Exception {
		java.util.Vector v = new java.util.Vector();
		if (buffer == null)
			return v;
		java.util.StringTokenizer tmp = new java.util.StringTokenizer(buffer, delemeter);
		while (tmp.hasMoreElements()) {
			v.addElement(tmp.nextElement());
		}
		return v;
	}

	/**
	 * submit select tag
	 * 
	 * @param java.lang.String
	 *            select name
	 * @param java.lang.String[]
	 *            select value array
	 * @param java.lang.String[]
	 *            select option array
	 * @param java.lang.String
	 *            select default value
	 * 
	 **/
	public static String selectTag(String name, String[] values, String[] options, String defaultValue) {
		return selectTag(name, values, options, defaultValue, "");
	}

	/**
	 * submit select tag
	 * 
	 * @param java.lang.String
	 *            select name
	 * @param java.lang.String[]
	 *            select value array
	 * @param java.lang.String[]
	 *            select option array
	 * @param java.lang.String
	 *            select default value
	 * @param java.lang.String
	 *            select class style
	 **/
	public static String selectTag(String name, String[] values, String[] options, String defaultValue, String style) {
		if (values == null || options == null || values.length != options.length)
			return "";
		StringBuffer tmp = new StringBuffer();

		if (style.length() > 0)
			tmp.append("<select name=\"" + name + "\" class='" + style + "'>\n");
		else
			tmp.append("<select name=\"" + name + "\">\n");

		for (int i = 0; i < values.length; i++) {
			if (values[i].equals(defaultValue)) {
				tmp.append("<option selected value=\"" + values[i] + "\">" + options[i] + "\n");
			} else {
				tmp.append("<option value=\"" + values[i] + "\">" + options[i] + "\n");
			}
		}
		tmp.append("</select>");
		return tmp.toString();
	}

	/**
	 * submit select tag
	 * 
	 * @param java.lang.String
	 *            select name
	 * @param java.lang.String[]
	 *            select value array
	 * @param java.lang.String[]
	 *            select option array
	 * @param java.lang.String
	 *            select default value
	 *
	 **/
	public static String actionSelectTag(String name, String[] values, String[] options, String defaultValue) {
		return actionSelectTag(name, values, options, defaultValue, "");
	}

	/**
	 * submit select tag
	 * 
	 * @param java.lang.String
	 *            select name
	 * @param java.lang.String[]
	 *            select value array
	 * @param java.lang.String[]
	 *            select option array
	 * @param java.lang.String
	 *            select default value
	 * @param java.lang.String
	 *            select class style
	 **/
	public static String actionSelectTag(String name, String[] values, String[] options, String defaultValue,
			String style) {
		if (values == null || options == null || values.length != options.length)
			return "";
		StringBuffer tmp = new StringBuffer();

		if (style.length() > 0)
			tmp.append("<select name=\"" + name + "\" onChange=\"this.form.submit()\" class=" + style + ">\n");
		else
			tmp.append("<select name=\"" + name + "\" onChange=\"this.form.submit()\">\n");

		for (int i = 0; i < values.length; i++) {
			if (values[i].equals(defaultValue)) {
				tmp.append("\t<option selected value=\"" + values[i] + "\">" + options[i] + "\n");
			} else {
				tmp.append("\t<option value=\"" + values[i] + "\">" + options[i] + "\n");
			}
		}
		tmp.append("</select>");

		return tmp.toString();
	}

	/**
	 * submit select tag
	 * 
	 * @param java.lang.String
	 *            select name
	 * @param java.lang.String[]
	 *            select value array
	 * @param java.lang.String[]
	 *            select option array
	 * @param java.lang.String
	 *            select default value
	 * @param java.lang.String
	 *            select class style
	 **/
	public static String actionScriptSelectTag(String name, String[] values, String[] options, String defaultValue,
			String style, String script) {
		if (values == null || options == null || values.length != options.length)
			return "";
		StringBuffer tmp = new StringBuffer();

		if (style.length() > 0)
			tmp.append("<select name=\"" + name + "\" onChange=\"" + script + "\" class=" + style + ">\n");
		else
			tmp.append("<select name=\"" + name + "\" onChange=\"" + script + "\">\n");

		for (int i = 0; i < values.length; i++) {
			if (values[i].equals(defaultValue)) {
				tmp.append("\t<option selected value=\"" + values[i] + "\">" + options[i] + "\n");
			} else {
				tmp.append("\t<option value=\"" + values[i] + "\">" + options[i] + "\n");
			}
		}
		tmp.append("</select>");

		return tmp.toString();
	}

	/**
	 * alert Tag 생성
	 * 
	 * @param String
	 *            messgae
	 * @param boolean
	 *            history true|false
	 * @return String
	 **/
	public static String alertTag(String message, boolean back) {
		StringBuffer tmp = new StringBuffer();
		tmp.append("<script>alert(' " + message + " ');");
		if (back)
			tmp.append("history.back();");
		tmp.append("</script>");
		return tmp.toString();
	}

	/**
	 *
	 * @param java.lang.String
	 * @return java.lang.String
	 **/
	public static String scriptTag(String scriptBody) {
		StringBuffer tmp = new StringBuffer();
		tmp.append("<script>");
		tmp.append(scriptBody);
		tmp.append("</script>");
		return tmp.toString();
	}

	/**
	 * alert Tag 생성
	 * 
	 * @param String
	 *            messgae
	 * @param boolean
	 *            history true|false
	 * @return String
	 **/
	public static String alertTag(String message, String url) {
		StringBuffer tmp = new StringBuffer();

		tmp.append("<script>");
		if (!message.equals(""))
			tmp.append("alert(' " + message + " ');");
		if (!url.equals(""))
			tmp.append("location.href='" + url + "';");
		tmp.append("</script>");
		return tmp.toString();
	}

	/**
	 * 현재 날짜.
	 * 
	 * @return Date Type yyy.MM.dd
	 *
	 **/
	public static String getCurrentDate() {
		Date now = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String thedate = sdf.format(now);
		return thedate;
	}

	/**
	 * 시스템 날짜를 구한다
	 * 
	 * @param pattern
	 *            패턴
	 * @return pattern에 맞는 날짜 string
	 * @see <a href=
	 *      "http://java.sun.com/docs/books/tutorial/i18n/format/datepattern.html">패턴</a>
	 */
	public static String getCurrentDate(String pattern) {
		return new SimpleDateFormat(pattern).format(new Date());
	}

	/**
	 * 현재 시각.
	 * 
	 * @return Time Type H:m:s
	 **/
	public static String getCurrentTime() {
		SimpleDateFormat sdf = new SimpleDateFormat("H:mm:ss");
		String thetime = sdf.format(new Date());
		return thetime;
	}

	@SuppressWarnings("unused")
	public static String getStringcut(int leng, String str) throws Exception  {
		int hanlen = 0;
		int englen = 0;
		int cutleng = 0;
		char nc_str[] = str.trim().toCharArray();
		if (nc_str.length >= leng) {
			String cut_str = str.substring(0, leng);
			char strlen[] = cut_str.trim().toCharArray();
		}

		if (nc_str.length >= leng) {
			String cut_str = str.substring(0, leng);
			char strlen[] = cut_str.trim().toCharArray();
			for (int i = 0; i < strlen.length; i++) {
				String strlen2 = (new Character(strlen[i])).toString();
				int hash = strlen2.hashCode();
				if (hash > 127)
					hanlen++;
				else
					englen++;
			}

			if (hanlen % 2 != 0)
				hanlen--;

			cutleng = hanlen + englen;
			cut_str = cut_str.substring(0, cutleng);
			cut_str = cut_str + "...";
			return cut_str;
		} else {
			return str;
		}
	}

	/**
	 * replace 함수
	 * 
	 * @param String
	 *            src 원본 String
	 * @param String
	 *            oldstr 원본 String 내의 바꾸기 전 문자열
	 * @param String
	 *            newstr 바꾼 후 문자열
	 * @return String 치환이 끝난 문자열
	 *
	 **/
	public static String repWord(String src, String oldstr, String newstr) {
		if (src == null)
			return null;

		StringBuffer dest = new StringBuffer("");
		int len = oldstr.length();
		int srclen = src.length();
		int pos = 0;
		int oldpos = 0;

		while ((pos = src.indexOf(oldstr, oldpos)) >= 0) {
			dest.append(src.substring(oldpos, pos));
			dest.append(newstr);
			oldpos = pos + len;
		}
		if (oldpos < srclen)
			dest.append(src.substring(oldpos, srclen));
		return dest.toString();
	}

	/**
	 * replace 함수
	 * 
	 * @param String
	 *            src 원본 String
	 * @param String
	 *            oldstr 원본 String 내의 바꾸기 전 문자열
	 * @param String
	 *            newstr 바꾼 후 문자열
	 * @return String 치환이 끝난 문자열
	 *
	 **/
	public static String repWord1(Object src, String oldstr, String newstr) {
		if (src == null)
			return null;

		StringBuffer dest = new StringBuffer("");
		int len = oldstr.length();
		int srclen = ((String) src).length();
		int pos = 0;
		int oldpos = 0;

		while ((pos = ((String) src).indexOf(oldstr, oldpos)) >= 0) {
			dest.append(((String) src).substring(oldpos, pos));
			dest.append(newstr);
			oldpos = pos + len;
		}
		if (oldpos < srclen)
			dest.append(((String) src).substring(oldpos, srclen));
		return dest.toString();
	}

	/**
	 * 8859_1 --> euc-kr
	 *
	 **/
	public static String toKor(String s) throws java.io.UnsupportedEncodingException {
		if (s == null)
			return "";
		if (s.equals(""))
			return "";
		else
			return new String(s.getBytes("8859_1"), "KSC5601");
	}

	/**
	 * euc-kr --> 8859_1
	 *
	 **/
	public static String toEng(String s) throws java.io.UnsupportedEncodingException {
		if (s == null)
			return "";
		if (s.equals(""))
			return "";
		else
			return new String(s.getBytes("KSC5601"), "8859_1");
	}

	/**
	 * Cp1252 --> KSC5601
	 *
	 **/
	public static String toPage(String s) throws java.io.UnsupportedEncodingException {
		if (s == null)
			return "";
		if (s.equals(""))
			return "";
		else
			return new String(s.getBytes("Cp1252"), "KSC5601");
	}

	/**
	 * hlml 스트링에서 <> 안의 것을 제외한 스트링
	 * 
	 * @param String
	 * @return String
	 **/
	public static String htmlParse(String s) {
		return htmlParse(s, 1000);
	}

	/**
	 * hlml 스트링에서 <> 안의 것을 제외한 스트링
	 * 
	 * @param java.lang.String
	 * @param int
	 *            size
	 * @return String
	 *
	 **/
	public static String htmlParse(String s, int size) {
		if (s == null)
			return null;
		StringBuffer buffer = new StringBuffer();

		if (s.length() > size)
			s = s.substring(0, size);

		char[] c = s.toCharArray();
		int len = c.length;
		boolean inTag = false;
		for (int i = 0; i < len; i++) {
			if (!inTag) {
				if (c[i] == '<')
					inTag = true;
				else
					buffer.append(c[i]);
			} else {
				if (c[i] == '>')
					inTag = false;
			}
		}
		return buffer.toString();
	}

	/**
	 * htmlsrc의 string값에 '<', '>', '\"', '&nbsp;' 의 문자를 변환한다. ※ 나모에서 사용.
	 */
	public static String convertHtmlchars(String htmlstr) {
		String convert = new String();
		convert = replace(htmlstr, "<", "&lt;");
		convert = replace(convert, ">", "&gt;");
		convert = replace(convert, "\"", "&quot;");
		convert = replace(convert, "&nbsp;", "&amp;nbsp;");
		return convert;
	}

	/**
	 * htmlsrc의 string값에 '<', '>', '\"', '&nbsp;' 의 문자를 변환한다. ※ 나모에서 사용.
	 */
	public static String convertHtmlcharsBr(String htmlstr) {
		if (htmlstr == null)
			return "";

		String convert = new String();

		convert = replace(htmlstr, "<", "&lt;");
		convert = replace(convert, ">", "&gt;");
		convert = replace(convert, "\"", "&quot;");
		convert = replace(convert, "&nbsp;", "&amp;nbsp;");
		convert = replace(convert, "\r\n", "<br>");
		return convert;
	}

	/**
	 * 스트링중에서 특정스트링을 대소문자 구별하여 변경한다.
	 * 
	 * @param target
	 *            바꾸려는 문자열을 가진 원본
	 * @param old
	 *            찾을 문자열
	 * @param newer
	 *            바꿔줄 문자열
	 */
	public static String replace(String target, String old, String newer) {
		int i = target.indexOf(old);
		if (i == -1) {
			return target;
		}

		StringBuffer buf = new StringBuffer();
		buf.append(target.substring(0, i) + newer);

		if (i + old.length() < target.length()) {
			buf.append(replace(target.substring(i + old.length(), target.length()), old, newer));
		}

		return buf.toString();

	}

	/**
	 * '%' --> &#37
	 **/
	public static String percentParse(String s) {
		return repWord(s, "%", "&#37");
	}

	/**
	 * ' --> &acute
	 **/
	public static String acuteParse(String s) {
		return repWord(s, "'", "&acute;");
	}

	public static String xssParse(String s) {
		s = repWord(s, "<script>", "");
		s = repWord(s, "</script>", "");
		s = repWord(s, "<", "&lt;");
		s = repWord(s, ">", "&gt;");
		// s = repWord(s,"(","&#40;");
		// s = repWord(s,")","&#41;");
		// s = repWord(s,"#","&#35;");
		// s = repWord(s,"&","&#38;");

		return s;
	}

	public static String GetReplaceTag2Editor(String s) {
		// s = repWord(s,"<script>", "&lt;script&gt;");
		s = repWord(s, "\"", "'");
		s = repWord(s, "&amp;", "&amp;amp;");
		s = repWord(s, "&lt;", "&amp;lt;");
		s = repWord(s, "&gt;", "&amp;gt;");
		s = repWord(s, "&quot;", "&amp;quot;");
		s = repWord(s, "<", "&lt;");
		s = repWord(s, ">", "&gt;");
		s = repWord(s, "CHR(34)", "&quot;");
		return s;
	}

	/**
	 * <code>
	 *     스크립트 파싱
	 *	  <script>
	 *       ...
	 *     </script>
	 *  </code>
	 *
	 **/
	public static String scriptParse(String s) {
		s = repWord(s, "<script>", "&lt;script&gt;");
		return repWord(s, "</script>", "&lt;/script&gt;");
	}

	/** 
	 *
	 *
	 */
	public static final String strFix(String str, int num, String option) {
		StringBuffer sb = new StringBuffer();
		if (str.length() > num) {
			if (option.equals("y")) {
				sb.append(str.substring(0, (num - 3)) + "..");
			} else {
				sb.append(str.substring(0, num));
			}
		} else {
			sb.append(str);
		}
		return sb.toString();
	}

	public static final String strFixByte(String str, int num) throws Exception {
		if (str == null)
			return "";

		String tmp = str;
		int slen = 0, blen = 0;
		char c;
		if (tmp.getBytes("euc-kr").length > num) {
			while (blen + 1 < num) {
				c = tmp.charAt(slen);
				blen++;
				slen++;
				if (c > 127)
					blen++; // 2-byte character..
			}
			tmp = tmp.substring(0, slen) + "..";
		}
		return tmp;
	}

	public static final String dateFix(String s) {
		java.util.StringTokenizer st = new java.util.StringTokenizer(s, " ");
		String date_str = st.nextToken();
		return date_str;
	}

	/** 
	 *
	 *
	 */
	public static final String alignFix(String s) {
		String strFlag = "";
		char flag = s.charAt(0);
		switch (flag) {
		case 'L':
			strFlag = "left";
			break;
		case 'C':
			strFlag = "center";
			break;
		case 'R':
			strFlag = "right";
			break;
		}
		return strFlag;
	}

	public static final String fileNameDB(String srcName) {
		String temp = "";
		if (srcName != null) {
			String fname = srcName.substring(0, srcName.lastIndexOf("_"));
			String fileExt = srcName.substring(srcName.lastIndexOf("."));
			temp = fname + fileExt;
		}
		return temp;
	}

	public static final String fileNameLocal(String srcName) {
		String temp = "";
		if (srcName != null)
			temp = srcName.substring(srcName.lastIndexOf("_") + 1);

		return temp;
	}

	public static final boolean checkValue(String entity) {
		boolean isFlag = false;
		if (entity != null)
			if (entity.length() > 0)
				isFlag = true;

		return isFlag;
	}

	public static final String fileExt(String fileName) {
		String temp = "";
		if (fileName != null) {
			fileName.substring(fileName.lastIndexOf("."));
		}
		return temp;
	}

	public static final String getDeptSize(String thread) {
		int threadSize = thread.length();
		StringBuffer strBuffer = new StringBuffer();
		for (int i = 3; i < threadSize; i += 2)
			strBuffer.append("&nbsp;");
		return strBuffer.toString();
	}

	public static boolean imgCheck(String FileExt) {
		boolean flag;
		flag = false;
		FileExt = FileExt.toLowerCase();
		if (".jpg".equals(FileExt) || ".gif".equals(FileExt) || ".bmp".equals(FileExt) || ".png".equals(FileExt)) {
			flag = true;
		}

		return flag;
	}

	public static boolean upfileCheck(String FileExt) {
		boolean flag;
		flag = false;
		FileExt = FileExt.toLowerCase();
		if (".jsp".equals(FileExt) || ".class".equals(FileExt) || ".html".equals(FileExt) || ".htm".equals(FileExt)
				|| ".swf".equals(FileExt)) {
			flag = true;
		}

		return flag;
	}

	public static boolean movieCheck(String FileExt) {
		boolean flag;
		flag = false;
		if (!FileExt.equals("")) {
			String ext = FileExt.substring(FileExt.lastIndexOf("."));
			;
			ext = ext.toLowerCase();
			if (".avi".equals(ext) || ".mp4".equals(ext) || ".wmv".equals(ext) || ".asf".equals(ext)
					|| ".flv".equals(ext)) {
				flag = true;
			}
		}

		return flag;
	}

	public static boolean juminCheck(String ju1, String ju2) {
		boolean juminOk;
		int juminint[] = new int[13];
		int sangsu[] = { 2, 3, 4, 5, 6, 7, 8, 9, 2, 3, 4, 5 };
		String jumin = ju1 + ju2;
		char[] juch = jumin.toCharArray();

		for (int i = 0; i < juch.length; i++) {
			juminint[i] = (int) juch[i] - 48; // ‘8’
		}

		int sum = 0;
		for (int j = 0; j < sangsu.length; j++) {
			sum = sum + juminint[j] * sangsu[j];
		}

		int A = 11 - sum % 11;

		if (A >= 10) {
			A = A - 10;
		}

		if (A == juminint[juminint.length - 1]) {
			juminOk = true;
		} else {
			juminOk = false;
		}

		return juminOk;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List divideContent(String content, int size) throws java.io.UnsupportedEncodingException {
		List contents = new ArrayList();

		int contentLength = content.getBytes("euc-kr").length;
		int charSize = size;

		int stringlength, bytelength;
		char c;
		String resultContent = "";

		while (contentLength > 0) {

			stringlength = bytelength = 0;

			if (contentLength > charSize) {
				while (bytelength < charSize) {
					c = content.charAt(stringlength);
					bytelength++;
					stringlength++;
					if (c > 127)
						bytelength++; // 한글이다..
				}
			} else {
				stringlength = content.length();
				bytelength = content.getBytes().length;
			}

			contentLength -= bytelength;
			resultContent = content.substring(0, stringlength);
			content = content.substring(stringlength);

			if (resultContent.length() == 0)
				break;

			contents.add(resultContent);
		}
		return contents;
	}

	

	//private int mnSubDirCnt;

	

	/**
	 * 특정 날짜를 지정된 포맷형식으로 변환.
	 * 
	 * @param date
	 *            변경할 날짜
	 * @param form
	 *            변경할 형식
	 * @return 삭제 성공여부 (0 : 정상 종료, 1 : 인수가 디렉토리가 아니거나 존재하지 않음. 2 : 서브 폴더가 1000개 이상임.
	 *         - 무한 루프를 방지하기 위함. 3 : 루틴수행중 삭제하지 못한 파일이 있음. 4 : 파일은 삭제 하였으나 폴더를 삭제하지
	 *         못함.)
	 *
	 */
	public static String formatDate(String date, String form) {

		Calendar day = Calendar.getInstance();
		int nowYear = Integer.parseInt(date.substring(0, 4));
		int nowMonth = Integer.parseInt(date.substring(5, 7)) - 1;
		int nowDate = Integer.parseInt(date.substring(8, 10));
		day.set(nowYear, nowMonth, nowDate);

		Date today = day.getTime();
		SimpleDateFormat formatter = new SimpleDateFormat(form);
		String thedate = formatter.format(today);

		return thedate;
	}

	/**
	 * 사업자번호형식에 맞춰서 - 추가.
	 * 
	 * @param str
	 *            사업자번호(0531204212)
	 * @return 사업자번호 형식에 따른 사업자번호(053-12-04212 )
	 */
	public String frmBizno(String str) throws Exception {
		return setDelim(str, "999-99-99999");
	}

	/**
	 * 숫자로 구성된 문자열을 특정형식으로 포맷한다.<br>
	 * 결과값 2008-08-10
	 * 
	 * @param str
	 *            바꿀 문자열
	 * @param delimPtrn
	 *            바뀔 형태
	 * @return 출력 결과: 2000-12-15
	 */
	public String setDelim(String str, String delimPtrn) throws Exception {
		if ((str == null) || (str.trim().length() == 0) || (str.equals("0")))
			return "";
		StringBuffer sb = new StringBuffer();
		for (int i = 0, j = 0; i < delimPtrn.length(); i++) {
			if (delimPtrn.charAt(i) == '9') {
				sb.append(str.charAt(j));
				j++;
			} else {
				sb.append(delimPtrn.charAt(i));
			}
		}
		return sb.toString();
	}

	public static Object getNvl(Object val, Object r_val) {
		if (val == null)
			return (Object) r_val;
		else
			return val;
	}

	public static String getURL(HttpServletRequest request) {

		StringBuffer strURL = new StringBuffer();
		UrlPathHelper urlPathHelper = new UrlPathHelper();
		String originalURL = urlPathHelper.getOriginatingRequestUri(request);

		strURL.append(originalURL);
		if (getParam(request).length() > 0) {
			strURL.append("?");
			strURL.append(getParam(request));
		}
		return strURL.toString();
	}

	public static String getFullURL(HttpServletRequest request) {

		StringBuffer strURL = new StringBuffer();
		request.getRequestURL();

		strURL.append(request.getRequestURL());
		if (getParam(request).length() > 0) {
			strURL.append("?");
			strURL.append(getParam(request).replace("&amp;", "&"));
		}
		return strURL.toString();
	}

	public static String getURI(HttpServletRequest request) {

		StringBuffer strURL = new StringBuffer();
		UrlPathHelper urlPathHelper = new UrlPathHelper();
		String originalURL = urlPathHelper.getOriginatingRequestUri(request);

		strURL.append(originalURL);
		return strURL.toString();
	}

	public static String getURLRemoveXSS(HttpServletRequest request) {

		StringBuffer strURL = new StringBuffer();
		UrlPathHelper urlPathHelper = new UrlPathHelper();
		String originalURL = urlPathHelper.getOriginatingRequestUri(request);
		String strParams = getParam(request);
		// 특수 문자 제거

		strParams = strParams.replaceAll("\\(", "& #40;").replaceAll("\\)", "& #41;");
		strParams = strParams.replaceAll("'", "& #39;");
		strParams = strParams.replaceAll("eval\\((.*)\\)", "");
		strParams = strParams.replaceAll("[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\']", "\"\"");
		strParams = strParams.replaceAll("script", "");
		strParams = strParams.replaceAll(":", "");

		strURL.append(originalURL);
		if (strParams.length() > 0) {
			strURL.append("?");
			strURL.append(strParams);
		}
		return strURL.toString();
	}

	public static String getMbURLRemoveXSS(HttpServletRequest request) {
		StringBuffer strURL = new StringBuffer();
		UrlPathHelper urlPathHelper = new UrlPathHelper();
		String originalURL = urlPathHelper.getOriginatingRequestUri(request);
		String strParams = getParam(request);
		// 특수 문자 제거

		strParams = strParams.replaceAll("\\(", "& #40;").replaceAll("\\)", "& #41;");
		strParams = strParams.replaceAll("'", "& #39;");
		strParams = strParams.replaceAll("eval\\((.*)\\)", "");
		strParams = strParams.replaceAll("[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\']", "\"\"");
		strParams = strParams.replaceAll("script", "");
		strParams = strParams.replaceAll(":", "");

		strURL.append(originalURL);
		if (strParams.length() > 0) {
			strURL.append("?");
			strURL.append(strParams);
		}

		return strURL.toString();
	}

	public static String getFullURLRemoveXSS(HttpServletRequest request) {

		StringBuffer strURL = new StringBuffer();
		String strParams = getParam(request);
		// 특수 문자 제거

		strParams = strParams.replaceAll("\\(", "& #40;").replaceAll("\\)", "& #41;");
		strParams = strParams.replaceAll("'", "& #39;");
		strParams = strParams.replaceAll("eval\\((.*)\\)", "");
		strParams = strParams.replaceAll("[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\']", "\"\"");
		strParams = strParams.replaceAll("script", "");
		strParams = strParams.replaceAll(":", "");

		strURL.append(request.getRequestURL());
		if (strParams.length() > 0) {
			strURL.append("?");
			strURL.append(strParams);
		}
		return strURL.toString();
	}

	public static String getParam(HttpServletRequest request) {
		String paramString = "";

		Map<String, String[]> hm = request.getParameterMap();

		for (Entry<String, String[]> entry : hm.entrySet()) {
			Object obj = (Object) entry.getValue();
			String[] str = (String[]) obj;

			if (!paramString.equals("")) {
				paramString += "&amp;" + entry.getKey() + "=";
			} else {
				paramString += entry.getKey() + "=";
			}

			for (int index = 0; index < str.length; index++) {
				paramString += str[index];
			}
		}

		return paramString;
	}

	/**
	 * date : 변경할 날짜 orignalformat : 입력한 date의 형식 wantformat : 변경하고 싶은 형식
	 **/

	public static String getFormatDate(String date, String orignalformat, String wantformat) throws Exception {
		String day = "";
		Date d = null;
		SimpleDateFormat dd = new SimpleDateFormat(orignalformat, new Locale("en"));
		ParsePosition parse = new ParsePosition(0);
		d = dd.parse(date.replaceAll("\\p{Cntrl}", ""), parse);
		Calendar cal = Calendar.getInstance();
		cal.setTime(d);

		SimpleDateFormat sdf = new SimpleDateFormat(wantformat);
		day = sdf.format(cal.getTime());
		return day;
	}

	/*
	 * XSS 방지
	 */
	public static String removeXSS(String str, boolean use_html) {
		String str_low = "";
		if (use_html) { // HTML tag를 사용하게 할 경우 부분 허용

			StringBuffer strBuff = new StringBuffer();
			for (int j = 0; j < str.length(); j++) {
				char c = str.charAt(j);
				switch (c) {
				case '<':
					strBuff.append("&lt;");
					break;
				case '>':
					strBuff.append("&gt;");
					break;
				case '&':
					strBuff.append("&amp;");
					break;
				case '"':
					strBuff.append("&quot;");
					break;
				case '\'':
					strBuff.append("&apos;");
					break;
				default:
					strBuff.append(c);
					break;
				}
			}
			str = strBuff.toString();

			// HTML tag를 모두 제거
			// str = str.replaceAll("<","&lt;");
			// str = str.replaceAll(">","&gt;");

			// 특수 문자 제거
			/*
			 * str = str.replaceAll("\\(", "& #40;").replaceAll("\\)", "& #41;"); str =
			 * str.replaceAll("'", "& #39;"); str = str.replaceAll("eval\\((.*)\\)", "");
			 * str = str.replaceAll("[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\']", "\"\"");
			 * str = str.replaceAll("script", ""); str = str.replaceAll(":", "");
			 */
			// str = str.replaceAll("\"","&gt;");
			// str = str.replaceAll("&", "&amp;");
			// str = str.replaceAll("%00", null);
			// str = str.replaceAll("\"", "&#34;");
			// str = str.replaceAll("\'", "&#39;");
			// str = str.replaceAll("%", "&#37;");
			// str = str.replaceAll("../", "");
			// str = str.replaceAll("..\\\\", "");
			// str = str.replaceAll("./", "");
			// str = str.replaceAll("%2F", "");
			// str = str.replaceAll("\\(", "");
			// str = str.replaceAll("\\)", "");

			// str = str.replaceAll("\\(", "&#40");
			// str = str.replaceAll("\\)", "&#41");

			// 허용할 HTML tag만 변경
			// str = str.replaceAll("&amp;lt;p&amp;gt;", "<p>");
			// str = str.replaceAll("&amp;lt;p&amp;gt;", "<p>");
			// str = str.replaceAll("&amp;lp;P&gt;&amp;gt", "</p>");
			// str = str.replaceAll("&amp;lp;p&gt;&amp;gt", "</p>");
			// str = str.replaceAll("&amp;lt;br&amp;gt;", "<br>");
			// str = str.replaceAll("&amp;lt;BR&amp;gt;", "<BR>");
			// str = str.replaceAll("&amp;lp;br&gt;&amp;gt;", "</br>");
			// str = str.replaceAll("&amp;lp;BR&gt;&amp;gt;", "</Br>");
			// 스크립트 문자열 필터링 (선별함 - 필요한 경우 보안가이드에 첨부된 구문 추가)
			str_low = str.toLowerCase();
			if (str_low.contains("javascript") || str_low.contains("script") || str_low.contains("iframe")
					|| str_low.contains("vbscript") || str_low.contains("applet") || str_low.contains("embed")
					|| str_low.contains("object") || str_low.contains("frame") || str_low.contains("grameset")
					|| str_low.contains("layer") || str_low.contains("bgsound") || str_low.contains("alert")
					|| str_low.contains("onblur") || str_low.contains("onchange") || str_low.contains("onclick")
					|| str_low.contains("ondblclick") || str_low.contains("enerror") || str_low.contains("onfocus")
					|| str_low.contains("onload") || str_low.contains("onmouse") || str_low.contains("onscroll")
					|| str_low.contains("onsubmit") || str_low.contains("onunload") || str_low.contains("onmouseover")
					|| str_low.contains("expression") || str_low.contains("onerror")) {

				// str = str_low;
				str = str.replaceAll("(?i)javascript", "x-javascript");
				str = str.replaceAll("(?i)script", "x-script");
				str = str.replaceAll("(?i)iframe", "x-iframe");
				str = str.replaceAll("(?i)vbscript", "x-vbscript");
				str = str.replaceAll("(?i)applet", "x-applet");
				str = str.replaceAll("(?i)embed", "x-embed");
				str = str.replaceAll("(?i)object", "x-object");
				str = str.replaceAll("(?i)frame", "x-frame");
				str = str.replaceAll("(?i)grameset", "x-grameset");
				str = str.replaceAll("(?i)layer", "x-layer");
				str = str.replaceAll("(?i)bgsound", "x-bgsound");
				str = str.replaceAll("(?i)alert", "x-alert");
				str = str.replaceAll("(?i)onblur", "x-onblur");
				str = str.replaceAll("(?i)onchange", "x-onchange");
				str = str.replaceAll("(?i)onclick", "x-onclick");
				str = str.replaceAll("(?i)ondblclick", "x-ondblclick");
				str = str.replaceAll("(?i)enerror", "x-enerror");
				str = str.replaceAll("(?i)onfocus", "x-onfocus");
				str = str.replaceAll("(?i)onload", "x-onload");
				str = str.replaceAll("(?i)onmouse", "x-onmouse");
				str = str.replaceAll("(?i)onmouseover", "x-onmouseover");
				str = str.replaceAll("(?i)onscroll", "x-onscroll");
				str = str.replaceAll("(?i)onsubmit", "x-onsubmit");
				str = str.replaceAll("(?i)onunload", "x-onunload");
				str = str.replaceAll("(?i)expression", "x-expression");
				str = str.replaceAll("(?i)onerror", "x-onerror");
			}
		} else { // HTML tag를 사용하지 못하게 할 경우
			str = str.replaceAll("\"", "&gt;");
			str = str.replaceAll("&", "&amp;");
			str = str.replaceAll("<", "&lt;");
			str = str.replaceAll(">", "&gt;");
			str = str.replaceAll("%00", null);
			str = str.replaceAll("\"", "&#34;");
			str = str.replaceAll("\'", "&#39;");
			str = str.replaceAll("%", "&#37;");
			str = str.replaceAll("../", "");
			str = str.replaceAll("..\\\\", "");
			str = str.replaceAll("./", "");
			str = str.replaceAll("%2F", "");
		}
		return str;
	}

	/*
	 * XSS 방지문자 제거
	 */
	public static String removeXSSpreventChar(String str) {
		String str_low = "";
		str_low = str.toLowerCase();
		if (str_low.contains("javascript") || str_low.contains("script") || str_low.contains("iframe")
				|| str_low.contains("vbscript") || str_low.contains("applet") || str_low.contains("embed")
				|| str_low.contains("object") || str_low.contains("frame") || str_low.contains("grameset")
				|| str_low.contains("layer") || str_low.contains("bgsound") || str_low.contains("alert")
				|| str_low.contains("onblur") || str_low.contains("onchange") || str_low.contains("onclick")
				|| str_low.contains("ondblclick") || str_low.contains("enerror") || str_low.contains("onfocus")
				|| str_low.contains("onload") || str_low.contains("onmouse") || str_low.contains("onscroll")
				|| str_low.contains("onsubmit") || str_low.contains("onunload") || str_low.contains("onmouseover")
				|| str_low.contains("expression") || str_low.contains("onerror")) {
			// str = str_low;
			str = str.replaceAll("x-javascript", "javascript");
			str = str.replaceAll("x-script", "script");
			str = str.replaceAll("x-iframe", "iframe");
			str = str.replaceAll("x-vbscript", "vbscript");
			str = str.replaceAll("x-applet", "applet");
			str = str.replaceAll("x-embed", "embed");
			str = str.replaceAll("x-object", "object");
			str = str.replaceAll("x-frame", "frame");
			str = str.replaceAll("x-grameset", "grameset");
			str = str.replaceAll("x-layer", "layer");
			str = str.replaceAll("x-bgsound", "bgsound");
			str = str.replaceAll("x-alert", "alert");
			str = str.replaceAll("x-onblur", "onblur");
			str = str.replaceAll("x-onchange", "onchange");
			str = str.replaceAll("x-onclick", "onclick");
			str = str.replaceAll("x-ondblclick", "ondblclick");
			str = str.replaceAll("x-enerror", "enerror");
			str = str.replaceAll("x-onfocus", "onfocus");
			str = str.replaceAll("x-onload", "onload");
			str = str.replaceAll("x-onmouse", "onmouse");
			str = str.replaceAll("x-onmouseover", "onmouseover");
			str = str.replaceAll("x-onscroll", "onscroll");
			str = str.replaceAll("x-onsubmit", "onsubmit");
			str = str.replaceAll("x-onunload", "onunload");
			str = str.replaceAll("x-expression", "expression");
			str = str.replaceAll("x-onerror", "onerror");
		}
		return str;
	}

	public static boolean checkId(String str) {
		if (str == null || "".equals(str))
			return true;
		String regex = null;
		regex = "^[0-9]*$";
		if (Pattern.matches(regex, str))
			return false;

		regex = "^[a-zA-Z]*$";
		if (Pattern.matches(regex, str))
			return false;

		regex = "^[a-zA-Z][a-zA-Z0-9]{5,14}$";
		if (!Pattern.matches(regex, str))
			return false;
		return true;
	}

	public static boolean checkPw(String str) {
		if (str == null || "".equals(str))
			return true;
		String regex = null;
		regex = "^[0-9]*$";
		if (Pattern.matches(regex, str))
			return false;

		regex = "^[a-zA-Z]*$";
		if (Pattern.matches(regex, str))
			return false;

		regex = "^.{5,19}$";
		if (!Pattern.matches(regex, str))
			return false;
		return true;
	}

	public static boolean checkPostNo(String str) {
		if (str == null || "".equals(str))
			return true;
		String regex = null;
		regex = "^[0-9]{5}$";
		return Pattern.matches(regex, str);
	}

	public static boolean checkNum(String str) {
		if (str == null || "".equals(str))
			return true;
		String regex = null;
		regex = "^[0-9]*$";
		return Pattern.matches(regex, str);
	}

	public static boolean checkEmail(String str) {
		removeXSSpreventChar(str);
		if (str == null || "".equals(str) || "@".equals(str))
			return true;
		String regex = null;
		/* regex = "^([\\w\\.-]*)@(?:\\w+\\.-)+\\w+$"; */
		regex = "^([\\w\\.-]+)@([a-z\\d\\.-]+)\\.([a-z\\.]{2,6})$";
		return Pattern.matches(regex, str);
	}

	public static boolean checkFloat(String str) {
		if (str == null || "".equals(str))
			return true;
		String regex = null;
		regex = "^[0-9](\\.)[0-9]*$";
		return Pattern.matches(regex, str);
	}

	public static boolean checkTel(String str) {
		if (str == null || "".equals(str))
			return true;
		String regex = null;
		regex = "^\\d{2,4}--$";
		if (Pattern.matches(regex, str))
			return true;
		regex = "^\\d{2,4}-\\d{3,4}-\\d{4}$";
		if (Pattern.matches(regex, str))
			return true;
		return false;
	}

	public static boolean checkMaxLength(String str, int length) {
		if (str.length() > length)
			return false;
		return true;
	}

	public static boolean checkMinLength(String str, int length) {
		if (str.length() < length)
			return false;
		return true;
	}

	public static boolean checkRangeLength(String str, int minLength, int maxLength) {
		if (str.length() < minLength)
			return false;
		if (str.length() > maxLength)
			return false;
		return true;
	}

	public static boolean checkBizReg(String str) {
		if (str == null || "".equals(str) || "--".equals(str))
			return true;
		str = str.replaceAll("-", "");
		String regex = null;
		regex = "^\\d{10}$";
		if (Pattern.matches(regex, str))
			return true;
		return false;
	}

	public static boolean checkCorpReg(String str) {
		if (str == null || "".equals(str) || "-".equals(str))
			return true;
		str = str.replaceAll("-", "");
		String regex = null;
		regex = "^\\d{13}$";
		if (Pattern.matches(regex, str))
			return true;
		return false;
	}

	public static boolean checkRrn(String str) {
		if (str == null || "".equals(str) || "-".equals(str))
			return true;
		String regex = null;
		regex = "^\\d{6}-\\d{7}$";
		if (Pattern.matches(regex, str))
			return true;
		return false;
	}

	public static boolean checkName(String str, String realNm) {
		if (str.equals(realNm)) {
			return true;
		}
		return false;
	}

	/**
	 * 객체가 null인지 확인하고 null인 경우 "" 로 바꾸는 메서드
	 * 
	 * @param object
	 *            원본 객체
	 * @return resultVal 문자열
	 */
	public static String isNullToString(Object object) {
		String string = "";

		if (object != null) {
			string = object.toString().trim();
		}

		return string;
	}

	/**
	 * <p>
	 * String이 비었거나("") 혹은 null 인지 검증한다.
	 * </p>
	 *
	 * <pre>
	 *  StringUtil.isEmpty(null)      = true
	 *  StringUtil.isEmpty("")        = true
	 *  StringUtil.isEmpty(" ")       = false
	 *  StringUtil.isEmpty("bob")     = false
	 *  StringUtil.isEmpty("  bob  ") = false
	 * </pre>
	 *
	 * @param str
	 *            - 체크 대상 스트링오브젝트이며 null을 허용함
	 * @return <code>true</code> - 입력받은 String 이 빈 문자열 또는 null인 경우
	 */
	public static boolean isEmpty(String str) {
		return str == null || str.length() == 0;
	}

	/**
	 * 
	 * obj 값이 null 인 경우 chr로 치환하여 반환 처리
	 * 
	 * @param obj
	 *            - null 체크 할 parameter 값
	 * @param chr
	 *            - obj가 null인 경우 치환 값
	 * @author YOOSJ (2016.06.21)
	 * @return String
	 * 
	 */
	public static String null2String(Object obj, String chr) {
		String str;

		if (null == obj || "".equals(String.valueOf(obj))) {
			str = chr.trim();
		} else {
			str = String.valueOf(obj);
		}

		return str;
	}

	/**
	 * 
	 * obj 값이 null 인 경우 chr로 치환하여 반환 처리 chr가 "" 값인 경우 0으로 치환하여 반환 처리
	 * 
	 * @param obj
	 *            - null 체크 할 parameter 값
	 * @param chr
	 *            - obj가 null인 경우 치환 값
	 * @author YOOSJ (2016.06.21)
	 * @return Integer
	 * 
	 */
	public static Integer null2Int(Object obj, String chr) {
		int cnt;

		chr = "".equals(chr.trim()) ? "0" : chr;
		if (null == obj || "".equals(String.valueOf(obj))) {
			cnt = Integer.parseInt(chr);
		} else {
			cnt = Integer.parseInt(String.valueOf(obj));
		}

		return cnt;
	}

	/**
	 * @ 최초생성일자 2018. 6. 19. @ author Dev @ returnParam HashMap<String,Object>
	 * 
	 * @param request
	 * @param str
	 * @return
	 */
	public static HashMap<String, Object> getSessionMap(HttpServletRequest request, String str) {
		HashMap<String, Object> sessionMap = new HashMap<String, Object>();
		HttpSession session = request.getSession();
		sessionMap.putAll((Map<? extends String, ? extends Object>) session.getAttribute(str)); // hashMap 에 session 값을
																								// 담음
		return sessionMap;
	}

	/**
	 * @ 최초생성일자 2018. 6. 19. @ author Dev @ returnParam ArrayList<String>
	 * 
	 * @param request
	 * @return
	 */
	public static ArrayList<String> reqParamList(HttpServletRequest request) {
		Enumeration enums = request.getParameterNames();
		ArrayList<String> paramList = new ArrayList<String>();

		while (enums.hasMoreElements()) {
			String paramName = (String) enums.nextElement();
			String[] parameters = request.getParameterValues(paramName);

			if (parameters.length > 1) {
				paramList.add(paramName);
			}
		}
		return paramList;
	}

	/**
	 * @ 최초생성일자 2018. 8. 20.2018. 6. 4. @ author Dev
	 * 
	 * @param request
	 * @param fileNm
	 * @return
	 */
	public static String encodFileNm(HttpServletRequest request, String fileName) throws Exception {
		String userAgent = request.getHeader("User-Agent");

		boolean ie6 = userAgent.indexOf("MSIE 5.5") > -1;
		boolean ie = userAgent.indexOf("MSIE") > -1;
		boolean safari = userAgent.indexOf("Safari") > -1 || userAgent.indexOf("Firefox") > -1;

		if (ie6) {
			fileName = URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", "%20");
		} else if (ie) {
			fileName = URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", "%20");
		} else if (safari) {
			if (userAgent.contains("Firefox")) {
				fileName = new String(fileName.getBytes("UTF-8"), "8859_1");
			} else if (userAgent.contains("Opera")) {
				fileName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
			} else if (userAgent.contains("Chrome")) {
				fileName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
			} else if (userAgent.contains("Safari")) {
				fileName = URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", "%20");
			}
		} else {
				fileName = URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", "%20");
		}
		return fileName;
	}

	public static HashMap<String, Object> strNull2String(HashMap<String, Object> map, String asStr, String chgStr) {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();

		Iterator<?> itr = map.entrySet().iterator();
		while (itr.hasNext()) {
			Entry<?, ?> etr = (Entry<?, ?>) itr.next();
			String value = Util.null2String(etr.getValue(), "");
			if (asStr.equals(value)) {
				value = "";
			}
			resultMap.put(Util.null2String(etr.getKey(), ""), value);
		}
		return resultMap;
	}

	/***********************************
	 * 파라미터 디코드
	 * 
	 * @param hashMap
	 * @return hashMap TODO
	 ***********************************/
	public static HashMap<String, Object> getDecodeValue(HashMap<String, Object> hashMap) throws Exception  {
		Iterator<?> itr = hashMap.entrySet().iterator();
		while (itr.hasNext()) {
			Entry<?, ?> etr = (Entry<?, ?>) itr.next();
			String value = "";
			value = URLDecoder.decode(Util.null2String(etr.getValue(), ""), "UTF-8");

			hashMap.put(Util.null2String(etr.getKey(), ""), value);
		}

		return hashMap;
	}
	
	
	/***********************************
	 * 상대경로로 접근하는 문자열 치환 및 제거
	 * 
	 * @param String
	 * @return String
	 ***********************************/
	public static String removeRelativePath(String path) {
		if(path == "") {
			return "";
		}
		path = path.replaceAll("\\.\\.\\/", "");
		path = path.replaceAll("\\.\\/", "");
		path = path.replaceAll("\\.", "");
		
		return path;
	}
	
}