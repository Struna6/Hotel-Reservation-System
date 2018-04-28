package JavaFX.Komunikaty;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class Potwierdzenie
{
    static public boolean akc = false;

    static public void show(String message)
    {
        Stage stage = new Stage();
        stage.setTitle("Potwierdzenie");
        stage.setMinWidth(400);
        stage.setMinHeight(100);
        stage.initModality(Modality.APPLICATION_MODAL);

        Button btnT = new Button("Tak");
        Button btnN = new Button("Nie");
        Label l1 = new Label(message);
        VBox vBox = new VBox(20);
        vBox.setAlignment(Pos.CENTER);

        HBox hBox = new HBox(20);
        hBox.getChildren().addAll(btnT, btnN);
        hBox.setAlignment(Pos.CENTER);

        vBox.getChildren().addAll(l1, hBox);
        Scene scene = new Scene(vBox, 400, 100);
        stage.setScene(scene);

        btnT.setOnAction(e ->
        {
            e.consume();
            stage.close();
            akceptacja(true);
        });

        btnN.setOnAction(e ->
        {
            e.consume();
            stage.close();
            akceptacja(false);
        });

        stage.showAndWait();
    }

        private static void akceptacja(boolean akceptuj)
        {
            akc = akceptuj;
        }
}
