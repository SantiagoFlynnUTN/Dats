package sample;

import javafx.application.Application;
import javafx.beans.Observable;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import sample.Model.FileUtils.FileManager;
import sample.Model.StringUtils;

import java.io.IOException;
import java.util.stream.Collectors;

public class Main extends Application {


    private Stage primaryStage;
    private Pane rootLayout;
    private TextField searchTextField;
    private ListView<String> datsListView;
    private FileManager fileManager;
    private TextArea objectsTextArea;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage){
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Dats Workshop");

        initRootLayout();

        initVisualComponents();
    }

    /**
     * Initializes the root layout.
     */
    private void initRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("Views/root.fxml"));
            rootLayout = (Pane) loader.load();

            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadDatFiles(String filename) throws Exception {

        fileManager = new FileManager();
        fileManager.readFileAsListOfStrings(filename);

        fileManager.parseObjects();

        objectsTextArea.setText(fileManager.getObjects().stream()
                .map(o -> o.verbose)
                .collect(Collectors.joining()));
    }


    private void initVisualComponents() {

        searchTextField = (TextField) rootLayout.lookup("#searchTextField");
        objectsTextArea = (TextArea) rootLayout.lookup("#objectsTextArea");
        datsListView = (ListView<String>) rootLayout.lookup("#datsListView");

        searchTextField.setOnKeyPressed(this::onTextAreaKeyPressedEvent);

        datsListView.getSelectionModel().selectedItemProperty().addListener(this::onDatsListItemClick);
    }

    private void onDatsListItemClick(Observable observable) {
        String fileName = datsListView.getSelectionModel().getSelectedItem();

        try {
            loadDatFiles(fileName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void onTextAreaKeyPressedEvent(KeyEvent event){

        TextField searchTextField = (TextField) rootLayout.lookup("#searchTextField");

        objectsTextArea.setText(fileManager.getObjects().stream()
                .map(o -> o.verbose)
                .filter(s -> StringUtils.compareIgnoreCaseAndSpecialCharacters(s, searchTextField.getText() + event.getText()))
                .collect(Collectors.joining()));
    }
}
