package ao.ipddf.dimensionamento.database;

import java.sql.*;
import java.io.File;

public class DatabaseManager {

    // Caminho salvo na pasta do usuário (Ex: C:\Users\SeuNome\dimensionamento_db)
    private static final String DB_DIR = System.getProperty("user.home") + File.separator + "dimensionamento_db";
    private static final String DB_URL = "jdbc:h2:" + DB_DIR + "/dimensionamento";
    private static final String DB_USER = "sa";
    private static final String DB_PASSWORD = "";

    private static Connection connection;

    // ...[código igual ao seu até a linha de inicialização]...

    public static void inicializar() throws SQLException {
        // Garante que a pasta existe
        new File(DB_DIR).mkdirs();
        connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        criarTabelas();
        // POPULAR TABELAS NORMATIVAS AGORA LIBERADO
        popularTabelasNormativas();
    }
    /**
     * Cria todas as tabelas do sistema
     */
    private static void criarTabelas() throws SQLException {
        Statement stmt = connection.createStatement();
        
        // Tabela de Projetos
        stmt.execute(
            "CREATE TABLE IF NOT EXISTS projetos (" +
            "  id VARCHAR(36) PRIMARY KEY," +
            "  nome VARCHAR(200) NOT NULL," +
            "  endereco VARCHAR(500)," +
            "  cliente VARCHAR(200)," +
            "  tipo_alimentacao VARCHAR(20)," +
            "  tensao_nominal DOUBLE," +
            "  data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
            "  data_modificacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
            ")"
        );
        
        // Tabela de Compartimentos
        stmt.execute(
            "CREATE TABLE IF NOT EXISTS compartimentos (" +
            "  id VARCHAR(36) PRIMARY KEY," +
            "  projeto_id VARCHAR(36) NOT NULL," +
            "  nome VARCHAR(100) NOT NULL," +
            "  tipo VARCHAR(50)," +
            "  comprimento DOUBLE," +
            "  largura DOUBLE," +
            "  altura DOUBLE," +
            "  area DOUBLE," +
            "  numero_lampadas INT," +
            "  potencia_lampadas DOUBLE," +
            "  nivel_iluminacao INT," +
            "  numero_tomadas_ug INT," +
            "  possui_ar_condicionado BOOLEAN," +
            "  capacidade_ac DOUBLE," +
            "  potencia_ac DOUBLE," +
            "  FOREIGN KEY (projeto_id) REFERENCES projetos(id) ON DELETE CASCADE" +
            ")"
        );
        
        // Tabela de Tomadas Específicas
        stmt.execute(
            "CREATE TABLE IF NOT EXISTS tomadas_especificas (" +
            "  id INT AUTO_INCREMENT PRIMARY KEY," +
            "  compartimento_id VARCHAR(36) NOT NULL," +
            "  descricao VARCHAR(100)," +
            "  potencia DOUBLE," +
            "  independente BOOLEAN," +
            "  FOREIGN KEY (compartimento_id) REFERENCES compartimentos(id) ON DELETE CASCADE" +
            ")"
        );
        
        // Tabela de Circuitos
        stmt.execute(
            "CREATE TABLE IF NOT EXISTS circuitos (" +
            "  id VARCHAR(36) PRIMARY KEY," +
            "  projeto_id VARCHAR(36) NOT NULL," +
            "  numero INT," +
            "  tipo VARCHAR(50)," +
            "  fase VARCHAR(1)," +
            "  potencia_instalada DOUBLE," +
            "  potencia_aparente DOUBLE," +
            "  corrente_servico DOUBLE," +
            "  secao_condutor DOUBLE," +
            "  disjuntor INT," +
            "  curva_disjuntor VARCHAR(10)," +
            "  FOREIGN KEY (projeto_id) REFERENCES projetos(id) ON DELETE CASCADE" +
            ")"
        );
        
        // Tabela de Sistema Fotovoltaico
        stmt.execute(
            "CREATE TABLE IF NOT EXISTS sistemas_fotovoltaicos (" +
            "  id INT AUTO_INCREMENT PRIMARY KEY," +
            "  projeto_id VARCHAR(36) UNIQUE NOT NULL," +
            "  demanda_energetica_diaria DOUBLE," +
            "  horas_pico_solar DOUBLE," +
            "  rendimento_total DOUBLE," +
            "  numero_paineis INT," +
            "  potencia_painel DOUBLE," +
            "  potencia_total_paineis DOUBLE," +
            "  capacidade_baterias DOUBLE," +
            "  tensao_baterias DOUBLE," +
            "  energia_armazenada DOUBLE," +
            "  potencia_inversor DOUBLE," +
            "  autonomia DOUBLE," +
            "  FOREIGN KEY (projeto_id) REFERENCES projetos(id) ON DELETE CASCADE" +
            ")"
        );
        
        // Tabela de Lâmpadas (Catálogo)
        stmt.execute(
            "CREATE TABLE IF NOT EXISTS lampadas (" +
            "  id INT AUTO_INCREMENT PRIMARY KEY," +
            "  tipo VARCHAR(50)," +
            "  marca VARCHAR(50)," +
            "  potencia DOUBLE," +
            "  potencia_balastro DOUBLE," +
            "  fluxo_luminoso DOUBLE," +
            "  temperatura_cor INT," +
            "  vida_util INT" +
            ")"
        );
        
        // Tabela de Coeficientes de Utilização
        stmt.execute(
            "CREATE TABLE IF NOT EXISTS coeficientes_utilizacao (" +
            "  id INT AUTO_INCREMENT PRIMARY KEY," +
            "  tipo_iluminacao VARCHAR(50)," +
            "  indice_local VARCHAR(10)," +
            "  reflexao_tecto INT," +
            "  reflexao_parede INT," +
            "  coeficiente DOUBLE" +
            ")"
        );
        
        // Tabela de Níveis de Iluminação por Tipo de Compartimento
        stmt.execute(
            "CREATE TABLE IF NOT EXISTS niveis_iluminacao (" +
            "  id INT AUTO_INCREMENT PRIMARY KEY," +
            "  tipo_compartimento VARCHAR(50) UNIQUE," +
            "  nivel_lux INT" +
            ")"
        );
        
        stmt.close();
    }
    
    /**
     * Popula as tabelas normativas com dados das normas portuguesas
     */
    private static void popularTabelasNormativas() throws SQLException {
        popularLampadas();
        popularNiveisIluminacao();
        popularCoeficientesUtilizacao();
    }
    
    /**
     * Popula catálogo de lâmpadas
     */
    private static void popularLampadas() throws SQLException {
        // Verifica se já existe dados
        Statement check = connection.createStatement();
        ResultSet rs = check.executeQuery("SELECT COUNT(*) FROM lampadas");
        rs.next();
        if (rs.getInt(1) > 0) {
            rs.close();
            check.close();
            return; // Já populado
        }
        rs.close();
        check.close();
        
        PreparedStatement stmt = connection.prepareStatement(
            "INSERT INTO lampadas (tipo, marca, potencia, potencia_balastro, " +
            "fluxo_luminoso, temperatura_cor, vida_util) VALUES (?, ?, ?, ?, ?, ?, ?)"
        );
        
        // Lâmpada Fluorescente T5 (conforme trabalho)
        stmt.setString(1, "Fluorescente T5");
        stmt.setString(2, "LumiaStar");
        stmt.setDouble(3, 24.0);
        stmt.setDouble(4, 6.0);
        stmt.setDouble(5, 2000.0);
        stmt.setInt(6, 6500);
        stmt.setInt(7, 20000);
        stmt.executeUpdate();
        
        // Outras lâmpadas populares
        stmt.setString(1, "LED");
        stmt.setString(2, "Generic");
        stmt.setDouble(3, 12.0);
        stmt.setDouble(4, 0.0);
        stmt.setDouble(5, 1200.0);
        stmt.setInt(6, 6000);
        stmt.setInt(7, 50000);
        stmt.executeUpdate();
        
        stmt.close();
    }
    
    /**
     * Popula níveis de iluminação por tipo de compartimento
     */
    private static void popularNiveisIluminacao() throws SQLException {
        Statement check = connection.createStatement();
        ResultSet rs = check.executeQuery("SELECT COUNT(*) FROM niveis_iluminacao");
        rs.next();
        if (rs.getInt(1) > 0) {
            rs.close();
            check.close();
            return;
        }
        rs.close();
        check.close();
        
        PreparedStatement stmt = connection.prepareStatement(
            "INSERT INTO niveis_iluminacao (tipo_compartimento, nivel_lux) VALUES (?, ?)"
        );
        
        // Conforme Anexo 6 do trabalho
        String[][] dados = {
            {"SALA_ESTAR", "100"},
            {"SALA_JANTAR", "100"},
            {"SALA_VISITAS", "100"},
            {"QUARTO", "100"},
            {"SUITE", "100"},
            {"COZINHA", "100"},
            {"WC", "100"},
            {"LAVABO", "100"},
            {"CORREDOR", "60"},
            {"ESCADA", "60"},
            {"AREA_SERVICO", "60"},
            {"CLOSET", "100"},
            {"ESCRITORIO", "100"},
            {"VARANDA", "60"}
        };
        
        for (String[] dado : dados) {
            stmt.setString(1, dado[0]);
            stmt.setInt(2, Integer.parseInt(dado[1]));
            stmt.executeUpdate();
        }
        
        stmt.close();
    }
    
    /**
     * Popula coeficientes de utilização (simplificado - versão completa no Anexo 5)
     */
    private static void popularCoeficientesUtilizacao() throws SQLException {
        Statement check = connection.createStatement();
        ResultSet rs = check.executeQuery("SELECT COUNT(*) FROM coeficientes_utilizacao");
        rs.next();
        if (rs.getInt(1) > 0) {
            rs.close();
            check.close();
            return;
        }
        rs.close();
        check.close();
        
        PreparedStatement stmt = connection.prepareStatement(
            "INSERT INTO coeficientes_utilizacao (tipo_iluminacao, indice_local, " +
            "reflexao_tecto, reflexao_parede, coeficiente) VALUES (?, ?, ?, ?, ?)"
        );
        
        // Dados simplificados do Anexo 5 - Iluminação DIRECTA
        Object[][] dados = {
            {"DIRECTA", "A", 75, 50, 0.72},
            {"DIRECTA", "B", 75, 50, 0.71},
            {"DIRECTA", "C", 75, 50, 0.68},
            {"DIRECTA", "D", 75, 50, 0.65},
            {"DIRECTA", "E", 75, 50, 0.60},
            {"DIRECTA", "F", 75, 50, 0.56},
            {"DIRECTA", "G", 75, 50, 0.54},
            {"DIRECTA", "H", 75, 50, 0.50},
            {"DIRECTA", "I", 75, 50, 0.49},
            {"DIRECTA", "J", 75, 50, 0.45},
            // SEMI-DIRECTA
            {"SEMI-DIRECTA", "H", 75, 50, 0.33},
            {"SEMI-DIRECTA", "I", 75, 50, 0.30},
            {"SEMI-DIRECTA", "J", 75, 50, 0.24},
            // SEMI-INDIRECTA
            {"SEMI-INDIRECTA", "H", 75, 50, 0.15},
            {"SEMI-INDIRECTA", "J", 75, 50, 0.23}
        };
        
        for (Object[] dado : dados) {
            stmt.setString(1, (String) dado[0]);
            stmt.setString(2, (String) dado[1]);
            stmt.setInt(3, (Integer) dado[2]);
            stmt.setInt(4, (Integer) dado[3]);
            stmt.setDouble(5, (Double) dado[4]);
            stmt.executeUpdate();
        }
        
        stmt.close();
    }
    
    /**
     * Obtém a conexão com o banco de dados
     */
    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        }
        return connection;
    }
    
    /**
     * Fecha a conexão com o banco de dados
     */
    public static void fecharConexao() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }
}