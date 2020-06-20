package Controllers;

import Exceptions.EmptyFieldException;
import Model.Date;
import Model.Movie;
import Services.FileSystemService;
import Services.MovieService;
import Services.UserService;
import javafx.scene.control.TextField;
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

public class AddingPageControllerTest extends ApplicationTest {
    private AddingPageController controller;

    private List<Date> date = new ArrayList<>();
    private Date data;
    private Movie movie;
    private double price1;
    private int seats1;

    @BeforeClass
    public static void setupClass() throws Exception {
        FileSystemService.APPLICATION_FOLDER = ".test-cinema";
        FileSystemService.initApplicationHomeDirIfNeeded();
        UserService.loadUsersFromFile();
    }

    @Before
    public void setUp() throws Exception {
        FileUtils.cleanDirectory(FileSystemService.getApplicationHomePath().toFile());
        UserService.loadUsersFromFile();
        controller = new AddingPageController();
        controller.movie = new Movie();
        controller.data = new Date();
        controller.title= new TextField();
        controller.description= new TextField();
        controller.trailer = new TextField();
        controller.image = new TextField();
        controller.price = new TextField();

    }

    @Test
    public void addingDateAction() throws IOException {
        controller.choiceBoxDay.setValue("Monday");
        controller.choiceBoxHour.setValue("10:00");
        controller.seats.setText("");
        controller.handleAddingDateAction();
        long l= new Long(controller.date.size());
        assertEquals(1,l);

    }

    @Test
    public void addingMovieAction() throws IOException, EmptyFieldException {
        controller.choiceBoxDay.setValue("Monday");
        controller.choiceBoxHour.setValue("10:00");
        controller.seats.setText("");
        controller.handleAddingDateAction();
        controller.title.setText("Titlu_test");
        controller.description.setText("Description_test");
        controller.image.setText("Image_test");
        controller.price1=10;
        controller.trailer.setText("Trailer_test");
        controller.movie = new Movie(controller.title.getText(),controller.description.getText(),controller.trailer.getText(),controller.image.getText(),controller.price1,controller.date);
        MovieService.addMovie(controller.movie);
        long l=new Long(MovieService.getMovies().size());
        assertEquals(1,l);
    }

}
