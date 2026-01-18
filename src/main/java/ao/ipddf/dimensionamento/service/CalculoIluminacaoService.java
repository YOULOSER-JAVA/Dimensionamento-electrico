package ao.ipddf.dimensionamento.service;

import ao.ipddf.dimensionamento.model.Compartimento;
import ao.ipddf.dimensionamento.util.TabelasNormativas;

/**
 * Serviço para cálculos de iluminação
 * Implementa o Método dos Lumens conforme trabalho
 */
public class CalculoIluminacaoService {

    /**
     * Calcula o número de lâmpadas necessárias para um compartimento
     * Fórmula: NL = ΦT / Φl
     */
    public int calcularNumeroLampadas(Compartimento comp, DadosLampada lampada) {
        // 1. Calcular altura útil
        double alturaUtil = calcularAlturaUtil(comp.getAltura(), 0.75, 0);

        // 2. Calcular índice de local (K)
        double K = calcularIndiceLocal(comp.getArea(), alturaUtil,
                comp.getComprimento(), comp.getLargura());

        // 3. Obter coeficiente de utilização (μ)
        double mu = TabelasNormativas.obterCoeficienteUtilizacao(K, 0.7, 0.5, "DIRECTA");

        // 4. Calcular fluxo total necessário
        double fluxoTotal = calcularFluxoTotal(
                comp.getNivelIluminacao(),
                comp.getArea(),
                1.35, // Factor de depreciação padrão
                mu
        );

        // 5. Calcular número de lâmpadas
        int numeroLampadas = (int) Math.ceil(fluxoTotal / lampada.getFluxoLuminoso());

        return numeroLampadas;
    }

    /**
     * Calcula a altura útil
     * Fórmula: Hú = H - (Hm + Hp)
     */
    private double calcularAlturaUtil(double alturaTotal, double alturaMesa, double alturaPiso) {
        return alturaTotal - (alturaMesa + alturaPiso);
    }

    /**
     * Calcula o índice de local
     * Fórmula: K = A / [Hú × (C + L)]
     */
    private double calcularIndiceLocal(double area, double alturaUtil,
                                       double comprimento, double largura) {
        return area / (alturaUtil * (comprimento + largura));
    }

    /**
     * Calcula o fluxo total necessário
     * Fórmula: ΦT = (E × A × D) / μ
     */
    private double calcularFluxoTotal(int nivelIluminacao, double area,
                                      double factorDepreciacao, double coeficienteUtilizacao) {
        return (nivelIluminacao * area * factorDepreciacao) / coeficienteUtilizacao;
    }
}
