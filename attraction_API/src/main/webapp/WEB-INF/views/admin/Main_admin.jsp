<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="/WEB-INF/views/nav.jsp" %>
<c:if test="${loginMember.userID != boardNum.id && loginMember.userID !=admindbID}">
	<script>
		alert("관리자만 접근 가능합니다.")
		location.href="/"
	</script>
</c:if>
<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="https://cdn.jsdelivr.net/npm/slick-carousel@1.8.1/slick/slick.css"/>
<link rel="stylesheet" type="text/css" href="https://cdn.jsdelivr.net/npm/slick-carousel@1.8.1/slick/slick-theme.css"/>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
</head>
<link rel="stylesheet" type="text/css" href="/resources/css/Main.css" /> 
<link rel="stylesheet" type="text/css" href="/resources/css/Slide_admin.css" />
<body>
 
<form action="http://localhost:8080/api" method="get">
    <div class="wrap">
        <div class="container">
            <img src="resources/img/경복궁-main1.jpg" id="img1">
            <div class="main-title1">API를 활용한</div>
            <div class="main-title2">서울 관광지 소개 웹 개발</div>
            <div class="sub-title">Development of Seoul Tourist Site Introduction Web</div>
            <!-- 마우스 아이콘 -->
            <div class="buttonPosition">
                <img src="resources/img/icon/mouse2.png" width="50px" height="50px">
            </div>
        </div>
        <div class="container">
            <div class="sub1-title1">홈페이지 소개</div>
            <div>
               <div class="sub1-image-box1">
                 <img src="resources/img/icon/brochure.png" id="brochure">
                 </div>
                 <div class="sub1-image-box1">
                    <img src="resources/img/icon/map.png" id="map">
                 </div>
                 <div class="sub1-image-box1">
                    <img src="resources/img/icon/noticeboard.png" id="noticeboard">
                 </div>
            </div>
             <div>
                <div class="sub1-image-label">관광지 추천 서비스</div>
            <div class="sub1-image-label">관광지 지도</div>
            <div class="sub1-image-label">소통 게시판</div>
             </div>
        </div>
        <div class="container">
            <div class="sub2-title1">AI를 활용한 관광지 추천 서비스</div>
            <button class="sub2-button">추천받으러 가기</button>
            <div class="image-box1">
               <img src="resources/img/한옥마을.jpg" id="spring">
            </div>
            <div class="image-box2">
               <img src="resources/img/야경.jpg" id="spring1">
            </div>
            <div class="image-box3">
               <img src="resources/img/컴터테.jpg" id="spring1">
            </div>
        </div>
        <div class="container">
            <div class="sub2-title1">실시간 미세먼지 예측 서비스</div>
            <button class="sub2-button">확인해보기</button>
            <div class="image-box4">
               <img src="resources/img/spring.jpg" id="finedust">
            </div>
        </div>
      <div class="container">
          <div class="slick-slider">
                <c:forEach var="imagelist" items="${sessionScope.imageList}" varStatus="loop">
                    <div class="image-item" data-id="${imagelist.news_num}" data-link="${imagelist.news_link}">
                        <a href="${imagelist.news_link}">
                            <img class="image_weather" src="${imagelist.news_path}" alt="${imagelist.news_alt}">
                        </a>
                  <form class="uploadForm" enctype="multipart/form-data">
                            <input type="file" name="imageFile" accept="image/png, image/jpeg" class="button_editimage">
                        </form>
                        <input type="text" class="editedValue" name="url" value="${imagelist.news_link}">
                        <button type="button" class="button_editurl" data-id="${imagelist.news_num}" data-index="${loop.index}">수정</button>
                        <div><button type='button' class="button_delete" data-id="${imagelist.news_num}">삭제</button></div>
                    </div>
                </c:forEach>
                <a class="button_insert" onclick="openPopup()"><img src="resources/img/icon/추가버튼.png" width="50px" height="50px"></a>
            <div id="imagePopup" class="popup">
                <div class="popup-content">
                    <form id="imageForm" enctype="multipart/form-data">
                        <input type="file" name="imageFile" accept="image/png, image/jpeg" class="button_insertimage">
                        <input type="text" id="urlInput" name="url" placeholder="경로를 입력하세요">
                        <input type="submit" value="추가">
                    </form>
                    <button onclick="closePopup(event)">닫기</button>
                </div>
            </div>
          </div>
      </div>
    </div>
</form>
    <!-- 맨위로 버튼 -->
    <a id="btn_gotop" href="#">
        <img src="resources/img/icon/up-arrow-angle.png" id="up">
    </a>
</body>
<script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/slick-carousel@1.8.1/slick/slick.min.js"></script>
<script src="/resources/js/Main.js"></script>
<script src="/resources/js/editImage.js"></script>
</html>