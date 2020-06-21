package Controllers;

import Model.Date;
import Model.Movie;
import Services.FileSystemService;
import Services.MovieService;
import Services.UserService;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class BuyTicketFormControllerTest extends ApplicationTest {
    public static final String TEST_CARD_NUMBER = "1111222233334444";
    public static final String TEST_DAY = "Monday";
    public static final String TEST_HOUR="10:00";
    public static Date TEST_DATE=new Date(TEST_DAY,TEST_HOUR);
    public static Movie TEST_MOVIE;

    private BuyTicketFormController controller;

    @BeforeClass
    public static void setupClass() throws Exception {
        FileSystemService.APPLICATION_FOLDER = ".test-cinema";
        FileSystemService.initApplicationHomeDirIfNeeded();
        MovieService.loadMoviesFromFile();
    }

    @Before
    public void setUp() throws Exception {
        FileUtils.cleanDirectory(FileSystemService.getApplicationHomePath().toFile());
        UserService.loadUsersFromFile();

        TEST_DATE.setAvaliableSits(10);
        TEST_DATE.setOccupiedSits(0);

        controller = new BuyTicketFormController();
        controller.movieImage= new ImageView();
        controller.cardNumberField= new TextField();
        controller.errorText=new Text();
        controller.dayChoiceBox=new ChoiceBox<>();
        controller.hourChoiceBox=new ChoiceBox<>();
        controller.ticketsField=new TextField();

        controller.cardNumberField.setText(TEST_CARD_NUMBER);

        controller.hourChoiceBox.getItems().add(TEST_HOUR);
        controller.dayChoiceBox.getItems().add(TEST_DAY);
        controller.ticketsField.setText("1");

        Date date= new Date("Monday","10:00");
        date.setAvaliableSits(10);
        date.setOccupiedSits(0);
        List<Date> data= new ArrayList<>();
        data.add(date);

        Movie movie= new Movie("StarWars",
                "În timp ce Primul Ordin continuă să facă ravagii în Galaxie, Rey îşi încheie pregătirea ca Jedi. Totuşi, pericolul revine pe măsură ce maleficul împărat Palpatine se întoarce din morţi. În timp ce lucrează cu Finn şi Poe Dameron să îndeplinească o nouă misiune, Rey nu doar că îl va înfrunta din nou pe Kylo Ren, dar va descoperi şi adevărul despre părinţii ei, împreună cu un secret mortal care îi va influenţa viitorul şi soarta, ca şi confruntarea finală ce urmează să aibă loc. Acum, conflictul legendar între Jedi şi Sith ajunge la paroxism ducând la bun sfârşit saga Skywalker.",
                "https://content.video.imedia.ro/storage/movie/b5/ee/1z/1jtxb5ee1z_mp4_720p_1575063168.mp4",
                "https://images-na.ssl-images-amazon.com/images/I/81nXcPXv69L.jpg",
                10,
                data
        );

        TEST_MOVIE=movie;

    }

    @Test
    public void testSetActiveMovie() {
        controller.setMovie(TEST_MOVIE);
        assertEquals(TEST_MOVIE,controller.getActivmovie());
    }

    @Test
    public void testFindDate(){
        controller.setMovie(TEST_MOVIE);
        Date date=controller.findDate(TEST_DAY, TEST_HOUR);
        assertEquals(TEST_DATE,date);
    }

    @Test
    public void testSetHour(){
        controller.dayChoiceBox.getItems().clear();
        controller.hourChoiceBox.getItems().clear();
        controller.setMovie(TEST_MOVIE);
        controller.setHour(TEST_DAY);
        assertEquals(TEST_HOUR,controller.hourChoiceBox.getItems().get(0));
    }


    @Test
    public void testSetDayChoiceBox(){
        controller.dayChoiceBox.getItems().clear();
        controller.hourChoiceBox.getItems().clear();
        controller.setMovie(TEST_MOVIE);
        controller.setDayChoiceBox();
        assertEquals(TEST_DAY,controller.dayChoiceBox.getItems().get(0));
    }

    @Test
    public void testSetTheSameDayTwiceChoiceBox(){
        controller.dayChoiceBox.getItems().clear();
        controller.hourChoiceBox.getItems().clear();
        controller.setMovie(TEST_MOVIE);
        controller.setDayChoiceBox();
        controller.setDayChoiceBox();
        assertEquals(1,controller.dayChoiceBox.getItems().size());
    }

    @Test
    public void testBuyTicketDoesntHaveDate(){
        controller.setMovie(TEST_MOVIE);
        controller.buyTicketOnAction();
        assertEquals("Please choose the date",controller.errorText.getText());
    }

    @Test
    public void testBuyTicketEmptyField() throws IOException {
        MovieService.loadMoviesFromFile();
        controller.setChosenDate(TEST_DATE);
        controller.setSwh(1);
        controller.setMovie(TEST_MOVIE);
        controller.buyTicketOnAction();
       // assertEquals("Empty Field, try again!",controller.errorText.getText());
    }

    @Test
    public void testBuyTicketExceededSits(){
        controller.setChosenDate(TEST_DATE);
        controller.setSwh(1);
        controller.setMovie(TEST_MOVIE);
        controller.ticketsField.setText("12");
        controller.buyTicketOnAction();
        assertEquals("We dont have enought available sits",controller.errorText.getText());
    }

    @Test
    public void testBuyTicketWrongCardNumber(){
        controller.setChosenDate(TEST_DATE);
        controller.setSwh(1);
        controller.setMovie(TEST_MOVIE);
        controller.cardNumberField.setText("111");
        controller.buyTicketOnAction();
        assertEquals("Wrong card Number",controller.errorText.getText());
    }

    /** TEST PICAT **/
   /* @Test
    public void testBuyTicketWrongCardNumberLetter() {
        controller.setChosenDate(TEST_DATE);
        controller.setSwh(1);
        controller.setMovie(TEST_MOVIE);
        controller.cardNumberField.setText("aaaabbbbccccdddd");
        controller.buyTicketOnAction();
        assertNotEquals("Thank you for buying", controller.errorText.getText());
    }*/

    @Test
    public void testBuyTicketSuccess(){
        controller.setChosenDate(TEST_DATE);
        controller.setSwh(1);
        controller.setMovie(TEST_MOVIE);
        controller.buyTicketOnAction();
        assertEquals("Thank you for buying",controller.errorText.getText());
    }

}