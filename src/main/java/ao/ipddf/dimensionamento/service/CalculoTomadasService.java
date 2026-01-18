package ao.ipddf.dimensionamento.service;

import ao.ipddf.dimensionamento.model.*;

/**
 * Serviço para cálculos de tomadas
 * Implementa o método 25VA/m² conforme artigo 435º R.S.I.U.E.E
 */
public class CalculoTomadasService {
    
    private static final double POTENCIA_TOMADA_UG = 250.0; // W
    private static final double FACTOR_POTENCIA = 0.8;
    
    /**
     * Calcula número de tomadas de uso geral (TUG)
     * Método: 25VA/m²
     */
    public int calcularNumeroTomadasUG(Compartimento comp) {
        // Potência aparente mínima: Smc = A × 25VA
        double potenciaAparenteMin = comp.getArea() * 25.0;
        
        // Potência ativa mínima: Pmc = Smc × cos φ
        double potenciaAtivaMin = potenciaAparenteMin * FACTOR_POTENCIA;
        
        // Número de tomadas: Nº tom = Pmc / Ptom
        int numeroTomadas = (int) Math.ceil(potenciaAtivaMin / POTENCIA_TOMADA_UG);
        
        // Mínimo conforme norma
        return Math.max(numeroTomadas, obterMinimoTomadas(comp.getTipo()));
    }
    
    /**
     * Retorna o número mínimo de tomadas conforme tipo de compartimento
     */
    private int obterMinimoTomadas(TipoCompartimento tipo) {
        switch (tipo) {
            case QUARTO:
            case SUITE:
                return 2; // Duas tomadas em lados opostos
            case COZINHA:
            case SALA_ESTAR:
                return 3; // Três tomadas
            default:
                return 1;
        }
    }
    
    /**
     * Determina tomadas de uso específico (TUE) para um compartimento
     */
    public void determinarTomadasEspecificas(Compartimento comp) {
        switch (comp.getTipo()) {
            case COZINHA:
                comp.adicionarTomadaEspecifica(new TomadaEspecifica("Geladeira", 500, false));
                comp.adicionarTomadaEspecifica(new TomadaEspecifica("Arca", 500, false));
                comp.adicionarTomadaEspecifica(new TomadaEspecifica("Microondas", 1000, true));
                comp.adicionarTomadaEspecifica(new TomadaEspecifica("Forno Eléctrico", 1000, true));
                comp.adicionarTomadaEspecifica(new TomadaEspecifica("Lava-louça", 1500, true));
                comp.adicionarTomadaEspecifica(new TomadaEspecifica("Termoacumulador", 1000, true));
                comp.adicionarTomadaEspecifica(new TomadaEspecifica("Coifa", 500, false));
                break;
                
            case AREA_SERVICO:
                comp.adicionarTomadaEspecifica(new TomadaEspecifica("Máquina de Lavar", 1500, true));
                break;
                
            case WC:
            case LAVABO:
                comp.adicionarTomadaEspecifica(new TomadaEspecifica("Móvel WC", 1000, true));
                break;
        }
    }
}

