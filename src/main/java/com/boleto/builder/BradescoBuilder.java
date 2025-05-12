package com.boleto.builder;

import com.boleto.model.BancoInfo;

public class BradescoBuilder extends BoletoBuilder {

    public BradescoBuilder(String digitoConta) {
        setBancoInfo(new BancoInfo("237", "Banco Bradesco S.A.", "06", digitoConta));
    }
    @Override
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

        String linha =
                String.format("%s.%s %s.%s %s.%s %s %s",
                        campo1.substring(0, 5), campo1.substring(5),
                        campo2.substring(0, 5), campo2.substring(5),
                        campo3.substring(0, 5), campo3.substring(5),
                        campo4,
                        campo5
                );

        getBoleto().setLinhaDigitavel(linha);
    };
    @Override
    public void buildCodigoBarras() {
        String codigoBanco = getBancoInfo().getCodigoBanco();
        char moeda = super.moeda;
        String vencimento = getTitulo().getDataVencimento();
        String valor = getTitulo().getValor().toString();
        String campoLivre = getCampoLivre();

        String parcial = codigoBanco + moeda + vencimento + valor + campoLivre;

        String dv = mod11(parcial);

        getBoleto().setCodigoBarras(codigoBanco + moeda + dv + vencimento + valor + campoLivre);
    }

    private String getCampoLivre() {
        String carteiraPadded = String.format("%03d", Integer.parseInt(getBancoInfo().getCarteira()));
        String agenciaPadded = String.format("%05d", Integer.parseInt(getBancoInfo().getAgencia()));
        String contaPadded = String.format("%05d", Integer.parseInt(getBancoInfo().getContaCorrente()));
        String nossoNumeroPadded = String.format("%011d", Long.parseLong(getBancoInfo().getNossoNumero()));

        return carteiraPadded + agenciaPadded + contaPadded + getBancoInfo().getDigitoConta() + nossoNumeroPadded + "0";
    }

}
