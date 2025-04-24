package com.egov.template.common.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

public class CommonUtils {

	public static String getRandomString(){
		return UUID.randomUUID().toString().replaceAll("-", "");
	}


	/**
     * Hash값으로 변환
     * @param {String} strMsg
     * @return {String} HashValue
     */
    public static String getHash(String strMsg) {

        StringBuffer sbOutpt = new StringBuffer();
        MessageDigest md = null;

        try {
            md = MessageDigest.getInstance("SHA-512"); md.update(strMsg.getBytes());
            byte[] arrDigest = md.digest(); final int LNGTH = arrDigest.length;
            for ( int i = 0 ; i < LNGTH ; i++ )
                sbOutpt.append(Integer.toString((arrDigest[i] & 0xff) + 0x100, 16).substring(1));
        } catch ( NoSuchAlgorithmException e ) {
            sbOutpt.append(strMsg);
        }

        return sbOutpt.toString();
    }

    /**
     * 사용자 비밀번호 + 아이디를 합쳐 salt 기법을 만든다.
     * @param {String} userPw
     * @param {String} usId
     * @return {String} saltPassword
     */
    public static String getSaltPassword(String userPw, String usId) {

    	return userPw + usId;
    }

    /**
     * 시작년도와 증가시킬 기간을 입력하여 년도 리스트를 리턴한다.
     * @param {int} stYear
     * @param {int} term
     * @return {List<String>} list
     */
    public static List<String> getComboYear(int stYear, int term) {

    	List<String> list = new ArrayList<String>();

    	int numIndex = 0;

    	for (int i = stYear; i < stYear + term; i++) {

    		list.add(String.valueOf(stYear + numIndex++));
		}

    	return list;
    }

    /**
     * 0 ~ 24시에 대한 String을 리턴한다.
     * @return {List<String>} list
     */
    public static List<String> getComboTime() {

    	List<String> list = new ArrayList<String>();

    	for (int i = 0; i < 24; i++) {

    		list.add(String.valueOf(i));
		}

    	return list;
    }
    
    public String cho_hangul(String name){
        
        String rtName ="";
        char epName;
        
       for(int i=0; i<name.length();i++){
             epName = name.charAt(i);
             rtName+= Direct(epName);
       }
      
        return rtName;
     }
     
     public String Direct(char b){
        int first ;
        String[] cho = {"ㄱ","ㄲ","ㄴ","ㄷ","ㄸ","ㄹ","ㅁ","ㅂ","ㅃ","ㅅ","ㅆ","ㅇ","ㅈ","ㅉ","ㅊ","ㅋ","ㅌ","ㅍ","ㅎ"};
        Pattern regex = Pattern.compile("/^[가-힝a-zA-Z]+$/");
         
        String charS = new Character(b).toString();
        
        if(Pattern.matches("^[a-zA-Z]*$", charS)){
           return "A-Z";
        } else if (Pattern.matches("^[0-9]*$", charS)){
        	return "0-9";
        } else{
           first = (b-44032)/(21*28);
           if(first >=0 && first <=18){
        	   if ( cho[first].equals("ㄱ") || cho[first].equals("ㄲ")  ){
        		   return "가";
        	   } else if ( cho[first].equals("ㄴ") ){
        		   return "나";
        	   } else if ( cho[first].equals("ㄷ") || cho[first].equals("ㄸ")  ){
        		   return "다";
        	   } else if ( cho[first].equals("ㄹ") ){
        		   return "라";
        	   } else if ( cho[first].equals("ㅁ") ){
        		   return "마";
        	   } else if ( cho[first].equals("ㅂ") || cho[first].equals("ㅃ")  ){
        		   return "바";
        	   } else if ( cho[first].equals("ㅅ") || cho[first].equals("ㅆ")  ){
        		   return "사";
        	   } else if ( cho[first].equals("ㅇ") ){
        		   return "아";
        	   } else if ( cho[first].equals("ㅈ") || cho[first].equals("ㅉ")  ){
        		   return "자";
        	   } else if ( cho[first].equals("ㅊ") ){
        		   return "차";
        	   } else if ( cho[first].equals("ㅋ") ){
        		   return "카";
        	   } else if ( cho[first].equals("ㅌ") ){
        		   return "타";
        	   } else if ( cho[first].equals("ㅍ") ){
        		   return "파";
        	   } else if ( cho[first].equals("ㅎ") ){
        		   return "하";
        	   } else {
        		   return "A";
        	   }
           }
           return "A";
        }
         
     }
     
     public static String ranpass(int count){
  		Random rnd =new Random();

  		StringBuffer buf =new StringBuffer();

  		 
  		for(int i=0;i<count;i++){
  			rnd.setSeed(new Date().getTime());
  		    if(rnd.nextBoolean()){
  		        buf.append((char)((int)(rnd.nextInt(26))+97));
  		    }else{
  		        buf.append((rnd.nextInt(10))); 
  		    }
  		}
  		
  		return buf.toString();
  	}
    
     
 	public static boolean isEmpty(Object obj) {
		if (obj == null) {
			return true;
		} else if ("".equals(obj)) {
			return true;
		} else if (obj instanceof Object[]) {
			for (Object v : ((Object[])obj)) {
				if (!isEmpty(v)) return false;
			}
			return true;
		} else return false;
	}
  
 	
	/**
	 * @ 날짜 시스템에서 날짜를 구해온다.
	 */
	public static String getDateNumber(String ptn) {
		SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat (ptn, Locale.KOREA);
		Date dt_src  = new Date ();
		String ret = mSimpleDateFormat.format(dt_src);
		return ret;
	}
 	
 	
	/**
	 * @ 날짜 (yyyyMMddHHmmssXXXX)구하기
	 * @return
	 * 	ex) 201311251322552540
	 */
	public static String getLongNumber() {
		String num = getDateNumber("yyyyMMddHHmmss");
		String tmp = getDateNumber("SSS");
		tmp = getDigitNum(Integer.parseInt(tmp));
		num = num + tmp;
		return num;
	} 	
 
	
	/**
	 * @ XXXX구하기 위한 진수변환 및 자리채움
	 */
	public static String getDigitNum(int num) {
		// 길이가 4자리여야 한다.
		return setLeftPadding(getDigitNum(num, 10, 6),4,"0");
	}		
	
	
	/**
	 * @ 10진수를 X(X<10)진수로 변환 (10진수를 X로 나눈 몫을 계속 나눠 단계별로 떨어지는 나머지를 역순으로 적으면 됨)
	 */
	public static String getDigitNum(int num, int srcDigit, int tgtDigit) {
		String ret = "";
		int cul = 0;
		if (srcDigit < 10) {
			String tmp = Integer.toString(num);
			num = 0;
			for (int i=0;i<tmp.length();i++) {
				num += Integer.parseInt(tmp.substring(i, i+1))*(int)Math.pow(srcDigit, tmp.length()-i-1);
			}
		}
		if (tgtDigit > 1 && tgtDigit < 10) {
			ret = Integer.toString(num%tgtDigit)+ret;
			cul = num/tgtDigit;
			while (cul > 0) {
				ret = Integer.toString(cul%tgtDigit)+ret;
				cul = cul/tgtDigit;
			}
		} else {
			ret = Integer.toString(num);
		}
		return ret;
	}
	
	
	/**
	 * 필요한 길이가 안되는 경우 입력한 문자로 채움 
	 */
	public static String setLeftPadding(String str, int length, String padStr) {
		String ret = "";
		int lenStr = str.length();
		if(lenStr < length) {
			char strResult[] = new char[length];
			char strArr[] = str.toCharArray();
			for(int i=0;i<length;i++) {
				if(i < (length-lenStr)) {
						strResult[i] = padStr.charAt(0);
				} else {
					strResult[i] = strArr[i - (length - lenStr)];
				}
			}
			ret = new String(strResult);
		} else {
			ret = str;
		}
		return ret;
	}
	
	
	
}
