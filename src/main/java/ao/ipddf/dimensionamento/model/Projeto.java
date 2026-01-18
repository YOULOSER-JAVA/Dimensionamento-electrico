package ao.ipddf.dimensionamento.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Representa um projeto completo de dimensionamento elétrico
 */
public class Projeto {
    private String id;
    private String nome;
    private String endereco;
    private String cliente;
    private TipoAlimentacao tipoAlimentacao;
    private double tensaoNominal;
    private List<Compartimento> compartimentos;
    private List<Circuito> circuitos;
    private SistemaFotovoltaico sistemaFotovoltaico;

    public Projeto() {
        this.id = UUID.randomUUID().toString();
        this.compartimentos = new ArrayList<>();
        this.circuitos = new ArrayList<>();
    }

    // Getters e Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public TipoAlimentacao getTipoAlimentacao() {
        return tipoAlimentacao;
    }

    public void setTipoAlimentacao(TipoAlimentacao tipo) {
        this.tipoAlimentacao = tipo;
    }

    public double getTensaoNominal() {
        return tensaoNominal;
    }

    public void setTensaoNominal(double tensao) {
        this.tensaoNominal = tensao;
    }

    public List<Compartimento> getCompartimentos() {
        return compartimentos;
    }

    public void setCompartimentos(List<Compartimento> comps) {
        this.compartimentos = comps;
    }

    public List<Circuito> getCircuitos() {
        return circuitos;
    }

    public void setCircuitos(List<Circuito> circs) {
        this.circuitos = circs;
    }

    public SistemaFotovoltaico getSistemaFotovoltaico() {
        return sistemaFotovoltaico;
    }

    public void setSistemaFotovoltaico(SistemaFotovoltaico sistema) {
        this.sistemaFotovoltaico = sistema;
    }

    public void adicionarCompartimento(Compartimento comp) {
        this.compartimentos.add(comp);
    }

    public void adicionarCircuito(Circuito circ) {
        this.circuitos.add(circ);
    }
}
