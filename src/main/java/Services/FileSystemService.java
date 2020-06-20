package Services;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileSystemService {
    public static String APPLICATION_FOLDER = ".cinema";
    private static final String USER_FOLDER = System.getProperty("user.home");

    public static Path getPathToFile(String... path) {
        return getApplicationHomePath().resolve(Paths.get(".", path));
    }

    public static void initApplicationHomeDirIfNeeded() {
        if (!Files.exists(getApplicationHomePath()))
            getApplicationHomePath().toFile().mkdirs();
    }

    public static Path getApplicationHomePath() {
        return Paths.get(USER_FOLDER, APPLICATION_FOLDER);
    }
}
