package com.boleto.builder;

import com.boleto.model.BancoInfo;

public class BradescoBuilder extends BoletoBuilder {

    public BradescoBuilder() {
        setBancoInfo(new BancoInfo("237", "Banco Bradesco S.A.", "06"));
    }
    @Override
    public void buildLinhaDigitavel() {

    };
    @Override
    public void buildCodigoBarras() {

    };

}
