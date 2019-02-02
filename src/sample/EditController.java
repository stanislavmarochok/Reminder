package sample;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class EditController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label editUsername;

    @FXML
    private Button editEditButton;

    @FXML
    private Button editDeleteButton;

    @FXML
    private TextArea editText;

    @FXML
    void initialize(String username, String text, int por_num) {
        editText.setText(text);

        editEditButton.setOnAction(event -> {

            editText(username, text, por_num);

            close(username);

        });


        editDeleteButton.setOnAction(event -> {

            deleteNote(username, text, por_num);

            close(username);

        });

    }

    private void deleteNote(String username, String text, int por_num) {
        DatabaseAddingNotes dbHandler = new DatabaseAddingNotes();
        Note note = new Note();
        note.setUsername(username);
        note.setText(text);

        dbHandler.getNote(note, 2, null, por_num);
    }

    private void close(String username){
        editEditButton.getScene().getWindow().hide();

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/sample/app.fxml"));

        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Parent root = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));

        HomeController controllerEditBook = loader.getController(); //получаем контроллер для второй формы
        controllerEditBook.initialize(username); // передаем необходимые параметры

        stage.show();
    }

    private void editText(String username, String text, int por_num) {
        DatabaseAddingNotes dbHandler = new DatabaseAddingNotes();
        Note note = new Note();
        note.setUsername(username);
        note.setText(text);

        String newText = editText.getText();

        dbHandler.getNote(note, 1, newText, por_num);
    }
}
