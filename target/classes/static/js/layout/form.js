$(document).ready(function () {
    
    /* 경고 메시지 */
    $("a.navbar-brand, ul.nav.navbar-nav li a").on("click", function () {
        var confirmationMessage = '경고 : 저장되지 않은 내용이 있습니다.\n\n페이지를 떠나면 변경 내용이 손실됩니다. 계속하시겠습니까?\n(확인을 누르면 페이지가 이동됩니다.)';
        var confirmed = confirm(confirmationMessage);
        return confirmed;
    });
  });