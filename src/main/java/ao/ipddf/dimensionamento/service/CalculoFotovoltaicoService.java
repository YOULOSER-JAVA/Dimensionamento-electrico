package ao.ipddf.dimensionamento.service;

import ao.ipddf.dimensionamento.model.Compartimento;
import ao.ipddf.dimensionamento.model.Projeto;
import ao.ipddf.dimensionamento.model.TomadaEspecifica;

/**
 * Serviço para dimensionamento do Sistema Fotovoltaico OFF-Grid
 */
class CalculoFotovoltaicoService {

    /**
     * Calcula a demanda energética diária
     * ED = Σ(Unidades × P × t)
     */
    public double calcularDemandaEnergetica(Projeto projeto, boolean apenasCargasPrioritarias) {
        double demandaTotal = 0.0;

        for (Compartimento comp : projeto.getCompartimentos()) {
            // Iluminação (4h/dia para cargas prioritárias)
            if (comp.getNumeroLampadas() > 0) {
                demandaTotal += comp.getNumeroLampadas() * comp.getPotenciaLampadas() * 4;
            }

            // Tomadas de uso geral (2h/dia)
            demandaTotal += comp.getNumeroTomadasUG() * 250 * 2;

            // Tomadas específicas prioritárias (24h para geladeira e arca)
            for (TomadaEspecifica tue : comp.getTomadasEspecificas()) {
                if (tue.getDescricao().contains("Geladeira") ||
                        tue.getDescricao().contains("Arca")) {
                    demandaTotal += tue.getPotencia() * 24;
                }
            }
        }

        return demandaTotal; // Wh/dia
    }

    /**
     * Calcula o rendimento total do sistema
     * Considera todas as perdas
     */
    public double calcularRendimentoTotal() {
        double perdaTemperatura = 0.074;      // 7,4%
        double perdaIncompatibilidade = 0.015; // 1,5%
        double perdaSujeira = 0.03;            // 3%
        double perdaOhmica = 0.015;            // 1,5%
        double perdaConversor = 0.042;         // 4,2%

        double perdasTotais = perdaTemperatura + perdaIncompatibilidade +
                perdaSujeira + perdaOhmica + perdaConversor;

        return 1.0 - perdasTotais; // Aproximadamente 0.82 (82%)
    }

    /**
     * Calcula a potência total dos painéis
     * Fórmula: Pt = ED / (HSP × η)
     */
    public double calcularPotenciaPaineis(double demandaDiaria, double HSP, double rendimento) {
        return demandaDiaria / (HSP * rendimento);
    }

    /**
     * Calcula o número de painéis necessários
     * Fórmula: Qtd = Pt / Ppanel
     */
    public int calcularNumeroPaineis(double potenciaTotal, double potenciaPainel) {
        return (int) Math.ceil(potenciaTotal / potenciaPainel);
    }

    /**
     * Calcula a capacidade das baterias
     * Fórmula: Cbat = (ED × Dautonomia) / (ηbat × DOD × Vn)
     */
    public double calcularCapacidadeBaterias(double demandaDiaria, double autonomia,
                                             double rendimentoBat, double DOD, double tensao) {
        return (demandaDiaria * autonomia) / (rendimentoBat * DOD * tensao);
    }
}
