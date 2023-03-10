package com.example.townmarket.common.domain.address;

import java.nio.charset.Charset;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;


@Component
public class BaseUtility {

    private final String HOST_v2 = "https://pcmap-api.place.naver.com";
    private final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/103.0.0.0 Safari/537.36";
    private final String REFERER = "https://map.naver.com/";

    public HttpHeaders getDefaultHeader() {
        MediaType mediaType = new MediaType("application", "json", Charset.forName("UTF-8"));
        HttpHeaders httpHeaders = new HttpHeaders();
        MultiValueMap<String, String> headerValues = new LinkedMultiValueMap<>();
        headerValues.add(HttpHeaders.ACCEPT, "*/*");
        headerValues.add(HttpHeaders.HOST, HOST_v2);
        headerValues.add(HttpHeaders.USER_AGENT, USER_AGENT);
        headerValues.add("Referer", REFERER);
        headerValues.add("Connection", "keep-alive");
        httpHeaders.addAll(headerValues);
        httpHeaders.setContentType(mediaType);
        return httpHeaders;
    }
}