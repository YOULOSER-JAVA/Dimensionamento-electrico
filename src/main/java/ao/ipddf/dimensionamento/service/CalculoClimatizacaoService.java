package ao.ipddf.dimensionamento.service;

import ao.ipddf.dimensionamento.model.Compartimento;

/**
 * Serviço para cálculos de ar condicionado
 * Implementa o método 80VA/m² conforme artigo 415º R.S.I.U.E.E
 */
class CalculoClimatizacaoService {

    private static final double FACTOR_POTENCIA = 0.8;
    private static final double EER = 10.23; // BTU/h por Watt

    /**
     * Calcula a capacidade do ar condicionado em BTU/h
     * Método: 80VA/m²
     */
    public double calcularCapacidadeAC(Compartimento comp) {
        // Potência aparente mínima: Smc = A × 80VA
        double potenciaAparenteMin = comp.getArea() * 80.0;

        // Potência ativa mínima: Pmc = Smc × cos φ
        double potenciaAtivaMin = potenciaAparenteMin * FACTOR_POTENCIA;

        // Capacidade em BTU/h: Cac = (3600 × Pmc) / 1055
        double capacidadeBTU = (3600.0 * potenciaAtivaMin) / 1055.0;

        return capacidadeBTU;
    }

    /**
     * Determina a capacidade comercial mais próxima
     */
    public int determinarCapacidadeComercial(double capacidadeCalculada) {
        int[] capacidadesComerciais = {7000, 9000, 12000, 18000, 24000, 30000};

        for (int capacidade : capacidadesComerciais) {
            if (capacidade >= capacidadeCalculada) {
                return capacidade;
            }
        }

        return capacidadesComerciais[capacidadesComerciais.length - 1];
    }

    /**
     * Calcula a potência eléctrica consumida pelo AC
     * Baseado no EER do equipamento
     */
    public double calcularPotenciaEletrica(int capacidadeBTU) {
        // P = Capacidade BTU/h / EER
        return capacidadeBTU / EER;
    }
}
