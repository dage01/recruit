$(document).ready(function () {
  $(window).scroll(function () {
    var navbar = $('.navbar');
    var scrollPosition = $(window).scrollTop();

    // 네비게이션 바의 색상을 스크롤 위치에 따라 조정
    if (scrollPosition > 0) {
      navbar.css('background-color', 'rgba(0, 0, 0, 0.85)');
    } else {
      navbar.css('background-color', 'transparent'); // 투명으로 설정
    }
  });
});

function getCurrentPage() {
  return window.location.pathname; // 현재 페이지의 경로를 반환합니다.
}

var pageContents = [
  {
    url: ['/job-posting/list', '/job-posting/detail', '/'],
    leadsIndex: 0,
    backgroundImage: 'url("/images/layout/bg_donga01.jpg")'
  },

  {
    url: ['/job-posting/fill-form', '/resume/basic-info', '/resume/education', '/resume/experience', '/resume/language', '/resume/certifications', '/resume/self-introduction', '/resume/upload-file', '/resume/submit-application'],
    leadsIndex: 1,
    backgroundImage: 'url("/images/layout/img_visual01.jpg")'
  },

  {
    url: ['/applicant_login', '/apply_list'],
    leadsIndex: 2,
    backgroundImage: 'url("/images/layout/bg_history02.jpg")'
  }
];

var leadsContents = [
  '채용공고',
  '입사지원',
  '전형결과'
];


// jQuery 문법으로 변환된 코드

function setBackgroundImage() {
  var currentPage = getCurrentPage(); // 현재 페이지 URL을 가져옵니다.
  var content = pageContents.find(function (page) {
    return $.inArray(currentPage, page.url) !== -1;
  }); // 현재 페이지의 정보를 찾습니다.
  if (content) {
    $('#header').css('background-image', content.backgroundImage);
  }
}

function setPageLeadsContent() {
  setBackgroundImage(); // 배경 이미지를 설정합니다.
  var currentPage = getCurrentPage(); // 현재 페이지 URL을 가져옵니다.
  var content = pageContents.find(function (page) {
    return $.inArray(currentPage, page.url) !== -1;
  }); // 현재 페이지의 정보를 찾습니다.
  if (content) {
    var leadsContent = leadsContents[content.leadsIndex]; // leads 내용을 가져옵니다.
    if (leadsContent) {
      $('#leadsContent').text(leadsContent); // leads 내용을 설정합니다.
    } else {
      console.error("Leads content not found for current page");
    }
  } else {
    console.error("Page content not found for current page");
  }
}

$(document).ready(function () {
  setPageLeadsContent();
});

$(window).on('hashchange', function () {
  setPageLeadsContent();
});