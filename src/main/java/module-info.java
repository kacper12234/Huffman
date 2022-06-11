module com.example.huffman {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires lombok;

    opens com.example.huffman to javafx.fxml;
    exports com.example.huffman;
}