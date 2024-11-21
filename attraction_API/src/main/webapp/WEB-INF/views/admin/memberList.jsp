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
<html>
<head>
	<meta charset="UTF-8">
	<title>Insert title here</title>
	<link rel="stylesheet" type="text/css" href="/resources/css/memberList.css" /> 
	<script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>
</head>
<body>
<div class="memberlist">
	<div class="search">
	    <form action="#" method="post" id="searchForm">
	        <select id="searchTopic" name="searchTopic" style="font-family:'noto-reg';">
	        	<option value="all">전체</option>
	            <option value="id">아이디</option>
	            <option value="name">이름</option>
	            <option value="sex">성별</option>
	            <option value="age">나이</option>
	            <option value="area">선택지역</option>
	        </select>
	        <input type="text" id="searchKeyword" name="searchKeyword" style="font-family:'noto-reg';">
	        <button type="button" onclick="search()" style="font-family:'noto';">검색</button>
	    </form>
    </div>
	<div class="mlist">
		<div class="mtitle">
			<div class="num">고유번호</div>
			<div class="id">아이디</div>
			<div class="pw">비밀번호</div>
			<div class="name">이름</div>
			<div class="sex">성별</div>
			<div class="age">나이대</div>
			<div class="area1">선택지역1</div>
			<div class="area2">선택지역2</div>
			<div class="area3">선택지역3</div>
			<div class="role">역할</div>
			<div class="kang">기능</div>
		</div>
		<c:choose>
    		<c:when test="${not empty user_searchList}">
				<c:forEach items="${user_searchList}" var="vo" varStatus="status">
					<div class="mpage">
						<div class="num">${vo.userSerial}</div>
						<div class="id">${vo.userID}</div>
						<div class="pw">${vo.userPassword}</div>
						<div class="name">${vo.userName}</div>
						<div class="sex">${vo.userSex}</div>
						<div class="age">${vo.userAge}</div>
						<div class="area1">${vo.userArea1}</div>
						<div class="area2">${vo.userArea2}</div>
						<div class="area3">${vo.userArea3}</div>
						<div class="role">${roles[status.index]}</div> 
						<div class="adminbutton">
		                    <input type="hidden" name="user_id" value="${vo.userID}">
		                    <input type="hidden" name="user_pw" value="${vo.userPassword}">
		                    <button type="button" onclick="upgradeToAdmin('${vo.userID}', '${vo.userPassword}')" class="ab">관리권한</button>
		                </div>
		                <div class="userbutton">
		                	<input type="hidden" name="user_id" value="${vo.userID}">
							<button type="button" onclick="deleteToAdmin('${vo.userID}')" class="ub">일반권한</button>
						</div>
						<div class="deletebutton">
							<input type="hidden" name="user_id" value="${vo.userID}">
							<button type="button" onclick="deleteToUser('${vo.userID}')" class="db">회원탈퇴</button>
						</div>
					</div>
				</c:forEach>
			</c:when>
			<c:otherwise>
				<c:forEach items="${list}" var="vo" varStatus="status">
					<div class="mpage">
						<div class="num">${vo.userSerial}</div>
						<div class="id">${vo.userID}</div>
						<div class="pw">${vo.userPassword}</div>
						<div class="name">${vo.userName}</div>
						<div class="sex">${vo.userSex}</div>
						<div class="age">${vo.userAge}</div>
						<div class="area1">${vo.userArea1}</div>
						<div class="area2">${vo.userArea2}</div>
						<div class="area3">${vo.userArea3}</div>
						<div class="role">${roles[status.index]}</div> 
						<div class="adminbutton">
		                    <input type="hidden" name="user_id" value="${vo.userID}">
		                    <input type="hidden" name="user_pw" value="${vo.userPassword}">
		                    <button type="button" onclick="upgradeToAdmin('${vo.userID}', '${vo.userPassword}')" class="ab">관리권한</button>
		                </div>
		                <div class="userbutton">
		                	<input type="hidden" name="user_id" value="${vo.userID}">
							<button type="button" onclick="deleteToAdmin('${vo.userID}')" class="ub">일반권한</button>
						</div>
						<div class="deletebutton">
							<input type="hidden" name="user_id" value="${vo.userID}">
							<button type="button" onclick="deleteToUser('${vo.userID}')" class="db">회원탈퇴</button>
						</div>
					</div>
				</c:forEach>
			</c:otherwise>
		</c:choose>
	</div>
</div>
</body>
<script src="/resources/js/memberList.js"></script>
</html>