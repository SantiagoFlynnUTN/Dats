package sample.Model;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.Model.FileUtils.FileManager;
import sample.Model.FileUtils.FileScanner;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("Views/sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 200));
        primaryStage.show();

        FileScanner.showFilesInFolder(primaryStage);
        FileManager.readFileAsListOfStrings("AreasStats.dat");
    }

    public static void main(String[] args) {
        launch(args);
    }
}
