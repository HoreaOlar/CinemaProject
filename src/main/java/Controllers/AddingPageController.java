package Controllers;

import Exceptions.EmptyFieldException;
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
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AddingPageController {
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
    @FXML
    private Button addingButton;
    @FXML
    private Button addingDate;
    @FXML
    private  ChoiceBox<String> choiceBoxDay = new ChoiceBox<>();
    @FXML
    private ChoiceBox<String> choiceBoxHour = new ChoiceBox<>();
    @FXML
    private Text textEroare;

    private List<Date> date = new ArrayList<>();
    private Date data;
    private Movie movie;
    private double price1;
    private int seats1;

    @FXML
    public  void initialize(){
        choiceBoxDay.getItems().addAll("Monday","Tuesday","Wednesday","Thursday","Friday","Saturday","Sunday");
        choiceBoxDay.setValue("Monday");
        choiceBoxHour.getItems().addAll("10:00","12:00","14:00","16:00","18:00","20:00");
        choiceBoxHour.setValue("10:00");

    }


    public void setAdministratorPage() throws IOException {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("AdministratorPage.fxml"));
        Stage stage=new Stage();
        stage.setTitle("Administrator Page");
        stage.setScene(new Scene(root, 1366,768));
        stage.setResizable(false);

        MovieService.loadMoviesFromFile();
        MovieService.setMoviesAdmin();

        stage.show();
    }


    public void handleAddingDateAction() throws IOException{
        //salvarea datelor intr-o lista de date, care va fi ulterior adaugata intr-un obiect film
        data = new Date(choiceBoxDay.getValue(),choiceBoxHour.getValue());
        if(seats.getText().equals(""))
            seats1=0;
        else
            seats1= Integer.valueOf(seats.getText());
        data.setAvaliableSits(seats1);
        date.add(data);
    }


    public void handleAddingButtonAction() throws IOException, EmptyFieldException {
        //salvarea detaliilor filmului
        //crearea unui nou obiect film si adaugarea in json
        //am nevoie de un buton pentru adaugarea datei!

         try{
             if(!price.getText().equals(""))
                price1 = Double.valueOf(price.getText());
             else
                 throw new EmptyFieldException();
            movie = new Movie(CE(title.getText()), CE(description.getText()), CE(trailer.getText()), CE(image.getText()), CE(price1), date);
            MovieService.addMovie(movie);
            setAdministratorPage();
            Stage stage = (Stage) addingButton.getScene().getWindow();
            stage.close();
        }catch(EmptyFieldException e){
            textEroare.setText("Empty fields!");
        }
    }

    public String CE(String string) throws EmptyFieldException{ // Verifica daca exista campuri necompletate in formular
        if(string.equals(""))
            throw new EmptyFieldException();
        else
            return string;
    }
    public double CE(double d) throws EmptyFieldException{
        if(d==0)
            throw new EmptyFieldException();
        else
            return d;
    }

}
