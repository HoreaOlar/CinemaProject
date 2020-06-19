package Controllers;

import Model.Movie;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

public class BuyTicketFormController {

    @FXML
    private ImageView movieImage;

    @FXML
    private TextField cardNumberField;

    @FXML
    private Text errorText;

    @FXML
    private ChoiceBox<String> dayChoiceBox;

    @FXML
    private ChoiceBox<String> hourChoiceBox;

    @FXML
    private Button buyTicketOnAction;

    @FXML
    private TextField ticketsField;
    private static Movie movie;
    @FXML
    private void initialize(){
        Image image=new Image(movie.getImage(),160,200,false,false);
        movieImage.setImage(image);
    }

    public static void setMovie(Movie mov) {
        movie = mov;
    }
}
