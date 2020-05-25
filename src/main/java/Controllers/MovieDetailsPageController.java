package Controllers;

import Model.Movie;
import Services.MovieService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;
import javafx.scene.text.Text;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.io.IOException;

public class MovieDetailsPageController {

    @FXML
    private ImageView movieImage;

    @FXML
    private Text movieDescription;

    @FXML
    private Button buyTicketButton;

    @FXML
    private WebView movieTrailer;

    @FXML
    private Button backButton;

    @FXML
    private TilePane addReviewPane;

    @FXML
    private TextField addReviewField;

    @FXML
    private Button addReviewButton;

    @FXML
    private Text ticketPriceText;

    private static Movie movie;

    @FXML
    private void initialize() throws IOException {
       Image img=new Image(movie.getImage());
       movieImage.setImage(img);
       movieDescription.setText(movie.getTitle()+ "\n"+ movie.getDescription());
       movieTrailer.getEngine().load(movie.getTrailer());
       ticketPriceText.setText(movie.getPrice()+"$/ticket");


    }

    @FXML
    void buyTicketAction(ActionEvent event) {

    }

    @FXML
    void addReviewAction(ActionEvent event) {

    }

    @FXML
    void toMoviesPageAction(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("MoviesPage.fxml"));
        Stage stage=new Stage();
        stage.setTitle("Movies Page");
        stage.setScene(new Scene(root, 1366,768));
        MovieService.setMoviesButtons();
        movieTrailer.getEngine().load(null);
        stage.show();
        Stage stage1 = (Stage) backButton.getScene().getWindow();
        stage1.close();
    }


    public static void setMovie(Movie mov) {
        movie = mov;
    }
}
