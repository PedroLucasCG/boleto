package com.boleto.builder;
import com.boleto.model.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class BoletoBuilder {
    private Boleto boleto;
    private Boleto boletoReset;
    protected char moeda = '9';
    private BoletoBuilder builder;

    public BoletoBuilder() {}
    public BoletoBuilder(String nome,
                         String cnpjCpf,
                         String Endereco,
                         String numeroDocumento,
                         String dataVencimento,
                         BigDecimal valor,
                         String digitoConta,
                         String codigoBanco) {

        this.boleto = new Boleto(nome,
                cnpjCpf,
                Endereco,
                numeroDocumento,
                dataVencimento,
                valor);

        switch (codigoBanco){
            case "001":
                this.builder = new BancoDoBrasilBuilder(this.boleto, digitoConta);
                break;

            case "341":
                this.builder = new ItauBuilder(this.boleto, digitoConta);
                break;

            case "237":
                this.builder = new BradescoBuilder(this.boleto, digitoConta);
                break;
        }

        this.boletoReset = this.boleto;
    }

    public void reset() {
        this.boleto = this.boletoReset;
    }

    public void buildBeneficiario(Beneficiario beneficiario) {
        boleto.setBeneficiario(beneficiario);
    }

    public void buildSacado(Sacado sacado) {
        boleto.setSacado(sacado);
    }

    public void buildTitulo(Titulo titulo) {
        boleto.setTitulo(titulo);
    }

    public Titulo getTitulo() {
        return boleto.getTitulo();
    }

    public void setBancoInfo(BancoInfo info) {
        boleto.setBancoInfo(info);
    }

    public BancoInfo getBancoInfo() {
        return boleto.getBancoInfo();
    }

    public void buildContaCorrente(String contaCorrente) {
        boleto.getBancoInfo().setContaCorrente(contaCorrente);
    }

    public void buildAgencia(String agencia) {
        boleto.getBancoInfo().setAgencia(agencia);
    }

    public void buildNossoNumero(String nossoNumero) {
        boleto.getBancoInfo().setNossoNumero(nossoNumero);
    }

    public void buildConvenioBB(String convenioBB) {
        boleto.getBancoInfo().setConvenioBB(convenioBB);
    }

    public void buildDacItau(String dacItau) {
        boleto.getBancoInfo().setDacItau(dacItau);
    }

    public void buildLinhaDigitavel() {
        boleto.setLinhaDigitavel(this.builder.getLinhaDigitavel());
    };
    public void buildCodigoBarras() {
        boleto.setCodigoBarras(this.builder.getCodigoBarras());
    };

    public String getCodigoBarras(){ return "";}
    public String getLinhaDigitavel(){ return "";}

    public String mod10(String numero) {
        int soma = 0;
        int peso = 2;
        for(int c = 0; c < numero.length(); c++) {
            char caracter = numero.charAt(c);
            int digito = Character.getNumericValue(caracter);
            int produto = digito * peso;
            soma += produto >= 10 ? ((produto / 10) + (produto % 10)) : produto;

            peso = peso == 2 ? 1 : 2;
        }
        int resultado = 10 - (soma % 10);
        return Integer.toString(resultado);
    }

    public String mod11(String numero) {
        int soma = 0;
        int peso = 2;

        for (int i = numero.length() - 1; i >= 0; i--) {
            int digito = Character.getNumericValue(numero.charAt(i));
            soma += digito * peso;
            peso++;
            if (peso > 9) peso = 2;
        }

        int resto = soma % 11;
        int dv;

        if (resto == 0 || resto == 1 || resto == 10) {
            dv = 1;
        } else {
            dv = 11 - resto;
        }

        return Integer.toString(dv);
    }

    protected String getFatorVencimento(String dataVencimento) {
        LocalDate base = LocalDate.of(1997, 10, 7);
        LocalDate vencimento = LocalDate.parse(dataVencimento, DateTimeFormatter.ofPattern("yyyyMMdd"));
        long dias = ChronoUnit.DAYS.between(base, vencimento);
        return String.format("%04d", dias);
    }

    protected String formatValor(BigDecimal valor) {
        BigDecimal centavos = valor.multiply(BigDecimal.valueOf(100));
        return String.format("%010d", centavos.longValue());
    }

    public Boleto getBoleto() {
        return boleto;
    };
}
