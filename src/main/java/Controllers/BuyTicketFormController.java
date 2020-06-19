package Controllers;

import Model.Date;
import Model.Movie;
import Services.MovieService;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import java.util.Objects;

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
    private TextField ticketsField;

    private static Movie activmovie;
    private Date chosenDate;

    @FXML
    private void initialize(){

        Image image=new Image(activmovie.getImage(),160,200,false,false);
        movieImage.setImage(image);
        setDayChoiceBox();
        dayChoiceBox.getSelectionModel()
                .selectedItemProperty()
                .addListener( (ObservableValue<? extends String> observable, String oldValue, String newValue) ->
                {
                    hourChoiceBox.getItems().clear();
                    setHour(newValue);
                    // setChosenTrainer(UserService.setByTrainer(newValue));

                });

        hourChoiceBox.getSelectionModel()
                .selectedItemProperty()
                .addListener( (ObservableValue<? extends String> observable, String oldValue, String newValue) ->
                {
                    findDate(dayChoiceBox.getValue(),newValue);
                });

    }

    @FXML
    void buyTicketOnAction(ActionEvent event) {

    }


    public static void setMovie(Movie mov) {
        activmovie = mov;
    }

    void setDayChoiceBox(){
        for (Date date: activmovie.getDate())
            if(!dayChoiceBox.getItems().contains(date.getDay()))
                dayChoiceBox.getItems().add(date.getDay());

    }


    public ChoiceBox<String> getHourChoiceBox() {
        return hourChoiceBox;
    }

    public  Date findDate(String day, String hour) {
        for(Date date: activmovie.getDate())
            if(Objects.equals(date.getDay(),day) && Objects.equals(date.getHour(),hour))
                return date;
        return  null;

    }

    public void setHour(String newValue) {
        for(Date date: activmovie.getDate())
            if(date.getDay().equals(newValue))
                getHourChoiceBox().getItems().add(date.getHour());
    }



}
