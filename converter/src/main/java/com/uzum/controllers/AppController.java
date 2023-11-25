package com.uzum.controllers;

import com.uzum.dto.ComissionDto;
import com.uzum.dto.ConvertationDto;
import com.uzum.dto.ConvertationResultDto;
import com.uzum.dto.RateDto;
import com.uzum.services.AppService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;


@RequiredArgsConstructor
@RestController
@RequestMapping
public class AppController {

    private final AppService appService;

    @GetMapping("/convert")
    public ConvertationResultDto getCalculation(@RequestParam String from, @RequestParam String to, @RequestParam String amount) {
        return appService.getCalculation(from, to, amount);
    }

    @PostMapping("/convert")
    public ConvertationResultDto getConvertation(@RequestBody ConvertationDto dto) {
        return appService.getConvertation(dto);
    }

    @GetMapping("/officialrates")
    public RateDto getRates(@RequestParam String date, @RequestParam String pair) throws IOException {
        return appService.getRate(date, pair);
    }

    @PostMapping("/setcomission")
    public ComissionDto setComissions(@RequestHeader("X-Secret-Key") String secretKey, @RequestBody ComissionDto dto) {
        return appService.setComissions(secretKey, dto);
    }
}
