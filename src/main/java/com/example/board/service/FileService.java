package com.example.board.service;

import com.example.board.mapper.ArticleMapper;
import com.example.board.model.FileVo;
import com.example.board.util.StringUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
@Service
public class FileService {
    @Value("${spring.servlet.multipart.location}")
    String attaches;
    ArticleMapper articleMapper;

    public FileService(ArticleMapper articleMapper) {
        this.articleMapper = articleMapper;
    }

    public List<FileVo> uploadFiles(MultipartFile[] multipartFiles) throws IOException {
        MultipartFile[] requestFiles = multipartFiles;
        List<FileVo> files = new ArrayList<>();

        for (MultipartFile file : requestFiles) {
            if (StringUtil.isNullOrEmpty(file.getOriginalFilename())) {
                return null;
            }
            String uuidName = UUID.randomUUID() + "_" + file.getOriginalFilename();

            //파일 업로드
            File uploadFile = new File(attaches + uuidName);
            file.transferTo(uploadFile);

            //파일 정보 생성
            FileVo fileVo = FileVo.builder().dir(attaches)
                    .uuidName(uuidName)
                    .originalName(file.getOriginalFilename())
                    .build();

            //저장 리스트 추가
            files.add(fileVo);
        };
        return files;
    }

    public Resource downloadFile(FileVo fileInfo) {
        String filePath = fileInfo.getDir() + fileInfo.getUuidName();
        File downloadFile = new File(filePath);
        Resource resource = new FileSystemResource(downloadFile);

        return resource;
    }

    public FileVo getFileInfo(int fileId) {
        return articleMapper.selectFile(fileId);
    }

    public String fileNameEncoder(String fileName) throws UnsupportedEncodingException {
        return URLEncoder.encode(fileName, StandardCharsets.UTF_8.toString())
                .replaceAll("\\+", "%20");
    }
    //파일id로 게시글 맵핑 삭제
    public void removeFiles(int [] fileIds) {
        Arrays.stream(fileIds).forEach(fileId -> {
            articleMapper.deleteFile(fileId);
        });
    }
}
