package JavaFX.Komunikaty;

import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;



public class Blad
{
    static public void show(String komunikat)
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(komunikat);
        alert.setTitle("Błąd!");
        alert.showAndWait();
    }
}
