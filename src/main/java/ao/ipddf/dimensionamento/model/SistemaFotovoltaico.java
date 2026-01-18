package ao.ipddf.dimensionamento.model;

/**
 * Sistema Fotovoltaico OFF-Grid
 */
public class SistemaFotovoltaico {
    private double demandaEnergeticaDiaria; // Wh/dia
    private double horasPicoSolar; // HSP
    private double rendimentoTotal; // %
    private int numeroPaineis;
    private double potenciaPainel; // Wp
    private double potenciaTotalPaineis; // W
    private double capacidadeBaterias; // Ah
    private double tensaoBaterias; // V
    private double energiaArmazenada; // Wh
    private double potenciaInversor; // W
    private double autonomia; // dias

    // Getters e Setters
    public double getDemandaEnergeticaDiaria() {
        return demandaEnergeticaDiaria;
    }

    public void setDemandaEnergeticaDiaria(double demanda) {
        this.demandaEnergeticaDiaria = demanda;
    }

    public double getHorasPicoSolar() {
        return horasPicoSolar;
    }

    public void setHorasPicoSolar(double hsp) {
        this.horasPicoSolar = hsp;
    }

    public double getRendimentoTotal() {
        return rendimentoTotal;
    }

    public void setRendimentoTotal(double rend) {
        this.rendimentoTotal = rend;
    }

    public int getNumeroPaineis() {
        return numeroPaineis;
    }

    public void setNumeroPaineis(int num) {
        this.numeroPaineis = num;
    }

    public double getPotenciaPainel() {
        return potenciaPainel;
    }

    public void setPotenciaPainel(double pot) {
        this.potenciaPainel = pot;
    }

    public double getPotenciaTotalPaineis() {
        return potenciaTotalPaineis;
    }

    public void setPotenciaTotalPaineis(double pot) {
        this.potenciaTotalPaineis = pot;
    }

    public double getCapacidadeBaterias() {
        return capacidadeBaterias;
    }

    public void setCapacidadeBaterias(double cap) {
        this.capacidadeBaterias = cap;
    }

    public double getTensaoBaterias() {
        return tensaoBaterias;
    }

    public void setTensaoBaterias(double tensao) {
        this.tensaoBaterias = tensao;
    }

    public double getEnergiaArmazenada() {
        return energiaArmazenada;
    }

    public void setEnergiaArmazenada(double energia) {
        this.energiaArmazenada = energia;
    }

    public double getPotenciaInversor() {
        return potenciaInversor;
    }

    public void setPotenciaInversor(double pot) {
        this.potenciaInversor = pot;
    }

    public double getAutonomia() {
        return autonomia;
    }

    public void setAutonomia(double aut) {
        this.autonomia = aut;
    }
}
