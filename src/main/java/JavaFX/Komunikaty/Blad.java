package JavaFX.Komunikaty;

import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
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
        Stage stage = new Stage();
        stage.setTitle("Błąd");
        stage.setMinWidth(400);
        stage.setMinHeight(100);
        stage.initModality(Modality.APPLICATION_MODAL);

        Button btn = new Button("OK");
        Label l1 = new Label(komunikat);
        VBox vBox = new VBox(20);
        vBox.setAlignment(Pos.CENTER);

        vBox.getChildren().addAll(l1,btn);
        Scene scene = new Scene(vBox,400,100);
        stage.setScene(scene);

        btn.setOnAction(e ->
        {
            e.consume();
            stage.close();
        });

        stage.showAndWait();

    }
}
