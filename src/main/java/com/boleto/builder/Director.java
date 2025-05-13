package com.boleto.builder;

import com.boleto.model.*;

public class Director {
    private final BoletoBuilder boletoBuilder;

    public Director(BoletoBuilder boletoBuilder) {
        this.boletoBuilder = boletoBuilder;
    }

    public Boleto buildBoleto(String agencia, String contaCorrente, String carteira,
                                          String nossoNumero, String convenio,
                                          Beneficiario beneficiario, Sacado sacado, Titulo titulo) {

        boletoBuilder.buildAgencia(agencia);
        boletoBuilder.buildContaCorrente(contaCorrente);
        boletoBuilder.buildNossoNumero(nossoNumero);

        if (boletoBuilder.getBoleto().getBancoInfo().getCodigoBanco() == "001") {
            boletoBuilder.buildConvenioBB(convenio);
        }

        boletoBuilder.buildBeneficiario(beneficiario);
        boletoBuilder.buildSacado(sacado);
        boletoBuilder.buildTitulo(titulo);
        boletoBuilder.buildCodigoBarras();
        boletoBuilder.buildLinhaDigitavel();

        return boletoBuilder.getBoleto();
    }

}
