package com.boleto.model;

public class BancoInfo {
    private String codigoBanco;
    private String nomeBanco;
    private String contaCorrente;
    private String carteira;
    private String agencia;
    private String nossoNumero;
    private String convenioBB;
    private String dacItau;

    public BancoInfo(String codigoBanco, String nomeBanco, String carteira) {
        this.codigoBanco = codigoBanco;
        this.nomeBanco = nomeBanco;
        this.carteira = carteira;
    }
}
