package Controllers;

import Services.MovieService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;

import java.io.IOException;


public class MoviesPageController {
    @FXML
    private TilePane tilePane;
    @FXML
    private Button lofOffButton;
    @FXML
    private ScrollPane scrool;

    @FXML
    private void initialize()
    {
        scrool.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrool.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        MovieService.injectmp(this);
    }

    public void setFirstPage() throws IOException{
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("FirstPage.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Cinema Application");
        stage.setScene(new Scene(root, 1366,768));
        stage.setResizable(false);
        stage.show();
    }

    public void handleLogOffButton() throws IOException{
        setFirstPage();
        Stage stage = (Stage) lofOffButton.getScene().getWindow();
        stage.close();
    }

    public TilePane getTilePane() {
        return tilePane;
    }

}
