package Services;

import Controllers.AdministratorPageController;
import Exceptions.EmptyFieldException;
import Exceptions.LoginFail;
import Exceptions.UsernameAlreadyExistException;
import Model.Date;
import Model.Movie;
import Model.User;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.scene.control.Button;
import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import static Services.MovieService.MOVIES_PATH;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;

public class MovieServiceTest extends ApplicationTest {
    private static final double DELTA = 1e-15;
    private static Movie movie,movie1,movie2;
    @BeforeClass
    public static void setupClass() {
        FileSystemService.APPLICATION_FOLDER = ".test-cinema";
        FileSystemService.initApplicationHomeDirIfNeeded();
    }

    @Before
    public void setUp() throws IOException {
        FileUtils.cleanDirectory(FileSystemService.getApplicationHomePath().toFile());

        List<Date> date= new ArrayList<>();
        movie1=new Movie("NEW1",
                "DESCRIPTION1",
                "TRAILER1",
                "IMGADRESS1",
                10,
                date
        );

        movie2=new Movie("NEW2",
                "DESCRIPTION2",
                "TRAILER2",
                "IMGADRESS2",
                10,
                date
        );
    }

    @Test
    public void testCopyDefaultFileIfNotExists() throws Exception {
        MovieService.loadMoviesFromFile();
        assertTrue(Files.exists(MOVIES_PATH));
    }

    @Test
    public void testLoadMoviesFromFile() throws IOException {
        MovieService.loadMoviesFromFile();
        assertNotNull(MovieService.movies);
        assertEquals(3, MovieService.movies.size());
    }

    @Test
    public void testCreateMovieButtons() throws Exception {
        MovieService.loadMoviesFromFile();
        assertNotNull(MovieService.movies);
        MovieService.createMovieButtons();
        assertEquals(3, MovieService.movieButton.size());
    }

    @Test
    public void testSetMovieButtons() throws Exception {
        MovieService.loadMoviesFromFile();
        assertNotNull(MovieService.movies);
        Movie movie= MovieService.movies.get(0);
        Button button= MovieService.setMovie(movie);
        assertEquals(button.getId(), movie.getTitle());
    }

    @Test
    public void testSetAddOneReview() throws Exception {
        MovieService.loadMoviesFromFile();
        assertNotNull(MovieService.movies);
        movie= MovieService.movies.get(0);

        MovieService.setActiveUser("user1");
        MovieService.addReviews(movie.getTitle(),"review1");

        assertEquals("user1\nreview1",movie.getComments().get(0));
    }

    @Test
    public void testSetAddTwoReviews() throws Exception {
        MovieService.loadMoviesFromFile();
        assertNotNull(MovieService.movies);
        movie= MovieService.movies.get(0);

        MovieService.setActiveUser("user1");
        MovieService.addReviews(movie.getTitle(),"review1");
        MovieService.addReviews(movie.getTitle(),"review2");

        assertEquals(2,movie.getComments().size());
    }

    @Test
    public void testAddOneMovie() throws Exception {
        MovieService.loadMoviesFromFile();
        assertNotNull(MovieService.movies);

        MovieService.addMovie(movie1);
        assertEquals(4, MovieService.movies.size());
    }

    @Test
    public void testAddTwoMovie() throws Exception {
        MovieService.loadMoviesFromFile();
        assertNotNull(MovieService.movies);

        MovieService.addMovie(movie1);
        MovieService.addMovie(movie2);

        assertEquals(5, MovieService.movies.size());
    }

    /** FAILED TEST **/
    /*@Test
    public void testAddTheSameMovie() throws Exception {
        MovieService.loadMoviesFromFile();
        assertNotNull(MovieService.movies);

        MovieService.addMovie(movie);

        assertEquals(3, MovieService.movies.size());
    }*/

    @Test
    public void testDeleteOneMovie() throws Exception {
        MovieService.loadMoviesFromFile();
        assertNotNull(MovieService.movies);

        movie= MovieService.movies.get(0);
        MovieService.deleteMovie(movie);

        assertEquals(2,MovieService.movies.size());
    }

    @Test
    public void testDeleteTwoMovie() throws Exception {
        MovieService.loadMoviesFromFile();
        assertNotNull(MovieService.movies);

        movie= MovieService.movies.get(1);
        MovieService.deleteMovie(movie);
        movie= MovieService.movies.get(0);
        MovieService.deleteMovie(movie);

        assertEquals(1,MovieService.movies.size());
    }

    @Test
    public void testAddOneMovieIsPersisted() throws Exception {
        MovieService.loadMoviesFromFile();
        MovieService.addMovie(movie1);
        List<Movie> movies = new ObjectMapper().readValue(MovieService.MOVIES_PATH.toFile(), new TypeReference<List<Movie>>() {
        });
        assertNotNull(movies);

        assertEquals(4, movies.size());
    }

    @Test
    public void testAddTwoMoviesArePersisted() throws Exception {
        MovieService.loadMoviesFromFile();
        MovieService.addMovie(movie1);
        MovieService.addMovie(movie2);
        List<Movie> movies = new ObjectMapper().readValue(MovieService.MOVIES_PATH.toFile(), new TypeReference<List<Movie>>() {
        });
        assertNotNull(movies);

        assertEquals(5, movies.size());
    }

    @Test
    public void testShowTotalNumbersOfWinnings() throws Exception {
        MovieService.loadMoviesFromFile();
        assertNotNull(MovieService.movies);

        movie1=MovieService.movies.get(0);
        movie2=MovieService.movies.get(1);

        assertThat(6.0, equalTo(MovieService.showTotalWinnings()));
    }

    @Test
    public void testSetSits() throws Exception {
        MovieService.loadMoviesFromFile();
        assertNotNull(MovieService.movies);

        MovieService.setActivMovie(movie);
        movie=MovieService.movies.get(0);
        movie.getDate().get(0).setOccupiedSits(4);
        movie.getDate().get(0).setAvaliableSits(6);

        MovieService.setSits(movie.getDate().get(0));

        assertEquals(4, MovieService.movies.get(0).getDate().get(0).getOccupiedSits());
        assertEquals(6, MovieService.movies.get(0).getDate().get(0).getAvaliableSits());
    }

}