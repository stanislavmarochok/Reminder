package sample;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class SignUpController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField signUpName;

    @FXML
    private TextField signUpSurname;

    @FXML
    private Button signUpButton;

    @FXML
    private TextField signUpLogin;

    @FXML
    private PasswordField signUpPassword;

    @FXML
    private RadioButton signUpRadioBoxMale;

    @FXML
    private RadioButton signUpRadioBoxFemale;

    @FXML
    private ToggleGroup gender;

    @FXML
    void initialize() {

        signUpButton.setOnAction(event -> {

            signUpNewUser();

        });
    }

    private void close() {

        signUpButton.getScene().getWindow().hide();

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/sample/sample.fxml"));

        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Parent root = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));

        stage.showAndWait();

    }

    private void signUpNewUser() {

        DatabaseHandler dbHandler = new DatabaseHandler();

        String firstName  = signUpName.getText();
        String surName  = signUpSurname.getText();
        String userName  = signUpLogin.getText();
        String password  = signUpPassword.getText();
        String gender  = "";

        if (signUpRadioBoxMale.isSelected())
            gender = "Male";
        else
            gender = "Female";

        User user = new User(firstName, surName, userName, password, gender);

        if(!firstName.equals("") || !surName.equals("") || !userName.equals("") || !password.equals("")){
            dbHandler.signUpUser(user);

            close();
        }
        else{
            System.out.println("Something is incorrect!");
        }


    }
}
