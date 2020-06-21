package Controllers;

import Exceptions.EmptyFieldException;
import Exceptions.ExceededSitsException;
import Exceptions.WrongCardNumberException;
import Model.Date;
import Model.Movie;
import Services.MovieService;
import javafx.application.Platform;
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
    ImageView movieImage;
    @FXML
    TextField cardNumberField;
    @FXML
    Text errorText;
    @FXML
    ChoiceBox<String> dayChoiceBox;
    @FXML
    ChoiceBox<String> hourChoiceBox;
    @FXML
    TextField ticketsField;


    private static Movie activmovie;



    private Date chosenDate;



    private int swh=0;

    @FXML
    private void initialize() {
        Image image = new Image(activmovie.getImage(), 160, 200, false, false);
        movieImage.setImage(image);
        setDayChoiceBox();
        dayChoiceBox.getSelectionModel()
                .selectedItemProperty()
                .addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) ->
                {
                    hourChoiceBox.getItems().clear();
                    setHour(newValue);
                });

        hourChoiceBox.getSelectionModel()
                .selectedItemProperty()
                .addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) ->
                {
                    chosenDate=findDate(dayChoiceBox.getValue(), newValue);
                    swh=1;
                });

    }

    @FXML
    void buyTicketOnAction() {
        errorText.setText("");
        if(swh==0)
            errorText.setText("Please choose the date");
        else
        try {
            MovieService.checkBuy(ticketsField.getText(), chosenDate.getAvaliableSits(), cardNumberField.getText());

            chosenDate.setOccupiedSits(chosenDate.getOccupiedSits()+Integer.parseInt(ticketsField.getText()));
            chosenDate.setAvaliableSits(chosenDate.getAvaliableSits()-Integer.parseInt(ticketsField.getText()));

            MovieService.setSits(chosenDate);
            MovieService.setWinnings(activmovie.getTitle(),Integer.parseInt(ticketsField.getText()));

            errorText.setText("Thank you for buying");
        } catch (EmptyFieldException|ExceededSitsException|WrongCardNumberException e) {

            errorText.setText(e.getMessage());
            System.out.println();
        }
    }

    public void setDayChoiceBox(){
        for (Date date: activmovie.getDate())
            if(!dayChoiceBox.getItems().contains(date.getDay()))
                dayChoiceBox.getItems().add(date.getDay());

    }

    public void setHour(String newValue) {
        for(Date date: activmovie.getDate())
            if(date.getDay().equals(newValue))
                hourChoiceBox.getItems().add(date.getHour());
    }

    public  Date findDate(String day, String hour) {
        for(Date date: activmovie.getDate())
            if(Objects.equals(date.getDay(),day) && Objects.equals(date.getHour(),hour))
                return date;
        return  null;

    }

    public static void setMovie(Movie mov) {
        activmovie = mov;
    }
    public static Movie getActivmovie() { return activmovie; }
    public void setChosenDate(Date chosenDate) { this.chosenDate = chosenDate; }
    public void setSwh(int swh) {this.swh = swh; }
}
