package Controllers;

import Services.MovieService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;

import java.io.IOException;

public class AdministratorPageController {
    @FXML
    private TilePane tilePane;
    @FXML
    private Button button;
    @FXML
    private Button logOffButton;
    @FXML
    private Button seeWinningsButton;
    @FXML
    private void initialize(){
        MovieService.injectapc(this);
    }


    public void setAddingPage() throws IOException {

        Parent root;
        root = FXMLLoader.load(getClass().getClassLoader().getResource("AddingPage.fxml"));
        Stage stage=new Stage();
        stage.setTitle("Adding Page");
        stage.setScene(new Scene(root, 1366,768));
        stage.setFullScreen(false);

        stage.show();
    }

    public void setFirstPage() throws IOException{
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("FirstPage.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Cinema Application");
        stage.setScene(new Scene(root, 1366,768));
        stage.setFullScreen(false);
        stage.setResizable(false);
        stage.show();
    }

    public void setWinningsPage() throws IOException{
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("WinningsPage.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Winnings Page");
        stage.setScene(new Scene(root, 600,400));
        stage.setFullScreen(false);
        stage.setResizable(false);
        stage.show();
    }

    public void handleLogOffButtonAction() throws IOException{
        setFirstPage();
        Stage stage = (Stage) logOffButton.getScene().getWindow();
        stage.close();
    }

    public void handleAddButtonAction() throws IOException {
        setAddingPage();
        Stage stage = (Stage) button.getScene().getWindow();
        stage.close();
    }

    public void handleSeeWinningsButton() throws IOException{
        setWinningsPage();
    }

    public TilePane getTilePane() {
        return tilePane;
    }
}
