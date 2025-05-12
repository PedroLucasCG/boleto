package com.boleto.builder;

import com.boleto.model.BancoInfo;

public class ItauBuilder extends BoletoBuilder{

    public ItauBuilder() {
        setBancoInfo(new BancoInfo("341", "Banco Itaú Unibanco S.A.", "109"));
    }
}
