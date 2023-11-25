package com.uzum.dto;


import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ConvertationDto {
    private String from;
    private String to;
    private Double amount;
}
