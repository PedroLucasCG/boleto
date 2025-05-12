package com.boleto.builder;
import com.boleto.model.*;

public class BoletoBuilder {
    private Boleto boleto;
    protected char moeda = '9';
    private BoletoBuilder builder;

    public BoletoBuilder() {
        this.boleto = new Boleto();
        switch (this.boleto.getBancoInfo().getCodigoBanco()){
            case "001":
                this.builder = new BancoDoBrasilBuilder("8");
                break;

            case "341":
                this.builder = new ItauBuilder("7");
                break;

            case "237":
                this.builder = new BradescoBuilder("5");
                break;
        }
    }

    public void reset() {
        this.boleto = new Boleto();
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
        return this.boleto.getBancoInfo();
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

    public void buildLinhaDigitavel() {};
    public void buildCodigoBarras() {};

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
        int[] pesos = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        int sum = 0;
        int c = 0;
        for (int i = 0; i < numero.length(); i++) {
            sum += pesos[i] * Character.getNumericValue(numero.charAt(i));
            c++;
            if(i >= 9) c = 0;
        }

        int valor = sum % 11;
        int resultado = 11 - valor;
        resultado = resultado == 0 || resultado > 9 ? 1 : resultado;

        return Integer.toString(resultado);
    }

    public Boleto getBoleto() {
        return boleto;
    };
}
