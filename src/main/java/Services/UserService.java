package Services;

import Exceptions.CouldNotWriteUsersException;
import Exceptions.EmptyFieldException;
import Exceptions.LoginFail;
import Exceptions.UsernameAlreadyExistException;
import Model.User;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FileUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class UserService {
    static List<User> users;
    public static final Path USERS_PATH = FileSystemService.getPathToFile("config", "users.json");

    /********************************
     * SAVE USERS FROM FILE IN LIST *
     ********************************/
    public static void loadUsersFromFile() throws IOException {
        if (!Files.exists(USERS_PATH)) {
            FileUtils.copyURLToFile(UserService.class.getClassLoader().getResource("users.json"), USERS_PATH.toFile());
        }

        ObjectMapper objectMapper = new ObjectMapper();
        users = objectMapper.readValue(USERS_PATH.toFile(), new TypeReference<List<User>>() {});
    }


    /**************************************
     * ADD A NEW USER IN LIST AND IN JSON *
     **************************************/
    public static void addUser(String username, String password) throws Exception {
        checkEmptyField(username,password);
        checkUsernameAlreadyExist(username);

        users.add(new User(username, encodePassword(username, password)));
        persistUsers();
    }


    /********************************************
     * CHECK IF THE FILED FROM GUY AREN'T EMPTY *
     ********************************************/
    public static void checkEmptyField(String username, String password) throws EmptyFieldException {
        if(username.equals("") || password.equals("")) throw new EmptyFieldException();
    }


    /****************************************************
     * CHECK IF THE USERNAME ALREDY EXISTS IN JSON/LIST *
     ****************************************************/
    public static void checkUsernameAlreadyExist(String username) throws UsernameAlreadyExistException {

        for (User user : users) {
            if (Objects.equals(username, user.getUsername()))
                throw new UsernameAlreadyExistException(username);
        }
    }


    /****************************
     * CHECK CORRECT CREDENTIALS*
     ****************************/
    public static void checkLoginCredentials(String username,String password) throws LoginFail{
        String encodePassword=encodePassword(username, password);

        int sw=0;
        for (User user : users) {
            if (Objects.equals(username, user.getUsername())) {
                sw=1;
                if (!Objects.equals(encodePassword, user.getPassword()))
                    throw new LoginFail();
            }
        }
        if(sw==0) throw new LoginFail(); /** IF USERNAME DOES'T EXIST IN LIST**/
    }


    /**************************
     * ADD USERS IN JSON FILE *
     **************************/
    private static void persistUsers() {
        try {

            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(USERS_PATH.toFile(), users);

        } catch (IOException e) {
            throw new CouldNotWriteUsersException();
        }
    }


    /*******************
     * ENCODE PASSWORD *
     *******************/
    public static String encodePassword(String salt, String password) {
        MessageDigest md = getMessageDigest();
        md.update(salt.getBytes(StandardCharsets.UTF_8));
        byte[] hashedPassword = md.digest(password.getBytes(StandardCharsets.UTF_8));


        return new String(hashedPassword, StandardCharsets.UTF_8)
                .replace("\"", ""); //to be able to save in JSON format
    }

    private static MessageDigest getMessageDigest() {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("SHA-512");
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("SHA-512 does not exist!");
        }
        return md;
    }





    /******************
     * NEED FOR TESTS *
     ******************/
    public static List<User> getUsers() {
        return  users;
    }
}
