package Services;

import Controllers.AdministratorPageController;
import Controllers.BuyTicketFormController;
import Controllers.MovieDetailsPageController;
import Controllers.MoviesPageController;
import Exceptions.CouldNotWriteUsersException;
import Exceptions.EmptyFieldException;
import Exceptions.ExceededSitsException;
import Exceptions.WrongCardNumberException;
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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
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
import java.util.Objects;

public class MovieService {

    private  static MoviesPageController mpc;
    private static AdministratorPageController apc;
    private  static MovieDetailsPageController mdpc;
    private static  BuyTicketFormController btfc;
    private static List<Movie> movies=new ArrayList<>();
    private static final Path USERS_PATH = FileSystemService.getPathToFile("config", "movies.json");
    private static List<Button> movieButton=new ArrayList<>();
    private static String activUser;
    private static Movie activMovie;



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
    public static void injectmdpc(MovieDetailsPageController u) {
        mdpc= u;
    }
    public static void injectapc(AdministratorPageController u) {
        apc = u;
    }
    public static void injecbtfc(BuyTicketFormController u) { btfc=u;}

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
                setMovieDetails(movie);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        return button;
    }

    private static void setMovieDetails(Movie movie) throws IOException {
        MovieDetailsPageController.setMovie(movie);
        BuyTicketFormController.setMovie(movie);
        activMovie=movie;

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

    public static void checkBuy(String ticketsField, int avaliableSits, String cardNumberField) throws Exception{
        if(ticketsField.equals("")) throw new EmptyFieldException();
        if(Integer.parseInt(ticketsField)>avaliableSits) throw new ExceededSitsException();
        if(cardNumberField.length()!=16) throw new WrongCardNumberException();
    }


    public String toString(){
        String string = new String("");
       // for()
        return string;
    }

    public static void persistMovies() {
        try {

            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(USERS_PATH.toFile(), movies);

        } catch (IOException e) {
            throw new CouldNotWriteUsersException();
        }

    }





    private static void setReview(String review) {
        TextArea textArea= new TextArea(review);
        textArea.setPrefWidth(752);
        textArea.setPrefHeight(200);
        textArea.setWrapText(true);
        textArea.setEditable(false);

        mdpc.getTilePaneReview().getChildren().add(textArea);
    }

    public static void setReviews(Movie movie) {
        for(String review: movie.getComments())
            setReview(review);
    }

    public static void addReviews(String title,String review) throws IOException {

        for (Movie movie: movies)
            if(movie.getTitle().equals(title))
                movie.getComments().add(activUser+"\n"+review);

        persistMovies();

    }

    public static void setActiveUser(String text) {
        activUser=text;
    }

    public static void setSits(Date date){
        for (Date datem: movies.get(movies.indexOf(activMovie)).getDate())
            if(Objects.equals(datem.getHour(),date.getHour()) && Objects.equals(datem.getDay(),date.getDay()))
            {
                datem.setAvaliableSits(date.getAvaliableSits());
                datem.setOccupiedSits(date.getOccupiedSits());
            }

        persistMovies();

    }

}
