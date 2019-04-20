package sample;

import javafx.application.Application;
import javafx.beans.Observable;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import sample.Model.FileUtils.FileParser;
import sample.Model.IndexedObj;
import sample.Model.StringUtils;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main extends Application {


    private Stage primaryStage;
    private Pane rootLayout;
    private TextField searchTextField;
    private ListView<String> datsListView;
    private FileParser fileParser;
    private TextArea objectsTextArea;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage){
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Dats Workshop");

        fileParser = new FileParser();

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

        fileParser.parseObjectsFromFile(filename);

        objectsTextArea.setText(fileParser.getObjects().stream()
                .map(o -> o.verbose)
                .collect(Collectors.joining()));
    }

    private void initVisualComponents() {

        searchTextField = (TextField) rootLayout.lookup("#searchTextField");
        objectsTextArea = (TextArea) rootLayout.lookup("#objectsTextArea");
        datsListView = (ListView<String>) rootLayout.lookup("#datsListView");

        searchTextField.setOnKeyReleased(this::onTextAreaKeyPressedEvent);
        objectsTextArea.setOnKeyReleased(this::onObjectsAreaChangeEvent);
        datsListView.getSelectionModel().selectedItemProperty().addListener(this::onDatsListItemClick);
    }

    private void onObjectsAreaChangeEvent(KeyEvent keyEvent) {

        //obtengo los objetos filtrados por el search textview
        List<String> objectsSplited = fileParser.parseObjectsFromDataStream(objectsTextArea.getText());

        //Obtengo el objeto modificado dentro del object area
        String modifiedObject = objectsSplited.stream().filter(o -> !fileParser.getObjects().stream().map(o2 -> o2.verbose).collect(Collectors.toList()).contains(o))
                .findFirst().get();

        //obtengo el [ID] del objeto modificado
        String objId = Arrays.stream(modifiedObject.split("\\n")).collect(Collectors.toList()).get(0);

        //busco el objeto que debo reemplazar por el modificado
        IndexedObj oldObj = fileParser.getObjects().stream().filter(o -> o.verbose.contains(objId)).findFirst().get();

        //le cambio el valor modificado
        oldObj.verbose = modifiedObject;

        //busco el indice dentro de los objetos
        int objIndex = fileParser.getObjects().indexOf(oldObj);

        //seteo el objeto modificado en la lista de objetos y queda persistido el cambio
        fileParser.getObjects().set(objIndex, oldObj);

        //reescribo el archivo con el objeto modificado
        fileParser.persistObjectsOnFile();
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

        objectsTextArea.setText(fileParser.getObjects().stream()
                .map(o -> o.verbose)
                .filter(s -> StringUtils.compareIgnoreCaseAndSpecialCharacters(s, searchTextField.getText()))
                .collect(Collectors.joining()));
    }


}
