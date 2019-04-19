package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import sample.Model.FileUtils.FileManager;

import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Collectors;

public class Main extends Application {


    private Stage primaryStage;
    private Pane rootLayout;
    private TextField searchTextField;
    private FileManager fileManager;
    private TextArea objectsTextArea;

    @Override
    public void start(Stage primaryStage) throws Exception{
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Hello World");

        fileManager = new FileManager();
        fileManager.readFileAsListOfStrings("Obj.dat");

        fileManager.parseObjects();

        initRootLayout();

        test();

        objectsTextArea.setText(fileManager.getObjects().stream()
                .map(o -> o.verbose)
                .collect(Collectors.joining()));

        }

    private void doSomething(KeyEvent event){

        TextField searchTextField = (TextField) rootLayout.lookup("#searchTextField");

        objectsTextArea.setText(fileManager.getObjects().stream()
                .map(o -> o.verbose)
                .filter(s -> containsIgnoreCase(s, searchTextField.getText()))
                .collect(Collectors.joining()));

    }

    private boolean containsIgnoreCase(String str1, String str2){

        return Arrays.stream(str2.toLowerCase().split(" ")).allMatch(s -> str1.toLowerCase().contains(s));
    }

    private void test() {

        objectsTextArea = (TextArea) rootLayout.lookup("#objectsTextArea");
        searchTextField = (TextField) rootLayout.lookup("#searchTextField");

        searchTextField.setOnKeyPressed(this::doSomething);
    }

    public static void main(String[] args) {
        launch(args);
    }


    /**
     * Initializes the root layout.
     */
    public void initRootLayout() {
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

    public Stage getPrimaryStage() {
        return primaryStage;
    }
}
