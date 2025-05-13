package com.boleto.builder;

import com.boleto.model.BancoInfo;

public class ItauBuilder extends BoletoBuilder{
    private Boleto boleto;
    public ItauBuilder(Boleto boleto, String digitoConta) {
        boleto.setBancoInfo(new BancoInfo("341", "Banco Itaú Unibanco S.A.", "109", digitoConta));
        this.boleto = boleto;
    }

    @Override
    public String getCodigoBarras() {
        String codigoBanco = boleto.getBancoInfo().getCodigoBanco();
        char moeda = super.moeda;
        String vencimento = boleto.getTitulo().getDataVencimento();
        String valor = formatValor(boleto.getTitulo().getValor());

        String campoLivre = boleto.getBancoInfo().getConvenioBB()
                          + boleto.getBancoInfo().getNossoNumero()
                          + boleto.getBancoInfo().getAgencia()
                          + boleto.getBancoInfo().getContaCorrente()
                          + boleto.getBancoInfo().getCarteira();
        campoLivre = String.format("%-25s", campoLivre).replace(' ', '0');

        String parcial = codigoBanco + moeda + vencimento + valor + campoLivre;
        String dv = mod11(parcial);
        return codigoBanco + moeda + dv + vencimento + valor + campoLivre;
    }

    @Override
    public String getLinhaDigitavel() {
        String bc = boleto.getCodigoBarras();

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
}
