package com.uzum.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.uzum.dto.ComissionDto;
import com.uzum.dto.ConvertationDto;
import com.uzum.dto.ConvertationResultDto;
import com.uzum.dto.RateDto;
import com.uzum.exceptions.MoneyRanOutException;
import com.uzum.exceptions.NotFoundException;
import com.uzum.exceptions.SecretKeyNotEqualsException;
import com.uzum.mappers.ComissionMapper;
import com.uzum.models.Account;
import com.uzum.models.Comission;
import com.uzum.repositories.AccountRepository;
import com.uzum.repositories.AppConfigRepository;
import com.uzum.repositories.ComissionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class AppService {

    private final AccountRepository accountRepository;
    private final ComissionRepository comissionRepository;
    private final AppConfigRepository appConfigRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public ConvertationResultDto getCalculation(String from, String to, String amount) {
        Double result;
        if (from.equals("UZS") || to.equals("UZS")) {
            Comission comission = comissionRepository.getComissionByFromAndTo(from, to);
            if (comission == null) throw new NotFoundException("Указанная валютная пара не найдена!");
            result = calculate(comission, Double.parseDouble(amount));
        } else {
            Comission comission = comissionRepository.getComissionByFromAndTo(from, "UZS");
            if (comission == null) throw new NotFoundException("Указанная валютная пара не найдена!");
            result = calculate(comission, Double.parseDouble(amount));
            comission = comissionRepository.getComissionByFromAndTo("UZS", to);
            if (comission == null) throw new NotFoundException("Указанная валютная пара не найдена!");
            result = calculate(comission, result);
        }

        return ConvertationResultDto.builder().from(from).to(to).result(String.format("%.6f", result)).build();
    }

    public Double calculate(Comission commission, Double amount) {
        return (amount * commission.getConversionRate()) * (100 - commission.getCommission()) * 0.01;
    }

    public ConvertationResultDto getConvertation(ConvertationDto dto) {
        Double result;
        if (dto.getFrom().equals("UZS") || dto.getTo().equals("UZS")) {
            Comission comission = comissionRepository.getComissionByFromAndTo(dto.getFrom(), dto.getTo());
            if (comission == null) throw new NotFoundException("Указанная валютная пара не найдена!");
            result = calculate(comission, dto.getAmount());
        } else {
            Comission comission = comissionRepository.getComissionByFromAndTo(dto.getFrom(), "UZS");
            if (comission == null) throw new NotFoundException("Указанная валютная пара не найдена!");
            result = calculate(comission, dto.getAmount());
            comission = comissionRepository.getComissionByFromAndTo("UZS", dto.getTo());
            if (comission == null) throw new NotFoundException("Указанная валютная пара не найдена!");
            result = calculate(comission, result);
            Account account = accountRepository.getAccountByCurrencyName(dto.getTo());
            if (result > account.getBalance()) {
                throw new MoneyRanOutException("Имеющихся средств недостаточно для выполнения указанной операции");
            } else {
                account.setBalance(account.getBalance() - result);
                accountRepository.save(account);
                Account accountFrom = accountRepository.getAccountByCurrencyName(dto.getFrom());
                accountFrom.setBalance(accountFrom.getBalance() + dto.getAmount());
            }
        }
        return ConvertationResultDto.builder().from(dto.getFrom()).to(dto.getTo()).result(String.format("%.6f", result)).build();
    }

    public ComissionDto setComissions(String secretKey, ComissionDto dto) {
        if (!secretKey.equals(appConfigRepository.getSecretKey()))
            throw new SecretKeyNotEqualsException("Секретный ключ не совпал!");
        Comission comission = comissionRepository.getComissionByFromAndTo(dto.getFrom(), dto.getTo());
        if (comission == null)
            throw new NotFoundException("Указанная валютная пара не найдена!");
        comission.setCommission(dto.getCommission());
        return ComissionMapper.toDto(comissionRepository.save(comission));
    }

    public RateDto getRate(String date, String pair) throws IOException {
        String from = pair.split("/")[0];
        String to = pair.split("/")[1];

        if (!from.equals("UZS")) throw new NotFoundException("Указанная валютная пара не найдена");

        URL url;
        URLConnection conn;
        InputStream is;
        try {
            url = new URL("https://cbu.uz/ru/arkhiv-kursov-valyut/json/all/" + date);
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
        List<RateDto> rates = objectMapper.readValue(result.toString(), new TypeReference<>() {
        });
        return rates.stream().filter(rateDto -> rateDto.getCcy().equals(to))
                .findAny().orElseThrow(() -> new NotFoundException("Указанная валютная пара не найдена"));
    }
}
