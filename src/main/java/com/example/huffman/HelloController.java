package com.example.huffman;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.Window;

public class HelloController {
    @FXML
    private TextArea textField;

    @FXML
    private Label encodedText;

    @FXML
    private Label decodedText;

    private final Huffman huffman;
    private final TreeView treeView;

    public HelloController(){
        this.huffman = new Huffman();
        this.treeView = new TreeView();
    }

    @FXML
    protected void onHelloButtonClick() {
        final String text = textField.getText();
        if(text != null && !text.isBlank()) {
            final Node node = huffman.buildTree(text);
            encodedText.setText(new Huffman().encode(node, text));
            decodedText.setText(text);
            treeView.drawTree(node);
            final ObservableList<Window> windows = Stage.getWindows();
            if(windows.size() > 1){
                windows.get(1).hide();
            }
            Stage stage = new Stage();

            stage.setTitle("Tree");

            AnchorPane container = treeView.drawTree(node);

            ScrollPane scrollPane = new ScrollPane(container);

            Scene scene = new Scene(scrollPane, 800, 800);
            stage.setScene(scene);
            stage.show();
        }
    }

    @FXML
    protected void onExampleButtonClick() {
        final String text = " To begin to toboggan first buy a toboggan, but don't buy too big a toboggan. Too big a toboggan is too big a toboggan to buy to begin to toboggan.";
        textField.setText(text);
        onHelloButtonClick();
    }
}