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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.TilePane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.io.IOException;

public class MovieDetailsAdminPageController {
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

    @FXML
    private Button deleteButton;

    @FXML
    private Button modifyScheduleButton;

    private static Movie movie;

    @FXML
    private void initialize() throws IOException {
        MovieService.injectamdpc(this);

        scroll1.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scroll1.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scroll2.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scroll2.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        Image img=new Image(movie.getImage());
        movieImage.setImage(img);
        movieDescription.setText(movie.getTitle()+ "\n"+ movie.getDescription());
        movieTrailer.getEngine().load(movie.getTrailer());
        //MovieService.setReviews(movie);


    }

    @FXML
    public void toAdministratorPage(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("AdministratorPage.fxml"));
        Stage stage=new Stage();
        stage.setTitle("Administrator Page");
        stage.setScene(new Scene(root, 1366,768));
        MovieService.setMoviesAdmin();
        movieTrailer.getEngine().load(null);
        stage.show();
        Stage stage1 = (Stage) backButton.getScene().getWindow();
        stage1.close();
    }

    public static void setMovie(Movie mov) {
        movie = mov;
    }

    public void setAdministratorPage() throws IOException {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("AdministratorPage.fxml"));
        Stage stage=new Stage();
        stage.setTitle("Administrator Page");
        stage.setScene(new Scene(root, 1366,768));
        stage.setFullScreen(false);

        MovieService.loadMoviesFromFile();
        MovieService.setMoviesAdmin();

        stage.show();
    }

    public void setModifyingMovieDetailsPage() throws IOException {
        ModifyingMovieDetailsController.setMovie(movie);

        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("ModifyingMovieDetails.fxml"));
        Stage stage=new Stage();
        stage.setTitle("Modifying Schedule Page");
        stage.setScene(new Scene(root, 600,400));
        stage.setFullScreen(false);

        stage.show();
    }
    public void handleDeleteButtonAction() throws IOException, InterruptedException {
        movieTrailer.getEngine().load(null);
        MovieService.deleteMovie(movie);
        setAdministratorPage();
        Stage stage= (Stage) deleteButton.getScene().getWindow();
        stage.close();
    }

    public void handleModifyButtonAction()  throws IOException{
        setModifyingMovieDetailsPage();
    }

    public TilePane getTilePaneReview() {
        return tilePaneReview;
    }

}
