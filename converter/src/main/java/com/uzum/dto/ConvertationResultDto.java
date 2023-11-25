package com.uzum.dto;


import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ConvertationResultDto {
    private String from;
    private String to;
    private String result;
}
