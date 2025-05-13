package com.boleto.model;

public class Beneficiario {
    private String nome;
    private String cnpjCpf;
    private String Endereco;

    public Beneficiario(String nome, String cnpjCpf, String Endereco) {
        this.nome = nome;
        this.cnpjCpf = cnpjCpf;
        this.Endereco = Endereco;
    }

    public String getNome() {
        return nome;
    }
    public String getCnpjCpf() {
        return cnpjCpf;
    }
    public String getEndereco() {
        return Endereco;
    }
}
