package com.example.board.service;

import com.example.board.mapper.ArticleMapper;
import com.example.board.model.*;
import com.example.board.util.Paging;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleService {
    ArticleMapper articleMapper;

    public ArticleService(ArticleMapper articleMapper) {
        this.articleMapper = articleMapper;
    }

    //검색 조건이 들어간 총 게시글 수
    public int getArticleCountBySearch(Search search) {
        return articleMapper.countArticleOption(search);
    }

    //페이징 빌드  getPaging 네이밍
    public Paging getCriteria(int currentPage, int totalCount) {

        Paging paging = Paging.builder()
                .currentPage(currentPage)
                .totalCount(totalCount)
                .build();
        return paging;
    }

    //검색조건과 페이징으로 원하는 구간 게시글 정보
    public List<Article> getArticleList(Search search, Paging paging) {
        return articleMapper.selectArticleList(search, paging);
    }

    //해당 게시글 정보
    public Article getArticleOne(int articleId) {
        return articleMapper.selectArticleById(articleId);

    }

    //해당 게시글 댓글 정보
    public List<Comment> getArticleComment(int articleId) {
        return articleMapper.selectComments(articleId);
    }

    //해당 게시글 파일 정보
    public List<FileVo> getArticleFiles(int articleId) {
        return articleMapper.selectFiles(articleId);
    }
    /**
     * INSERT 구간
     */

    //게시글 생성 후 id 받기
    public int createArticle(Article article) {
        articleMapper.insertArticle(article);
        return article.getId();
    }

    //해당 게시글 댓글 생성
    public int createComment(Comment comment) {
        articleMapper.insertComment(comment);
        return comment.getId();
    }

    //게시글 파일 정보 생성
    public void createFileList(List<FileVo> files, int articleId) {
        if (files != null) {
            files.forEach(fileVo -> {
                fileVo.setArticleId(articleId);
                articleMapper.insertFile(fileVo);
            });
        }
    }

    //사용자 입력 비밀번호와 게시글 비밀번호 일치 확인
    public boolean passwordMatchConfirm(Article article) {
        int matchCount =  articleMapper.articlePasswordMatch(article);
        return matchCount != 0;
    }

    //수정 요청 게시글
    public void modifyArticle(ModifyArticle modifyArticle) {
        Article article = Article.builder()
                .id(modifyArticle.getId())
                .title(modifyArticle.getTitle())
                .author(modifyArticle.getAuthor())
                .content(modifyArticle.getContent())
                .categoryId(modifyArticle.getCategoryId())
                .build();

        articleMapper.updateArticle(article);
    }

    public Comment getCommentById(int id) {
        return articleMapper.selectCommentById(id);
    }

}
