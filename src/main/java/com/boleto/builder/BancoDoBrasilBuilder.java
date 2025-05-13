package com.boleto.builder;

import com.boleto.model.BancoInfo;

public class BancoDoBrasilBuilder extends BoletoBuilder{
    Boleto boleto;
    public BancoDoBrasilBuilder(Boleto boleto, String digitoConta) {
        boleto.setBancoInfo(new BancoInfo("001", "Banco do Brasil S.A.", "18", digitoConta));
        this.boleto = boleto;
    }

    @Override
    public String mod11(String numero) {
        int[] pesos = {2, 3, 4, 5, 6, 7, 8, 9};
        int sum = 0;
        int c = 0;
        for (int i = 0; i < numero.length(); i++) {
            sum += pesos[c] * Character.getNumericValue(numero.charAt(i));
            c++;
            if(i >= 7) c = 0;
        }

        int valor = sum % 11;
        int resultado = 11 - valor;
        resultado = resultado == 10 ? 'X' : resultado == 11 ? '0' : resultado;

        return Integer.toString(resultado);
    }

    @Override
    public String getLinhaDigitavel() {
        String bc = this.boleto.getCodigoBarras();

        String campo1 = bc.substring(0, 4) + bc.substring(19, 24);
        campo1 += mod10(campo1);
        String campo2 = bc.substring(24, 34);
        campo2 += mod10(campo2);
        String campo3 = bc.substring(34, 44);
        campo3 += mod10(campo3);
        String campo4 = bc.substring(4, 5);
        String campo5 = bc.substring(5, 9) + bc.substring(9, 19);

        String linha = String.format("%s.%s %s.%s %s.%s %s %s",
                campo1.substring(0,5), campo1.substring(5),
                campo2.substring(0,5), campo2.substring(5),
                campo3.substring(0,5), campo3.substring(5),
                campo4, campo5
        );

        return linha;
    }

    @Override
    public String getCodigoBarras() {
        String codigoBanco = boleto.getBancoInfo().getCodigoBanco();
        char moeda = super.moeda;
        String vencimento = boleto.getTitulo().getDataVencimento();
        String valor = formatValor(boleto.getTitulo().getValor());

        String campoLivre = boleto.getBancoInfo().getCarteira()
                          + boleto.getBancoInfo().getNossoNumero()
                          + boleto.getBancoInfo().getDacItau()
                          + boleto.getBancoInfo().getAgencia()
                          + boleto.getBancoInfo().getContaCorrente()
                          + "0000";
        String parcial = codigoBanco + moeda + vencimento + valor + campoLivre;
        String dv = mod11(parcial);
        return codigoBanco + moeda + dv + vencimento + valor + campoLivre;
    }
}
