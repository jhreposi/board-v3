package com.example.board.model;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class FileVo {
    private int id;
    private int articleId;
    private String uuidName;
    private String originalName;
    private String dir;

    @Builder
    public FileVo(int articleId, String uuidName, String originalName, String dir) {
        this.articleId = articleId;
        this.uuidName = uuidName;
        this.originalName = originalName;
        this.dir = dir;
    }
}
