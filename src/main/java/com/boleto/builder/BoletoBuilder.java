package com.boleto.builder;
import com.boleto.model.*;

public class BoletoBuilder {
    private Boleto boleto;

    public void reset() {
        this.boleto = new Boleto();
    }

    public void setBeneficiario(Beneficiario beneficiario) {
        boleto.setBeneficiario(beneficiario);
    }

    public void setSacado(Sacado sacado) {
        boleto.setSacado(sacado);
    }

    public void setTitulo(Titulo titulo) {
        boleto.setTitulo(titulo);
    }

    public void setBancoInfo(BancoInfo info) {
        boleto.setBancoInfo(info);
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
            if(i == 9) c = 0;
            c++;
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
