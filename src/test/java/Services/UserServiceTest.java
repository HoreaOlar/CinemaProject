package Services;

import Exceptions.EmptyFieldException;
import Exceptions.LoginFail;
import Exceptions.UsernameAlreadyExistException;
import Model.User;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import static org.junit.Assert.*;

public class UserServiceTest extends ApplicationTest {
    @BeforeClass
    public static void setupClass() {
        FileSystemService.APPLICATION_FOLDER = ".test-cinema";
        FileSystemService.initApplicationHomeDirIfNeeded();
    }

    @Before
    public void setUp() throws IOException {
        FileUtils.cleanDirectory(FileSystemService.getApplicationHomePath().toFile());
    }

    @Test
    public void testCopyDefaultFileIfNotExists() throws Exception {
        UserService.loadUsersFromFile();
        assertTrue(Files.exists(UserService.USERS_PATH));
    }

    @Test
    public void testLoadUserFromFile() throws IOException {
        UserService.loadUsersFromFile();
        assertNotNull(UserService.users);
        assertEquals(1, UserService.users.size());
    }

    @Test
    public void testAddOneUser() throws Exception {
        UserService.loadUsersFromFile();
        UserService.addUser("user1", "pass1");
        assertNotNull(UserService.users);
        assertEquals(2, UserService.users.size());
    }

    @Test
    public void testAddTwoUser() throws Exception {
        UserService.loadUsersFromFile();
        UserService.addUser("user1", "pass1");
        UserService.addUser("user2", "pass2");
        assertNotNull(UserService.users);
        assertEquals(3, UserService.users.size());
    }

    /*@Test(expected = UsernameAlreadyExistException.class)
    public void testAddUserAlreadyExists() throws Exception {
        UserService.loadUsersFromFile();
        UserService.addUser("user1", "pass1");
        assertNotNull(UserService.users);
        UserService.checkUsernameAlreadyExist("user1");
    }*/

    @Test(expected = EmptyFieldException.class)
    public void testEmtyField() throws Exception {
        UserService.checkEmptyField("","");
    }

    @Test(expected = LoginFail.class)
    public void testCheckLoginCredentials() throws Exception {
        UserService.loadUsersFromFile();
        UserService.addUser("user1", "pass1");
        assertNotNull(UserService.users);
        UserService.checkLoginCredentials("user2","pass2");
    }

    @Test
    public void testAddOneUserIsPersisted() throws Exception {
        UserService.loadUsersFromFile();
        UserService.addUser("user1", "pass1");
        List<User> users = new ObjectMapper().readValue(UserService.USERS_PATH.toFile(), new TypeReference<List<User>>() {
        });
        assertNotNull(users);
        assertEquals(2, users.size());
    }

    @Test
    public void testAddTwoUserArePersisted() throws Exception {
        UserService.loadUsersFromFile();
        UserService.addUser("user1", "pass1");
        UserService.addUser("user2", "pass2");
        List<User> users = new ObjectMapper().readValue(UserService.USERS_PATH.toFile(), new TypeReference<List<User>>() {
        });
        assertNotNull(users);
        assertEquals(3, users.size());
    }

    @Test
    public void testPasswordEncoding() {
        assertNotEquals("pass1", UserService.encodePassword("test1", "pass1"));
    }

}