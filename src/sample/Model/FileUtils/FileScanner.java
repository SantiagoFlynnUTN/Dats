package sample.Model.FileUtils;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Parent;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

public class FileScanner {

    public static void showFilesInFolder(Stage primaryStage){
        ListView<String> list = new ListView<String>();
        ObservableList<String> items = FXCollections.observableArrayList (
                "Single", "Double", "Suite", "Family App");
        list.setItems(items);
    }
}
