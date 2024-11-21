$(document).ready(function () {
        var slickSlider = $('.slick-slider');
        var direction = 1; // 1: 다음 방향, -1: 이전 방향

        slickSlider.slick({
            infinite: true,//무한반복옵션
            draggable : true,//드래그가능여부
            slidesToShow: 3,//이미지 보이는 갯수
            slidesToScroll: 1,//한번에 넘어가는 이미지 수
            autoplay: true, //자동으로 넘어가게 ture
            autoplaySpeed: 3000, // 3초 간격으로 자동 슬라이드
            prevArrow: '#prevBtn',
            nextArrow: '#nextBtn',
            responsive: [
                {
                    breakpoint: 768,
                    settings: {
                        slidesToShow: 1
                    }
                }
            ]
        });

        // 더블클릭하여 이미지의 링크로 이동
        $('.image-item').on('dblclick', function () {
            var link = $(this).data('link');
            window.location.href = link;
        });

        $('#prevBtn').click(function () {
            direction = -1;//-1이면 반대방향
            slickSlider.slick('slickPrev');
        });

        $('#nextBtn').click(function () {
            direction = 1; //1이면 정방향
            slickSlider.slick('slickNext');
        });

        // 자동 슬라이드 이벤트
        slickSlider.on('beforeChange', function (event, slick, currentSlide, nextSlide) {
// 슬라이드 이동 방향에 따라 direction 변경
            direction = nextSlide > currentSlide ? 1 : -1;
        });

        // 자동 슬라이드 방향 조절
        setInterval(function () {
            direction === 1 ? slickSlider.slick('slickNext') : slickSlider.slick('slickPrev');
        }, 3000);
    });
