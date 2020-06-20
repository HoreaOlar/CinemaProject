package Controllers;

import Services.MovieService;
import javafx.fxml.FXML;
import javafx.scene.layout.TilePane;
import javafx.scene.text.Text;

public class WinningsPageController {

    @FXML
    Text text;

    @FXML
    public void initialize(){
        text.setText(String.valueOf(MovieService.showTotalWinnings()) + " $");
    }

    public double getWinnings(){
        return MovieService.showTotalWinnings();
    }
}
