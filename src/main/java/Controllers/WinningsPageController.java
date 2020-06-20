package Controllers;

import Services.MovieService;
import javafx.fxml.FXML;
import javafx.scene.layout.TilePane;
import javafx.scene.text.Text;

public class WinningsPageController {
    private TilePane tilePane;
    @FXML
    private Text text;

    public void WinningsPageController(){

    }

    @FXML
    public void initialize(){
        text.setText(String.valueOf(MovieService.showTotalWinnings()) + " $");
    }

    public TilePane getTilePane() {
        return tilePane;
    }
}
