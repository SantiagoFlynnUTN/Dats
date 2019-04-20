package sample.Model;

import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import sample.Model.FileUtils.FileManager;
import sample.Model.FileUtils.FileParser;

import java.io.IOException;
import java.util.stream.Collectors;

public class ContextGenerator {

    final String OBJECTS = "Obj.dat";

    private String filename;
    private Pane root;

    public ContextGenerator(){}

    public void generate(String filename, Pane root){

        this.filename = filename;
        this.root = root;

        if(OBJECTS.equals(filename)){
            generateObjectsContext();
        }
    }

    private void generateObjectsContext() {
        Button addNewItemButton = (Button) root.lookup("#addNewItemButton");
        addNewItemButton.setOnMouseClicked(this::addItemToFile);

    }

    private void addItemToFile(MouseEvent mouseEvent) {

        int objQty = FileParser.getInstance().getObjects().size() - 1;

        String newObj = "\n[OBJ" + objQty + "]\n"
                + "Name=\n";

        FileParser.getInstance().getObjects().get(0).verbose = "[INIT]\nNumOBJs=" + objQty + "\n\n";

        try {
            FileManager.getInstance().addToEndOfFile(newObj, OBJECTS);
            //reload text area
            ((TextArea) root.lookup("#objectsTextArea")).setText(FileParser.getInstance().getObjects()
                    .stream().map(o -> o.verbose).collect(Collectors.joining()));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
