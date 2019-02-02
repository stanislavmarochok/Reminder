package sample;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class AddNoteController {

    private String Username;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label appUsername;

    @FXML
    private TextArea addText;

    @FXML
    private Button appAddNote;

    @FXML
    void initialize(String username) {
        appUsername.setText(username);
        Username = username;

        appAddNote.setOnAction(event -> { // event on pressing button
            String text = addText.getText();

            addToReminds(text); // call function to add new remain
        });
    }

    private void addToReminds(String text) { // adds new remain
        System.out.println("Added text: " + text); // debug helper


        DatabaseAddingNotes dbHandler = new DatabaseAddingNotes(); // create object

        String UserName  = Username;
        String Text  = text;

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("MM:dd HH:mm:ss");

        String Data_Time  = sdf.format(cal.getTime()); // get date

        Note note = new Note(UserName, Text, Data_Time); // create object note
        dbHandler.addNewNote(note); // call function of adding

        close(); // close window and open another window
    }

    private void close() {
            // close window and open
            appAddNote.getScene().getWindow().hide();

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

            HomeController controllerEditBook = loader.getController(); // get controller of another form
            controllerEditBook.initialize(Username); // send neccesary parametr

            stage.showAndWait();
    }
}
