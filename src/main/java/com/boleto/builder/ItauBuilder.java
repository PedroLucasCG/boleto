package com.boleto.builder;

import com.boleto.model.BancoInfo;

public class ItauBuilder extends BoletoBuilder{

    public ItauBuilder(String digitoConta) {
        setBancoInfo(new BancoInfo("341", "Banco Itaú Unibanco S.A.", "109", digitoConta));
    }

    public void buildCodigoBarras() {
        String codigoBanco = getBancoInfo().getCodigoBanco();
        char moeda = super.moeda;
        String vencimento = getTitulo().getDataVencimento();
        String valor = getTitulo().getValor().toString();

        String campoLivre = getBancoInfo().getConvenioBB()
                          + getBancoInfo().getNossoNumero()
                          + getBancoInfo().getAgencia()
                          + getBancoInfo().getContaCorrente()
                          + getBancoInfo().getCarteira();
        campoLivre = String.format("%-25s", campoLivre).replace(' ', '0');

        String parcial = codigoBanco + moeda + vencimento + valor + campoLivre;
        String dv = mod11(parcial);
        getBoleto().setLinhaDigitavel(codigoBanco + moeda + dv + vencimento + valor + campoLivre);
    }

    public void buildLinhaDigitavel() {
        String bc = getBoleto().getCodigoBarras();

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

        getBoleto().setLinhaDigitavel(linha);
    }
}
