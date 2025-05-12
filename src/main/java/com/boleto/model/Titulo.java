package com.boleto.model;

import java.math.BigDecimal;

public class Titulo {
    private String numeroDocumento;
    private String dataVencimento;
    private BigDecimal valor;

    public String getDataVencimento() {
        return dataVencimento;
    }

    public BigDecimal getValor() {
        return valor;
    }
}
