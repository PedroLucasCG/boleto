# Projeto: Gerador de Boletos Bancários (Java - Padrão Builder)

Este projeto tem como objetivo implementar um **gerador de boletos bancários** utilizando o **padrão de projeto Builder** em Java. O sistema permite criar boletos personalizados com informações do beneficiário, sacado, título, banco e gerar a linha digitável, código de barras e PDF com o boleto.

## 🧱 Estrutura do Projeto

- **Modelos (`model`)**
  - `Beneficiario.java`
  - `Sacado.java`
  - `Titulo.java`
  - `BancoInfo.java`
  - `Boleto.java`

- **Builder (`builder`)**
  - `BoletoBuilder.java` (classe abstrata base)
  - `BradescoBuilder.java`, `ItauBuilder.java`, `BancoDoBrasilBuilder.java` (implementações específicas)
  - `Director.java` (orquestra o processo de construção)

- **Geração de PDF**
  - Utiliza a biblioteca [iText](https://itextpdf.com/) para criar o boleto com código de barras.

## 🚀 Como Executar

1. Clone o repositório:
   ```bash
   git clone https://github.com/PedroLucasCG/boleto.git
Adicione ao pom.xml se estiver usando Maven:
```bash
<dependency>
    <groupId>com.itextpdf</groupId>
    <artifactId>itext7-core</artifactId>
    <version>7.2.0</version>
</dependency>
