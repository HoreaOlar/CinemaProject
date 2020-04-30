package Services;

import Exceptions.CouldNotWriteUsersException;
import Exceptions.EmptyFieldException;
import Exceptions.LoginFail;
import Exceptions.UsernameAlreadyExistException;
import Model.Movie;
import Model.User;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FileUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;

public class MovieService {
    private static List<Movie> movies;
    private static final Path USERS_PATH = FileSystemService.getPathToFile("config", "movies.json");



    public static void loadUsersFromFile() throws IOException {

        if (!Files.exists(USERS_PATH)) {
            FileUtils.copyURLToFile(UserService.class.getClassLoader().getResource("movies.json"), USERS_PATH.toFile());
        }

        ObjectMapper objectMapper = new ObjectMapper();
        movies = objectMapper.readValue(USERS_PATH.toFile(), new TypeReference<List<Movie>>() {
        });
    }





}
