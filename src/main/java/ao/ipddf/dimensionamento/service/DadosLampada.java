package ao.ipddf.dimensionamento.service;

/**
 * Classe auxiliar para dados de lâmpada
 */
class DadosLampada {
    private String tipo;
    private double potencia;
    private double fluxoLuminoso;
    private double potenciaBalastro;

    public DadosLampada(String tipo, double potencia, double fluxo, double balastro) {
        this.tipo = tipo;
        this.potencia = potencia;
        this.fluxoLuminoso = fluxo;
        this.potenciaBalastro = balastro;
    }

    public String getTipo() {
        return tipo;
    }

    public double getPotencia() {
        return potencia;
    }

    public double getFluxoLuminoso() {
        return fluxoLuminoso;
    }

    public double getPotenciaBalastro() {
        return potenciaBalastro;
    }

    public double getPotenciaTotal() {
        return potencia + potenciaBalastro;
    }
}
