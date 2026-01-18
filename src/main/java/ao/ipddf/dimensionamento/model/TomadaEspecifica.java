package ao.ipddf.dimensionamento.model;

/**
 * Representa uma tomada de uso específico (TUE)
 */
public class TomadaEspecifica {
    private String descricao;
    private double potencia; // W
    private boolean independente;

    public TomadaEspecifica(String descricao, double potencia, boolean independente) {
        this.descricao = descricao;
        this.potencia = potencia;
        this.independente = independente;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String desc) {
        this.descricao = desc;
    }

    public double getPotencia() {
        return potencia;
    }

    public void setPotencia(double pot) {
        this.potencia = pot;
    }

    public boolean isIndependente() {
        return independente;
    }

    public void setIndependente(boolean ind) {
        this.independente = ind;
    }
}
