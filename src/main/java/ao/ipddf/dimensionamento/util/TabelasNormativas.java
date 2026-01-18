package ao.ipddf.dimensionamento.util;

import ao.ipddf.dimensionamento.database.DatabaseManager;
import java.sql.*;

/**
 * Classe utilitária com tabelas normativas conforme
 * Regulamentos Portugueses (R.S.I.U.E.E, etc.)
 */
public class TabelasNormativas {
    
    /**
     * Converte valor de K (índice de local) para letra conforme Anexo 2
     */
    public static String obterGrupoIndiceLocal(double K) {
        if (K > 4.5) return "A";
        if (K >= 3.5) return "B";
        if (K >= 2.75) return "C";
        if (K >= 2.25) return "D";
        if (K >= 1.75) return "E";
        if (K >= 1.38) return "F";
        if (K >= 1.12) return "G";
        if (K >= 0.9) return "H";
        if (K >= 0.7) return "I";
        return "J";
    }
    
    /**
     * Obtém coeficiente de utilização do banco de dados
     */
    public static double obterCoeficienteUtilizacao(double K, double reflexaoTecto, 
                                                    double reflexaoParede, String tipoIluminacao) {
        String grupoK = obterGrupoIndiceLocal(K);
        
        try {
            Connection conn = DatabaseManager.getConnection();
            PreparedStatement stmt = conn.prepareStatement(
                "SELECT coeficiente FROM coeficientes_utilizacao " +
                "WHERE tipo_iluminacao = ? AND indice_local = ? " +
                "AND reflexao_tecto = ? AND reflexao_parede = ?"
            );
            
            stmt.setString(1, tipoIluminacao);
            stmt.setString(2, grupoK);
            stmt.setInt(3, (int)(reflexaoTecto * 100));
            stmt.setInt(4, (int)(reflexaoParede * 100));
            
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getDouble("coeficiente");
            }
            
            rs.close();
            stmt.close();
            
        } catch (SQLException e) {
            System.err.println("Erro ao obter coeficiente: " + e.getMessage());
        }
        
        // Valor padrão conservador se não encontrar
        return 0.35;
    }
    
    /**
     * Obtém nível de iluminação recomendado para tipo de compartimento
     */
    public static int obterNivelIluminacao(String tipoCompartimento) {
        try {
            Connection conn = DatabaseManager.getConnection();
            PreparedStatement stmt = conn.prepareStatement(
                "SELECT nivel_lux FROM niveis_iluminacao WHERE tipo_compartimento = ?"
            );
            
            stmt.setString(1, tipoCompartimento);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt("nivel_lux");
            }
            
            rs.close();
            stmt.close();
            
        } catch (SQLException e) {
            System.err.println("Erro ao obter nível iluminação: " + e.getMessage());
        }
        
        return 100; // Valor padrão
    }
    
    /**
     * Fator de simultaneidade conforme número de circuitos (Anexo 9)
     */
    public static double obterFatorSimultaneidade(int numeroCircuitos) {
        if (numeroCircuitos <= 4) return 1.0;
        if (numeroCircuitos <= 9) return 0.78;
        if (numeroCircuitos <= 14) return 0.63;
        if (numeroCircuitos <= 19) return 0.53;
        if (numeroCircuitos <= 24) return 0.49;
        if (numeroCircuitos <= 29) return 0.46;
        return 0.44;
    }
    
    /**
     * Determina corrente nominal de disjuntor conforme corrente de serviço
     */
    public static int determinarDisjuntor(double correnteServico) {
        int[] valores = {6, 10, 16, 20, 25, 32, 40, 50, 63, 80, 100, 125};
        
        for (int valor : valores) {
            if (valor >= correnteServico * 1.15) { // Margem de segurança
                return valor;
            }
        }
        
        return valores[valores.length - 1];
    }
    
    /**
     * Determina seção de condutor conforme corrente (simplificado - Anexo 8)
     */
    public static double determinarSecaoCondutor(double corrente, String metodoInstalacao) {
        // Tabela simplificada para PVC - Método B1 (tubos embutidos)
        if (corrente <= 15.5) return 1.5;
        if (corrente <= 21) return 2.5;
        if (corrente <= 28) return 4;
        if (corrente <= 36) return 6;
        if (corrente <= 46) return 10;
        if (corrente <= 61) return 16;
        if (corrente <= 80) return 25;
        if (corrente <= 101) return 35;
        if (corrente <= 125) return 50;
        if (corrente <= 151) return 70;
        if (corrente <= 182) return 95;
        if (corrente <= 210) return 120;
        return 150;
    }
    
    /**
     * Curva de disjuntor conforme tipo de carga
     */
    public static String determinarCurvaDisjuntor(String tipoCarga) {
        switch (tipoCarga) {
            case "ILUMINACAO":
            case "TOMADA_UG":
                return "B"; // Cargas resistivas
            case "TOMADA_UE":
            case "CLIMATIZACAO":
                return "C"; // Cargas indutivas/motores
            default:
                return "C";
        }
    }
    
    /**
     * Fator de depreciação conforme tipo de ambiente (Anexo 7)
     */
    public static double obterFatorDepreciacao(String tipoAmbiente, String qualidadeManutencao) {
        // Tabela simplificada
        if (tipoAmbiente.equals("INDUSTRIAL")) {
            switch (qualidadeManutencao) {
                case "BOM": return 1.45;
                case "MEDIO": return 1.65;
                case "MAU": return 2.00;
            }
        }
        
        // Padrão residencial com lâmpada reflector incorporado
        switch (qualidadeManutencao) {
            case "BOM": return 1.25;
            case "MEDIO": return 1.35;
            case "MAU": return 1.45;
            default: return 1.35;
        }
    }
    
    /**
     * Coeficiente de reflexão conforme cor/material (Anexo 3)
     */
    public static double obterCoeficienteReflexao(String tipo, String cor) {
        if (tipo.equals("TECTO")) {
            switch (cor) {
                case "CLARO": return 0.7;
                case "MEDIO": return 0.5;
                case "ESCURO": return 0.3;
            }
        } else if (tipo.equals("PAREDE")) {
            switch (cor) {
                case "CLARO": return 0.5;
                case "MEDIO": return 0.3;
                case "ESCURO": return 0.1;
            }
        }
        return 0.5; // Padrão
    }
    
    /**
     * HSP (Horas de Pico Solar) para Luanda
     */
    public static double obterHSPLuanda() {
        return 5.22; // Conforme trabalho, página 33
    }
    
    /**
     * Perdas do sistema fotovoltaico (Anexo - trabalho pág 33)
     */
    public static class PerdasFotovoltaico {
        public static final double TEMPERATURA = 0.074;      // 7,4%
        public static final double INCOMPATIBILIDADE = 0.015; // 1,5%
        public static final double SUJEIRA = 0.03;            // 3%
        public static final double OHMICA = 0.015;            // 1,5%
        public static final double CONVERSOR = 0.042;         // 4,2%
        
        public static double calcularRendimentoTotal() {
            double perdasTotais = TEMPERATURA + INCOMPATIBILIDADE + 
                                 SUJEIRA + OHMICA + CONVERSOR;
            return 1.0 - perdasTotais; // ~82%
        }
    }
    
    /**
     * EER (Energy Efficiency Ratio) para ar condicionado
     */
    public static double obterEER() {
        return 10.23; // BTU/h por Watt (conforme trabalho)
    }
    
    /**
     * Potências comerciais de ar condicionado disponíveis
     */
    public static int[] obterCapacidadesComerciais() {
        return new int[]{7000, 9000, 12000, 18000, 24000, 30000};
    }
}