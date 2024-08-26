package com.example.board.controller;

import com.example.board.model.Article;
import com.example.board.model.Comment;
import com.example.board.model.FileVo;
import com.example.board.model.Search;
import com.example.board.service.ArticleService;
import com.example.board.service.FileService;
import com.example.board.util.Paging;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
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

    @GetMapping("/list")
    public ResponseEntity<ArticleListResponse> getArticles(@ModelAttribute Search search) {
        search.defaultSearchValue();
        log.info(search.toString());
        int articleCount = articleService.getArticleCountBySearch(search);
        Paging paging = articleService.getPaging(search.getPageNum(), articleCount);
        List<Article> articles = articleService.getArticleList(search, paging);

        return ResponseEntity.ok(new ArticleListResponse(articles, search, paging));
    }

    @GetMapping("/view/{id}")
    public ResponseEntity<ArticleOneResponse> getArticleOne(
            @PathVariable("id") int articleId) {
        Article article = articleService.getArticleOne(articleId);
        List<Comment> comments = articleService.getArticleComment(articleId);
        List<FileVo> files = articleService.getArticleFiles(articleId);

        return ResponseEntity.ok(new ArticleOneResponse(article, comments, files));
    }

    @PostMapping("/comment")
    public ResponseEntity<Comment> createComment(@RequestBody Comment comment) {
        int createdCommentId = articleService.createComment(comment);
        Comment getComment = articleService.getCommentById(createdCommentId);

        return ResponseEntity.ok(getComment);
    }

    @PostMapping("/write")
    public ResponseEntity<Integer> createArticle(@Valid @ModelAttribute Article article,
                              @RequestPart("files") MultipartFile[] multipartFiles) throws IOException {
        int createdArticleId = articleService.createArticle(article);
        List<FileVo> uploadedFileInfo = fileService.uploadFiles(multipartFiles);
        articleService.createFileList(uploadedFileInfo, createdArticleId);

        return ResponseEntity.ok(createdArticleId);
    }

}
