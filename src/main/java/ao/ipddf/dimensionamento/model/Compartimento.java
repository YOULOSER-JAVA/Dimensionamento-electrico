package ao.ipddf.dimensionamento.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Representa um compartimento da residência
 */
public class Compartimento {
    private String id;
    private String nome;
    private double comprimento; // metros
    private double largura; // metros
    private double altura; // metros (pé direito)
    private double area; // m²
    private TipoCompartimento tipo;

    // Iluminação
    private int numeroLampadas;
    private double potenciaLampadas;
    private int nivelIluminacao; // lux

    // Tomadas
    private int numeroTomadasUG; // Tomadas de Uso Geral
    private List<TomadaEspecifica> tomadasEspecificas;

    // Climatização
    private boolean possuiArCondicionado;
    private double capacidadeAC; // BTU/h
    private double potenciaAC; // W

    public Compartimento() {
        this.id = UUID.randomUUID().toString();
        this.tomadasEspecificas = new ArrayList<>();
    }

    public Compartimento(String nome, double comprimento, double largura, double altura) {
        this();
        this.nome = nome;
        this.comprimento = comprimento;
        this.largura = largura;
        this.altura = altura;
        this.area = comprimento * largura;
    }

    // Getters e Setters
    public String getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getComprimento() {
        return comprimento;
    }

    public void setComprimento(double comp) {
        this.comprimento = comp;
        this.area = this.comprimento * this.largura;
    }

    public double getLargura() {
        return largura;
    }

    public void setLargura(double larg) {
        this.largura = larg;
        this.area = this.comprimento * this.largura;
    }

    public double getAltura() {
        return altura;
    }

    public void setAltura(double alt) {
        this.altura = alt;
    }

    public double getArea() {
        return area;
    }

    public TipoCompartimento getTipo() {
        return tipo;
    }

    public void setTipo(TipoCompartimento tipo) {
        this.tipo = tipo;
    }

    public int getNumeroLampadas() {
        return numeroLampadas;
    }

    public void setNumeroLampadas(int num) {
        this.numeroLampadas = num;
    }

    public double getPotenciaLampadas() {
        return potenciaLampadas;
    }

    public void setPotenciaLampadas(double pot) {
        this.potenciaLampadas = pot;
    }

    public int getNivelIluminacao() {
        return nivelIluminacao;
    }

    public void setNivelIluminacao(int nivel) {
        this.nivelIluminacao = nivel;
    }

    public int getNumeroTomadasUG() {
        return numeroTomadasUG;
    }

    public void setNumeroTomadasUG(int num) {
        this.numeroTomadasUG = num;
    }

    public List<TomadaEspecifica> getTomadasEspecificas() {
        return tomadasEspecificas;
    }

    public void adicionarTomadaEspecifica(TomadaEspecifica tomada) {
        this.tomadasEspecificas.add(tomada);
    }

    public boolean isPossuiArCondicionado() {
        return possuiArCondicionado;
    }

    public void setPossuiArCondicionado(boolean possui) {
        this.possuiArCondicionado = possui;
    }

    public double getCapacidadeAC() {
        return capacidadeAC;
    }

    public void setCapacidadeAC(double cap) {
        this.capacidadeAC = cap;
    }

    public double getPotenciaAC() {
        return potenciaAC;
    }

    public void setPotenciaAC(double pot) {
        this.potenciaAC = pot;
    }
}
