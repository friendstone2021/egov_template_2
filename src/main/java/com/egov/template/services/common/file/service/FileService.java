/**
 * ${projectDescription}
 * <p>
 * ${projectName}
 * ${packageName}
 * FileService.java
 *
 * @author ${author}
 * @version 1.0
 * @since ${since}
 */
package com.egov.template.services.common.file.service;

import com.egov.template.services.common.file.mapper.FileMapper;
import com.egov.template.services.common.file.vo.TbCmFileDtlVO;
import com.egov.template.services.common.file.vo.TbCmFileGroupVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

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
@Service
public class FileService {

    @Value("${spring.servlet.multipart.location}")
    private String FILE_STORE_LOCATION;

    @Autowired
    private FileMapper fileMapper;

    @Transactional
    public void uploadFiles(String originFileGroupId, List<MultipartFile> addFiles, List<String> deleteFileIds) throws IOException {

        //신규 파일그룹 아이디 발급
        String fileGroupId = UUID.randomUUID().toString();

        //기존 파일 그룹아이디가 있었으면 기존 파일 정보를 조회
        TbCmFileGroupVO originFileGroup = null;
        if(originFileGroupId != null && !originFileGroupId.isEmpty()) {
            TbCmFileGroupVO fileGroupParam = new TbCmFileGroupVO();
            fileGroupParam.setFileGroupId(originFileGroupId);
            fileGroupParam.setUseYn("Y");
            originFileGroup = fileMapper.selectFileGrp(fileGroupParam);
        }

        //신규 파일 그룹 저장 디렉토리 생성
        String fileGroupPath = FILE_STORE_LOCATION + File.separatorChar + fileGroupId;

        if(makeFileGroupDir(FILE_STORE_LOCATION, fileGroupPath)){

            //기존 파일 그룹이 없었다면 새로 파일그룹을 생성하여 저장하고 끝
            if(originFileGroup == null){
                TbCmFileGroupVO newFileGroup = new TbCmFileGroupVO();
                newFileGroup.setFileGroupId(fileGroupId);
                newFileGroup.setUseYn("Y");
                newFileGroup.setRegDt(LocalDateTime.now());
                newFileGroup.setRgtrId("SYSTEM");

                fileMapper.insertFileGrp(newFileGroup);

                for(MultipartFile file : addFiles){

                    String fileId = UUID.randomUUID().toString();

                    TbCmFileDtlVO fileDetail = new TbCmFileDtlVO();
                    fileDetail.setFileGroupId(fileGroupId);
                    fileDetail.setFileId(fileId);
                    fileDetail.setFilePathNm(fileGroupPath + File.separatorChar + fileId);
                    fileDetail.setStrgFileNm(fileId);
                    fileDetail.setOrgnlFileNm(file.getOriginalFilename());
                    fileDetail.setFileExtnNm(Objects.requireNonNull(file.getOriginalFilename()).substring(Objects.requireNonNull(file.getOriginalFilename()).lastIndexOf(".") + 1));
                    fileDetail.setFileSz(file.getSize());
                    fileDetail.setUseYn("Y");
                    fileDetail.setRegDt(LocalDateTime.now());
                    fileDetail.setRgtrId("SYSTEM");

                    fileMapper.insertFileDtl(fileDetail);

                    //파일저장
                    file.transferTo(new File(fileGroupId + File.separatorChar + fileId));
                }
            }else{ //기존 파일 그룹이 있었다면 파일목록에서 신규추가된 파일은 추가하고 삭제된 파일은 제외해서 저장

                List<TbCmFileDtlVO> newFileDetails = new ArrayList<>();

                //기존 파일 목록 가져오기
                TbCmFileDtlVO fileDtlParam = new TbCmFileDtlVO();
                fileDtlParam.setFileGroupId(originFileGroup.getFileGroupId());
                fileDtlParam.setUseYn("Y");
                List<TbCmFileDtlVO> originFileDetails = fileMapper.selectFileDtlList(fileDtlParam);

                //파일이 삭제 목록에 있는지 확인하고 없으면 새로운 파일 리스트에 추가
                if(deleteFileIds != null && !deleteFileIds.isEmpty()){
                    for(TbCmFileDtlVO originFileDetail : originFileDetails){
                        boolean isDeleted = false;
                        for(String deleteFileId : deleteFileIds){
                            if(originFileDetail.getFileId().equals(deleteFileId)){
                                isDeleted = true;
                                break;
                            }
                        }
                        if(!isDeleted){
                            String fileId = UUID.randomUUID().toString();

                            TbCmFileDtlVO fileDetail = new TbCmFileDtlVO();
                            fileDetail.setFileGroupId(fileGroupId);
                            fileDetail.setFileId(fileId);
                            fileDetail.setFilePathNm(fileGroupPath + File.separatorChar + fileId);
                            fileDetail.setStrgFileNm(fileId);
                            fileDetail.setOrgnlFileNm(originFileDetail.getOrgnlFileNm());
                            fileDetail.setFileExtnNm(Objects.requireNonNull(originFileDetail.getOrgnlFileNm()).substring(Objects.requireNonNull(originFileDetail.getOrgnlFileNm()).lastIndexOf(".") + 1));
                            fileDetail.setFileSz(originFileDetail.getFileSz());
                            fileDetail.setUseYn("Y");
                            fileDetail.setRegDt(LocalDateTime.now());
                            fileDetail.setRgtrId("SYSTEM");

                            newFileDetails.add(fileDetail);

                            File originFile = new File(originFileDetail.getFilePathNm());
                            File copyFile = new File(fileGroupPath + File.separatorChar + fileId);
                            FileUtils.copyFile(originFile, copyFile);
                        }
                    }

                    //파일 그룹 등록
                    TbCmFileGroupVO newFileGroup = new TbCmFileGroupVO();
                    newFileGroup.setFileGroupId(fileGroupId);
                    newFileGroup.setBfrFileGroupId(originFileGroup.getFileGroupId());
                    newFileGroup.setUseYn("Y");
                    newFileGroup.setRegDt(LocalDateTime.now());
                    newFileGroup.setRgtrId("SYSTEM");

                    fileMapper.insertFileGrp(newFileGroup);

                    //파일 디테일 목록 등록
                    for(TbCmFileDtlVO fileDetail : newFileDetails){
                        fileMapper.insertFileDtl(fileDetail);
                    }

                    //기존 파일 사용여부 업데이트
                    originFileGroup.setUseYn("N");
                    fileMapper.updateFileGrp(originFileGroup);
                }
            }

        }
    }

    private boolean makeFileGroupDir(String strDirPath, String filePath){
        File uploadPath = new File(strDirPath);
        boolean createdDir;
        if(!uploadPath.exists() || !uploadPath.isDirectory()){
            createdDir = uploadPath.mkdirs();
        } else {
            createdDir = true;
        }

        boolean created = false;
        if(createdDir){
            File objFile = new File(filePath);
            if(!objFile.exists() || !objFile.isDirectory()){
                created = objFile.mkdirs();
            }else {
                created = true;
            }
        }

        return created;
    }

}
