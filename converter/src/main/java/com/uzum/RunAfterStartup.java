package com.uzum;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.uzum.dto.RateDto;
import com.uzum.models.Account;
import com.uzum.models.Comission;
import com.uzum.repositories.AccountRepository;
import com.uzum.repositories.ComissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
@RequiredArgsConstructor
public class RunAfterStartup {

    private final ComissionRepository comissionRepository;
    private final AccountRepository accountRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @EventListener(ApplicationReadyEvent.class)
    public void runAfterStartup() throws IOException {
        updateRates();
    }

    public void updateRates() throws IOException {
        URL url;
        URLConnection conn;
        InputStream is;
        try {
            url = new URL("https://cbu.uz/ru/arkhiv-kursov-valyut/json/all/" + LocalDateTime.now().format(DateTimeFormatter.ISO_DATE));
            conn = url.openConnection();
            is = conn.getInputStream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        StringBuilder result = new StringBuilder();
        BufferedReader rd = new BufferedReader(new InputStreamReader(is));
        String line;
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }
        rd.close();
        List<RateDto> rates = objectMapper.readValue(result.toString(), new TypeReference<>() {});
        rates.forEach(rateDto -> {
            Comission comission = comissionRepository.getComissionByFromAndTo(rateDto.getCcy(), "UZS");
            if (comission == null) comission = Comission.builder()
                    .to("UZS").from(rateDto.getCcy()).commission(0).build();
            comission.setConversionRate(Double.parseDouble(rateDto.getRate()) / Double.parseDouble(rateDto.getNominal()));
            comissionRepository.save(comission);
            comission = comissionRepository.getComissionByFromAndTo("UZS", rateDto.getCcy());
            if (comission == null) comission = Comission.builder()
                    .from("UZS").to(rateDto.getCcy()).commission(0).build();
            comission.setConversionRate(Double.parseDouble(rateDto.getNominal()) / Double.parseDouble(rateDto.getRate()));
            comissionRepository.save(comission);

            Account account = accountRepository.getAccountByCurrencyName(rateDto.getCcy());
            if (account == null) {
                account = Account.builder().currencyName(rateDto.getCcy()).balance(0.0).build();
                accountRepository.save(account);
            }
        });
    }
}