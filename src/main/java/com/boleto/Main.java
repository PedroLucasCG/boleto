package com.boleto;

import com.boleto.builder.*;
import com.boleto.model.*;
import com.itextpdf.barcodes.BarcodeInter25;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        List<String> bancos = List.of("001", "237", "341");

        for (String bancoCodigo : bancos) {
            Beneficiario beneficiario = new Beneficiario("Empresa Exemplo", "12345678000190", "Rua Exemplo, 123");
            Sacado sacado = new Sacado("Cliente Teste", "98765432100", "Av. Cliente, 456");
            Titulo titulo = new Titulo("000123", "20250520", new BigDecimal("1500.00"));

            Director director = new Director(
                    new BoletoBuilder(
                            "Empresa Exemplo",
                            "12345678000190",
                            "Rua Exemplo, 123",
                            "000123",
                            "20250520",
                            new BigDecimal("1500.00"),
                            "5",
                            bancoCodigo
                    )
            );

            Boleto boleto = director.buildBoleto(
                    "4321",
                    "123456",
                    "18",
                    "87654321",
                    "1234567",
                    beneficiario,
                    sacado,
                    titulo
            );

            System.out.println("\nBoleto gerado para banco: " + boleto.getBancoInfo().getCodigoBanco());
            System.out.println("Código de barras: " + boleto.getCodigoBarras());
            System.out.println("Linha digitável: " + boleto.getLinhaDigitavel());

            String outputPath = "boletos/boleto_" + bancoCodigo + ".pdf";
            generate(boleto, outputPath);
        }
    }

    public static void generate(Boleto boleto, String outputPath) throws IOException {
        File file = new File(outputPath);
        file.getParentFile().mkdirs();

        PdfWriter writer = new PdfWriter(outputPath);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf, PageSize.A4);

        document.add(new Paragraph("BOLETO BANCÁRIO").setFontSize(16).setBold());
        document.add(new Paragraph("Banco: " + boleto.getBancoInfo().getNomeBanco()));
        document.add(new Paragraph("Beneficiário: " + boleto.getBeneficiario().getNome()));
        document.add(new Paragraph("Sacado: " + boleto.getSacado().getNome()));
        document.add(new Paragraph("Valor: R$ " + boleto.getTitulo().getValor()));
        document.add(new Paragraph("Vencimento: " + boleto.getTitulo().getDataVencimento()));
        document.add(new Paragraph("Número do Documento: " + boleto.getTitulo().getNumeroDocumento()));
        document.add(new Paragraph("Linha Digitável: " + boleto.getLinhaDigitavel()));

        String codigoBarras = boleto.getCodigoBarras();
        if (codigoBarras != null && codigoBarras.length() == 44) {
            BarcodeInter25 barcode = new BarcodeInter25(pdf);
            barcode.setCode(codigoBarras);
            barcode.setExtended(true);
            barcode.setBarHeight(40f);
            barcode.setX(1.2f);

            PdfCanvas canvas = new PdfCanvas(pdf.addNewPage());
            Image barcodeImage = new Image(barcode.createFormXObject(ColorConstants.BLACK, ColorConstants.WHITE, pdf));
            document.add(barcodeImage);
        } else {
            document.add(new Paragraph("Código de barras inválido ou não disponível."));
        }

        document.close();
        System.out.println("PDF salvo em: " + outputPath);
    }
}