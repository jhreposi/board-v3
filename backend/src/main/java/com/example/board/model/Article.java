package com.example.board.model;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Article {
    private String name; //category name
    private int id;
    @DecimalMin(value = "1")
    private int categoryId;

    @NotEmpty
    private String title;

    @Size(min = 2, max = 10)
    private String author;

    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[!@#$%^&*])[A-Za-z\\d!@#$%^&*]{4,15}$")
    private String password;

    @Size(min = 3)
    private String content;
    private int viewCount;
    private String postDate;
    private String editDate;
    private String withFile;


}
