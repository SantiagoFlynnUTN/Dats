package sample.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

import java.io.File;
import java.util.Arrays;
import java.util.Objects;

public class RootController {

    @FXML
    private ListView<String> datsListView;

    private ObservableList<String> observableList = FXCollections.observableArrayList();

    public void initialize(){

        initializeObservableList();

        datsListView.setItems(observableList);
    }

    private void initializeObservableList() {

        File actual = new File(".");

        Arrays.stream(Objects.requireNonNull(actual.listFiles()))
                .filter(a -> a.getName().contains(".dat"))
                .forEach(a -> observableList.add(a.getName()));
    }
}
