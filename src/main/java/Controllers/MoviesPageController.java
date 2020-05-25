package Controllers;

import Services.MovieService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;

import java.io.IOException;


public class MoviesPageController {
    @FXML
    private TilePane tilePane;

    @FXML
    private void initialize(){
        MovieService.injectmp(this);
    }

    public TilePane getTilePane() {
        return tilePane;
    }


}
