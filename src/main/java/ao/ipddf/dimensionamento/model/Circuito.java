package ao.ipddf.dimensionamento.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Representa um circuito elétrico
 */
public class Circuito {
    private String id;
    private int numero;
    private TipoCircuito tipo;
    private Fase fase;
    private double potenciaInstalada; // W
    private double potenciaAparente; // VA
    private double correnteServico; // A
    private double secaoCondutor; // mm²
    private int disjuntor; // A
    private String curvaDisjuntor;
    private List<String> compartimentosAtendidos;

    public Circuito() {
        this.id = UUID.randomUUID().toString();
        this.compartimentosAtendidos = new ArrayList<>();
    }

    // Getters e Setters
    public String getId() {
        return id;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int num) {
        this.numero = num;
    }

    public TipoCircuito getTipo() {
        return tipo;
    }

    public void setTipo(TipoCircuito tipo) {
        this.tipo = tipo;
    }

    public Fase getFase() {
        return fase;
    }

    public void setFase(Fase fase) {
        this.fase = fase;
    }

    public double getPotenciaInstalada() {
        return potenciaInstalada;
    }

    public void setPotenciaInstalada(double pot) {
        this.potenciaInstalada = pot;
    }

    public double getPotenciaAparente() {
        return potenciaAparente;
    }

    public void setPotenciaAparente(double pot) {
        this.potenciaAparente = pot;
    }

    public double getCorrenteServico() {
        return correnteServico;
    }

    public void setCorrenteServico(double corr) {
        this.correnteServico = corr;
    }

    public double getSecaoCondutor() {
        return secaoCondutor;
    }

    public void setSecaoCondutor(double secao) {
        this.secaoCondutor = secao;
    }

    public int getDisjuntor() {
        return disjuntor;
    }

    public void setDisjuntor(int disj) {
        this.disjuntor = disj;
    }

    public String getCurvaDisjuntor() {
        return curvaDisjuntor;
    }

    public void setCurvaDisjuntor(String curva) {
        this.curvaDisjuntor = curva;
    }

    public List<String> getCompartimentosAtendidos() {
        return compartimentosAtendidos;
    }

    public void adicionarCompartimento(String comp) {
        this.compartimentosAtendidos.add(comp);
    }
}
