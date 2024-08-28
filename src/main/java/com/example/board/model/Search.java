package com.example.board.model;

import com.example.board.util.StringUtil;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@ToString
public class Search {
    private int pageNum;
    private String startDate;
    private String endDate;
    private String category;
    private String keyword;


    public Search(int pageNum, String startDate, String endDate, String category, String keyword) {
        this.pageNum = pageNum;
        this.startDate = startDate;
        this.endDate = endDate;
        this.category = category;
        this.keyword = keyword;
    }


    public Search() {
        this.pageNum = 1;
        this.startDate = "";
        this.endDate = "";
        this.category = "";
        this.keyword = "";
    }


    public void defaultSearchValue() {
        this.startDate = defaultStartDate(this.startDate);
        this.endDate = defaultEndDate(this.endDate);
        this.pageNum = defaultPageNum(this.pageNum);
        if (!StringUtil.isNullOrEmpty(this.keyword)) {
            this.keyword.trim();
        }
    }

    private String defaultStartDate(String startDate) {
        if (StringUtil.isNullOrEmpty(startDate)) {
            return LocalDate.now().minusYears(1).toString();
        }
        return startDate;
    }
    private String defaultEndDate(String endDate) {
        if (StringUtil.isNullOrEmpty(endDate)) {
            //yyyy-MM-dd 형식이라 day +1 안하면 오늘날자 미포함됨
            return LocalDate.now().plusDays(1).toString();
        }
        return endDate;
    }
    private int defaultPageNum(int pageNum) {
        if (pageNum == 0) {
            return 1;
        } else {
            return pageNum;
        }
    }
}