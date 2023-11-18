package com.oblitus.serviceApp.Common;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class PageSortUtil {
    public static Pageable pageable;
    public static Sort sort;

    public static void preparePaginationAndSorting(String sortField, Boolean desc, Integer size, Integer page){
        sort = Sort.unsorted();
        if(sortField != null){
            sort = Sort.by(sortField).ascending();
            if(desc != null){
                if(desc){
                    sort = Sort.by(sortField).descending();
                }
            }
        }

        pageable = Pageable.unpaged();
        if(size != null){
            pageable = PageRequest.ofSize(size);
            if(page != null){
                pageable = PageRequest.of(page-1,size);
                if(sort.isSorted()){
                    pageable = PageRequest.of(page-1,size,sort);
                }
            }
        }
    }
}
