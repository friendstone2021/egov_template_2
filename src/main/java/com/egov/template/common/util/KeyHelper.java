package com.egov.template.common.util;

/**
 * Unique key 생성기 Helper 클래스
 *
 * @author cjlee
 */
public class KeyHelper {

    /**
     * IKeyGenerator KEY_GENERATOR
     */
    private static final KeyGenerator KEY_GENERATOR = new KeyGenerator();

    /**
     * String Unique key 생성
     *
     * @return String Unique key 리턴
     */
    public static String getStringKey() {
        return KEY_GENERATOR.getStringKey();
    }

    /**
     * String Unique key 생성
     *
     * @param division
     * @return String Unique key 리턴
     */
    public static String getStringKey(String division) {
        return KEY_GENERATOR.getStringKey(division);
    }

    /**
     * Date Unique key 생성
     *
     * @return Date Unique key 리턴
     */
    public static String getDateStringKey() {
        return KEY_GENERATOR.getDateStringKey();
    }

    /**
     * Date Unique key 생성
     *
     * @return Date Unique key 리턴
     */
    public static String getDateStringKey(String division) {
        return KEY_GENERATOR.getDateStringKey(division);
    }
}