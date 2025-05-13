package com.boleto.builder;

import com.boleto.model.BancoInfo;

import java.math.BigDecimal;

public class BradescoBuilder extends BoletoBuilder {
    private Boleto boleto;

    public BradescoBuilder(Boleto boleto, String digitoConta) {
        boleto.setBancoInfo(new BancoInfo("237", "Banco Bradesco S.A.", "06", digitoConta));
        this.boleto = boleto;
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

        String linha =
                String.format("%s.%s %s.%s %s.%s %s %s",
                        campo1.substring(0, 5), campo1.substring(5),
                        campo2.substring(0, 5), campo2.substring(5),
                        campo3.substring(0, 5), campo3.substring(5),
                        campo4,
                        campo5
                );

        return linha;
    };

    @Override
    public String getCodigoBarras() {
        String codigoBanco = boleto.getBancoInfo().getCodigoBanco();
        char moeda = super.moeda;
        String vencimento = getFatorVencimento(boleto.getTitulo().getDataVencimento());;
        String valor = formatValor(boleto.getTitulo().getValor());
        String campoLivre = getCampoLivre();

        String parcial = codigoBanco + moeda + vencimento + valor + campoLivre;

        String dv = mod11(parcial);

        return codigoBanco + moeda + dv + vencimento + valor + campoLivre;
    }

    private String getCampoLivre() {
        String agencia = String.format("%04d", Integer.parseInt(boleto.getBancoInfo().getAgencia()));
        String carteira = String.format("%02d", Integer.parseInt(boleto.getBancoInfo().getCarteira()));
        String nossoNumero = String.format("%010d", Long.parseLong(boleto.getBancoInfo().getNossoNumero()));
        String contaCorrente = String.format("%07d", Integer.parseInt(boleto.getBancoInfo().getContaCorrente()));
        String digitoConta = boleto.getBancoInfo().getDigitoConta();

        return agencia + carteira + nossoNumero + contaCorrente + digitoConta;
    }

}
