package com.boleto.model;

import java.math.BigDecimal;

public class Titulo {
    private String numeroDocumento;
    private String dataVencimento;
    private BigDecimal valor;

    public Titulo(String numeroDocumento, String dataVencimento, BigDecimal valor) {
        this.numeroDocumento = numeroDocumento;
        this.dataVencimento = dataVencimento;
        this.valor = valor;
    }

    public String getNumeroDocumento() {
        return numeroDocumento;
    }

    public String getDataVencimento() {
        return dataVencimento;
    }

    public BigDecimal getValor() {
        return valor;
    }
}
