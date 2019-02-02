package sample;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.OverrunStyle;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import javax.xml.soap.Text;

public class HomeController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane appMainAP;

    @FXML
    private GridPane appGridPane;

    @FXML
    private Label appUsername;

    @FXML
    private ImageView imageButtonHome;

    @FXML
    private Button appAddNote;

    @FXML
    private Button appFindNote;

    private Pane paneContainer;
    private Label paneLabel;

    @FXML
    void initialize(String username) {

        appUsername.setText(username);

        appAddNote.setOnAction(event -> {
            openNewWindow("/sample/addNote.fxml", username);
        });

        appFindNote.setOnAction(event -> {
            openSearchWindow("/sample/findNote.fxml", username);
        });

        printNotes(username);

    }

    private void openSearchWindow(String window, String username) {


        appFindNote.getScene().getWindow().hide();

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(window));

        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Parent root = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));

        SearchController controllerEditBook = loader.getController(); //получаем контроллер для второй формы
        controllerEditBook.initialize(username); // передаем необходимые параметры

        stage.show();
    }


    public void openNewWindow(String window, String loginText){


        appAddNote.getScene().getWindow().hide();

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(window));

        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Parent root = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));

        AddNoteController controllerEditBook = loader.getController(); //получаем контроллер для второй формы
        controllerEditBook.initialize(loginText); // передаем необходимые параметры

        stage.show();
    }

    private void printNotes(String loginText) {
        DatabaseAddingNotes dbHandler = new DatabaseAddingNotes();
        Note note = new Note();
        note.setUsername(loginText);

        ResultSet result = dbHandler.getNote(note, 0, null, 0);

        ScrollPane scroller = new ScrollPane();
        scroller.setFitToWidth(true);

        int counter = 0;
        try {
            while (result.next()) {
                System.out.println(result.getString(3));

                paneLabel = new Label();
                paneLabel.setMaxHeight(10.0);
                paneLabel.setMaxWidth(125.0);
                paneLabel.setMinWidth(125.0);
                paneLabel.setTextOverrun(OverrunStyle.ELLIPSIS);
                paneLabel.setText(result.getString(3));
                paneLabel.setStyle("-fx-background-color: #" + "F3C22E");
                paneLabel.setPadding(new Insets(40));

                appGridPane.add(paneLabel, (counter < 4) ? counter : counter % 4, counter / 4);
                appGridPane.setMargin(paneLabel, new Insets(10));

                counter++;
            }
        }catch(SQLException e){
            e.printStackTrace();
        }

        for (Node node : appGridPane.getChildren()) {
            node.setOnMouseClicked((MouseEvent t) -> {

                int row = appGridPane.getRowIndex(node);
                int column = appGridPane.getColumnIndex(node);
                int index = row * 4 + column;


                ResultSet temp_result = dbHandler.getNote(note, 0, null, 0);

                int temp_index = 0;
                try {
                    while (temp_result.next()) {
                        if (temp_index == index){
                            String text = temp_result.getString(3);
                            int por_num = Integer.parseInt(temp_result.getString(1));
                            System.out.println(text + " " + por_num);
                            openEditWindow(loginText, text, por_num);
                            break;
                        }

                        temp_index++;
                    }
                }catch(SQLException e){
                    e.printStackTrace();
                }


            });

            node.setOnMouseEntered(event -> {
                node.setStyle("-fx-background-color: #" + "FFD044");
            });
            node.setOnMouseExited(event -> {
                node.setStyle("-fx-background-color: #" + "F3C22E");
            });
        }

        if (counter >= 1){
            System.out.println("Success!!");

        }
        else{
            System.out.println("Enter valid login or password");
        }
    }

    private void openEditWindow(String loginText, String text, int por_num) {

        appAddNote.getScene().getWindow().hide();

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/sample/edit.fxml"));

        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Parent root = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));

        EditController controllerEditBook = loader.getController(); //получаем контроллер для второй формы
        controllerEditBook.initialize(loginText, text, por_num); // передаем необходимые параметры

        stage.show();
    }
}
