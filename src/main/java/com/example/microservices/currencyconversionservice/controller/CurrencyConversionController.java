package com.example.microservices.currencyconversionservice.controller;

import com.example.microservices.currencyconversionservice.model.CurrencyConversionBean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@RestController
public class CurrencyConversionController {

    @GetMapping("/currency-converter/from/{from}/to/{to}/quantity/{quantity}")
    public CurrencyConversionBean convertCurrency(
            @PathVariable String from, @PathVariable String to, @PathVariable BigDecimal quantity){


        Map<String, String> urlVariables = new HashMap<>();
        urlVariables.put("from", from);
        urlVariables.put("to", to);

        ResponseEntity<CurrencyConversionBean> forEntity =
                new RestTemplate().getForEntity("http://localhost:8000/currency-exchange/from/{from}/to/{to}",
                CurrencyConversionBean.class,
                urlVariables);

        CurrencyConversionBean body = forEntity.getBody();

        return CurrencyConversionBean.builder()
                .id(body.getId())
                .from(from)
                .to(to)
                .quantity(quantity)
                .conversionMultiple(quantity.multiply(body.getConversionMultiple()))
                .totalCalculateAmount(quantity)
                .port(body.getPort())
                .build();
    }
}
