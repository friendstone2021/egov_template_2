/**
 * ${projectDescription}
 * <p>
 * ${projectName}
 * ${packageName}
 * ExcelController.java
 *
 * @author ${author}
 * @version 1.0
 * @since ${since}
 */
package com.egov.template.services.common.file.web;

import com.egov.template.common.util.ExcelUtil;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.parser.ParseException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;

/**
 * ${programDescription}
 * @author ${author}
 * @since ${since}
 * @version 1.0
 *
 * <pre>
 * == 개정이력(Modification Information)==
 * 수정일                 수정자          수정내용
 * -------      -------   ----------------------------
 * ${since}   ${author}   최초 작성
 *
 * </pre>
 */
@Slf4j
@Controller
public class ExcelController {

    @RequestMapping(value = "/excel/excelDownload.do")
    public ResponseEntity<byte[]> downloadExcel(@RequestParam String fileName, @RequestParam String headers, @RequestParam String rows) {

        byte[] excelData;
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        httpHeaders.setContentDispositionFormData("attachment", fileName);

        try {
            excelData = ExcelUtil.createExcel(headers, rows);
        } catch (ParseException | IOException e) {
            throw new RuntimeException(e);
        }

        return ResponseEntity.ok().headers(httpHeaders).body(excelData);
    }

    @RequestMapping(value = "/excel/testExcelDownload.do")
    public String  testExcelDownload() {
        return "test/testExcelDownload";
    }
}
