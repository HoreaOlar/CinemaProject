package Controllers;

import Services.MovieService;
import javafx.fxml.FXML;
import javafx.scene.layout.TilePane;


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
