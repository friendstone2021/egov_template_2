var ctx = getContextPath();
var cm = {};

/**
 * 스크립트용 contextPath 설정
 *
 * @returns
 */
function getContextPath() {
    return sessionStorage.getItem("contextpath");

}

// 로딩바
cm.fnLoading = function (flag) {
    if (flag) {
        $(".loading-container").show();
    } else {
        $(".loading-container").hide();
    }

}

$(function () {

    var options = {
        closeText: "CLOSE",
        prevText: "이전달",
        nextText: "다음달",
        currentText: "TODAY",
        monthNames: ["1월(JAN)", "2월(FEB)", "3월(MAR)", "4월(APR)", "5월(MAY)", "6월(JUN)", "7월(JUL)", "8월(AUG)", "9월(SEP)", "10월(OCT)", "11월(NOV)", "12월(DEC)"],
        monthNamesShort: ["1월", "2월", "3월", "4월", "5월", "6월", "7월", "8월", "9월", "10월", "11월", "12월"],
        dayNames: ["SUN", "MON", "TUE", "WED", "THU", "FRI", "SAT"],
        dayNamesShort: ["SUN", "MON", "TUE", "WED", "THU", "FRI", "SAT"],
        dayNamesMin: ["SUN", "MON", "TUE", "WED", "THU", "FRI", "SAT"],
        weekHeader: "Wk",
        dateFormat: "yy-mm-dd",
        firstDay: 0,
        isRTL: false,
        showMonthAfterYear: true,
        yearSuffix: ""
    };

});

$(document).ready(function () {

    // 막대그래프 bar길이 설정
    $('.bar-graph').each(function () {
        var value = $(this).find('.value').text();

        if ($(this).hasClass('memory')) {
            var maxValue = 20; // memory 최대값 20으로 가정
            var percentage = (value / maxValue) * 100;

            $(this)
                .find('.bar')
                .css('width', percentage + '%');
        } else {
            $(this)
                .find('.bar')
                .css('width', value + '%');
        }

    });

    // SW목록의 grid
    function checkHeight() {
        $('.sw-list .grid-wrap').each(function () {
            var $wrap = $(this);
            var $table = $wrap.find('.grid-list-a');
            var wrapHeight = $wrap.outerHeight();
            var tableHeight = $table.outerHeight();

            if (tableHeight + 2 >= wrapHeight) {
                $wrap.addClass('auto');
            } else {
                $wrap.removeClass('auto');
            }

            console.log(wrapHeight, tableHeight);
        });
    }

    checkHeight();

    $(window).resize(function () {
        checkHeight();
    });

    // 해당 패널 열기
    // $('.rack-box a').on('click', function (e) {
    //   e.preventDefault();

    //   // console.log('click ', e.target);

    //   $('#serverPanel').addClass('open');
    //   document.querySelectorAll('.rack-box a').disabled = true;
    // });

    // $('.panel .btn-close').on('click', function () {
    //   $(this).closest('#serverPanel').removeClass('open');
    // });

    // $('.rack-box a').on('click', function (e) {
    //   e.preventDefault();

    //   const targetText = $(this).text().trim().replace(/\s+/g, ' ');

    //   $('.panel').each(function () {
    //     const panelTitle = $(this).find('.panel-title .tit').text().trim();

    //     if (panelTitle === targetText) {
    //       $(this).addClass('open');
    //     } else {
    //       $(this).removeClass('open');
    //     }
    //   });
    // });

    // $('.panel .btn-close').on('click', function () {
    //   $(this).closest('.panel').removeClass('open');
    // });
    // 세팅메뉴 snb

    $('#lnb .depth1 > li.active > .depth2').show();

    $('#lnb .depth1 > li > a').click(function (e) {
        e.preventDefault();

        var $parentLi = $(this).parent('li');
        var $depth2 = $parentLi.find('.depth2');

        if ($parentLi.hasClass('active')) {
            $depth2.slideUp(300);
            $parentLi.removeClass('active');
        } else {
            // 현재 클릭된 요소가 active 클래스가 없는 경우에만 실행
            if (!$(this).closest('#lnb').find('.depth1 > li.active').is($parentLi)) {
                $('#lnb .depth1 > li.active > .depth2').slideUp(300);
                $('#lnb .depth1 > li.active').removeClass('active');
            }

            $parentLi.addClass('active');
            $depth2.stop(true, true).slideDown(300);
        }
    });


    //** table 순서 변경
    let draggedRow = null;
    let placeholder;
    // var table = $('.move-grid tbody');
    var serviceBody = $('#serviceBody');
    var groupBody = $('#groupBody');

    //서비스 순서 드래그 시작
    serviceBody.on('mousedown', '.btn-move', function (e) {
        e.preventDefault();
        draggedRow = $(this).closest('tr');

        placeholder = $('<tr class="placeholder"></tr>').html(draggedRow.html()).css({
            border: '2px dashed #ddd',
            boxShadow: '4px 4px 4px 0px rgba(0, 0, 0, 0.15)',
            opacity: 0.5,
            pointerEvents: 'none',
        });

        draggedRow.after(placeholder);
        draggedRow.addClass('dragging');
        $(document).on('mousemove', service_moveRow);
    });
    //그룹등록 드래그 시작
    groupBody.on('mousedown', '.btn-move', function (e) {
        e.preventDefault();
        draggedRow = $(this).closest('tr');

        placeholder = $('<tr class="placeholder"></tr>').html(draggedRow.html()).css({
            border: '2px dashed #ddd',
            boxShadow: '4px 4px 4px 0px rgba(0, 0, 0, 0.15)',
            opacity: 0.5,
            pointerEvents: 'none',
        });

        draggedRow.after(placeholder);
        draggedRow.addClass('dragging');
        $(document).on('mousemove', group_moveRow);
    });

    //서비스 순서 행 이동처리
    function service_moveRow(e) {
        const mouseY = e.pageY;
        placeholder.hide();
        let targetRow = serviceBody
            .find('tr')
            .filter(function () {
                const offset = $(this).offset();
                const height = $(this).outerHeight();
                return mouseY > offset.top && mouseY < offset.top + height;
            })
            .not('.dragging, .placeholder');

        placeholder.show();

        if (targetRow.length) {
            placeholder.insertBefore(targetRow);
        } else {
            const lastRow = serviceBody.find('tr').last();
            if (mouseY > lastRow.offset().top + lastRow.outerHeight()) {
                placeholder.insertAfter(lastRow);
            }
        }
    }

    //그룹등록 행 이동처리
    function group_moveRow(e) {
        const mouseY = e.pageY;
        placeholder.hide();
        let targetRow = groupBody
            .find('tr')
            .filter(function () {
                const offset = $(this).offset();
                const height = $(this).outerHeight();
                return mouseY > offset.top && mouseY < offset.top + height;
            })
            .not('.dragging, .placeholder');

        placeholder.show();

        if (targetRow.length) {
            placeholder.insertBefore(targetRow);
        } else {
            const lastRow = groupBody.find('tr').last();
            if (mouseY > lastRow.offset().top + lastRow.outerHeight()) {
                placeholder.insertAfter(lastRow);
            }
        }
    }

    // 드래그 종료
    $(document).on('mouseup', function (e) {
        if (draggedRow) {
            console.log('draggedRow');
            placeholder.replaceWith(draggedRow);
            draggedRow.removeClass('dragging');
            draggedRow = null;
            placeholder = null;
            if (e.target.id == 'svOrder_grid') {
                $(document).off('mousemove', service_moveRow);
            } else if (e.target.id == 'group_grid') {
                $(document).off('mousemove', group_moveRow);
            }
            updateOrder(serviceBody);
            updateOrder(groupBody);
        }
    });

    // 순서 갱신
    function updateOrder(table) {
        table.find('.order-num').each(function (index) {
            $(this).text(index + 1);
        });
    }

    // ** 이미지 파일 첨부
    const $imgArea = $('.img-wrap .img-area');

    $('.img-wrap #imgFile').on('change', function (event) {
        const file = event.target.files[0];
        $imgArea.hide();

        if (file) {
            const reader = new FileReader();

            reader.onload = function (e) {
                $imgArea.css({
                    background: `#e5e5e5 url(${e.target.result})`,
                });
                $imgArea.show();
                $imgArea.html('');
            };
            reader.readAsDataURL(file);
        } else {
            $imgArea.hide();
        }
    });

    $imgArea.hide();
});

/**
 * 스크립트용 contextPath 설정
 *
 * @returns
 */
function getContextPath() {
    return sessionStorage.getItem("contextpath");

}


// 함수명 : cm.alert
// 내 용 : 공통 alert 컴포넌트
// 인 자 : options, callbackFnc
// 반환값 : 없음
// 사용법
// paramObj = {icon : success, title : '제목', text : '내용'}
// callbackFnc = 콜백함수
cm.alert = function (paramObj, callbackFnc) {
    let defaultOption = {
        icon: 'success',
        title: '',
        text: '',
    };
    let options = {};
    if (typeof paramObj === 'string') {
        options = Object.assign(defaultOption, options);
        options.title = paramObj;
    } else if (typeof paramObj === 'object') {
        options = Object.assign(defaultOption, paramObj);
    } else {
        options = defaultOption;
    }

};


// 함수명 : cm.confirm
// 내 용 : 공통 confirm 컴포넌트
// 인 자 : options, callbackFnc
// 반환값 : 없음
// 사용법
// paramObj = {icon : success, title : '제목', text : '내용'}
// callbackFnc = 콜백함수
cm.confirm = function (paramObj, callbackFnc, cancelCallbackFnc) {
    let defaultOption = {
        title: '',
        text: '',
        icon: 'warning',
        showCancelButton: true, // cancel버튼 보이기. 기본은 원래 없음
        confirmButtonColor: '#3085d6', // confrim 버튼 색깔 지정
        cancelButtonColor: '#d33', // cancel 버튼 색깔 지정
        confirmButtonText: '확인', // confirm 버튼 텍스트 지정
        cancelButtonText: '취소', // cancel 버튼 텍스트 지정
        reverseButtons: false, // 버튼 순서 거꾸로
    };

    let options = {};
    if (typeof paramObj === 'string') {
        options = Object.assign(defaultOption, options);
        options.title = paramObj;
    } else if (typeof paramObj === 'object') {
        options = Object.assign(defaultOption, paramObj);
    } else {
        options = defaultOption;
    }

};


//함수명 : cm.ajax
//내 용 : 공통 ajax 컴포넌트
//인 자 : paramObj, callbackFnc
//반환값 : 없음
//사용법
//paramObj = {url : /.../.../..do, data : $(paramObj.frm).serialize()}
//callbackFnc = 콜백함수
cm.ajax = function (paramObj, callbackFnc) {
    var csrfToken = $("meta[name='_csrf']").attr("content");
    var csrfHeader = $("meta[name='_csrf_header']").attr("content");


    var contentType;
    var processData;
    var async = true;
    // 컨텐츠 타입
    if (paramObj.contentType === undefined) {
        contentType = "application/x-www-form-urlencoded; charset=utf-8";
    } else {
        contentType = paramObj.contentType;
    }
    if (paramObj.processData === undefined) {
        processData = true;
    } else {
        processData = paramObj.processData;
    }
    // 비동기 여부
    if (paramObj.async !== undefined) {
        async = paramObj.async;
    }

    $.ajax({
        url: paramObj.url,
        type: 'POST',
        async: async,
        cache: true,
        /*timeout: 3000,*/
        data: paramObj.data !== undefined ? paramObj.data : {},
        contentType: contentType,
        processData: processData,
        dataType: 'json',
        beforeSend: function (xhr) {
            xhr.setRequestHeader(csrfHeader, csrfToken);
            // XHR Header를 포함해서 HTTP Request를 하기전에 호출됩니다.
            //cm.fnLoading(true);	// 로딩 시작
        },
        success: function (data, status, xhr) {
            // 정상적으로 응답 받았을 경우에는 success 콜백이 호출되게 됩니다.
            // 이 콜백 함수의 파라미터에서는 응답 바디, 응답 코드 그리고 XHR 헤더를 확인할 수 있습니다.
            /*if(data && data.status === 'FAIL_AUTH'){
              cm.alert({title : '로그인이 필요한 서비스 입니다. 로그인페이지로 이동합니다.', icon: 'warning'}, function(){
                location.href = ctx + '/com/sicis/li/li/loginForm.do';
              });
              return;
            }*/
            if (typeof callbackFnc === 'function') {
                callbackFnc(data);
            }
        },
        error: function (xhr, status, error) {
            // 응답을 받지 못하였다거나 정상적인 응답이지만 데이터 형식을 확인할 수 없기 때문에
            // error 콜백이 호출될 수 있습니다.
            // 예를 들어, dataType을 지정해서 응답 받을 데이터 형식을 지정하였지만,
            // 서버에서는 다른 데이터형식으로 응답하면 error 콜백이 호출되게 됩니다.
            console.log(error);
            cm.alert({title: '요청처리를 실패하였습니다.', icon: 'warning'});
        },
        complete: function (xhr, status) {
            // success와 error 콜백이 호출된 후에 반드시 호출됩니다.
            // try - catch - finally의 finally 구문과 동일합니다.
            //cm.fnLoading(false);	// 로딩 종료
        },
    });
};

//함수명 : cm.defaultAjax
//내 용 : 공통 ajax 컴포넌트
//인 자 : paramObj, callbackFnc
//반환값 : 없음
//사용법
//paramObj = {url : /.../.../..do, data : $(paramObj.frm).serialize()}
//callbackFnc = 콜백함수
cm.defaultAjax = function (paramObj, callbackFnc) {

    var csrfToken = $("meta[name='_csrf']").attr("content");
    var csrfHeader = $("meta[name='_csrf_header']").attr("content");

    var contentType;
    var async = true;
    // 컨텐츠 타입
    if (paramObj.contentType === undefined) {
        contentType = "application/x-www-form-urlencoded; charset=utf-8";
    } else {
        contentType = paramObj.contentType;
    }
    // 비동기 여부
    if (paramObj.async !== undefined) {
        async = paramObj.async;
    }

    $.ajax({
        url: paramObj.url,
        type: 'POST',
        async: async,
        cache: true,
        /*timeout: 3000,*/
        data: paramObj.data !== undefined ? paramObj.data : {},
        contentType: contentType,
        dataType: 'json',
        beforeSend: function (xhr) {
            xhr.setRequestHeader(csrfHeader, csrfToken);
            // XHR Header를 포함해서 HTTP Request를 하기전에 호출됩니다.
        },
        success: function (data, status, xhr) {
            // 정상적으로 응답 받았을 경우에는 success 콜백이 호출되게 됩니다.
            // 이 콜백 함수의 파라미터에서는 응답 바디, 응답 코드 그리고 XHR 헤더를 확인할 수 있습니다.
            if (typeof callbackFnc === 'function') {
                callbackFnc(data);
            }
        },
        error: function (xhr, status, error) {
            // 응답을 받지 못하였다거나 정상적인 응답이지만 데이터 형식을 확인할 수 없기 때문에
            // error 콜백이 호출될 수 있습니다.
            // 예를 들어, dataType을 지정해서 응답 받을 데이터 형식을 지정하였지만,
            // 서버에서는 다른 데이터형식으로 응답하면 error 콜백이 호출되게 됩니다.
        },
        complete: function (xhr, status) {
            // success와 error 콜백이 호출된 후에 반드시 호출됩니다.
            // try - catch - finally의 finally 구문과 동일합니다.
        },
    })
};

//함수명 : cm.fileAjax
//내 용 : 파일 다운로드 ajax 컴포넌트
//인 자 : paramObj, callbackFnc
//반환값 : 없음
//사용법
//paramObj = {url : /.../.../..do, data : $(paramObj.frm).serialize()}
//callbackFnc = 콜백함수
cm.fileAjax = function (paramObj) {

    var csrfToken = $("meta[name='_csrf']").attr("content");
    var csrfHeader = $("meta[name='_csrf_header']").attr("content");

    var async = true;
    // 컨텐츠 타입
    if (paramObj.contentType === undefined) {
        contentType = "application/x-www-form-urlencoded; charset=utf-8";
    }
    // 비동기 여부
    if (paramObj.async !== undefined) {
        async = paramObj.async;
    }

    $.ajax({
        url: paramObj.url,
        type: 'POST',
        data: paramObj.data !== undefined ? paramObj.data : {},
        async: async,
        xhrFields: {
            responseType: 'blob'
        },
        beforeSend: function (xhr) {
            xhr.setRequestHeader(csrfHeader, csrfToken);
        },
        success: function (data, status, xhr) {
            let contentDisposition = xhr.getResponseHeader('Content-Disposition');

            console.log(xhr);

            if (data && contentDisposition && contentDisposition.indexOf('attachment') !== -1) {
                var filename = contentDisposition.split('filename*=UTF-8\'\'')[1] || contentDisposition.split('filename=')[1];
                filename = decodeURIComponent(filename).replace(/"/g, '');

                let downLink = document.createElement('a'); // 태그 상성
                let url = window.URL.createObjectURL(data); // url 생성
                downLink.href = url;

                downLink.download = filename; // 파일명 설정

                document.body.append(downLink);

                downLink.click();
                downLink.remove();
                window.URL.revokeObjectURL(downLink); // url 해제
            }
        },
        error: function (xhr) {
            if (xhr.status === 403) {
                alert("권한이 없습니다.");
            } else if (xhr.status === 404) {
                alert("파일이 존재하지 않습니다.");
            } else if (xhr.status === 302) {
                alert("오류가 발생하였습니다. 관리자에게 문의 바랍니다.");
            } else {
                alert("오류가 발생하였습니다. 관리자에게 문의 바랍니다.");
            }
        }
    });
};

cm.nullEscape = function (str) {
    if (!str || str === null || str.toString().toLowerCase() === 'null') {
        if (typeof str === 'number') {
            return '0';
        } else {
            return '';
        }
    } else {
        return str;
    }
};

cm.valid = function (validObj) {
    let validFlag = true;
    let errObj = {};
    if (validObj && typeof validObj === 'object' && validObj.length > 0) {
        $.each(validObj, function (i, v) {
            var el = $(v.frm).find('[name="' + v.name + '"]');
            if (el && v.isRequired) {
                if ($.trim(el.val()) === '') {
                    let title = v.title !== undefined ? v.title : el.attr('title');
                    errObj.msg = title + '은(는) 필수입니다.';
                    errObj.obj = v;
                    validFlag = false;
                    return false;
                }
            }

            if (el && v.minLength) {
                if ($.trim(el.val()) === '' || el.val().length < v.minLength) {
                    let title = v.title !== undefined ? v.title : el.attr('title');
                    errObj.msg = title + '은(는) ' + v.minLength + '자 이상 입력해야 합니다.';
                    errObj.obj = v;
                    validFlag = false;
                    return false;
                }
            }

            if (el && v.maxLength) {
                if ($.trim(el.val()) === '' || el.val().length > v.maxLength) {
                    let title = v.title !== undefined ? v.title : el.attr('title');
                    errObj.msg = title + '은(는) ' + v.minLength + '자까지 입력 가능 합니다.';
                    errObj.obj = v;
                    validFlag = false;
                    return false;
                }
            }
        });
    }

    if (!validFlag) {
        var focusObj = errObj.obj;
        $(focusObj.frm).find('[name="' + focusObj.name + '"]').focus();
        cm.alert({title: errObj.msg, icon: 'warning'});
    }

    return validFlag;
};

//함수명 : cm.numberKeypress
//내 용 : 공통 ajaxSave 컴포넌트
//인 자 : event
//반환값 : 없음
//사용법
//onkeypress = "return cm.numberKeypress(event);"
cm.numberKeypress = function (e) {
    var keyValue = event.keyCode;
    if (((keyValue >= 48) && (keyValue <= 57))) return true;
    else return false;
};

cm.numberKeyup = function (e) {
    var regExp = /[^0-9]/g;
    var ele = e.target;
    if (regExp.test(ele.value)) {
        ele.value = ele.value.replace(regExp, '');
    }
};


// 접속한 도메인 정보를 리턴한다.
cm.fnDefaultDomain = function () {
    var rtnDomain = getContextPath();
    var domainInfo = JSON.parse(sessionStorage.getItem(sessionStorage.getItem('active')));
    if (domainInfo.ctx) rtnDomain = domainInfo.domain + domainInfo.ctx;
    return rtnDomain;
};


// 전화번호 검증
cm.fnCttcValid = function (str) {
    var regPhone = /^01([0|1|6|7|8|9])?([0-9]{7,8})$/;
    if (!regPhone.test(str)) return false;
    return true;
};

// 생년월일 검증
cm.fnBrthdyValid = function (dateStr) {
    /*var regBrthdy = /^(19[0-9][0-9]|20\d{2})(0[0-9]|1[0-2])(0[1-9]|[1-2][0-9]|3[0-1])$/;
    if(!regBrthdy.test(str)) return false;
    return true;*/

    var year = Number(dateStr.substr(0, 4)); // 입력한 값의 0~4자리까지 (연)
    var month = Number(dateStr.substr(4, 2)); // 입력한 값의 4번째 자리부터 2자리 숫자 (월)
    var day = Number(dateStr.substr(6, 2)); // 입력한 값 6번째 자리부터 2자리 숫자 (일)
    var today = new Date(); // 날짜 변수 선언
    var yearNow = today.getFullYear(); // 올해 연도 가져옴

    if (dateStr.length === 8) {
        // 연도의 경우 1900 보다 작거나 yearNow 보다 크다면 false를 반환합니다.
        if (1900 > year || year > yearNow) {
            return false;
        } else if (month < 1 || month > 12) {
            return false;
        } else if (day < 1 || day > 31) {
            return false;
        } else if ((month == 4 || month == 6 || month == 9 || month == 11) && day == 31) {
            return false;
        } else if (month == 2) {

            var isleap = (year % 4 == 0 && (year % 100 != 0 || year % 400 == 0));
            if (day > 29 || (day == 29 && !isleap)) {
                return false;
            } else {
                return true;
            } //end of if (day>29 || (day==29 && !isleap))
        } else {
            return true;
        }//end of if
    } else {
        //1.입력된 생년월일이 8자 초과할때 :  auth:false
        return false;
    }
};

//위,경도의  DMS(도분초) 변환
cm.getValueDMS = function (ddVAlue) {

    if (isNaN(parseInt(ddVAlue))) return '-';

    var dValue = parseInt(ddVAlue);
    var mValue = parseInt((ddVAlue - dValue) * 60);
    var sValue = ((ddVAlue - dValue) * 60 - mValue) * 60;

    return dValue + '° ' + mValue + '\' ' + sValue.toFixed(4) + '" ';
};


// 두 지점 사이의 거리 계산
cm.getDistance = function (lat1, lon1, lat2, lon2) {
    if ((lat1 == lat2) && (lon1 == lon2))
        return 0;

    var radLat1 = Math.PI * lat1 / 180;
    var radLat2 = Math.PI * lat2 / 180;
    var theta = lon1 - lon2;
    var radTheta = Math.PI * theta / 180;
    var dist = Math.sin(radLat1) * Math.sin(radLat2) + Math.cos(radLat1) * Math.cos(radLat2) * Math.cos(radTheta);
    if (dist > 1)
        dist = 1;

    dist = Math.acos(dist);
    dist = dist * 180 / Math.PI;
    dist = dist * 60 * 1.1515 * 1.609344 * 1000;
    if (dist < 100) dist = Math.round(dist / 10) * 10;
    else dist = Math.round(dist / 100) * 100;

    if (dist >= 1000) {
        dist = (Math.round(dist / 1000 * 100) / 100) + ' ' + 'km';
    } else {
        dist = Math.round(dist) + ' ' + 'm';
    }

    return dist;
};

//두 지점 사이의 거리 계산 ( 미터 )
cm.getDistanceMeter = function (lat1, lon1, lat2, lon2) {
    if ((lat1 == lat2) && (lon1 == lon2))
        return 0;

    var radLat1 = Math.PI * lat1 / 180;
    var radLat2 = Math.PI * lat2 / 180;
    var theta = lon1 - lon2;
    var radTheta = Math.PI * theta / 180;
    var dist = Math.sin(radLat1) * Math.sin(radLat2) + Math.cos(radLat1) * Math.cos(radLat2) * Math.cos(radTheta);
    if (dist > 1)
        dist = 1;

    dist = Math.acos(dist);
    dist = dist * 180 / Math.PI;
    dist = dist * 60 * 1.1515 * 1.609344 * 1000;
    if (dist < 100) dist = Math.round(dist / 10) * 10;
    else dist = Math.round(dist / 100) * 100;


    dist = Math.round(dist);

    return dist;
};

// 숫자 사이 ,
cm.toLocalString = function (num, locales, option) {
    locales = typeof locales === 'undefined' ? 'ko-KR' : locales;
    return typeof option === 'undefined' ? num.toLocaleString(locales) : num.toLocaleString(locales, option);

}

//IPv4 validation
cm.validateIPv4 = function (ip) {
    const ipv4Regex = /^(25[0-5]|2[0-4][0-9]|1[0-9]{2}|[1-9]?[0-9])(\.(25[0-5]|2[0-4][0-9]|1[0-9]{2}|[1-9]?[0-9])){3}$/;
    return ipv4Regex.test(ip);
}

//IPv6 validation
cm.validateIPv6 = function (ip) {
    const ipv4Regex = /^(([0-9a-fA-F]{1,4}:){7,7}[0-9a-fA-F]{1,4}|([0-9a-fA-F]{1,4}:){1,7}:|([0-9a-fA-F]{1,4}:){1,6}:[0-9a-fA-F]{1,4}|([0-9a-fA-F]{1,4}:){1,5}(:[0-9a-fA-F]{1,4}){1,2}|([0-9a-fA-F]{1,4}:){1,4}(:[0-9a-fA-F]{1,4}){1,3}|([0-9a-fA-F]{1,4}:){1,3}(:[0-9a-fA-F]{1,4}){1,4}|([0-9a-fA-F]{1,4}:){1,2}(:[0-9a-fA-F]{1,4}){1,5}|[0-9a-fA-F]{1,4}:((:[0-9a-fA-F]{1,4}){1,6})|:((:[0-9a-fA-F]{1,4}){1,7}|:)|fe80:(:[0-9a-fA-F]{0,4}){0,4}%[0-9a-zA-Z]{1,}|::(ffff(:0{1,4}){0,1}:){0,1}((25[0-5]|(2[0-4]|1{0,1}[0-9]|)[0-9])\.){3,3}(25[0-5]|(2[0-4]|1{0,1}[0-9]|)[0-9])|([0-9a-fA-F]{1,4}:){1,4}:((25[0-5]|(2[0-4]|1{0,1}[0-9]|)[0-9])\.){3,3}(25[0-5]|(2[0-4]|1{0,1}[0-9]|)[0-9]))$/;
    return ipv4Regex.test(ip);
}

//음수, 소수점 제외 정수값 검증
cm.validateNumber = function (number) {
    const numberRegex = /^\d+$/;
    return numberRegex.test(number);
}

cm.isEmpty = function (value) {
    return value.trim() === '';
}

cm.validateRgb = function (rgb) {
    const match = /^rgb\((\d+),\s*(\d+),\s*(\d+)\)$/;
    return match.test(rgb);
}

cm.validatePassword = function (pw) {
    const match = /^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*\d)(?=.*[$@$!%*#?&])[A-Za-z\d$@$!%*#?&]{9,20}$/;
    return match.test(pw);
}

cm.setCurrentTime = function () {
    let date = new Date();

    let year = date.getFullYear();
    let month = date.getMonth() + 1;
    let days = date.getDate();
    let hours = date.getHours();
    let min = date.getMinutes();
    let sec = date.getSeconds();

    let format = (num) => num < 10 ? "0" + num : num;

    let fullDate = year + "-" + format(month) + "-" + format(days) + " " + format(hours) + ":" + format(min) + ":" + format(sec);
    return fullDate;
}

//hex값을 받아 decrement만큼 색을 더 연하게
cm.lightenHexColor = function (hex, decrement) {
    // HEX에서 R, G, B 값을 추출
    let r = parseInt(hex.substr(1, 2), 16);
    let g = parseInt(hex.substr(3, 2), 16);
    let b = parseInt(hex.substr(5, 2), 16);

    // 각 채널에 밝기를 추가 (증가 값은 increment로 설정)
    r = Math.min(255, r + decrement);
    g = Math.min(255, g + decrement);
    b = Math.min(255, b + decrement);

    // 새로운 밝은 색을 HEX로 변환
    return `#${((1 << 24) | (r << 16) | (g << 8) | b).toString(16).slice(1).toUpperCase()}`;
}