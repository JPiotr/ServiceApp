package com.oblitus.serviceApp.Common;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.data.domain.Page;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PageInfo {
    public int pageNumber;
    public int pageSize;
    public boolean sorted;
    public boolean last;
    public long totalElements;
    public int totalPages;

    public PageInfo(Page<?> page) {
        pageNumber = page.getNumber()+1;
        pageSize = page.getSize();
        sorted = page.getSort().isSorted();
        totalElements = page.getTotalElements();
        totalPages = page.getTotalPages();
        last = page.isLast();
    }
}
