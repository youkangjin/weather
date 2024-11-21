<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>nav</title>
    <link href="/resources/css/nav.css" rel="stylesheet" type="text/css" />
    <script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>
    <script></script>
  </head>

  <body>
    <div class="gtranslate_wrapper"></div>

    <nav style="padding:30px;">
      <div id="horizontal-underline"></div>
      <a href="#" id="MainPage">메인</a>
      <!-- <a href="#" id="loginPage">로그인</a> -->
          <c:choose>
        <c:when test="${empty user}">
            <a href="#" id="loginPage">로그인</a>
        </c:when>
        <c:otherwise>
            <a href="#" id="logoutPage">로그아웃</a>
        </c:otherwise>
    </c:choose>
      
      <a href="#" id="registerPage">회원가입</a>
      <a href="#" id="APIPage">추천</a>
      <a href="#" id="mapPage">지도</a>
      <a href="#" id="boardPage">게시판</a>
      <c:if test="${!empty loginMember && loginMember.userID == admindbID}">
      	<a href="#" id="memberPage">회원관리</a>
      </c:if>
      
      
      <a href="#" onclick="toggleLanguages()">언어</a>
      <a
        href="#"
        class="hide"
        id="chinese"
        onclick="changeLanguage(this, '中国话')"
        >中国话</a
      >
      <a
        href="#"
        class="hide"
        id="japanese"
        onclick="changeLanguage(this, '日本語')"
        >日本語</a
      >
      <a
        href="#"
        class="hide"
        id="english"
        onclick="changeLanguage(this, 'English')"
        >English</a
      >
      <a
        href="#"
        class="hide"
        id="korean"
        onclick="changeLanguage(this, '한국어')"
        >한국어</a
      >
      <!-- 한국어, 영어, 일본어만 -->
    </nav>

    <script> 
      $(document).ready(function () {
   	 	$("#MainPage").click(function () {
             // 로그인 페이지로 이동
             window.location.href = "/";
           });
        $("#loginPage").click(function () {
          // 로그인 페이지로 이동
          window.location.href = "/register/login";
        });
        $("#logoutPage").click(function (e) {
            e.preventDefault();  // 기본 이벤트(링크 클릭) 방지
        	//로그아웃
            window.location.href = "/logout";
		})

        $("#registerPage").click(function () {
          // 회원가입 페이지로 이동
          window.location.href = "/register/join";
        });
        $("#APIPage").click(function () {
            // 게시판 페이지로 이동
            window.location.href = "/api";
          });
        $("#boardPage").click(function () {
          // 게시판 페이지로 이동
          window.location.href = "/board/list";
        });
        
        $("#mapPage").click(function () {
            // 게시판 페이지로 이동
            window.location.href = "/live/map";
          });
        
        $("#memberPage").click(function () {
            window.location.href = "/admin/memberList";
          });
        
        $("#korean").click(function () {
            window.location.href = "/board/list";
          });

    	var list_text=[ $("strong"), $(".board_title.loaded p")]
       	var total_list = [$(".board_list .top div"),$(".board_list .board_num"),$(".board_list .admin_num")]
       	var obj={}

        const success_callback_header =  (v) => {
          var translatedString = {};
      	  var parsed=null
          for (var key in v) {
        	  parsed = JSON.parse(v[key]);
	            if (v.hasOwnProperty(key)) {            	
	            	translatedString[key]=parsed
	   				list_text[key].text(parsed.message.result.translatedText)
	            }
          }
        }
      	const ajax_call =(obj,num)=> $.ajax({
            url: "/board/translate",
            type: "POST",
            dataType: "json",
            contentType: "application/json",
            data: JSON.stringify(obj),
            success: result=>{
            	if(num==1){
            		success_callback_header(result)
            	} else if(num==2){
            		success_callback_title(result)
            	} 
            }
   
          })
      	const success_callback_title=  (v) => {
      		var cnt=0
      		 var translatedString = {};
      		 for (var key in v) {
           	   parsed = JSON.parse(v[key]);
               if (v.hasOwnProperty(key)) {  
            	   translatedString[key]=parsed
            	   var te= total_list[0].eq(cnt++)
      			   te.text(parsed.message.result.translatedText)
               }
      		}
      	}  	
          $("#chinese").click(function () {
          	  var obj={}
        	  list_text.map((i, idx) => obj[idx]= i.text())
        	  obj['lang']= "chinese" 
        	  //console.log(obj)
              ajax_call(obj,1)// 이미지 내의 한글 번역
	            //////////////////////////
              obj={}
          	  obj['lang']= "chinese" 
              total_list[0].each((idx,data)=>{
        		  //console.log(idx,data)
        		  obj['top_'+idx]= $(data).text()
        	  })
        	 ajax_call(obj,2) // 제목  글쓴이 작성일 번역 
                  
        	// 게시글 내용을 번역하는 함수
		    	function translatePostContents($post, lang) {
		    	    var $columns = $post.children(); // 열(column) 요소들을 가져옵니다.
		    	    var columnCount = $columns.length;
		    	    var translations = {}; // 각 열의 번역 결과를 저장할 객체

		    	    // 각 열에 대한 번역 요청을 보냅니다.
		    	    $columns.each(function (index) {
		    	        var columnText = $(this).text();
		    	        var obj = {
		    	            lang: lang,
		    	            content: columnText
		    	        };
		    	        $.ajax({
		    	            url: "/board/translate",
		    	            type: "POST",
		    	            dataType: "json",
		    	            contentType: "application/json",
		    	            data: JSON.stringify(obj),
		    	            success: function (v) {
		    	            	for (var key in v) {
		    	      	            if (v.hasOwnProperty(key)) {     
		    	      	            	parsed = JSON.parse(v[key]);
		    	      	                // 번역 결과를 저장합니다.
		    	      	            	translations['column_' + index] = parsed.message.result.translatedText
			    	      	            }
		    	                }
		    	                // 모든 열에 대한 번역이 완료되면 열을 대체합니다.
		    	                if (Object.keys(translations).length === columnCount) {
		    	                    $columns.each(function (i) {
		    	                        $(this).text(translations['column_' + i]);
		    	                    });
		    	                }
		    	            }
		    	        });
		    	    });
          	  }
        // 각 게시글에 대해 번역을 수행합니다.
	    	total_list[1].each(function () {
	    	    translatePostContents($(this), 'chinese'); // 게시글 내용을 중국어로 번역
	    	});
	    	total_list[2].each(function () {
	    	    translatePostContents($(this), 'chinese'); // 게시글 내용을 중국어로 번역
	    	});
          })
          $("#japanese").click(function () {
          	  var obj={}
        	  list_text.map((i, idx) => obj[idx]= i.text())
        	  obj['lang']= "japanese" 
        	  console.log(obj)
              ajax_call(obj,1)// 이미지 내의 한글 번역
	            //////////////////////////
              obj={}
          	  obj['lang']= "japanese" 
              total_list[0].each((idx,data)=>{
        		  console.log(idx,data)
        		  obj['top_'+idx]= $(data).text()
        	  })
        	 ajax_call(obj,2) // 제목  글쓴이 작성일 번역 
                  
        	// 게시글 내용을 번역하는 함수
		    	function translatePostContents($post, lang) {
		    	    var $columns = $post.children(); // 열(column) 요소들을 가져옵니다.
		    	    var columnCount = $columns.length;
		    	    var translations = {}; // 각 열의 번역 결과를 저장할 객체

		    	    // 각 열에 대한 번역 요청을 보냅니다.
		    	    $columns.each(function (index) {
		    	        var columnText = $(this).text();
		    	        var obj = {
		    	            lang: lang,
		    	            content: columnText
		    	        };
		    	        $.ajax({
		    	            url: "/board/translate",
		    	            type: "POST",
		    	            dataType: "json",
		    	            contentType: "application/json",
		    	            data: JSON.stringify(obj),
		    	            success: function (v) {
		    	            	for (var key in v) {
		    	      	            if (v.hasOwnProperty(key)) {     
		    	      	            	parsed = JSON.parse(v[key]);
		    	      	                // 번역 결과를 저장합니다.
		    	      	            	translations['column_' + index] = parsed.message.result.translatedText
			    	      	            }
		    	                }
		    	                // 모든 열에 대한 번역이 완료되면 열을 대체합니다.
		    	                if (Object.keys(translations).length === columnCount) {
		    	                    $columns.each(function (i) {
		    	                        $(this).text(translations['column_' + i]);
		    	                    });
		    	                }
		    	            }
		    	        });
		    	    });
          	  }
        // 각 게시글에 대해 번역을 수행합니다.
	    	total_list[1].each(function () {
	    	    translatePostContents($(this), 'japanese'); // 게시글 내용을 중국어로 번역
	    	});
	    	total_list[2].each(function () {
	    	    translatePostContents($(this), 'japanese'); // 게시글 내용을 중국어로 번역
	    	});
          })
          $("#english").click(function () {
          	  var obj={}
        	  list_text.map((i, idx) => obj[idx]= i.text())
        	  obj['lang']= "english" 
        	  //console.log(obj)
              ajax_call(obj,1)// 이미지 내의 한글 번역
	            //////////////////////////
              obj={}
          	  obj['lang']= "english" 
              total_list[0].each((idx,data)=>{
        		  //console.log(idx,data)
        		  obj['top_'+idx]= $(data).text()
        	  })
        	 ajax_call(obj,2) // 제목  글쓴이 작성일 번역 
                  
        	// 게시글 내용을 번역하는 함수
		    	function translatePostContents($post, lang) {
		    	    var $columns = $post.children(); // 열(column) 요소들을 가져옵니다.
		    	    var columnCount = $columns.length;
		    	    var translations = {}; // 각 열의 번역 결과를 저장할 객체

		    	    // 각 열에 대한 번역 요청을 보냅니다.
		    	    $columns.each(function (index) {
		    	        var columnText = $(this).text();
		    	        var obj = {
		    	            lang: lang,
		    	            content: columnText
		    	        };
		    	        $.ajax({
		    	            url: "/board/translate",
		    	            type: "POST",
		    	            dataType: "json",
		    	            contentType: "application/json",
		    	            data: JSON.stringify(obj),
		    	            success: function (v) {
		    	            	for (var key in v) {
		    	      	            if (v.hasOwnProperty(key)) {     
		    	      	            	parsed = JSON.parse(v[key]);
		    	      	                // 번역 결과를 저장합니다.
		    	      	            	translations['column_' + index] = parsed.message.result.translatedText
			    	      	            }
		    	                }
		    	                // 모든 열에 대한 번역이 완료되면 열을 대체합니다.
		    	                if (Object.keys(translations).length === columnCount) {
		    	                    $columns.each(function (i) {
		    	                        $(this).text(translations['column_' + i]);
		    	                    });
		    	                }
		    	            }
		    	        });
		    	    });
          	  }
        // 각 게시글에 대해 번역을 수행합니다.
	    	total_list[1].each(function () {
	    	    translatePostContents($(this), 'english'); // 게시글 내용을 중국어로 번역
	    	});
	    	total_list[2].each(function () {
	    	    translatePostContents($(this), 'english'); // 게시글 내용을 중국어로 번역
	    	});
          })
    })
    </script>
    <script src="/resources/js/nav.js"></script>
  </body>
</html>
