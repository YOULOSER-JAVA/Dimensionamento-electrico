package ao.ipddf.dimensionamento;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Classe principal do Sistema de Dimensionamento Elétrico Residencial
 * Desenvolvido por: Lewis Tchivimbi, Tadilson André e Miguel da Costa
 * Instituto Politécnico Dom Damião Franklin (IPDDF)
 *
 * Sistema completo para dimensionamento elétrico residencial com
 * integração de sistemas fotovoltaicos conforme normas portuguesas.
 */
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            // Carrega a tela principal
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/fxml/TelaPrincipal.fxml")
            );

            Scene scene = new Scene(loader.load(), 1200, 800);

            // Adiciona CSS
            scene.getStylesheets().add(
                    getClass().getResource("/css/estilo.css").toExternalForm()
            );

            // Configura a janela
            primaryStage.setTitle("Sistema de Dimensionamento Elétrico - IPDDF");
            primaryStage.setScene(scene);
            primaryStage.setMaximized(true);

            // Adiciona ícone (opcional)
            try {
                primaryStage.getIcons().add(
                        new Image(getClass().getResourceAsStream("/images/icone.png"))
                );
            } catch (Exception e) {
                System.out.println("Ícone não encontrado");
            }

            primaryStage.show();

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Erro ao carregar a interface: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        // Inicializa o banco de dados
        try {
            ao.ipddf.dimensionamento.database.DatabaseManager.inicializar();
            System.out.println("✓ Banco de dados inicializado com sucesso!");
        } catch (Exception e) {
            System.err.println("✗ Erro ao inicializar banco de dados: " + e.getMessage());
        }

        // Inicia a aplicação JavaFX
        launch(args);
    }
}