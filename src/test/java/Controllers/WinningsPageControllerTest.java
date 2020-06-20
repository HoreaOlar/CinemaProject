package Controllers;

import Services.FileSystemService;
import Services.UserService;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;
import static org.junit.Assert.*;

public class WinningsPageControllerTest extends ApplicationTest {
    public WinningsPageController controller;

    @BeforeClass
    public static void setupClass() throws Exception {
        FileSystemService.APPLICATION_FOLDER = ".test-registration-example";
        FileSystemService.initApplicationHomeDirIfNeeded();
        UserService.loadUsersFromFile();
    }

    @Before
    public void setUp() throws Exception {
        FileUtils.cleanDirectory(FileSystemService.getApplicationHomePath().toFile());
        UserService.loadUsersFromFile();
        controller = new WinningsPageController();
        controller.text = new Text();
    }

    @Test
    public void testWinnings(){
        controller.initialize();
        double d = new Double(controller.getWinnings());
        assertEquals(0.0,d,0.1);
    }
}
