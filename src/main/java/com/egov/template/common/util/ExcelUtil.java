/**
 * ${projectDescription}
 * <p>
 * ${projectName}
 * ${packageName}
 * ExcelUtil.java
 *
 * @author ${author}
 * @version 1.0
 * @since ${since}
 */
package com.egov.template.common.util;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

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
public class ExcelUtil {

    /**
     *
     * @param headerJson : "[[{\"title\":\"col1\"},{\"title\":\"col2\"},{\"title\":\"col3\"},{\"title\":\"col4\", \"rowspan\":2},{\"title\":\"col5\",\"colspan\":2}],[{\"title\":\"col1\",\"colspan\":3},{\"title\":\"col2\"},{\"title\":\"col3\"}]]"
     * @param rowJson : "[[{\"title\":\"col1\"},{\"title\":\"col2\"},{\"title\":\"col3\"},{\"title\":\"col4\", \"rowspan\":2},{\"title\":\"col5\"}],[{\"title\":\"col1\",\"colspan\":3},{\"title\":\"col2\"},{\"title\":\"col3\"}],[{\"title\":\"col1\"},{\"title\":\"col2\"},{\"title\":\"col3\"},{\"title\":\"col4\", \"rowspan\":2},{\"title\":\"col5\"}],[{\"title\":\"col1\",\"colspan\":3},{\"title\":\"col2\"},{\"title\":\"col3\"}]]"
     * @return byte[]
     * @throws ParseException
     * @throws IOException
     */
    public static byte[] createExcel(String headerJson, String rowJson) throws ParseException, IOException {

        XSSFWorkbook workbook = new XSSFWorkbook();

        XSSFSheet sheet = workbook.createSheet("Sheet1");

        JSONParser parser = new JSONParser();

        JSONArray headers = (JSONArray)parser.parse(headerJson);
        JSONArray rows = (JSONArray)parser.parse(rowJson);

        int rowCount = 0;

        if(headers != null && !headers.isEmpty()) {
            rowCount += generateRow(sheet, headers, 0);
        }

        if(rows != null && !rows.isEmpty()) {
            generateRow(sheet, rows, rowCount);
        }

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        workbook.close();

        return outputStream.toByteArray();
    }

    private static int generateRow(XSSFSheet sheet, JSONArray rowInfoArray, int rowOffset) {
        // 셀 점유 여부 체크
        Set<String> occupied = new HashSet<>();

        for (int r = 0; r < rowInfoArray.size(); r++) {
            JSONArray rowArray = (JSONArray)rowInfoArray.get(r);
            Row row = sheet.getRow(r+rowOffset);
            if (row == null) {
                row = sheet.createRow(r+rowOffset);
            }

            int c = 0;
            for (int i = 0; i < rowArray.size(); i++) {
                JSONObject cellInfo = (JSONObject)rowArray.get(i);

                // 비어있지 않은 곳 찾아서 이동
                while (occupied.contains(r + "," + c)) {
                    c++;
                }

                Cell cell = row.createCell(c);
                cell.setCellValue(cellInfo.get("title").toString());

                int rowspan = cellInfo.containsKey("rowspan")?Integer.parseInt(cellInfo.get("rowspan").toString()):1;
                int colspan = cellInfo.containsKey("colspan")?Integer.parseInt(cellInfo.get("colspan").toString()):1;

                // 병합 처리
                if (rowspan > 1 || colspan > 1) {
                    int lastRow = r + rowspan - 1;
                    int lastCol = c + colspan - 1;
                    sheet.addMergedRegion(new CellRangeAddress(r+rowOffset, lastRow+rowOffset, c, lastCol));

                    // 병합된 셀을 점유 표시
                    for (int rr = r; rr <= lastRow; rr++) {
                        for (int cc = c; cc <= lastCol; cc++) {
                            occupied.add(rr + "," + cc);
                        }
                    }
                } else {
                    occupied.add(r + "," + c);
                }

                c++;
            }
        }

        return rowInfoArray.size();
    }

}
