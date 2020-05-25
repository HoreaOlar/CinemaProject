package Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;
import javafx.scene.text.Text;
import javafx.scene.web.WebView;

public class MovieDetailsPageController {

    @FXML
    private ImageView movieImage;
    @FXML
    private Text movieDescription;
    @FXML
    private Button buyTicketButton;
    @FXML
    private WebView movieTrailer;
    @FXML
    private TextField buyTicketField;
    @FXML
    private TilePane addReviewPane;
    @FXML
    private TextField addReviewField;
    @FXML
    private Button addReviewButton;
    @FXML
    private void initialize(){
        Image img= new Image("https://images-na.ssl-images-amazon.com/images/I/71pCGJSF0lL._AC_SY741_.jpg");
        movieImage.setImage(img);
        movieTrailer.getEngine().load("https://content.video.imedia.ro/storage/movie/e1/gw/3x/1jzae1gw3x_mp4_720p_1579264801.mp4");
        movieDescription.setText("Title:The BeST mOvie Ever"+"\n"+"Acest film nu este boring, este the best of the best, desi imaginea sigur 100%, nu se potriveste cu trailerul, si asta nu e o descriere pana ii lumea, csf, ncsf, va doresc vizionare placuta si sper sa va placa filmul :))))");
    }

}
