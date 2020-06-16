import Services.MovieService;
import Services.UserService;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        UserService.loadUsersFromFile();
        MovieService.loadMoviesFromFile();
        MovieService.loadMoviesFromFile();
        MovieService.createMovieButtons();

        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("FirstPage.fxml"));
        primaryStage.setTitle("Cinema Application");
        primaryStage.setScene(new Scene(root, 1366,768));
        primaryStage.setFullScreen(false);
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
