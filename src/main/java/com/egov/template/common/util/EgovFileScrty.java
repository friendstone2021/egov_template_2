package com.egov.template.common.util;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Base64인코딩/디코딩 방식을 이용한 데이터를 암호화/복호화하는 Business Interface class
 * File Name EgovFileScrty.java
 * @Author   syoh
 * @since 2022.05.12
 * @version 1.0
 *
 * <pre>
 * == 개정이력(Modification Information)==
 * 수정일                 수정자          수정내용
 * -------      -------   ----------------------------
 * 2022.05.12   syoh           최초 작성
 *
 * COPYRIGHT 2023 GMT.. ALL RIGHT RESERVED.
 * </pre>
 */
public class EgovFileScrty {

	private static final Logger LOGGER = LoggerFactory.getLogger(EgovFileScrty.class);

	/**
	 * 데이터를 암호화하는 기능
	 *
	 * @param byte[] data 암호화할 데이터
	 * @return String result 암호화된 데이터
	 * @exception Exception
	 */
	public static String encodeBinary(byte[] data){
		if (data == null) {
			return "";
		}

		return new String(Base64.encodeBase64(data));
	}

	/**
	 * 데이터를 암호화하는 기능
	 *
	 * @param String data 암호화할 데이터
	 * @return String result 암호화된 데이터
	 * @exception Exception
	 */
	public static String encode(String data) {
		return encodeBinary(data.getBytes());
	}

	/**
	 * 데이터를 복호화하는 기능
	 *
	 * @param String data 복호화할 데이터
	 * @return String result 복호화된 데이터
	 * @exception Exception
	 */
	public static byte[] decodeBinary(String data) {
		return Base64.decodeBase64(data.getBytes());
	}

	/**
	 * 데이터를 복호화하는 기능
	 *
	 * @param String data 복호화할 데이터
	 * @return String result 복호화된 데이터
	 * @exception Exception
	 */
	public static String decode(String data) {
		return new String(decodeBinary(data));
	}


    /**
     * 비밀번호를 암호화하는 기능(복호화가 되면 안되므로 SHA-256 인코딩 방식 적용)
     *
     * @param password 암호화될 패스워드
     * @param id salt로 사용될 사용자 ID 지정
     * @return
     * @throws Exception
     * @throws Exception
     */
    public static String encryptPassword(String password, String id) {

		if (password == null) return "";
		if (id == null) return ""; // KISA 보안약점 조치 (2018-12-11, 신용호)

		byte[] hashValue = null; // 해쉬값

		MessageDigest md;
		try {
			md = MessageDigest.getInstance("SHA-256");
			md.reset();
			md.update(id.getBytes());

			hashValue = md.digest(password.getBytes());
		} catch (NoSuchAlgorithmException e) {
			LOGGER.error(e.getMessage());
		}


		return new String(Base64.encodeBase64(hashValue));
    }

    public static void main(String[] args) {
        System.out.println(EgovFileScrty.encryptPassword("ADMIN", "ADMIN"));
    }

    /**
     * 비밀번호를 암호화하는 기능(복호화가 되면 안되므로 SHA-256 인코딩 방식 적용)
     * @param data 암호화할 비밀번호
     * @param salt Salt
     * @return 암호화된 비밀번호
     * @throws Exception
     */
    public static String encryptPassword(String data, byte[] salt) {

		if (data == null) {
		    return "";
		}

		byte[] hashValue = null; // 해쉬값

		MessageDigest md;
		try {
			md = MessageDigest.getInstance("SHA-256");
			md.reset();
			md.update(salt);

			hashValue = md.digest(data.getBytes());
		} catch (NoSuchAlgorithmException e) {

			LOGGER.error(e.getMessage());
		}


		return new String(Base64.encodeBase64(hashValue));
    }

    /**
     * 비밀번호를 암호화된 패스워드 검증(salt가 사용된 경우만 적용).
     *
     * @param data 원 패스워드
     * @param encoded 해쉬처리된 패스워드(Base64 인코딩)
     * @return
     * @throws Exception
     */
    public static boolean checkPassword(String data, String encoded, byte[] salt) {
    	byte[] hashValue = null; // 해쉬값

    	MessageDigest md;
		try {
			md = MessageDigest.getInstance("SHA-256");
			md.reset();
			md.update(salt);
			hashValue = md.digest(data.getBytes());
		} catch (NoSuchAlgorithmException e) {

			LOGGER.error(e.getMessage());
		}


    	return MessageDigest.isEqual(hashValue, Base64.decodeBase64(encoded.getBytes()));
    }

	public static String hashString(String pwd, String id) {

		String inputData = pwd + id;

		StringBuilder hexString = new StringBuilder();

		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] encodedhash = digest.digest(inputData.getBytes(StandardCharsets.UTF_8));

			// 변환된 해시를 16진수 문자열로 변환
			for (byte b : encodedhash) {
				String hex = Integer.toHexString(0xff & b);
				if (hex.length() == 1) {
					hexString.append('0');
				}
				hexString.append(hex);
			}
		} catch (NoSuchAlgorithmException e) {
			LOGGER.error(e.getMessage());
		}

		return hexString.toString();
	}
}