package Services;

import Controllers.AdministratorPageController;
import Controllers.MovieDetailsAdminPageController;
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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
    //DECLARATIONS
    private static MoviesPageController mpc;
    private static AdministratorPageController apc;
    private static MovieDetailsPageController mdpc;
    private static MovieDetailsAdminPageController amdpc;

    private static List<Movie> movies=new ArrayList<>();
    private static List<Button> movieButton=new ArrayList<>();

    private static final Path MOVIES_PATH = FileSystemService.getPathToFile("config", "movies.json");

    private static String activUser;
    private static Movie activMovie;
    private static int which;


    //SAVE+ADD+DELETE MOVIES IN LIST/JSON
    /*********************************
     * SAVE MOVIES FROM FILE IN LIST *
     *********************************/
    public static void loadMoviesFromFile() throws IOException {
        if (!Files.exists(MOVIES_PATH)) {
            FileUtils.copyURLToFile(MovieService.class.getClassLoader().getResource("movies.json"), MOVIES_PATH.toFile());
        }

        ObjectMapper objectMapper = new ObjectMapper();
        movies = objectMapper.readValue(MOVIES_PATH.toFile(), new TypeReference<List<Movie>>() {
        });
    }

    /********************************
     * ADD A NEW MOVIE IN LIST *
     ********************************/
    public static void addMovie(Movie movie){
        movies.add(movie);
        persistMovies();
    }

    /***************************
     * ADD MOVIES IN JSON FILE *
     ***************************/
    public static void persistMovies() {
        try {

            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(MOVIES_PATH.toFile(), movies);

        } catch (IOException e) {
            throw new CouldNotWriteUsersException();
        }

    }

    /**********************************
     * DELETE THE MOVIE FROM JSON/LIST*
     **********************************/
    public static void deleteMovie(Movie movie){
        movies.remove(movie);
        persistMovies();
    }





    //GET CONTROLLERS
    /*******************************************************
     * PUT THE CONTROLLER IN A VARIABLE TO ACCESS TILEPANE *
     *******************************************************/
    public static void injectmp(MoviesPageController u) { mpc= u; which=1; }
    public static void injectapc(AdministratorPageController u) { apc = u; which=2; }
    public static void injectmdpc(MovieDetailsPageController u) {
        mdpc= u;
    }
    public static void injectamdpc(MovieDetailsAdminPageController u){ amdpc=u; }





    //MAKE BUTTONS FOR MOVIES
    /******************************************
     * DESIGN THT IMAGEVIEW FOR MOVIE BUTTONS *
     ******************************************/
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

        imageView.setClip(clip);

        return  imageView;
    }

    /*****************************
     * MAKE A BUTTON FOR A MOVIE *
     *****************************/
    public static Button setMovie(Movie movie){

        ImageView imageView = DesignImage(movie.getImage());

        Button button=new Button("",imageView);
        button.setPrefSize(285,390);
        button.setStyle("-fx-border-color: transparent;-fx-background-color: transparent; ");
        button.setTooltip(new Tooltip(movie.getTitle()));

        button.setOnAction(e -> {
            try {
                if(which==1)  setMovieDetails(movie);
                else
                    if(which ==2)  setMovieDetailsAdmin(movie);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        return button;
    }

    /*********************************************
     * CREATE A LIST WITH BUTTONS FOR EACH MOVIE *
     *********************************************/
    public static void createMovieButtons(){
        for (Movie movie : movies) {
            movieButton.add(setMovie(movie));
        }
    }





    //SHOW THE GUI FOR A MOVIE
    /*****************************************
     * SHOW THE GUI-DETAILS FOR CHOSEN MOVIE *
     *****************************************/
    private static void setMovieDetailsAdmin(Movie movie) throws IOException {
        MovieDetailsAdminPageController.setMovie(movie);

        Parent root = FXMLLoader.load(MovieService.class.getClassLoader().getResource("MovieDetailsAdminPage.fxml"));
        Stage stage=new Stage();
        stage.setTitle("Movies Details Page");
        stage.setScene(new Scene(root, 1366,768));

        stage.show();
        Stage stage1 = (Stage) apc.getTilePane().getScene().getWindow();
        stage1.close();
    }





    //WHEN PRESS A BUTTON GO TO THAT MOVIE
    /*****************************************
     * SHOW THE GUI-DETAILS FOR CHOSEN MOVIE *
     *****************************************/
    private static void setMovieDetails(Movie movie) throws IOException {
        MovieDetailsPageController.setMovie(movie);
        BuyTicketFormController.setMovie(movie);
        activMovie=movie;

        Parent root = FXMLLoader.load(MovieService.class.getClassLoader().getResource("MovieDetailsPage.fxml"));
        Stage stage=new Stage();
        stage.setTitle("Movies Details Page");
        stage.setScene(new Scene(root, 1366,768));
        stage.setResizable(false);

        stage.show();
        Stage stage1 = (Stage) mpc.getTilePane().getScene().getWindow();
        stage1.close();
    }





    //ADD BUTTONS IN TILEPANE
    /*******************************************
     * ADD THE BUTTONS IN TILEPANE TO SEE THEM *
     *******************************************/
    public static void setMoviesButtons(){
        for (Button button : movieButton) {
            mpc.getTilePane().getChildren().add(button);

        }
    }

    /*******************************************
     * ADD THE BUTTONS IN TILEPANE TO SEE THEM *
     *******************************************/
    public static void setMoviesAdmin(){
        for (Movie movie : movies) {
            apc.getTilePane().getChildren().add(setMovie(movie));
        }
    }





    //BUYING TICKETS
    /******************************************************
     * CHECK IF THE INORMATION FOR BUYING TICKETS ARE GOOD*
     ******************************************************/
    public static void checkBuy(String ticketsField, int avaliableSits, String cardNumberField) throws EmptyFieldException,ExceededSitsException,WrongCardNumberException{
        if(ticketsField.equals("")) throw new EmptyFieldException();
        if(Integer.parseInt(ticketsField)>avaliableSits) throw new ExceededSitsException();
        if(cardNumberField.length()!=16) throw new WrongCardNumberException();
    }





    //ADD REVIEW IN LIST/JSON/TILEPANE
    /*********************************************
     * ADD A NEW REVIEW FOR A MOVIE IN JSON/LIST *
     *********************************************/
    public static void addReviews(String title,String review) {
        for (Movie movie: movies)
            if(movie.getTitle().equals(title))
                movie.getComments().add(activUser+"\n"+review);
        persistMovies();
    }

    /****************************
     * ADD A REVIEW IN TILEPANE *
     ****************************/
    private static void setReview(String review) {
        TextArea textArea= new TextArea(review);
        textArea.setPrefWidth(752);
        textArea.setPrefHeight(200);
        textArea.setWrapText(true);
        textArea.setEditable(false);

        mdpc.getTilePaneReview().getChildren().add(textArea);
    }

    /*******************************************
     * ADD ALL REVIEWS IN TILEPANE FOR A MOVIE *
     *******************************************/
    public static void setReviews(Movie movie) {
        for(String review: movie.getComments())
            setReview(review);
    }




    //SET ACTIV USER
    /****************************
     * SET WHICH USER IS LOG IN *
     ****************************/
    public static void setActiveUser(String text) {
        activUser=text;
    }


    /*********************************************************************************
     * IF TICKETS ARE SELL, WE UPDATE (UN)AVALIABLE SITS FOR THAT MOVIE AT THAT DATE *
     *********************************************************************************/
    public static void setSits(Date date){
        if(movies.indexOf(activMovie)!=-1) //need for a test
        for (Date datem: movies.get(movies.indexOf(activMovie)).getDate())
            if(Objects.equals(datem.getHour(),date.getHour()) && Objects.equals(datem.getDay(),date.getDay()))
            {
                datem.setAvaliableSits(date.getAvaliableSits());
                datem.setOccupiedSits(date.getOccupiedSits());
            }

        persistMovies();

    }





    //CALCULATE THE WINNINGS
    /**********************************
     * CALCULATE WINNINGS FOR A MOVIE *
     **********************************/
    public static void setWinnings(String title, int nr){
        for(Movie i: movies)
            if(i.getTitle().equals(title))
                i.increaseWinnings(nr);

        persistMovies();
    }

    /****************************
     * CALCULATE TOTAL WINNINGS *
     ****************************/
    public  static double showTotalWinnings(){
        double sum=0;
        for(Movie i : movies)
            sum = sum + i.getWinnings();
        return sum;
    }

    public static List<Movie> getMovies(){
        return movies;
    }
}
