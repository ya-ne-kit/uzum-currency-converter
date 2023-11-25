package com.uzum.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ComissionDto {
    private String from;
    private String to;
    private Integer commission;
}
