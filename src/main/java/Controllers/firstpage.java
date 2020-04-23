package Controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import Services.UserService;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class firstpage {
    @FXML
    private Button loginB;
    @FXML
    private Button registerB;
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Text text;

    public void handleRegisterAction() {
        try {
            UserService.addUser(usernameField.getText(), passwordField.getText());
            text.setText("Account created successfully!");
        } catch (Exception e) {
            text.setText(e.getMessage());
        }
    }

    public void handleLoginAction() {
        try {
            UserService.checkLoginCredentials(usernameField.getText(),passwordField.getText());
            //go in the ,,Movies Page,,
            text.setText("Login successfully!");
        } catch (Exception e) {
            text.setText(e.getMessage());
        }
    }
}
