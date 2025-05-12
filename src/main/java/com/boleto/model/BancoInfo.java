package com.boleto.model;

public class BancoInfo {
    private String codigoBanco;
    private String nomeBanco;
    private String carteira;
    private String contaCorrente;
    private String agencia;
    private String nossoNumero;
    private String convenioBB;
    private String dacItau;
    private String digitoConta;

    public BancoInfo(String codigoBanco, String nomeBanco, String carteira, String digitoConta) {
        this.codigoBanco = codigoBanco;
        this.nomeBanco = nomeBanco;
        this.carteira = carteira;
        this.digitoConta = digitoConta;
    }

    public String getCodigoBanco() {
        return codigoBanco;
    }

    public String getNomeBanco() {
        return nomeBanco;
    }

    public String getCarteira() {
        return carteira;
    }

    public String getContaCorrente() {
        return contaCorrente;
    }

    public String getDigitoConta(){
        return digitoConta;
    }

    public void setContaCorrente(String contaCorrente) {
        this.contaCorrente = contaCorrente;
    }

    public String getAgencia() {
        return agencia;
    }

    public void setAgencia(String agencia) {
        this.agencia = agencia;
    }

    public String getNossoNumero() {
        return nossoNumero;
    }

    public void setNossoNumero(String nossoNumero) {
        this.nossoNumero = nossoNumero;
    }

    public String getConvenioBB() {
        return convenioBB;
    }

    public void setConvenioBB(String convenioBB) {
        this.convenioBB = convenioBB;
    }

    public String getDacItau() {
        return dacItau;
    }

    public void setDacItau(String dacItau) {
        this.dacItau = dacItau;
    }
}
