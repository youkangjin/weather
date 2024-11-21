function upgradeToAdmin(user_id, user_pw) {
    $.ajax({
        type: 'GET',
        url: '/admin/adminupdate',
        data: {
            'user_id': user_id,
            'user_pw': user_pw
        },
        success: function (data) {
            alert('관리자로 승급되었습니다.');
            location.reload(); // 페이지 새로고침
        },
        error: function () {
            alert('오류가 발생했습니다.');
        }
    });
}

function deleteToAdmin(user_id) {
    $.ajax({
        type: 'GET',
        url: '/admin/admindelete',
        data: {
            'user_id': user_id
        },
        success: function (data) {
            alert('관리자 권한이 해제되었습니다.');
            location.reload(); // 페이지 새로고침
        },
        error: function () {
            alert('오류가 발생했습니다.');
        }
    });
}

function deleteToUser(user_id) {
    $.ajax({
        type: 'GET',
        url: '/admin/userdelete',
        data: {
            'user_id': user_id
        },
        success: function (data) {
            alert('회원탈퇴.');
            location.reload(); // 페이지 새로고침
        },
        error: function () {
            alert('오류가 발생했습니다.');
        }
    });
}

function search() {
    var topic = $("#searchTopic").val();
    var keyword = $("#searchKeyword").val();

    $.ajax({
        type: 'GET',
        url: "/admin/search",
        data: { 
			'topic': topic,
			'keyword': keyword
		},
        success: function (data) {
			console.log("검색와료");
            location.reload();
        },
        error: function (error) {
            console.error("오류발생");
        }
    });
}
