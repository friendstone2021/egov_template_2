<%--
  ${projectDescription}

  ${projectName}
  ${sourcePath}
  testExcelDownload

  @author ${author}
  @version 1.0
  @since ${since}
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <meta name="_csrf" content="${_csrf.token}"/>
    <meta name="_csrf_header" content="${_csrf.headerName}"/>
    <title>Title</title>
    <script src="${contextPath}/js/jquery/jquery-3.6.0.min.js"></script>
    <script src="${contextPath}/js/jquery/jquery-ui.min.js"></script>
    <script src="${contextPath}/js/jquery/timepicker.js"></script>
    <script src="${contextPath}/js/swalert/sweetalert2.min.js"></script>
    <script src="${contextPath}/js/common.js"></script>
</head>
<script type="text/javascript">
    function download() {
        let formData = new FormData();
        formData.append('fileName', 'test.xlsx');
        formData.append('headers', '[[{\"title\":\"col1\"},{\"title\":\"col2\"},{\"title\":\"col3\"},{\"title\":\"col4\", \"rowspan\":2},{\"title\":\"col5\",\"colspan\":2}],[{\"title\":\"col1\",\"colspan\":3},{\"title\":\"col2\"},{\"title\":\"col3\"}]]');
        formData.append('rows', '[[{\"title\":\"col1\"},{\"title\":\"col2\"},{\"title\":\"col3\"},{\"title\":\"col4\", \"rowspan\":2},{\"title\":\"col5\"}],[{\"title\":\"col1\",\"colspan\":3},{\"title\":\"col2\"},{\"title\":\"col3\"}],[{\"title\":\"col1\"},{\"title\":\"col2\"},{\"title\":\"col3\"},{\"title\":\"col4\", \"rowspan\":2},{\"title\":\"col5\"}],[{\"title\":\"col1\",\"colspan\":3},{\"title\":\"col2\"},{\"title\":\"col3\"}]]');

        var csrfToken = $("meta[name='_csrf']").attr("content");
        var csrfHeader = $("meta[name='_csrf_header']").attr("content");

        $.ajax({
            url: '/excel/excelDownload.do', // 파일 다운로드 API
            method: 'POST',
            data: formData,
            processData: false, // 🔥 FormData는 가공하지 말고 그대로 보내야 함
            contentType: false,
            xhrFields: {
                responseType: 'blob' // 바이너리로 받기
            },
            beforeSend: function (xhr) {
                xhr.setRequestHeader(csrfHeader, csrfToken);
            },
            success: function (data, status, xhr) {
                // 파일 이름 추출 (Content-Disposition 헤더에서)
                var disposition = xhr.getResponseHeader('Content-Disposition');
                var filename = 'downloaded_file';
                if (disposition && disposition.indexOf('attachment') !== -1) {
                    var filenameRegex = /filename[^;=\n]*=((['"]).*?\2|[^;\n]*)/;
                    var matches = filenameRegex.exec(disposition);
                    if (matches != null && matches[1]) {
                        filename = matches[1].replace(/['"]/g, '');
                    }
                }

                // Blob 생성
                var blob = new Blob([data]);
                var url = window.URL.createObjectURL(blob);

                // 링크를 강제로 클릭해서 다운로드 시작
                var a = document.createElement('a');
                a.href = url;
                a.download = filename;
                document.body.appendChild(a);
                a.click();

                // 다운로드 후 클린업
                window.URL.revokeObjectURL(url);
                a.remove();
            },
            error: function (xhr, status, error) {
                console.error('Download failed:', error);
            }
        });
    }
</script>
<body>
<button id='download' onclick='download()'>Download</button>
</body>
</html>
