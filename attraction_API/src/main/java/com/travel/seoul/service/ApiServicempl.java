package com.travel.seoul.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.springframework.stereotype.Service;

@Service
public class ApiServicempl implements ApiService{

	@Override
	public String attractionAPI(String urlStr, String select, String value, String numOfRows, String apiType, String status_dt) {
		try {
    		// DB에 해당하는 API 호출
    		StringBuilder urlBuilder = new StringBuilder(urlStr); /*URL*/
    		urlBuilder.append("?" + URLEncoder.encode("select","UTF-8") + "="+URLEncoder.encode(select, "UTF-8")); 
    		urlBuilder.append("&" + URLEncoder.encode("value","UTF-8") + "=" + URLEncoder.encode(value, "UTF-8")); //** 섹션이랑 연결해야함 **//
    		urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode(numOfRows, "UTF-8")); /*한 페이지 결과 수*/
    		urlBuilder.append("&" + URLEncoder.encode("apiType","UTF-8") + "=" + URLEncoder.encode(apiType, "UTF-8")); /*결과형식(xml/json)*/
    		urlBuilder.append("&" + URLEncoder.encode("status_dt","UTF-8") + "=" + URLEncoder.encode(status_dt, "UTF-8")); /*기준일자*/ 
	        URL url = new URL(urlBuilder.toString());
	        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	        conn.setRequestMethod("GET");
	        conn.setRequestProperty("Content-type", "application/json");
	        System.out.println("Response code: " + conn.getResponseCode());
	        
	        // 데이터 읽어오는 작업 수행
	        BufferedReader rd;
	        // HTTP 응답 코드가 200에서 299 사이는 성공
	        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
	        	rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	        }
	        else {
	        	rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
	        }
	        
	        // 문자열 변환
	        StringBuilder sb = new StringBuilder();
	        String line;
	        while ((line = rd.readLine()) != null) {
	        	sb.append(line);
	        }
	        rd.close();
	        System.out.println(sb.toString());
	        
	        return sb.toString();
    	}
    	catch (IOException e) {
            return "API 호출 중 오류 발생";
        }
	}

}

