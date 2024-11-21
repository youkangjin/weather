$(document).on('change', '.button_editimage', function() {
    var fileInput = this.files[0];
	var formData = new FormData();
    formData.append('imageFile', fileInput);

	var id = $(this).closest(".image-item").data("id");
	formData.append('id',id);

	/*이미지 이름 인코딩*/
	var encodedFileName = encodeURIComponent(fileInput.name);   

    $.ajax({
        url: '/admin/uploadImage?id=' + id + '&fileName=' + encodedFileName, 
        type: 'POST',
        data: formData,
        contentType: false, 
        processData: false, 
        success: function(response) {
            console.log('Success:', response);
            alert('이미지가 수정되었습니다.');
            location.reload();  // 페이지 새로고침
        },
        error: function(error) {
            console.error('Error:', error);
            alert('이미지 업로드 중 오류가 발생했습니다.');
        }
    });
});


/*URL 수정하는 함수*/
$(".slick-slider").on("click", ".button_editurl", function () {
    var id = $(this).closest(".image-item").data("id");
	var index = $(this).data("index");
    var newValue = $(".editedValue").eq(index).val();

    $.ajax({
        type: 'GET',
        url: '/admin/editlink',
        data: {
            'id': id,
            'url': newValue
        },
        success: function (data) {
            alert('url이 수정되었습니다.');
            location.reload(); // 페이지 새로고침
        },
        error: function () {
            alert('오류가 발생했습니다.');
        }
    });
});

/*뉴스 삭제하는 함수*/
$(".slick-slider").on("click", ".button_delete", function () {
	var id = $(this).data("id");
    $.ajax({
        type: 'GET',
        url: '/admin/deleteNews',
        data: {
            'id': id
        },
        success: function (data) {
            alert('삭제되었습니다');
            location.reload(); // 페이지 새로고침
        },
        error: function () {
            alert('오류가 발생했습니다.');
        }
    });
});

/*뉴스 추가하는 함수*/
$(document).ready(function() {
    $("#imageForm").submit(function() {
        var formData = new FormData(this);  
        $.ajax({
            url: '/admin/insertNews',
            type: 'POST',
            data: formData,
            contentType: false,
            processData: false,
            success: function(response) {
                console.log('Success:', response);
                alert('뉴스가 추가되었습니다.');
                location.reload();
            },
            error: function(error) {
                console.error('Error:', error);
                alert('이미지를 등록하였습니다.');
            }
        });
    });
});
/*팝업창 열고 닫는 함수*/
function openPopup() {
    document.getElementById('imagePopup').style.display = 'block';
}
function closePopup(event) {
	event.preventDefault();
    document.getElementById('imagePopup').style.display = 'none';
}
