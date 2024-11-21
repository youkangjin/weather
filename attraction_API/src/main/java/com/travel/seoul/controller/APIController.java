package com.travel.seoul.controller;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.taglibs.standard.lang.jstl.EnumeratedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.travel.seoul.service.ApiService;
import com.travel.seoul.vo.UserVO;

import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;

@Controller
public class APIController {
	@Autowired
	private ApiService apiservice;
	 
	// 파이썬에서 숫자로 작업했기 때문에 각 숫자에 해당하는 값 리스트 생성
	List<String> numlist = Arrays.asList("강동구", "송파구", "종로구", "용산구", "도봉구", "마포구", "강남구", "중구", "동대문구", "광진구", "영등포구"
			, "서초구", "중랑구", "성동구", "동작구", "강서구", "강북구", "관악구", "구로구", "금천구", "노원구", "양천구"
			, "은평구", "서대문구", "성북구");
	List<String> namelist = Arrays.asList("선사유적지", "잠실롯데타워", "경복궁", "남산타워", "도봉산", "홍대거리", "코엑스", "명동", "DDP", "어린이대공원", "더현대"
			, "반포한강공원", "용마랜드", "서울숲", "보라매공원", "서울식물원", "북한산둘레길", "낙성대공원", "푸른수목원", "호암산잣나무산림욕장", "화랑대철도공원", "서서울호수공원"
			, "은평한옥마을", "서대문형무소", "정릉");
	List<String> imagelist = Arrays.asList("/resources/img/지역구별이미지/강동구-선사유적지.jpg", "/resources/img/지역구별이미지/송파구-잠실롯데타워.jpg"
			, "/resources/img/지역구별이미지/종로구-경복궁.jpg", "/resources/img/지역구별이미지/용산구-남산타워.jpg", "/resources/img/지역구별이미지/도봉구-도봉산.jpg"
			, "/resources/img/지역구별이미지/마포구-홍대거리.jpg", "/resources/img/지역구별이미지/강남구-코엑스.jpg", "/resources/img/지역구별이미지/중구-명동.jpg"
			, "/resources/img/지역구별이미지/동대문구-DDP.jpg", "/resources/img/지역구별이미지/광진구-어린이대공원.jpg", "/resources/img/지역구별이미지/영등포구-더현대.jpg"
			, "/resources/img/지역구별이미지/서초구-반포한강공원.jpg", "/resources/img/지역구별이미지/중랑구-용마랜드.jpg", "/resources/img/지역구별이미지/성동구-서울숲.jpg"
			, "/resources/img/지역구별이미지/동작구-보라매공원.jpg", "/resources/img/지역구별이미지/강서구-서울식물원.jpg", "/resources/img/지역구별이미지/강북구-북한산둘레길.jpg"
			, "/resources/img/지역구별이미지/관악구-낙성대공원.jpg", "/resources/img/지역구별이미지/구로구-푸른수목원.jpg", "/resources/img/지역구별이미지/금천구-호암산 잣나무산림욕장.jpg"
			, "/resources/img/지역구별이미지/노원구-화랑대철도공원.jpg", "/resources/img/지역구별이미지/양천구-서서울호수공원.jpg", "/resources/img/지역구별이미지/은평구-은평한옥마을.jpg"
			, "/resources/img/지역구별이미지/서대문구-서대문형무소.jpg", "/resources/img/지역구별이미지/성북구-정릉.jpg");
	
    @GetMapping("/api")
    public String attraction_api(Model model, HttpServletRequest request, HttpSession session) {
    	// /update에서 session 호출
    	String select1 = (String) session.getAttribute("select1");
    	String select2 = (String) session.getAttribute("select2");
    	String select3 = (String) session.getAttribute("select3");
    	
    	// 로그인 한 사용자 DB session 생성 및 호출
    	// 생성 코드
    	String userAge = null, userSex = null, attraction1 = null, attraction2 = null, attraction3 = null;
    	
    	UserVO vo = (UserVO) session.getAttribute("loginMember");
    	if(vo != null) {
    		userAge = (String) vo.getUserAge().split("대")[0];
    		userSex = (String) (vo.getUserSex().equals("여자")?"2":"1");
    		attraction1 = (String) vo.getUserArea1();
    		attraction2 = (String) vo.getUserArea2();
    		attraction3 = (String) vo.getUserArea3();
		}
    	
    	// 사용자가 로그인을 했다면 나이 API 호출
		if(userAge != null && !userAge.equals("")) {
    		String user_age = apiservice.attractionAPI("http://localhost:5000/api/attraction", "userAge", userAge, "500", "json", "20231121");
    		String[] sb_age = user_age.split("\"");
        	
    		model.addAttribute("age",userAge);
        	model.addAttribute("sb_age", namelist.get(Integer.parseInt(sb_age[1]) - 1));
        	model.addAttribute("sb_age_img", imagelist.get(Integer.parseInt(sb_age[1]) - 1));
    	}
		// 사용자가 로그인을 했다면 성별 API 호출
    	if(userSex != null && !userSex.equals("")) {
    		String user_sex = apiservice.attractionAPI("http://localhost:5000/api/attraction", "userSex", userSex, "500", "json", "20231121");
    		String[] sb_sex = user_sex.split("\"");
    		// 숫자에 해당하는 값으로 변경
    		if (userSex=="1") {
    			model.addAttribute("sex","남성");
    		}
    		else {
    			model.addAttribute("sex","여성");
    		}	
    		
        	model.addAttribute("sb_sex", namelist.get(Integer.parseInt(sb_sex[1]) - 1));
        	model.addAttribute("sb_sex_img", imagelist.get(Integer.parseInt(sb_sex[1]) - 1));
    	}
    	// 사용자가 로그인을 했다면 관광지1 API 호출
    	if(attraction1 != null && !attraction1.equals("")) {
    		// 선택한 관광지의 index 추출
        	int index1 = numlist.indexOf(attraction1);
            System.out.println(index1);
    		model.addAttribute("attraction1",attraction1);
        	model.addAttribute("attraction1_name", namelist.get(index1));
        	model.addAttribute("attraction1_img", imagelist.get(index1));
    	}	
    	// 사용자가 로그인을 했다면 관광지2 API 호출
    	if(attraction2 != null && !attraction2.equals("")) {
        	int index2 = numlist.indexOf(attraction2);
            System.out.println(index2);
    		model.addAttribute("attraction2",attraction2);
        	model.addAttribute("attraction2_name", namelist.get(index2));
        	model.addAttribute("attraction2_img", imagelist.get(index2));
    	}
    	// 사용자가 로그인을 했다면 관광지3 API 호출
    	if(attraction3 != null && !attraction3.equals("")) {
        	int index3 = numlist.indexOf(attraction3);
            System.out.println(index3);
    		model.addAttribute("attraction3",attraction3);
        	model.addAttribute("attraction3_name", namelist.get(index3));
        	model.addAttribute("attraction3_img", imagelist.get(index3));
    	}

    	// 추천 체크박스를 선택했다면 선택한 관광지의 API 호출
        List<Integer> intValue_list = new ArrayList<>();
    	for (int i = 1; i <= 3; i++) {
    	    String select = null;
    	    switch (i) {
    	        case 1: select = select1; break;
    	        case 2: select = select2; break;
    	        case 3: select = select3; break;
    	    }

    	    if (select != null && !select.equals("")) {
    	        String userSelect = apiservice.attractionAPI("http://localhost:5000/api/attraction", "attraction", select, "500", "json", "20231121");
    	        String[] sbSelect = userSelect.split("\"");
    	        String selectCheck = String.join("", sbSelect);
    	        session.setAttribute("sb_select" + i, selectCheck);

    	        model.addAttribute("select" + i, namelist.get(Integer.parseInt(select) - 1));
    	        model.addAttribute("sb_select" + i, namelist.get(Integer.parseInt(sbSelect[1]) - 1));

    	        String sbSelectWeather = apiservice.attractionAPI("http://localhost:5000/api/weather_prediction", "weather_prediction", selectCheck, "500", "json", "20231121");
    	        String cleanedString = sbSelectWeather.replaceAll("[\\[\\] ]", "");
    	        int commaIndex = cleanedString.indexOf(",");
    	        // 예측 미세먼지 농도
    	        if (commaIndex != -1) {
    	            String firstValue = cleanedString.substring(0, commaIndex);
    	            System.out.println("firstValue" + i + ": " + firstValue);
    	            double value = Double.parseDouble(firstValue);
    	            int intValue = (int) value;

    	            String weather = "";
        	        String weathermsg ="";
        	        if (intValue>= 150) {
        	           weather = "매우나쁨";
        	           weathermsg ="외출을 삼가시길 바랍니다";
        	           System.out.println(weathermsg);
        	           model.addAttribute("weather"+ i, weather);
        	           model.addAttribute("weathermsg"+ i, weathermsg);
        	        } else if (intValue>= 81) {
        	           weather = "나쁨";
        	           weathermsg ="외출을 삼가시길 바랍니다";
        	           System.out.println(weathermsg);
        	           model.addAttribute("weather"+ i, weather);
        	           model.addAttribute("weathermsg"+ i, weathermsg);
        	        } else if (intValue >= 31) {
        	           weather = "보통";
        	           weathermsg ="외출에 유의하시길 바랍니다";
        	           System.out.println(weathermsg);
        	           model.addAttribute("weather"+ i, weather);
        	           model.addAttribute("weathermsg"+ i, weathermsg);
        	        } else{
        	           weather = "좋음";
        	           weathermsg ="외출하시길 추천드립니다";
        	           System.out.println(weathermsg);
        	           model.addAttribute("weather"+ i, weather);
        	           model.addAttribute("weathermsg"+ i, weathermsg);
        	        }
    	        }
    	        // 현재 미세먼지 농도
    	        if (commaIndex != -1 && commaIndex < cleanedString.length() - 1) {
    	            String secondValue = cleanedString.substring(commaIndex + 1);
    	            System.out.println(secondValue.trim());
    	            int intValue = Integer.parseInt(secondValue);
    	            model.addAttribute("secondValue" + i, intValue);

    	            intValue_list.add(intValue);
    	        }
    	        System.out.println("intValue_list: "+intValue_list);
    	        int minValue = Collections.min(intValue_list);    	            
    	        System.out.println("minValue: "+minValue);
    	        
    	        
    	    }
    	}

    	return "API";
    }
    
    // 추천 체크박스 선택 시 호출
    @GetMapping("/update")
    public String update(Model model, HttpServletRequest request, HttpSession session) {
    	System.out.println("업데이트");
    	// 여러 값을 받을 경우 getParameterValues 사용
    	String select1=request.getParameterValues("attraction")[0];
    	String select2=request.getParameterValues("attraction")[1];
    	String select3=request.getParameterValues("attraction")[2];
    	
    	// api에 보낼 session 생성
    	session.setAttribute("select1", select1);
    	session.setAttribute("select2", select2);
    	session.setAttribute("select3", select3);
    	
    	String userAge = null, userSex = null;
    	
    	UserVO vo = (UserVO) session.getAttribute("loginMember");
    	if(vo != null) {
    		userAge = (String) vo.getUserAge().split("대")[0];
    		userSex = (String) (vo.getUserSex().equals("여자")?"2":"1");
		}
    	
    	try {
    		// update된 API 호출
    		StringBuilder urlBuilder = new StringBuilder("http://localhost:5000/api/updateList"); /*URL*/
	        urlBuilder.append("?" + URLEncoder.encode("select1","UTF-8") + "="+URLEncoder.encode(select1, "UTF-8")); 
	        urlBuilder.append("&" + URLEncoder.encode("select2","UTF-8") + "=" + URLEncoder.encode(select2, "UTF-8")); 
	        urlBuilder.append("&" + URLEncoder.encode("select3","UTF-8") + "=" + URLEncoder.encode(select3, "UTF-8"));
	        urlBuilder.append("&" + URLEncoder.encode("userAge","UTF-8") + "=" + URLEncoder.encode(userAge, "UTF-8"));
	        urlBuilder.append("&" + URLEncoder.encode("userSex","UTF-8") + "=" + URLEncoder.encode(userSex, "UTF-8"));
	        URL url = new URL(urlBuilder.toString());
	        System.out.println("url:"+url);
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
	         	
	        return "redirect:/api";
    	}
    	catch (IOException e) {
            model.addAttribute("apiResult", "API 호출 중 오류 발생");
            return "error";
        }
    }
}