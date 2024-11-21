$(document).ready(function () {
  let slides = $('.slides');
  let slideImg = $('.slides div');
  let currentIdx = 0;
  let slideCount = slideImg.length;
  let slideWidth = 1000;
  let slideMargin = 100;

  makeClone();

  function makeClone() {
    let cloneSlide_first = slideImg.first().clone();
    let cloneSlide_last = slideImg.last().clone();
    slides.append(cloneSlide_first);
    slides.prepend(cloneSlide_last);
  }
function gotoMap() {
	
    location.href="/map"
  }

  function initfunction() {
    slides.css({
      width: (slideWidth + slideMargin) * (slideCount + 2) + 'px',
      left: -(slideWidth + slideMargin) + 'px',
    });
  }

  initfunction();

  $('.next').on('click', function () {
    if (currentIdx <= slideCount - 1) {
      slides.css({
        left: -(currentIdx + 2) * (slideWidth + slideMargin) + 'px',
        transition: '0.5s ease-out',
      });
    }
    if (currentIdx === slideCount - 1) {
      setTimeout(function () {
        slides.css({
          left: -(slideWidth + slideMargin) + 'px',
          transition: '0s ease-out',
        });
      }, 500);
      currentIdx = -1;
    }
    currentIdx += 1;
  });

  $('.prev').on('click', function () {
    if (currentIdx >= 0) {
      slides.css({
        left: -currentIdx * (slideWidth + slideMargin) + 'px',
        transition: '0.5s ease-out',
      });
    }
    if (currentIdx === 0) {
      setTimeout(function () {
        slides.css({
          left: -slideCount * (slideWidth + slideMargin) + 'px',
          transition: '0s ease-out',
        });
      }, 500);
      currentIdx = slideCount;
    }
    currentIdx -= 1;
  });
});
