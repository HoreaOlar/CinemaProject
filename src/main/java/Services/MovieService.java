package Services;

import Controllers.AdministratorPageController;
import Controllers.MoviesPageController;
import Exceptions.CouldNotWriteUsersException;
import Model.Date;
import Model.Movie;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import org.apache.commons.io.FileUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class MovieService {

    private  static MoviesPageController mpc;
    private  static AdministratorPageController apc;
    private static List<Movie> movies=new ArrayList<>();
    private static final Path USERS_PATH = FileSystemService.getPathToFile("config", "movies.json");



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

    }
    public static void injectapc(AdministratorPageController u) {
        apc = u;
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
    public static Button setMovie(String url, String title){

        ImageView imageView = DesignImage(url);

        Button button=new Button("",imageView);
        button.setPrefSize(285,390);
        button.setStyle("-fx-border-color: transparent;-fx-background-color: transparent; ");
        button.setTooltip(new Tooltip(title));
        return button;
    }

    public static void setMovies(){

        for (Movie movie : movies) {
            mpc.getTilePane().getChildren().add(setMovie(movie.getImage(),movie.getTitle()));
        }
    }

    public static void setMoviesAdmin(){

        for (Movie movie : movies) {
            apc.getTilePane().getChildren().add(setMovie(movie.getImage(),movie.getTitle()));
        }
    }

    public static void addMovie(Movie movie){
        this.movies.add(movie);
    }
}
