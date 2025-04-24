<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="validator" uri="http://www.springmodules.org/tags/commons-validator" %>
<html lang="ko">
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <meta name="_csrf" content="${_csrf.token}"/>
    <meta name="_csrf_header" content="${_csrf.headerName}"/>
    <title>로그인</title>

    <!-- CSS link -->
    <link rel="stylesheet" href="${contextPath}/css/_guide.css" type="text/css"/>
    <link rel="stylesheet" href="${contextPath}/css/_client.css" type="text/css"/>
    <link rel="stylesheet" type="text/css" href="${contextPath}/css/login.css">

    <!-- favicon link -->
    <link rel="shortcut icon" href="${contextPath}/images/favicon.ico">
    <!-- Jquery & script link : 스크립트 링크-->
    <script type="text/javascript" src="<c:url value="/validator.do"/>"></script>
    <validator:javascript formName="loginForm" staticJavascript="false" xhtml="true" cdata="false"/>

    <script src="${contextPath}/js/jquery/jquery-3.6.0.min.js"></script>
    <script src="${contextPath}/js/jquery/jquery-ui.min.js"></script>
    <script src="${contextPath}/js/jquery/timepicker.js"></script>
    <script src="${contextPath}/js/swalert/sweetalert2.min.js"></script>
    <script src="${contextPath}/js/common.js"></script>

    <!-- jquery-ui css 링크 (Date picker) -->
    <link rel="stylesheet" type="text/css" href="${contextPath}/css/jquery/jquery-ui.css">
    <link rel="stylesheet" type="text/css" href="${contextPath}/css/jquery/timepicker.css">
    <link rel="stylesheet" type="text/css" href="${contextPath}/css/swalert/sweetalert2.min.css">

    <script src="${contextPath}/js/cryptojs/core.js"></script>
    <script src="${contextPath}/js/cryptojs/sha256.js"></script>

    <script type="text/javascript">

        $(document).ready(function () {
            //엔터 검색
            $(".login-area input").on("keyup", function (key) {
                if (key.keyCode === 13) {
                    userLogin();
                }
            });
        });

        function fnInit() {
            //getid(document.loginForm);


            <c:if test="${not empty fn:trim(message) &&  message ne ''}">
            <c:choose>
            <c:when test="${fn:trim(message) eq 'regStsReqst'}">
            alert('해당 계정은 승인대기중입니다.');
            return false;
            </c:when>
            <c:when test="${fn:trim(message) eq 'regStsReturn'}">
            alert('해당 계정은 신청 반려되었습니다. 시스템관리자에게 문의해주세요.');
            return false;
            </c:when>
            <c:when test="${fn:trim(message) eq 'invalidRoute'}">
            alert('시스템사용자만 로그인이 가능합니다.');
            return false;
            </c:when>
            <c:when test="${fn:trim(message) eq 'inorgNoLogin'}">
            alert('6개월 장기미접속자입니다. 시스템관리자에게 문의해주세요.');
            </c:when>
            <c:when test="${fn:trim(message) eq 'failCountOver'}">
            alert('5회이상 로그인에 실패하였습니다. 시스템관리자에게 문의해주세요.');
            </c:when>
            <c:when test="${fn:trim(message) eq 'invalidIdPass'}">
            alert('ID 또는 패스워드가 일치하지 않습니다.');
            </c:when>
            <c:when test="${fn:trim(message) eq 'databaseError'}">
            alert('데이터베이스 오류가 발생하였습니다. 시스템관리자에게 문의해주세요.');
            </c:when>
            <c:when test="${fn:trim(message) eq 'unusedUser'}">
            alert('미사용 설정된 계정입니다.');
            </c:when>
            <c:otherwise>
            alert("${message}");
            </c:otherwise>
            </c:choose>
            </c:if>

        }

        function setCookie(name, value, expires) {
            document.cookie = name + "=" + escape(value) + "; path=/; expires=" + expires.toGMTString();
        }

        function getCookie(Name) {
            var search = Name + "=";
            if (document.cookie.length > 0) { // 쿠키가 설정되어 있다면
                offset = document.cookie.indexOf(search);
                if (offset != -1) { // 쿠키가 존재하면
                    offset += search.length;
                    // set index of beginning of value
                    end = document.cookie.indexOf(";", offset);
                    // 쿠키 값의 마지막 위치 인덱스 번호 설정
                    if (end == -1)
                        end = document.cookie.length;
                    return unescape(document.cookie.substring(offset, end));
                }
            }
            return "";
        }

        //회원가입 화면으로 이동
        function userJoinForm() {
            $("#loginForm").attr("action", "<c:url value='"+"${contextPath}"+"/user/userJoinForm.do'/>").submit();

        }

        function userLogin() {

            var idReg = /^[a-z]+[a-z0-9]{5,19}$/g;
            // if (!idReg.test($("#username").val())) {
            //     alert("아이디는 영문자로 시작하는 6~20자 영문자 또는 숫자이어야 합니다.");
            //     $('#encryptPwd').focus();
            //     return;
            // }

            if ($('#encryptPwd').val() == '') {
                alert('비밀번호는 필수 입력값입니다.');
                $('#encryptPwd').focus();
                return false;
            }

            // if ($('#encryptPwd').val().length < 8 || $('#password').val().length > 20) {
            //     alert('패스워드 자리 수가 일치 하지 않습니다.(8자리 이상 20자리 이하)');
            //     $('#encryptPwd').focus();
            //     return false;
            // }

            // 패스워드와 솔트 생성
            var salt = $('#username').val();
            $('#encryptPwd').val(encryptPwd($('#encryptPwd').val(), salt));

            console.log(encryptPwd($('#encryptPwd').val(), salt));

            $("#loginForm").submit();

        }

        //패스워드 암호화
        function encryptPwd(pwd, id) {

            var password = pwd
            var salt = id

            // 솔트와 패스워드 결합
            var saltedPassword = password + salt;

            // SHA-256 해시 생성
            const encodedInput = CryptoJS.enc.Utf8.parse(saltedPassword); // UTF-8로 인코딩
            const hashedPassword = CryptoJS.SHA256(encodedInput).toString(CryptoJS.enc.Hex); // SHA-256 해시


            return hashedPassword;
        }


    </script>
</head>

<body onload="fnInit();">
<div class="site-wrap login">
    <div class="login-wrap">
        <form class="login-form" name="loginForm" id="loginForm" action="/actionLogin.do" method="post">
            <h1 class="title">
                로그인 페이지
            </h1>
            <div class="login-area">
                <div class="put-row">
                    <label class="put-label" for="username">아이디</label>
                    <div class="put-wrap put-id">
                        <input type="text" class="put-basic" id="username" name="username" placeholder="아이디를 입력하세요."/>
                    </div>
                </div>

                <div class="put-row">
                    <label class="put-label" for="password">비밀번호</label>
                    <div class="put-wrap put-pw">
                        <input type="password" class="put-basic" id="encryptPwd" name="encryptPwd" maxlength="20"
                               placeholder="비밀번호를 입력하세요." title="<spring:message code="li.li.password" />" value=""/>
                        <input type="hidden" id="password" name="password"
                               title="<spring:message code="li.li.password" />" value=""/>
                    </div>
                </div>
                <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                <button type="button" onclick="userLogin();" class="btn btn-login">로그인</button>
            </div>
            <!-- /login-area -->
        </form>
        <button onclick="userJoinForm();" class="btn btn-join">회원가입</button>
    </div>
</div>
</body>

</html>
