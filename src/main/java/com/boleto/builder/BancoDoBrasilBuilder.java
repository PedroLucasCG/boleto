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
        String fatorVencimento = getFatorVencimento(boleto.getTitulo().getDataVencimento());
        String valor = formatValor(boleto.getTitulo().getValor());

        String nossoNumero = String.format("%010d", Long.parseLong(boleto.getBancoInfo().getNossoNumero()));
        String agencia = String.format("%04d", Integer.parseInt(boleto.getBancoInfo().getAgencia()));
        String conta = String.format("%08d", Integer.parseInt(boleto.getBancoInfo().getContaCorrente()));
        String carteira = String.format("%02d", Integer.parseInt(boleto.getBancoInfo().getCarteira()));

        String campoLivre = nossoNumero + agencia + conta + carteira;

        String parcial = codigoBanco + moeda + fatorVencimento + valor + campoLivre;
        String dv = mod11(parcial);

        return codigoBanco + moeda + dv + fatorVencimento + valor + campoLivre;
    }
}
