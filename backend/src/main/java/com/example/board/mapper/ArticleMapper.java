package com.example.board.mapper;

import com.example.board.model.Article;
import com.example.board.model.Comment;
import com.example.board.model.FileVo;
import com.example.board.model.Search;
import com.example.board.util.Paging;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface ArticleMapper {
    List<Article> selectArticleList(@Param("search") Search search, @Param("paging")Paging paging);
    int insertArticle(Article article);
    int insertFile(FileVo fileVo);
    Article selectArticleById(int articleId);
    List<Comment> selectComments(int articleId);
    List<FileVo> selectFiles(int articleId);
    FileVo selectFile(int fileId);
    int countArticle();
    int countArticleOption(Search search);
    int insertComment(Comment comment);
    Comment selectCommentById(int id);
    int articlePasswordMatch(Article article);
    int updateArticle(Article article);
    int deleteFile(int fileId);
    void updateViewCount(int articleId);
    void deleteComments(int articleId);
    void deleteFiles(int articleId);
    void deleteArticle(int articleId);

}
