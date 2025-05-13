package com.boleto.builder;

import com.boleto.model.*;

import java.math.BigDecimal;

public class Boleto {
    private Beneficiario beneficiario;
    private Sacado sacado;
    private Titulo titulo;
    private BancoInfo bancoInfo;
    private String linhaDigitavel;
    private String codigoBarras;

    public Boleto(String nome,
                  String cnpjCpf,
                  String Endereco,
                  String numeroDocumento,
                  String dataVencimento,
                  BigDecimal valor) {

        this.beneficiario = new Beneficiario(nome, cnpjCpf, Endereco);
        this.sacado = new Sacado(nome, cnpjCpf, Endereco);
        this.titulo = new Titulo(numeroDocumento, dataVencimento, valor);
    }

    public void printBoleto() {}

    public Beneficiario getBeneficiario() {
        return beneficiario;
    }

    public void setBeneficiario(Beneficiario beneficiario) {
        this.beneficiario = beneficiario;
    }

    public Sacado getSacado() {
        return sacado;
    }

    public void setSacado(Sacado sacado) {
        this.sacado = sacado;
    }

    public Titulo getTitulo() {
        return titulo;
    }

    public void setTitulo(Titulo titulo) {
        this.titulo = titulo;
    }

    public BancoInfo getBancoInfo() {
        return bancoInfo;
    }

    public void setBancoInfo(BancoInfo bancoInfo) {
        this.bancoInfo = bancoInfo;
    }

    public String getLinhaDigitavel() {
        return linhaDigitavel;
    }

    public void setLinhaDigitavel(String linhaDigitavel) {
        this.linhaDigitavel = linhaDigitavel;
    }

    public String getCodigoBarras() {
        return codigoBarras;
    }

    public void setCodigoBarras(String codigoBarras) {
        this.codigoBarras = codigoBarras;
    }
}
