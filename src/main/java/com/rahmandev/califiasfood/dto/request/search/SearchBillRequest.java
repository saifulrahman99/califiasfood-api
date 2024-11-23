package com.rahmandev.califiasfood.dto.request.search;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SearchBillRequest {
    private String q;
    private String startDate;
    private String endDate;
    private Integer page;
    private Integer size;
    private String sortBy;
    private String direction;
}
