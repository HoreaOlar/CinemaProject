package Controllers;

import Services.MovieService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.TilePane;

public class AddingPageController {
    private TilePane tilePane;

    @FXML
    private TextField title;
    @FXML
    private TextField description;
    @FXML
    private TextField trailer;
    @FXML
    private TextField image;
    @FXML
    private TextField price;
    @FXML
    private TextField sits;
    @FXML
    private TextField dates;
    @FXML
    private Button addButton;

    public AddingPageController(){};
    private void initialize(){
        System.out.println("S-a initializat!");
    }
   /* private static void persistUsers() {
        try {

            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(MOVIES.toFile(), users);

        } catch (IOException e) {
            throw new CouldNotWriteUsersException();
        }
    } */

    public AddingPageController(TilePane tilePane) {
        this.tilePane = tilePane;
    }

    public TilePane getTilePane() {
        return tilePane;
    }
}
