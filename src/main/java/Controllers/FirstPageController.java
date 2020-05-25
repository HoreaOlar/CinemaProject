package Controllers;

import Services.MovieService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import Services.UserService;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class FirstPageController {
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

    public void setMoviesPage() throws IOException {

        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("MoviesPage.fxml"));
        Stage stage=new Stage();
        stage.setTitle("Movies Page");
        stage.setScene(new Scene(root, 1366,768));
        stage.setFullScreen(true);
        MovieService.setMoviesButtons();

        stage.show();
    }

    public void setAdministratorPage() throws IOException {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("AdministratorPage.fxml"));
        Stage stage=new Stage();
        stage.setTitle("Administrator Page");
        stage.setScene(new Scene(root, 1366,768));
        stage.setFullScreen(true);
        stage.show();
    }


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
            Stage stage = (Stage) loginB.getScene().getWindow();
            stage.close();
            if(Objects.equals(usernameField.getText(),"admin"))
                setAdministratorPage();
            else
            setMoviesPage();
        } catch (Exception e) {
            text.setText(e.getMessage());
        }
    }
}
