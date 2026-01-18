package ao.ipddf.dimensionamento.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory; // Importante!
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert.AlertType;
import ao.ipddf.dimensionamento.model.*;
import ao.ipddf.dimensionamento.service.*;

import java.net.URL;
import java.util.ResourceBundle;

public class TelaPrincipalController implements Initializable {

    // Componentes da Interface
    @FXML private TextField txtNomeProjeto;
    @FXML private TextField txtCliente;
    @FXML private TextField txtEndereco;
    @FXML private ComboBox<String> cmbTipoAlimentacao;
    @FXML private TextField txtTensaoNominal;

    @FXML private Label lblNumCompartimentos;
    @FXML private Label lblNumCircuitos;
    @FXML private Label lblPotenciaInstalada;
    @FXML private Label lblPotenciaContratada;
    @FXML private Label lblCorrenteServico;
    @FXML private Label lblNumPaineis;
    @FXML private Label lblPotenciaPaineis;
    @FXML private Label lblBaterias;
    @FXML private Label lblAutonomia;
    @FXML private Label lblStatus;
    @FXML private Label lblDashboard;

    @FXML private TabPane tabPaneMain;

    // --- TABELA COMPARTIMENTOS ---
    @FXML private TableView<Compartimento> tabelaCompartimentos;
    @FXML private TableColumn<Compartimento, String> colCompNome;
    @FXML private TableColumn<Compartimento, String> colCompTipo;
    @FXML private TableColumn<Compartimento, Double> colCompArea;
    @FXML private TableColumn<Compartimento, Integer> colCompLampadas;
    @FXML private TableColumn<Compartimento, Integer> colCompTomadasUG;
    // Adicione as outras colunas conforme necessário no FXML

    // --- TABELA CIRCUITOS ---
    @FXML private TableView<Circuito> tabelaCircuitos;
    // Defina as colunas de circuitos aqui também se precisar

    // Dados Observáveis para as Tabelas
    private ObservableList<Compartimento> listaCompartimentos;

    // Variáveis de Controle
    private Projeto projetoAtual;
    // private CalculoIluminacaoService servicoIluminacao; // Descomente quando separar os arquivos

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Inicializa serviços (Comentei pois você precisa separar os arquivos primeiro)
        // servicoIluminacao = new CalculoIluminacaoService();

        // 1. Configurar ComboBox
        cmbTipoAlimentacao.setItems(FXCollections.observableArrayList(
                "Monofásica", "Bifásica", "Trifásica"
        ));
        cmbTipoAlimentacao.setValue("Trifásica");
        txtTensaoNominal.setText("220");

        // 2. Configurar Tabela de Compartimentos (VINCULAÇÃO)
        colCompNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colCompTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        colCompArea.setCellValueFactory(new PropertyValueFactory<>("area"));
        colCompLampadas.setCellValueFactory(new PropertyValueFactory<>("numeroLampadas"));
        colCompTomadasUG.setCellValueFactory(new PropertyValueFactory<>("numeroTomadasUG"));

        listaCompartimentos = FXCollections.observableArrayList();
        tabelaCompartimentos.setItems(listaCompartimentos);

        // 3. Cria novo projeto
        novoProjeto();
        atualizarStatus("Sistema inicializado com sucesso");
    }

    @FXML
    private void novoProjeto() {
        projetoAtual = new Projeto();
        projetoAtual.setNome("Novo Projeto");

        // Limpar lista visual
        listaCompartimentos.clear();

        limparCampos();
        atualizarResumo();
        atualizarStatus("Novo projeto criado");
    }

    // Exemplo temporário para testar a tabela
    @FXML
    private void adicionarCompartimento() {
        // Num sistema real, abriria uma nova janela. Aqui vamos adicionar um teste.
        Compartimento teste = new Compartimento("Quarto Teste", 4.0, 3.0, 2.8);
        teste.setNumeroLampadas(2);
        teste.setNumeroTomadasUG(3);

        projetoAtual.adicionarCompartimento(teste);
        listaCompartimentos.add(teste); // Adiciona na lista visual

        atualizarResumo();
        atualizarStatus("Compartimento adicionado");
    }

    // ... Mantenha os outros métodos (guardarProjeto, sair, etc) ...

    @FXML
    private void guardarProjeto() {
        mostrarAlerta("Sucesso", "Projeto", "Botão Guardar Clicado", AlertType.INFORMATION);
    }

    @FXML
    private void sair() { System.exit(0); }

    // ... Mantenha os métodos de menu (abrirIluminacao, etc) ...
    @FXML private void abrirIluminacao() {}
    @FXML private void abrirTomadas() {}
    @FXML private void abrirClimatizacao() {}
    @FXML private void abrirCircuitos() {}
    @FXML private void abrirFotovoltaico() {}
    @FXML private void calcularTudo() {}
    @FXML private void editarCompartimento() {}
    @FXML private void removerCompartimento() {}
    @FXML private void gerenciarCompartimentos() { tabPaneMain.getSelectionModel().select(0); }
    @FXML private void gerarCircuitosAuto() {}
    @FXML private void editarCircuito() {}
    @FXML private void balancearFases() {}
    @FXML private void gerarMemorial() {}
    @FXML private void gerarTabelas() {}
    @FXML private void gerarListaMateriais() {}
    @FXML private void gerarDiagrama() {}
    @FXML private void gerarRelatorioCompleto() {}
    @FXML private void abrirCalculadora() {}
    @FXML private void abrirTabelasNormativas() {}
    @FXML private void abrirManual() {}
    @FXML private void abrirSobre() {}
    @FXML private void abrirProjeto() {}

    private void limparCampos() {
        txtNomeProjeto.clear();
        txtCliente.clear();
        txtEndereco.clear();
    }

    private void atualizarResumo() {
        if (projetoAtual != null) {
            lblNumCompartimentos.setText(String.valueOf(listaCompartimentos.size()));
        }
    }

    private void atualizarStatus(String msg) { lblStatus.setText(msg); }

    private void mostrarAlerta(String t, String h, String c, AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(t);
        alert.setHeaderText(h);
        alert.setContentText(c);
        alert.showAndWait();
    }

    private boolean validarProjeto() { return true; }
}