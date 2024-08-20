package com.example.board.controller;

import com.example.board.model.Article;
import com.example.board.model.Search;
import com.example.board.service.ArticleService;
import com.example.board.service.FileService;
import com.example.board.util.Paging;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

    @GetMapping("/list")
    public ResponseEntity<ArticleListResponse> getArticles(Search search) {
        int articleCount = articleService.getArticleCountBySearch(search);
        Paging paging = articleService.getPaging(search.getPageNum(), articleCount);
        List<Article> articles = articleService.getArticleList(search, paging);

        return ResponseEntity.ok(new ArticleListResponse(articles, search, paging));
    }

}
