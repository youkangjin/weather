<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
<title>Insert title here</title>
</head>
<script>
    document.addEventListener('DOMContentLoaded', function () {
        document.querySelector('.board_title').classList.add('loaded');
    });
</script>
<script type="text/javascript"></script>
<script src="https://code.jquery.com/jquery-3.7.1.js"></script>
<link rel="stylesheet" type="text/css" href="/resources/css/API_total.css" />
<body>
<%@ include file="/WEB-INF/views/nav.jsp" %>
	<div class="board_wrap">
        <div class="board_title"><strong>추천페이지</strong>
            <p>추천페이지 입니다.</p>
        </div>
	</div>
	<div class="sub1-title1">
    	나에게 맞는 관광지는?
    </div>
    <div class="sub1-title2">
    	회원가입한 정보를 토대로 관광지 추천을 받아보세요<br>
    	놀러가고싶은데 어디가지 고민한 적 있으신가요?<br>
    	미세먼지때문에 실외활동이 꺼려지시진 않았나요?<br>
    	성별, 나이, 관심지에 따라 인기많은 관광지를 소개하고,<br>
    	관광지를 선택하면 유사한 다른 관광지 추천과<br>
    	미세먼지 농도를 알려드립니다
    </div>
	<div class="controller">
		 <!-- &lang: 왼쪽 방향 화살표 &rang: 오른쪽 방향 화살표 --> 
		 <span class="prev">&lang;</span> 
		<span class="next">&rang;</span>
    </div>
    
	<!-- controller 변수 불러옴 -->
	<div id="slideShow">
		<div class="slides">
			<c:if test='${ not empty sb_age && sb_age ne "" }'>
				<div><label>${age}대 인기 관광지 : ${sb_age}</label>
				<image class="api_age_image1" src=${sb_age_img}></div>
			</c:if>
			<c:if test='${ not empty sb_sex && sb_sex ne "" }'>
				<div ><label>${sex} 인기 관광지 : ${sb_sex}</label>
				<image class="api_age_image1" src=${sb_sex_img} /></div>
			</c:if>
			<c:if test='${ not empty attraction1 && attraction1 ne "" }'>
				<div ><label>${attraction1} 는 "${attraction1_name}" 이 유명해요!</label>
				<image class="api_age_image1" src=${attraction1_img} /></div>
			</c:if>
			<c:if test='${ not empty attraction2 && attraction2 ne "" }'>
				<div ><label>${attraction2} 는 "${attraction2_name}" 이 유명해요!</label>
				<image class="api_age_image1" src=${attraction2_img} /></div>
			</c:if>
			<c:if test='${ not empty attraction3 && attraction3 ne "" }'>
				<div ><label>${attraction3} 는 "${attraction3_name}" 이 유명해요!</label>
				<image class="api_age_image1" src=${attraction3_img} /></div>
			</c:if>
		</div>
	</div>
	<div class="sub1-title3">
    	AI 추천
    </div>
    <div class="sub1-title4">
    	관심지 3개를 골라주세요!
    </div>

	<!-- 체크박스 -->
	<div class="board_wrap">
		<form action="/update" method="get">
			<div class="check">
				<!-- 체크박스 목록 -->
				<div class="label">
					<input type="checkbox" name="attraction" value="1"><label>선사유적지</label>
			  		<input type="checkbox" name="attraction" value="2"><label>잠실 롯데타워</label>
			  		<input type="checkbox" name="attraction" value="3"><label>경복궁</label></br>
			  		<input type="checkbox" name="attraction" value="4"><label>남산타워</label>
			  		<input type="checkbox" name="attraction" value="5"><label>도봉산</label>
			  		<input type="checkbox" name="attraction" value="6"><label>홍대거리</label></br>
			  		<input type="checkbox" name="attraction" value="7"><label>코엑스</label>
			  		<input type="checkbox" name="attraction" value="8"><label>명동</label>
			  		<input type="checkbox" name="attraction" value="9"><label>DDP</label></br>
			  		<input type="checkbox" name="attraction" value="10"><label>어린이대공원</label>
			  		<input type="checkbox" name="attraction" value="11"><label>더현대</label>
			  		<input type="checkbox" name="attraction" value="12"><label>반포한강공원</label></br>
			  		<input type="checkbox" name="attraction" value="13"><label>용마랜드</label>
			  		<input type="checkbox" name="attraction" value="14"><label>서울숲</label>
			  		<input type="checkbox" name="attraction" value="15"><label>보라매공원</label></br>
			  		<input type="checkbox" name="attraction" value="16"><label>서울식물원</label>
			  		<input type="checkbox" name="attraction" value="17"><label>북한산 둘레길</label>
			  		<input type="checkbox" name="attraction" value="18"><label>낙성대공원</label></br>
			  		<input type="checkbox" name="attraction" value="19"><label>푸른수목원</label>
			  		<input type="checkbox" name="attraction" value="20"><label>호암산 잣나무산림욕장</label>
			  		<input type="checkbox" name="attraction" value="21"><label>화랑대철도공원</label></br>
			  		<input type="checkbox" name="attraction" value="22"><label>서서울호수공원</label>
			  		<input type="checkbox" name="attraction" value="23"><label>은평한옥마을</label>
			  		<input type="checkbox" name="attraction" value="24"><label>서대문형무소</label></br>
			  		<div class="last"><input type="checkbox" name="attraction" value="25"><label>정릉</label></div>
			  	</div>
		  		<c:if test='${ not empty select1 && select1 ne "" }'>
					<label style="display: flex; justify-content: center;font-size: 23px; margin: 50px 50px 10px 50px;">추천 관광지는 &nbsp;<b style="word-spacing: 10px;">
						"${sb_select1}, ${sb_select2}, ${sb_select3}"</b>&nbsp; 입니다
					</label>
					<label style="display: flex; justify-content: center;font-size: 21px; margin: 50px 50px 10px 50px;">${sb_select1} 미세먼지 농도는 &nbsp;<b style="word-spacing: 10px;">"${weather1}"</b>&nbsp;으로 예상되니 ${weathermsg1}</label>
					<label style="display: flex; justify-content: center;font-size: 21px; margin: 10px 50px 10px 50px;">${sb_select2} 미세먼지 농도는 &nbsp;<b style="word-spacing: 10px;">"${weather2}"</b>&nbsp;으로 예상되니 ${weathermsg2}</label>
					<label style="display: flex; justify-content: center;font-size: 21px; margin: 10px 50px 50px 50px;">${sb_select3} 미세먼지 농도는 &nbsp;<b style="word-spacing: 10px;">"${weather3}"</b>&nbsp;으로 예상되니 ${weathermsg3}</label>
				</c:if>
				<div class="check-button">
					<button id="check-button1">결과 확인하기</button>
		</form>
					<button id="check-button2" onclick="javascript: form.action='/live/map'">지도페이지 가기</button>
				</div>
			</div>
	</div>
</body>
<script src="/resources/js/join.js"></script>
<script src="/resources/js/API.js"></script>
</html>