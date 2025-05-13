package com.boleto.model;

public class Sacado {
    private String nome;
    private String cnpjCpf;
    private String Endereco;

    public Sacado(String nome, String cnpjCpf, String Endereco) {
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
