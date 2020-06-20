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

public class FirstPageControllerTest extends ApplicationTest {
    public static final String TEST_USER = "user";
    public static final String TEST_PASSWORD = "pass";
    private FirstPageController controller;

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

        controller = new FirstPageController();
        controller.usernameField = new TextField();
        controller.passwordField = new PasswordField();
        controller.loginB= new Button();
        controller.registerB= new Button();
        controller.text= new Text();

        controller.passwordField.setText(TEST_PASSWORD);
        controller.usernameField.setText(TEST_USER);
    }

   /* @Test
    public void testRegistrationSuccess() {
        controller.handleRegisterAction();
        assertEquals(2, UserService.getUsers().size());
        assertEquals("Account created successfully!", controller.text.getText());
    }*/

    @Test
    public void testRegistrationSameUser() {
        controller.handleRegisterAction();
        controller.handleRegisterAction();
        assertEquals("An account with the username "+TEST_USER+" already exists!", controller.text.getText());
    }



}