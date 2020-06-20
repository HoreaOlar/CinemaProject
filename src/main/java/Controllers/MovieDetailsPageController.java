package Controllers;

import Model.Movie;
import Services.MovieService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.TilePane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.io.IOException;

public class MovieDetailsPageController {


    @FXML
    private Text movieDescription;

    @FXML
    private Text ticketPriceText;

    @FXML
    private WebView movieTrailer;

    @FXML
    private TextArea addReviewText;

    @FXML
    private Button addReviewButton;

    @FXML
    private ImageView movieImage;

    @FXML
    private ScrollPane scroll1;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private Button buyTicketButton;

    @FXML
    private Button backButton;

    @FXML
    private Rectangle rectangle;

    @FXML
    private TilePane tilePaneReview;

    @FXML
    private ScrollPane scroll2;

    private static Movie movie;

    @FXML
    private void initialize() throws IOException {
        MovieService.injectmdpc(this);

        scroll1.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scroll1.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scroll2.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scroll2.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        Image img=new Image(movie.getImage());
        movieImage.setImage(img);
        movieDescription.setText(movie.getTitle()+ "\n"+ movie.getDescription());
        movieTrailer.getEngine().load(movie.getTrailer());
        ticketPriceText.setText(movie.getPrice()+"$/ticket");
        MovieService.setReviews(movie);


    }

    @FXML
    void buyTicketAction(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("BuyTicketForm.fxml"));
        Stage stage=new Stage();
        stage.setTitle("Buy Tickets Page");
        stage.setScene(new Scene(root, 600,400));
        stage.show();

    }

    @FXML
    void addReviewAction(ActionEvent event) throws IOException {
        MovieService.addReviews(movie.getTitle(),addReviewText.getText());
        addReviewText.clear();

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



    public TilePane getTilePaneReview() {
        return tilePaneReview;
    }
}
