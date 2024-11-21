let page = 0;
const lastPage = 4; // 마지막 페이지

$("body").on("mousewheel", function (e) {
	var wheel = e.originalEvent.wheelDelta;
    if(wheel < 0){
        page++;
    }else if(wheel > 0){
        page--;
    }
    if(page <= 0){
        page=0;
        $('#btn_gotop').hide();
    }else if(page > lastPage){
        page = lastPage;
        $('#btn_gotop').show();
    }else{
        $('#btn_gotop').show();
    }

	var offset = $('.container:nth-child(' + (page + 1) + ')').offset();

	$('html').animate({scrollTop : offset.top}, 400);
});

$('#btn_gotop').click(function(){ 
	 $('html').animate({scrollTop : 0}, 400);
	page = 0;
    return false;
});

