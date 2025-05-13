package com.boleto;

import com.boleto.builder.Boleto;
import com.boleto.builder.BoletoBuilder;
import com.boleto.builder.Director;
import com.boleto.model.BancoInfo;
import com.boleto.model.Beneficiario;
import com.boleto.model.Sacado;
import com.boleto.model.Titulo;
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

public class Main {
    public static void main(String[] args) throws IOException {
        // Sample data
        String nome = "Empresa Exemplo";
        String cnpjCpf = "12345678000190";
        String endereco = "Rua Exemplo, 123";
        String numeroDocumento = "000123";
        String dataVencimento = "20250520";
        BigDecimal valor = new BigDecimal("1500.00");
        String digitoConta = "5";

        // Step 1: Instantiate builder
        BoletoBuilder builder = new BoletoBuilder(
                nome,
                cnpjCpf,
                endereco,
                numeroDocumento,
                dataVencimento,
                valor,
                digitoConta,
                "237"
        );

        // Step 2: Build remaining parts
        builder.buildContaCorrente("123456");
        builder.buildAgencia("4321");
        builder.buildNossoNumero("87654321");
        builder.buildConvenioBB("1234567");
        builder.buildContaCorrente("9876543");

        builder.buildBeneficiario(new Beneficiario("Empresa Exemplo", "12345678000190", "If true this will return abbreviated directions (N, E, etc). Otherwise this will return the"));
        builder.buildSacado(new Sacado("Cliente Teste", "98765432100", "If true this will return abbreviated directions (N, E, etc). Otherwise this will return the"));
        builder.buildTitulo(new Titulo(numeroDocumento, dataVencimento, valor));
        builder.buildCodigoBarras();
        builder.buildLinhaDigitavel();

        // Step 3: Use Director to finalize boleto
        Director director = new Director(builder);
        var boleto = director.constructSimpleBoleto(builder.getBoleto());

        // Step 4: Output result
        System.out.println("Boleto criado com sucesso:");
        System.out.println("Nome: " + boleto.getBeneficiario());
        System.out.println("Documento: " + boleto.getTitulo().getNumeroDocumento());
        System.out.println("Valor: " + boleto.getTitulo().getValor());
        System.out.println("Banco: " + boleto.getBancoInfo().getNomeBanco());
        System.out.println("código de barras: " + boleto.getCodigoBarras());
        System.out.println("Linha digitável: " + boleto.getLinhaDigitavel());

        generate(boleto, "C:\\Users\\Pedro Lucas\\Repositorios\\Boleto\\boleto\\boleto\\boleto.pdf");
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

        // Generate barcode from the codigo de barras
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
        System.out.println("Boleto PDF saved to: " + outputPath);
    }
}
