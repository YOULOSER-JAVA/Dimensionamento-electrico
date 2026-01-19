package ao.ipddf.dimensionamento.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
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
    @FXML private ComboBox<TipoAlimentacao> cmbTipoAlimentacao;
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
    @FXML private TableColumn<Compartimento, TipoCompartimento> colCompTipo;
    @FXML private TableColumn<Compartimento, Double> colCompArea;
    @FXML private TableColumn<Compartimento, Integer> colCompLampadas;
    @FXML private TableColumn<Compartimento, Integer> colCompTomadasUG;

    // --- TABELA CIRCUITOS ---
    @FXML private TableView<Circuito> tabelaCircuitos;
    // Defina as colunas de circuitos aqui também se precisar

    // Dados Observáveis para as Tabelas
    private ObservableList<Compartimento> listaCompartimentos;

    // Variáveis de Controle
    private Projeto projetoAtual;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // 1. Configurar ComboBox para Enum
        cmbTipoAlimentacao.setItems(FXCollections.observableArrayList(TipoAlimentacao.values()));
        cmbTipoAlimentacao.setValue(TipoAlimentacao.TRIFASICA);
        txtTensaoNominal.setText("220");

        // 2. Configurar Tabela de Compartimentos (VINCULAÇÃO)
        colCompNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colCompTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        colCompArea.setCellValueFactory(new PropertyValueFactory<>("area"));
        colCompLampadas.setCellValueFactory(new PropertyValueFactory<>("numeroLampadas"));
        colCompTomadasUG.setCellValueFactory(new PropertyValueFactory<>("numeroTomadasUG"));

        colCompTipo.setCellFactory(column -> new TableCell<Compartimento, TipoCompartimento>() {
            @Override
            protected void updateItem(TipoCompartimento item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? "" : item.toString());
            }
        });

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
        projetoAtual.setTipoAlimentacao(cmbTipoAlimentacao.getValue());
        try {
            projetoAtual.setTensaoNominal(Double.parseDouble(txtTensaoNominal.getText()));
        } catch (NumberFormatException e) {
            projetoAtual.setTensaoNominal(220);
        }

        listaCompartimentos.clear();
        limparCampos();
        atualizarResumo();
        atualizarStatus("Novo projeto criado");
    }

    @FXML
    private void adicionarCompartimento() {
        try {
            javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(
                    getClass().getResource("/fxml/AdicionarCompartimentoDialog.fxml"));
            DialogPane dialogPane = loader.load();
            AdicionarCompartimentoDialogController dialogController = loader.getController();

            Dialog<Compartimento> dialog = new Dialog<>();
            dialog.setDialogPane(dialogPane);
            dialog.setTitle("Adicionar Compartimento");

            dialog.setResultConverter(bt -> {
                if (bt.getButtonData() == ButtonBar.ButtonData.OK_DONE) {
                    return dialogController.buildCompartimento();
                }
                return null;
            });

            var result = dialog.showAndWait();
            result.ifPresent(comp -> {
                projetoAtual.adicionarCompartimento(comp);
                listaCompartimentos.add(comp);
                atualizarResumo();
                atualizarStatus("Compartimento adicionado: " + comp.getNome());
            });

        } catch (Exception e) {
            mostrarAlerta("Erro", "Não foi possível adicionar", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void guardarProjeto() {
        mostrarAlerta("Sucesso", "Projeto", "Botão Guardar Clicado", AlertType.INFORMATION);
    }

    @FXML
    private void sair() {
        try {
            ao.ipddf.dimensionamento.database.DatabaseManager.fecharConexao();
        } catch (Exception ignored) {}
        System.exit(0);
    }

    // Métodos de menu (implemente conforme for desenvolvendo cada um)
    @FXML private void abrirIluminacao() { mostrarAlerta("Dimensão", "Iluminação", "Função em desenvolvimento", AlertType.INFORMATION); }
    @FXML private void abrirTomadas() { mostrarAlerta("Dimensão", "Tomadas", "Função em desenvolvimento", AlertType.INFORMATION); }
    @FXML private void abrirClimatizacao() { mostrarAlerta("Dimensão", "Climatização", "Função em desenvolvimento", AlertType.INFORMATION); }
    @FXML private void abrirCircuitos() { mostrarAlerta("Dimensão", "Circuitos", "Função em desenvolvimento", AlertType.INFORMATION); }
    @FXML private void abrirFotovoltaico() { mostrarAlerta("Dimensão", "Fotovoltaico", "Função em desenvolvimento", AlertType.INFORMATION); }
    @FXML private void calcularTudo() { mostrarAlerta("Cálculos", "Cálculo Geral", "Função em desenvolvimento", AlertType.INFORMATION); }
    @FXML private void editarCompartimento() { mostrarAlerta("Edição", "Compartimento", "Função em desenvolvimento", AlertType.INFORMATION); }
    @FXML
    private void removerCompartimento() {
        Compartimento c = tabelaCompartimentos.getSelectionModel().getSelectedItem();
        if (c != null) {
            projetoAtual.getCompartimentos().remove(c);
            listaCompartimentos.remove(c);
            atualizarResumo();
            atualizarStatus("Compartimento removido");
        } else {
            mostrarAlerta("Aviso", "Remover Compartimento", "Selecione um compartimento para remover.", Alert.AlertType.WARNING);
        }
    }
    @FXML private void gerenciarCompartimentos() { tabPaneMain.getSelectionModel().select(0); }
    @FXML private void gerarCircuitosAuto() { mostrarAlerta("Gerar", "Circuitos Automáticos", "Função em desenvolvimento", AlertType.INFORMATION); }
    @FXML private void editarCircuito() { mostrarAlerta("Editar", "Circuito", "Função em desenvolvimento", AlertType.INFORMATION); }
    @FXML private void balancearFases() { mostrarAlerta("Balancear", "Fases", "Função em desenvolvimento", AlertType.INFORMATION); }
    @FXML private void gerarMemorial() { mostrarAlerta("Memorial", "Memorial Descritivo", "Função em desenvolvimento", AlertType.INFORMATION); }
    @FXML private void gerarTabelas() { mostrarAlerta("Tabelas", "Tabelas Técnicas", "Função em desenvolvimento", AlertType.INFORMATION); }
    @FXML private void gerarListaMateriais() { mostrarAlerta("Materiais", "Lista de Materiais", "Função em desenvolvimento", AlertType.INFORMATION); }
    @FXML private void gerarDiagrama() { mostrarAlerta("Diagrama", "Unifilar", "Função em desenvolvimento", AlertType.INFORMATION); }
    @FXML private void gerarRelatorioCompleto() { mostrarAlerta("Relatório", "Relatório Completo", "Função em desenvolvimento", AlertType.INFORMATION); }
    @FXML private void abrirCalculadora() { mostrarAlerta("Ferramenta", "Calculadora", "Função em desenvolvimento", AlertType.INFORMATION); }
    @FXML private void abrirTabelasNormativas() { mostrarAlerta("Ferramenta", "Tabelas Normativas", "Função em desenvolvimento", AlertType.INFORMATION); }
    @FXML private void abrirManual() { mostrarAlerta("Ajuda", "Manual", "Função em desenvolvimento", AlertType.INFORMATION); }
    @FXML private void abrirSobre() { mostrarAlerta("Ajuda", "Sobre", "Função em desenvolvimento", AlertType.INFORMATION); }
    @FXML private void abrirProjeto() { mostrarAlerta("Abrir", "Projeto", "Função em desenvolvimento", AlertType.INFORMATION); }

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
}