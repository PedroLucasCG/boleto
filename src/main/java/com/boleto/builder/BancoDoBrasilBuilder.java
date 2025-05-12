package com.boleto.builder;

import com.boleto.model.BancoInfo;

public class BancoDoBrasilBuilder extends BoletoBuilder{

    public BancoDoBrasilBuilder() {
        setBancoInfo(new BancoInfo("001", "Banco do Brasil S.A.", "18"));
    }

    @Override
    public String mod11(String numero) {
        int[] pesos = {2, 3, 4, 5, 6, 7, 8, 9};
        int sum = 0;
        int c = 0;
        for (int i = 0; i < numero.length(); i++) {
            sum += pesos[i] * Character.getNumericValue(numero.charAt(i));
            if(i == 9) c = 0;
            c++;
        }

        int valor = sum % 11;
        int resultado = 11 - valor;
        resultado = resultado == 10 ? 'X' : resultado == 11 ? '0' : resultado;

        return Integer.toString(resultado);
    }
}
