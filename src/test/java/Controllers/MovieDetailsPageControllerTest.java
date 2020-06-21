package Controllers;

import Services.FileSystemService;
import Services.MovieService;
import Services.UserService;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import java.io.IOException;

import static org.junit.Assert.*;

public class MovieDetailsPageControllerTest extends ApplicationTest {
    public static final String TEST_REVIEW = "review";
    private MovieDetailsPageController controller;

    @BeforeClass
    public static void setupClass() throws Exception {
        FileSystemService.APPLICATION_FOLDER = ".test-cinema";
        FileSystemService.initApplicationHomeDirIfNeeded();
        MovieService.loadMoviesFromFile();
    }

    @Before
    public void setUp() throws Exception {
        FileUtils.cleanDirectory(FileSystemService.getApplicationHomePath().toFile());
        MovieService.loadMoviesFromFile();

        controller = new MovieDetailsPageController();
        controller.addReviewText= new TextArea();

        controller.addReviewText.setText(TEST_REVIEW);
    }

    @Test
    public void testSetMovie() {
        controller.setMovie(MovieService.getMovies().get(0));
        assertEquals(controller.movie, MovieService.getMovies().get(0));
    }

    @Test
    public void testAddReviewAction() throws IOException {
        controller.setMovie(MovieService.getMovies().get(0));
        controller.addReviewAction();
        assertEquals(1,controller.movie.getComments().size());
        assertEquals("",controller.addReviewText.getText());
    }
}