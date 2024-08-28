package com.example.board.controller;

import com.example.board.model.*;
import com.example.board.service.ArticleService;
import com.example.board.service.FileService;
import com.example.board.util.Paging;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

@Slf4j
@RestController
@CrossOrigin("http://localhost:3000/")
@RequestMapping("/api/board")
public class ArticleController {
    ArticleService articleService;
    FileService fileService;

    public ArticleController(ArticleService articleService, FileService fileService) {
        this.articleService = articleService;
        this.fileService = fileService;
    }
    public record ArticleListResponse(List<Article> articles, Search search, Paging paging) {}
    public record ArticleOneResponse(Article article, List<Comment> comments, List<FileVo> files) {}

    //게시글 리스트
    @GetMapping("/list")
    public ResponseEntity<ArticleListResponse> getArticles(@ModelAttribute Search search) {
        search.defaultSearchValue();
        log.info("search => {}", search.toString());

        int articleCount = articleService.getArticleCountBySearch(search);
        Paging pagingCriteria = articleService.getCriteria(search.getPageNum(), articleCount);
        List<Article> articles = articleService.getArticleList(search, pagingCriteria);

        return ResponseEntity.ok(new ArticleListResponse(articles, search, pagingCriteria));
    }

    //게시글 단일 글  rest docs
    @GetMapping("/view/{id}")
    public ResponseEntity<ArticleOneResponse> getArticleOne(
            @PathVariable("id") int articleId) {

        Article article = articleService.getArticleOne(articleId);
        List<Comment> comments = articleService.getArticleComment(articleId);
        List<FileVo> files = articleService.getArticleFiles(articleId);
        articleService.modifyViewCount(articleId);

        return ResponseEntity.ok(new ArticleOneResponse(article, comments, files));
    }

    //댓글 생성
    @PostMapping("/comment")
    public ResponseEntity<Comment> createComment(@RequestBody Comment comment) {
        int createdCommentId = articleService.createComment(comment);
        Comment getComment = articleService.getCommentById(createdCommentId);

        return ResponseEntity.ok(getComment);
    }

    //게시글 생성
    @PostMapping("/write")
    public ResponseEntity<Integer> createArticle(@Valid @ModelAttribute Article article,
                              @RequestPart("files") MultipartFile[] multipartFiles) throws IOException {

        int createdArticleId = articleService.createArticle(article);
        List<FileVo> uploadedFileInfo = fileService.uploadFiles(multipartFiles);
        articleService.createFileList(uploadedFileInfo, createdArticleId);

        return ResponseEntity.ok(createdArticleId);
    }

    //파일 다운로드
    @GetMapping("/file-download")
    public ResponseEntity<Resource> fileDownload(@RequestParam("fileId") int fileId) throws UnsupportedEncodingException {

        FileVo fileInfo = fileService.getFileInfo(fileId);
        log.info("fileInfo {}", fileInfo.toString());

        String fileName = fileService.fileNameEncoder(fileInfo.getOriginalName());
        Resource resource = fileService.downloadFile(fileInfo);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, "application/octet-stream");
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"");
        headers.add(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, "Content-Disposition");

        log.info("filename => {}", fileName);
        log.info("headers => {}", headers.get(HttpHeaders.CONTENT_DISPOSITION));

        return new ResponseEntity<>(resource, headers, HttpStatus.OK);
    }

    /**
     * 게시글 수정
     * 비밀번호 정보 안맞을시 에러반환
     * 게시글 정보 수정, 파일관계 제거
     * @param modifyArticle
     * @return
     */
    @PutMapping("/modify")
    public ResponseEntity<String> modifyArticle(@RequestBody ModifyArticle modifyArticle) {
        log.info("article Info => {}", modifyArticle);
        Article passwordInfo = Article.builder()
                                .id(modifyArticle.getId())
                                .password(modifyArticle.getPassword())
                                .build();

        articleService.passwordMatchConfirm(passwordInfo);
        articleService.modifyArticle(modifyArticle);
        fileService.removeFiles(modifyArticle.getRemoveFiles());

        return ResponseEntity.ok("게시글이 수정되었습니다");
    }

    @DeleteMapping("/")
    public ResponseEntity<?> removeArticle(@RequestBody Article article) {
        articleService.passwordMatchConfirm(article);
        articleService.removeComments(article);
        articleService.removeFiles(article);
        articleService.removeArticle(article);

        return ResponseEntity.ok("게시글이 삭제되었습니다");
    }

}
