package Services;

import Controllers.AdministratorPageController;
import Controllers.ModifyingMovieDetailsController;
import Controllers.MovieDetailsPageController;
import Controllers.MoviesPageController;
import Exceptions.CouldNotWriteUsersException;
import Model.Date;
import Model.Movie;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import org.apache.commons.io.FileUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class MovieService {

    private  static MoviesPageController mpc;
    private static AdministratorPageController apc;
    private  static MovieDetailsPageController mdpc;
    private static List<Movie> movies=new ArrayList<>();
    private static final Path USERS_PATH = FileSystemService.getPathToFile("config", "movies.json");
    private static List<Button> movieButton=new ArrayList<>();
    private static int ok;


    public static void loadMoviesFromFile() throws IOException {

        if (!Files.exists(USERS_PATH)) {
            FileUtils.copyURLToFile(MovieService.class.getClassLoader().getResource("movies.json"), USERS_PATH.toFile());
        }

        ObjectMapper objectMapper = new ObjectMapper();
        movies = objectMapper.readValue(USERS_PATH.toFile(), new TypeReference<List<Movie>>() {
        });
    }

    public static void injectmp(MoviesPageController u) {
        mpc= u;
        ok=1;
    }
    public static void injectmdpc(MovieDetailsPageController u) {
        mdpc= u;
    }


    public static void injectapc(AdministratorPageController u) {
        apc = u;
        ok=2;
    }

    public static ImageView DesignImage(String url){

        Image image= new Image(url);
        ImageView imageView= new ImageView(image);
        imageView.setFitWidth(260);
        imageView.setFitHeight(370);
        imageView.setStyle("-fx-padding-top: 10px;");
        Rectangle clip=new Rectangle();
        clip.setWidth(260);
        clip.setHeight(370);

        clip.setArcWidth(40);
        clip.setArcHeight(40);

        clip.setStroke(Color.BLACK);
        imageView.setClip(clip);


        return  imageView;
    }
    public static Button setMovie(Movie movie){

        ImageView imageView = DesignImage(movie.getImage());

        Button button=new Button("",imageView);
        button.setPrefSize(285,390);
        button.setStyle("-fx-border-color: transparent;-fx-background-color: transparent; ");
        button.setTooltip(new Tooltip(movie.getTitle()));
        button.setOnAction(e -> {
            try {
                if(ok==1)
                    setMovieDetails(movie);
                else
                    modifyMovieDetails(movie);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        return button;
    }

    private static void setMovieDetails(Movie movie) throws IOException {
        MovieDetailsPageController.setMovie(movie);

        Parent root = FXMLLoader.load(MovieService.class.getClassLoader().getResource("MovieDetailsPage.fxml"));
        Stage stage=new Stage();
        stage.setTitle("Movies Details Page");
        stage.setScene(new Scene(root, 1366,768));

        stage.show();
        Stage stage1 = (Stage) mpc.getTilePane().getScene().getWindow();
        stage1.close();
    }

    public static void createMovieButtons(){
        for (Movie movie : movies) {
            movieButton.add(setMovie(movie));

        }
    }

    public static void setMoviesButtons(){

        for (Button button : movieButton) {

            mpc.getTilePane().getChildren().add(button);

        }
    }

    public static void setMoviesAdmin(){

        for (Movie movie : movies) {
            apc.getTilePane().getChildren().add(setMovie(movie));
        }
    }

    public static void addMovie(Movie movie){
        movies.add(movie);
        persistMovies();
    }

    private static void persistMovies() {
        try {

            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(USERS_PATH.toFile(), movies);

        } catch (IOException e) {
            throw new CouldNotWriteUsersException();
        }
    }


    private static void modifyMovieDetails(Movie movie) throws IOException {
        ModifyingMovieDetailsController.setMovie(movie);

        Parent root = FXMLLoader.load(MovieService.class.getClassLoader().getResource("ModifyingMovieDetails.fxml"));
        Stage stage=new Stage();
        stage.setTitle("Modifying Movies Details Page");
        stage.setScene(new Scene(root, 1366,768));

        stage.show();
        Stage stage1 = (Stage) apc.getTilePane().getScene().getWindow();
        stage1.close();
    }
}
