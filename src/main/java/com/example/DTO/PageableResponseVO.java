package com.example.DTO;


import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
@NoArgsConstructor
public class PageableResponseVO {
    private Object objects;
    private Integer currentPage;
    private Integer pageSize;
    private Long totalCount;
    private Integer totalPages;


    public PageableResponseVO(List<?> list, Page<?> page, PageableDTO customPageable) {
        this.objects = list;
        this.currentPage = customPageable.getPage();
        this.pageSize = customPageable.getPageSize();
        this.totalCount = page.getTotalElements();
        this.totalPages = page.getTotalPages();
    }
}