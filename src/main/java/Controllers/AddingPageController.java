package Controllers;

import Model.Date;
import Model.Movie;
import Services.MovieService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AddingPageController {
    ObservableList<String> dayList=  FXCollections.observableArrayList("Monday","Tuesday");
    private TilePane tilePane;
    //static ObservableList<String> dayList = FXCollections.observableArrayList("Monday","Tuesday");

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
    private TextField seats;
    private Date data;
    private Movie movie;
    private double price1;
    private int seats1;
    @FXML
    private Button addingButton;
    @FXML
    private Button addingDate;
    @FXML
    private  ChoiceBox<String> choiceBoxDay = new ChoiceBox<>();
    @FXML
    private ChoiceBox<String> choiceBoxHour = new ChoiceBox<>();

    private List<Date> date = new ArrayList<>();

    @FXML
    public  void initialize(){

        choiceBoxDay.getItems().addAll("Monday","Tuesday","Wednesday","Thursday","Friday","Saturday","Sunday");
        choiceBoxDay.setValue("Monday");
        choiceBoxHour.getItems().addAll("10:00","12:00","14:00","16:00","18:00","20:00");
        choiceBoxHour.setValue("10:00");

    }
   /* private static void persistUsers() {
        try {

            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(MOVIES.toFile(), users);

        } catch (IOException e) {
            throw new CouldNotWriteUsersException();
        }
    } */
    public AddingPageController(){}
    public AddingPageController(TilePane tilePane) {
        this.tilePane = tilePane;
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

    public void handleAddingDateAction() throws IOException{
        //salvarea datelor intr-o lista de date, care va fi ulterior adaugata intr-un obiect film
        data = new Date(choiceBoxDay.getValue(),choiceBoxHour.getValue());
        date.add(data);
        System.out.println(data.toString());
    }

    public void handleAddingButtonAction() throws IOException{
        //salvarea detaliilor filmului
        //crearea unui nou obiect film si adaugarea in json
        //am nevoie de un buton pentru adaugarea datei!
        price1 = Double.valueOf(price.getText());
        seats1 = Integer.valueOf(seats.getText());
        movie =  new Movie(title.getText(),description.getText(),trailer.getText(),image.getText(),price1,seats1,date);
        MovieService.addMovie(movie);
        setAdministratorPage();
        Stage stage = (Stage) addingButton.getScene().getWindow();
        stage.close();
    }

    public TilePane getTilePane() {
        return tilePane;
    }
}
