package ao.ipddf.dimensionamento.controller;

import ao.ipddf.dimensionamento.model.Compartimento;
import ao.ipddf.dimensionamento.model.TipoCompartimento;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class AdicionarCompartimentoDialogController implements Initializable {
    @FXML private TextField txtNome;
    @FXML private ComboBox<TipoCompartimento> cmbTipo;
    @FXML private TextField txtComprimento;
    @FXML private TextField txtLargura;
    @FXML private TextField txtAltura;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cmbTipo.getItems().setAll(TipoCompartimento.values());
    }

    public Compartimento buildCompartimento() {
        try {
            String nome = txtNome.getText().trim();
            TipoCompartimento tipo = cmbTipo.getValue();
            double comprimento = Double.parseDouble(txtComprimento.getText());
            double largura = Double.parseDouble(txtLargura.getText());
            double altura = Double.parseDouble(txtAltura.getText());

            if (nome.isEmpty() || tipo == null)
                return null;

            Compartimento comp = new Compartimento(nome, comprimento, largura, altura);
            comp.setTipo(tipo);
            return comp;
        } catch (Exception e) {
            return null;
        }
    }
}