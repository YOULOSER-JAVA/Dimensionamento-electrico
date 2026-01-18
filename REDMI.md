# Sistema de Dimensionamento Elétrico Residencial

## 📋 Sobre o Projeto

Sistema completo para dimensionamento elétrico residencial com integração de sistemas fotovoltaicos, desenvolvido conforme normas portuguesas (R.S.I.U.E.E, N.P).

**Desenvolvido por:**
- Lewis Tchivimbi
- Tadilson André
- Miguel da Costa

**Instituto Politécnico Dom Damião Franklin (IPDDF)**  
Luanda, Angola - 2024

---

## 🎯 Funcionalidades Principais

### ✅ Módulos Implementados

1. **Dimensionamento de Iluminação**
    - Cálculo pelo Método dos Lumens
    - Determinação do número de lâmpadas
    - Cálculo de índice de local (K)
    - Coeficiente de utilização (μ)

2. **Dimensionamento de Tomadas**
    - Tomadas de Uso Geral (TUG) - Método 25VA/m²
    - Tomadas de Uso Específico (TUE)
    - Classificação por dependência

3. **Dimensionamento de Climatização**
    - Cálculo de capacidade (BTU/h)
    - Método 80VA/m²
    - Determinação de potência elétrica

4. **Dimensionamento de Circuitos**
    - Divisão automática de circuitos
    - Balanceamento de fases (R, S, T)
    - Dimensionamento de condutores
    - Seleção de disjuntores

5. **Sistema Fotovoltaico OFF-Grid**
    - Cálculo de demanda energética diária
    - Dimensionamento de painéis solares
    - Dimensionamento de banco de baterias
    - Seleção de inversor
    - Sistema de prioridade energética

6. **Geração de Relatórios**
    - Memorial descritivo
    - Tabelas técnicas
    - Lista de materiais
    - Diagrama unifilar

---

## 🛠️ Tecnologias Utilizadas

- **Java 17** (LTS)
- **JavaFX 21** (Interface Gráfica)
- **Maven** (Gerenciamento de dependências)
- **H2 Database** (Banco de dados embutido)
- **Apache POI** (Geração de Excel)
- **iText 7** (Geração de PDF)
- **JFreeChart** (Gráficos)

---

## 📦 Como Instalar e Executar

### Pré-requisitos

1. **Java Development Kit (JDK) 17 ou superior**
    - Download: https://www.oracle.com/java/technologies/downloads/
    - Ou OpenJDK: https://adoptium.net/

2. **Apache Maven**
    - Download: https://maven.apache.org/download.cgi
    - Ou usar Maven Wrapper incluído no projeto

3. **IDE Recomendada**
    - IntelliJ IDEA (Community ou Ultimate)
    - Eclipse com plugin Maven
    - VS Code com extensões Java

### Passo a Passo - Para Iniciantes

#### 1. Instalar o Java

**Windows:**
```bash
# Baixe o instalador do JDK 17
# Execute o instalador
# Adicione ao PATH:
# Painel de Controle > Sistema > Configurações Avançadas > Variáveis de Ambiente
# Adicione: JAVA_HOME = C:\Program Files\Java\jdk-17
```

**Linux/Mac:**
```bash
# Usando SDKMAN (recomendado)
curl -s "https://get.sdkman.io" | bash
sdk install java 17.0.9-tem
```

#### 2. Verificar Instalação

```bash
java -version
# Deve mostrar: java version "17.x.x"
```

#### 3. Clonar/Baixar o Projeto

```bash
# Se tiver o código em ZIP, extraia em uma pasta
# Exemplo: C:\Projetos\DimensionamentoEletrico
```

#### 4. Estrutura de Pastas

Certifique-se que tem esta estrutura:

```
DimensionamentoEletrico/
├── src/
│   ├── main/
│   │   ├── java/
│   │   └── resources/
│   └── test/
├── pom.xml
└── README.md
```

#### 5. Compilar o Projeto

**Usando Maven (linha de comando):**

```bash
# Navegue até a pasta do projeto
cd DimensionamentoEletrico

# Compile o projeto
mvn clean install

# Se der erro de JavaFX, use:
mvn clean install -DskipTests
```

**Usando IntelliJ IDEA:**

1. Abra o IntelliJ IDEA
2. File > Open > Selecione a pasta do projeto
3. Aguarde o IntelliJ baixar dependências
4. Clique com botão direito em `pom.xml` > Maven > Reload Project

#### 6. Executar o Projeto

**Linha de Comando:**

```bash
mvn javafx:run
```

**IntelliJ IDEA:**

1. Localize o arquivo `Main.java`
2. Clique com botão direito > Run 'Main'

**Eclipse:**

1. Importar projeto Maven
2. Executar `Main.java` como Java Application

---

## 📖 Como Usar o Sistema

### Fluxo de Trabalho Recomendado

#### 1. Criar Novo Projeto

1. Abra o sistema
2. Menu **Ficheiro** > **Novo Projeto**
3. Preencha:
    - Nome do Projeto
    - Cliente
    - Endereço
    - Tipo de Alimentação (Monofásica/Trifásica)
    - Tensão Nominal (220V ou 380V)

#### 2. Adicionar Compartimentos

1. Clique em **"Compartimentos"** na barra de ferramentas
2. Clique **"Adicionar Compartimento"**
3. Preencha:
    - Nome (ex: Sala de Estar)
    - Tipo
    - Dimensões (Comprimento x Largura x Altura)
4. Repita para todos os compartimentos

#### 3. Dimensionar Iluminação

1. Menu **Dimensionamento** > **Iluminação**
2. Selecione o compartimento
3. Escolha o tipo de lâmpada
4. Sistema calcula automaticamente:
    - Número de lâmpadas necessárias
    - Potência total

#### 4. Dimensionar Tomadas

1. Menu **Dimensionamento** > **Tomadas**
2. Sistema calcula automaticamente TUG
3. Adicione TUE conforme necessário:
    - Geladeira (500W)
    - Microondas (1000W)
    - Ar Condicionado
    - etc.

#### 5. Dimensionar Climatização

1. Menu **Dimensionamento** > **Climatização**
2. Marque compartimentos com A/C
3. Sistema calcula capacidade em BTU/h
4. Mostra potência elétrica necessária

#### 6. Gerar Circuitos

1. Menu **Dimensionamento** > **Circuitos**
2. Clique **"Gerar Circuitos Automaticamente"**
3. Sistema divide:
    - Circuitos de Iluminação
    - Circuitos de TUG
    - Circuitos de TUE
    - Circuitos de Climatização
4. Clique **"Balancear Fases"** para equilibrar

#### 7. Dimensionar Sistema Fotovoltaico

1. Menu **Dimensionamento** > **Sistema Fotovoltaico**
2. Marque cargas prioritárias:
    - Iluminação
    - Geladeira e Arca
    - Tomadas essenciais
3. Defina autonomia desejada (dias)
4. Sistema calcula:
    - Número de painéis
    - Capacidade de baterias
    - Potência do inversor

#### 8. Gerar Relatórios

1. Menu **Relatórios** > **Relatório Completo**
2. Escolha formato (PDF ou Excel)
3. Salve o arquivo

---

## 📊 Exemplos de Cálculos

### Exemplo 1: Iluminação

**Compartimento:** Sala de Estar (4m x 2,1m x 3m altura)

```
Área = 4 × 2,1 = 8,4 m²
Altura Útil = 3 - 0,75 = 2,25 m
Índice K = 8,4 / [2,25 × (4 + 2,1)] = 0,61 → Grupo J
Coeficiente μ = 0,36 (da tabela)
Nível Iluminação = 100 lux
Factor Depreciação = 1,35

Fluxo Total = (100 × 8,4 × 1,35) / 0,36 = 3.150 lm
Lâmpada T5 = 2.000 lm
Número Lâmpadas = 3.150 / 2.000 = 1,57 ≈ 2 lâmpadas
```

### Exemplo 2: Tomadas

**Compartimento:** Cozinha (4,25m x 3,45m)

```
Área = 14,66 m²
Smc = 14,66 × 25 = 366,5 VA
Pmc = 366,5 × 0,8 = 293,2 W
Nº Tomadas = 293,2 / 250 = 1,17 ≈ 3 tomadas (mínimo)
```

### Exemplo 3: Ar Condicionado

**Compartimento:** Quarto (3,1m x 4,45m)

```
Área = 13,8 m²
Smc = 13,8 × 80 = 1.104 VA
Pmc = 1.104 × 0,8 = 883,2 W
Capacidade = (3.600 × 883,2) / 1.055 = 3.014 BTU/h
Comercial = 9.000 BTU/h (próximo disponível)
Potência = 9.000 / 10,23 = 880 W
```

---

## 🔧 Solução de Problemas

### Erro: "JavaFX não encontrado"

**Solução:**
```bash
# Execute com:
mvn javafx:run

# Ou adicione ao pom.xml:
<plugin>
    <groupId>org.openjfx</groupId>
    <artifactId>javafx-maven-plugin</artifactId>
</plugin>
```

### Erro: "Banco de dados não inicializa"

**Solução:**
1. Verifique se a pasta `database/` existe
2. Delete a pasta `database/` e execute novamente
3. Sistema criará novo banco automaticamente

### Interface não abre

**Solução:**
1. Verifique se Java 17+ está instalado
2. Verifique JAVA_HOME configurado
3. Execute: `mvn clean install -U`

---

## 📚 Referências Técnicas

### Normas Aplicadas

- **R.S.I.U.E.E** - Regulamento de Segurança de Instalação de Utilização de Energia Eléctrica
- **R.S.I.C.E.S.E** - Regulamento de Segurança de Instalações Colectivas
- **R.S.I.R.D.E.E.B.T** - Regulamento de Segurança das Redes de Distribuição
- **Normas Portuguesas (N.P)** - Instalações Eléctricas de Baixa Tensão

### Métodos de Cálculo

1. **Iluminação:** Método dos Lumens
2. **Tomadas:** Método 25VA/m²
3. **Climatização:** Método 80VA/m²
4. **Fotovoltaico:** Método de Demanda Energética

---

## 📞 Suporte

**Contato:**
- Email: [inserir email]
- Telefone: [inserir telefone]

**Instituto:**
- IPDDF - Instituto Politécnico Dom Damião Franklin
- Luanda, Angola

---

## 📄 Licença

Este projeto foi desenvolvido como Trabalho Final de Curso (TFC) no IPDDF.

© 2024 - Todos os direitos reservados.

---

## 🎓 Agradecimentos

Agradecemos ao orientador **Prof. Lic. Isidoro Muabi** e a todos os professores do IPDDF pelo apoio e conhecimento compartilhado ao longo do curso.

---

**Versão:** 1.0.0  
**Última atualização:** Janeiro 2025