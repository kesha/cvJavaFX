package kesha.cvjavafx;

import java.io.ByteArrayInputStream;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import kesha.cv.ComputerVision;

public class FXMLController implements Initializable {

    @FXML
    private Label label;
    @FXML
    private ImageView image;
    @FXML
    private ScrollPane sp;

    @FXML
    private void handleButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");
        byte[] pic = ComputerVision.doComputerVision();
        Image img = new Image(new ByteArrayInputStream(pic));
        image.setFitWidth(img.getWidth());
        image.setFitHeight(img.getHeight());
        image.setImage(img);
        label.setText(ComputerVision.info);
        sp.setContent(image);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
}
