package Controllers;

import Model.Date;
import Model.Movie;

import Services.MovieService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.IOException;


public class ModifyingMovieDetailsController {
    private static Movie movie;

    @FXML
    private Button deleteButton;
    @FXML
    private Button addButton;
    @FXML
    private ChoiceBox<String> choiceBoxDay;
    @FXML
    private ChoiceBox<String> choiceBoxHour;

    private Date data;
    @FXML
    private TextField seats;
    private int seats1;
    private int ok=1;
    @FXML
    public void initialize(){
        choiceBoxDay.getItems().addAll("Monday","Tuesday","Wednesday","Thursday","Friday","Saturday","Sunday");
        choiceBoxDay.setValue("Monday");
        choiceBoxHour.getItems().addAll("10:00","12:00","14:00","16:00","18:00","20:00");
        choiceBoxHour.setValue("10:00");
    }

    public static void setMovie(Movie mov) {
        movie = mov;
    }

    public void handleAddButtonAction() throws IOException{
        if(!(choiceBoxDay.getValue().equals("") && choiceBoxHour.getValue().equals("")))
        {
            data = new Date(choiceBoxDay.getValue(),choiceBoxHour.getValue());
            seats1 = Integer.valueOf(seats.getText());
            data.setAvaliableSits(seats1);
            for(Date date : movie.getDate()) {
                if ((data.getHour().equals(date.getHour())) && (data.getDay().equals(date.getDay())))
                    ok = 0;
            }
            if(ok==1) {
                movie.addDate(data);
                MovieService.persistMovies();
            }
        }

    }

    public void handleDeleteButtonAction() throws IOException {
        if (!(choiceBoxDay.getValue().equals("") && choiceBoxHour.getValue().equals(""))) {
            movie.deleteDate(choiceBoxDay.getValue(),choiceBoxHour.getValue());
            MovieService.persistMovies();
        }
    }
}
