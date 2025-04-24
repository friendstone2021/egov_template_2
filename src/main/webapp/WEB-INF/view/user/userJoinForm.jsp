<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="validator" uri="http://www.springmodules.org/tags/commons-validator" %>
<!DOCTYPE html>
<html lang="ko">
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="_csrf" content="${_csrf.token}"/>
    <meta name="_csrf_header" content="${_csrf.headerName}"/>
	<title>회원가입</title>

	<!-- CSS link -->
	<link rel="stylesheet" type="text/css" href="${contextPath}/css/_design.css">
	<link rel="stylesheet" type="text/css" href="${contextPath}/js/fontawesome/all.min.css" />

	<!-- Jquery & script link : 스크립트 링크-->
    <script src="${contextPath}/js/jquery/jquery-3.6.0.min.js"></script>
    <script src="${contextPath}/js/jquery/jquery-ui.min.js"></script>
	<script src="${contextPath}/js/common.js"></script>
    <script src="${contextPath}/js/fontawesome/all.min.js" charset="utf-8"></script>

    <script src="${contextPath}/js/cryptojs/core.js"></script>
    <script src="${contextPath}/js/cryptojs/sha256.js"></script>

	<!-- favicon link -->
	<link rel="shortcut icon" href="${contextPath}/images/favicon.ico">


	<script type="text/javascript">

		$(function(){

			//약관 모달에서 모두동의 체크 시
			$('#join-all').on('click', function(){

				if($('#join-all').is(':checked')){
					$('.join-agree-member input:checkbox').prop('checked',true);
				} else {
					$('.join-agree-member input:checkbox').prop('checked',false);
				}
			});

			// 회원가입 시스템 select
			$('.select-sys').on('click', function () {
			  $('.system-list').toggleClass('on');
			});

		});

		var idchk = false;	//아이디 중복확인

		//약관 모달 팝업
		function joinAgreeModal(){
			$('.join-agree-member').hide().addClass('off');
			$('.join-agree-member').attr('tabindex', 0).fadeIn();
			$('body').addClass('scrollLock');

			$('.join-agree-member').find(".btn-close").on("click", function () {
				$('.join-agree-member').fadeOut().addClass("off");
				$('body').removeClass('scrollLock');
			});
		}

		//아이디 중복확인
		function fnUserIdDplct(){
			var userId = $("#userId").val();
			if($.trim(userId) === '') {
				$("#userId").focus();
				alert('<spring:message code="cm.um.userManage.userIdRequired" />');
				return;
			}

			cm.ajax(
				{
					url : "<c:url value='/user/selectJoinUserIdDplct.do'/>"
					, data : { userId : userId }
				}, function(result){
					alert(result.message);
					if(result.status == 'SUCCESS'){
						idchk = true;
						$("#userEnpswd").focus();
					} else {
						idchk = false;
					}
				}
			);
		}

		//회원가입
		function saveUserJoin(){

			//약관동의
			// if(!$('#join-rule').is(':checked')){
			// 	alert('이용약관에 동의해주세요.');
			// 	return false;
			// }

			//개인정보 수집 및 이용
			if(!$('#join-law').is(':checked')){
				alert('개인정보 수집 및 이용에 동의해주세요.');
				return false;
			}

			if(!idchk){
				alert('아이디 중복 확인 해주세요.');
				return false;
			} else {

				var idReg = /^[a-z]+[a-z0-9]{5,19}$/g;
		        if( !idReg.test( $("#userId").val() ) ) {
		            alert("아이디는 영문자로 시작하는 6~20자 영문자 또는 숫자이어야 합니다.");
		            return;
		        }

				if($('#userEnpswd').val() == ''){
					alert('비밀번호는 필수 입력값입니다.');
					$('#userEnpswd').focus();
					return false;
				}

				//비밀번호&재입력 동일한지
				if($("#userEnpswd").val() !== $("#reuserEnpswd").val()){
					$("#reuserEnpswd").focus();
					alert('비밀번호가 일치하지 않습니다. 다시 입력해주세요.');
					return false;
				}
				var data =$('#userEnpswd').val();
				var passwordRegex = /^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*\d)(?=.*[$@$!%*#?&])[A-Za-z\d$@$!%*#?&]{9,20}$/

				if(passwordRegex.test(data)){
					if (!/(\w)\1\1/.test(data)) {
				        var cnt = 0
				        var cnt2 = 0
				        var tmp = ''
				        var tmp2 = ''
				        var tmp3 = ''
				        for (var i = 0; i < data.length; i++) {
				          	// charAt(): 문자값 반환
				          	tmp = data.charAt(i)
				          	tmp2 = data.charAt(i + 1)
				          	tmp3 = data.charAt(i + 2)

				          	// charCodeAt(): 유니코드값 반환
				          	if (tmp.charCodeAt(0) - tmp2.charCodeAt(0) === 1 && tmp2.charCodeAt(0) - tmp3.charCodeAt(0) === 1) {
				            	cnt = cnt + 1
				          	}
				          	if (tmp.charCodeAt(0) - tmp2.charCodeAt(0) === -1 && tmp2.charCodeAt(0) - tmp3.charCodeAt(0) === -1) {
				            	cnt2 = cnt2 + 1
				          	}
				        }
				        if (cnt > 0 || cnt2 > 0) {
							alert('연속된 문자를 3개 이상 사용하실 수 없습니다.\n(ex: 123, 321, abc, cba 포함 불가) ');
							$('#userEnpswd').focus();
							return false;
				        }
				    } else {
						alert('같은 문자를 3개 이상 사용할 수 없습니다.');
						$('#userEnpswd').focus();
						return false;
				    }
				}else{
					alert('비밀번호는 최소 9자에서 20자까지, 영문자, 숫자 및 특수 문자를 포함해야 합니다.');
					$('#userEnpswd').focus();
					return false;
				}

				//미입력 validate
				var valid = true;
				$('.join-input input[type="text"] ').each(function(){
					if($(this).val() == ''){
						alert($(this).attr("title") + '은(는) 필수 입력값입니다.');
						$(this).focus();
						valid = false;
						return false;
					}
				});

				if(valid){

					//비밀번호&재입력 동일한지
					if($("#userEnpswd").val() !== $("#reuserEnpswd").val()){
						$("#reuserEnpswd").focus();
							alert('비밀번호가 일치하지 않습니다. 다시 입력해주세요.');
						return false;
					}

					//
					if(!cm.fnCttcValid($(document.joinForm).find("[name='userTelNo']").val())){
						$(document.frm).find("[name='userTelNo']").focus();
						alert('올바른 연락처를 입력해주세요.');
						return false;
					}

					//선택한 증서별 시스템코드 담기
					 var chkArray = new Array();       
					 $('input:checkbox[name=edycKndArr]:checked').each(function() { // 체크된 체크박스의 value 값을 가지고 온다.            
						chkArray.push($(this).data('etc'));      
					  });       
					 $('#issuSystemArr').val(chkArray);


					var result = confirm('회원가입 하시겠습니까?');
					if(result){
						fnSave();
					}

				}
			}
		}

		function fnSave(){
			cm.ajax(
				{
					url : "<c:url value='/user/insertUserJoin.do'/>",
					data : $(document.joinForm).serialize()
				},
				fnSaveSuccess
			);
		}

		function fnSaveSuccess(result){
			if(result.status === 'SUCCESS'){
				alert(result.message);
				joinForm.action = '<c:url value="/loginPage.do"/>';
	 			joinForm.submit();
			}else{
				alert('회원가입을 다시 시도해주세요.');
			}
		}

		//모달 확인버튼 클릭 시
		function modalConfirmBtn(){
			$('.join-agree-member').fadeOut().addClass("off");
			$('body').removeClass('scrollLock');
		}
		//모달 닫기버튼 클릭 시 체크박스 false로 변경
		function modalCloseBtn(){
			$('input:checkbox').prop('checked',false);
		}


		//패스워드 암호화
		function encryptPwd(pwd, id){

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

<body>

	<!-- Site Wrap : 전체사이트 감싸기-->
	<div class="login-wrap">
		<div class="join-box-wrap">
		<div class="join-box">
			<h1 class="login-logo">회원가입</h1>
			<!-- 회원가입 -->
			<article class="join-infobox" >
				<h2 class="title-lv2">회원가입</h2>

				<form class="join-input" name="joinForm" id="joinForm" method="post">
					<input type="hidden" name="issuSystemArr" id="issuSystemArr">
					<div class="form-control">
						<label for="userID">아이디</label>
						<div class="d-flex jc-between">
							<input type="text" id="userId" name="userId" placeholder="아이디를 입력하세요." title="아이디" required="required" maxlength="20" class="d-flex w70 "/>
							<button type="button" class="btn line-blue w28  mt5" onclick="fnUserIdDplct();">아이디중복조회</button>
						</div>
					</div>
					<div class="form-control">
						<label for="userEnpswd">비밀번호</label>
						<input type="password" id="userEnpswd" name="userEnpswd" placeholder="비밀번호를 입력하세요." title="비밀번호" maxlength="20" required="required"/>
					</div>
					<div class="form-control">
						<label for="reuserEnpswd">비밀번호 재입력</label>
						<input type="password" id="reuserEnpswd" placeholder="비밀번호를 재입력하세요." title="비밀번호 재입력" maxlength="20" required="required"/>
					</div>
					<div class="form-control">
						<label for="userNm">이름</label>
						<input type="text" id="userNm" name="userNm" placeholder="이름을 입력하세요." title="이름" maxlength="10" required="required"/>
					</div>
					<div class="form-control">
						<label for="userTelNo">연락처</label>
						<input type="text" id="userTelNo" name="userTelNo" placeholder="연락처를 입력하세요." title="연락처" maxlength="11" class="number" required="required"/>
					</div>
					<div class="form-control">
						<label for="emlAddr">이메일</label>
						<input type="email" id="emlAddr" name="emlAddr" placeholder="이메일을 입력하세요." title="이메일" required="required" maxlength="30" />
					</div>

					<div class="flex flex-column join-agreement">
						<button type="button" onclick="joinAgreeModal();" id="joinAgreeModalBtn" class="btn btn-sm fill-gray btn-join-agree w100 h40">약관보기</button>
						<button type="button" class="btn fill-blue btn-join w100 h50 mt20" onclick="saveUserJoin();">회원가입</button>
					</div>

				</form>
			</article>

		</div>
		</div>
	</div>

</body>

<!-- 회원약관 모달 -->
<article class="modal-wrap modal-sm join-agree-member">
	<div class="layerpop-container">
		<header class="modal-header">
			<h2 class="title-lv2">약관 안내</h2>
			<button type="button" class="btn btn-close">닫기</button>
		</header>
		<section class="modal-body">
			<!-- Input Area : 인풋 컴퍼넌트 -->
			<form class="join-text">
				<h3 class="title-lv3">이용약관</h3>
				<div class="join-inner-text" tabindex="0">

					이용약관 내용

				</div>

				<div class="row flex mt10 pl15">
					<label for="join-law" class="chkbox">
						<input id="join-law" type="checkbox" name="join-agreement"  class="flex" required />
						위의 개인정보 수집 및 이용에 대한 안내에 동의합니다.
					</label>
				</div>

			</form>

			<div class="row flex jc-center mt30 bg-blue p10">
				<!--contxt start-->
				<label for="join-all" class="chkbox">
					<input id="join-all" type="checkbox" name="join-agreement" class="flex" required />
					전체동의
				</label>
			</div>

		</section>
		<footer class="modal-footer tr">
			<button type="button" class="btn btn-modal-footer line-gray btn-close" onclick="modalCloseBtn();">취소</button>
			<button type="button" class="btn btn-modal-footer line-blue" onclick="modalConfirmBtn();">확인</button>
		</footer>
	</div>
</article>

</html>
