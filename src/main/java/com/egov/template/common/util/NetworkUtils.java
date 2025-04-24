package com.egov.template.common.util;

import com.egov.template.common.vo.HttpConnectVO;
import org.springframework.web.client.RestTemplate;

public class NetworkUtils {

    static final RestTemplate restTemplate = new RestTemplate();

    private NetworkUtils() {
        throw new IllegalArgumentException("Unable to create constructor.");
    }

    public static String simpleGetComm(HttpConnectVO requestVO) {
        //   HttpHeaders headers = new HttpHeaders();
        //   headers.setContentType(MediaType.APPLICATION_JSON);
        String url = requestVO.getBaseUrl()+requestVO.getUrlStr();
        return restTemplate.getForObject(url, String.class);
    }
}
