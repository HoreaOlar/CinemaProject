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

    public void handleAddButtonAction() throws IOException {
        setAddingPage();
        Stage stage = (Stage) button.getScene().getWindow();
        stage.close();
    }
    public TilePane getTilePane() {
        return tilePane;
    }
}
