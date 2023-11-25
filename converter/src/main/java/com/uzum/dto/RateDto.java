package com.uzum.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class RateDto {
    private Integer id;
    @JsonProperty("Code")
    private String Code;
    @JsonProperty("Ccy")
    private String Ccy;
    @JsonProperty("Nominal")
    private String Nominal;
    @JsonProperty("Rate")
    private String Rate;
    @JsonProperty("Date")
    private String Date;
}
