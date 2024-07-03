package com.finance.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class FilterDTO {
    private String column;
    private String operator;
    private List<String> operands;
}
