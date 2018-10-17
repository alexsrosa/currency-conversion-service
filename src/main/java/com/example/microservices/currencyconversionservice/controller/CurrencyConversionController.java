package com.example.microservices.currencyconversionservice.controller;

import com.example.microservices.currencyconversionservice.model.CurrencyConversionBean;
import com.example.microservices.currencyconversionservice.proxy.CurrencyExchangeServiceProxy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
public class CurrencyConversionController {

    private CurrencyExchangeServiceProxy currencyExchangeServiceProxy;

    public CurrencyConversionController(CurrencyExchangeServiceProxy currencyExchangeServiceProxy) {
        this.currencyExchangeServiceProxy = currencyExchangeServiceProxy;
    }

    @GetMapping("/currency-converter/from/{from}/to/{to}/quantity/{quantity}")
    public CurrencyConversionBean convertCurrency(
            @PathVariable String from, @PathVariable String to, @PathVariable BigDecimal quantity){

        CurrencyConversionBean body = currencyExchangeServiceProxy.retrieveExchangeValue(from, to);

        return CurrencyConversionBean.builder()
                .id(body.getId())
                .from(from)
                .to(to)
                .quantity(quantity)
                .conversionMultiple(body.getConversionMultiple())
                .totalCalculateAmount(quantity.multiply(body.getConversionMultiple()))
                .port(body.getPort())
                .build();
    }
}
