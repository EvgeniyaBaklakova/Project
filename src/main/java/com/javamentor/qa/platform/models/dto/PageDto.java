package com.javamentor.qa.platform.models.dto;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Data
@Getter
@Setter
@AllArgsConstructor
public class PageDto<T> implements Serializable {
    private Long currentPageNumber;
    private Long totalPageCount;
    private Long totalResultCount;
    private List<T> items;
    private Long itemsOnPage;


    public PageDto() {

    }
}
