let horizontalUnderLine = document.getElementById("horizontal-underline");
let horizontalMenus = document.querySelectorAll("nav:first-child a");

horizontalMenus.forEach(menu => menu.addEventListener("click", (e) => horizontalIndicator(e)))

function horizontalIndicator(e) {
    horizontalUnderLine.style.left = e.currentTarget.offsetLeft + "px";
    horizontalUnderLine.style.width = e.currentTarget.offsetWidth + "px";
    horizontalUnderLine.style.top = e.currentTarget.offsetTop + e.currentTarget.offsetHeight + "px";
}

function toggleLanguages() {
    var navElement = document.querySelector('nav');
    navElement.classList.toggle('vertical-menu');

    var languageLinks = document.querySelectorAll('.hide');

    languageLinks.forEach(function (link) {
        link.classList.toggle('show');
    });
}

function changeLanguage(clickedLink, language) {
    var languageLinks = document.querySelectorAll('.hide');
    var navElement = document.querySelector('nav');

    languageLinks.forEach(function (link) {
        link.classList.remove('show');
    });

    clickedLink.classList.add('show');

    navElement.classList.remove('vertical-menu');
}
