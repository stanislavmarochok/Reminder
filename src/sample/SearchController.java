package sample;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class SearchController {

    private Pane paneContainer;
    private Label paneLabel;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane appAnchorPane;

    @FXML
    private Label appUsername;

    @FXML
    private AnchorPane appMainAP;

    @FXML
    private GridPane appGridPane;

    @FXML
    private Button findSearchButton;

    @FXML
    private TextField findTextField;

    @FXML
    void initialize(String username) {
        findSearchButton.setOnAction(event -> {
            String text = findTextField.getText();

            startSearching(username, text);
        });
    }

    private void startSearching(String username, String text) {
        DatabaseAddingNotes dbHandler = new DatabaseAddingNotes();
        Note note = new Note();
        note.setUsername(username);

        ResultSet result = dbHandler.getNote(note, 3, text, 0);

        ScrollPane scroller = new ScrollPane();
        scroller.setFitToWidth(true);

        int counter = 0;
        try {
            while (result.next()) {
                System.out.println(result.getString(3));

                paneLabel = new Label();
                paneLabel.setMaxHeight(10.0);
                paneLabel.setMaxWidth(500.0);
                paneLabel.setMinWidth(500.0);
                paneLabel.setTextOverrun(OverrunStyle.ELLIPSIS);
                paneLabel.setText(result.getString(3));
                paneLabel.setStyle("-fx-background-color: #" + "F3C22E");
                paneLabel.setPadding(new Insets(10));

                appGridPane.add(paneLabel, 0, counter);
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
                int index = row;
                System.out.println(index);


                ResultSet temp_result = dbHandler.getNote(note, 3, text, index);

                int temp_index = 0;
                try {
                    while (temp_result.next()) {
                        if (temp_index == index){
                            String new_text = temp_result.getString(3);
                            int por_num = Integer.parseInt(temp_result.getString(1));
                            System.out.println(text + " " + por_num);
                            openEditWindow(username, new_text, por_num);
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
    }

private void openEditWindow(String loginText, String text, int por_num) {

        findSearchButton.getScene().getWindow().hide();

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
