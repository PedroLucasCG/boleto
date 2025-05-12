package com.boleto.builder;

public class Director {
    private BoletoBuilder boletoBuilder;
    public Director(BoletoBuilder boletoBuilder) {
        this.boletoBuilder = boletoBuilder;
    }

    public Boleto constructSimpleBoleto(Boleto boleto) {
        return boleto;
    }
}
