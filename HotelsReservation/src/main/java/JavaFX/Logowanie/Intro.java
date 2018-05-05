package JavaFX.Logowanie;

import Core.DbService;
import JavaFX.Komunikaty.Blad;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.shape.*;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.Timer;
import java.util.TimerTask;


public class Intro
{
    static Stage stage;

    public static void show()
    {
        stage = new Stage();
        stage.setTitle("Welcome!");
        stage.setMinWidth(500);
        stage.setMinHeight(500);

        final Timeline timeline = new Timeline();

        Label l1 = new Label("Witamy!");
        l1.setStyle("-fx-font-size: 24;");

        Image image = new Image("logo.jpg");
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(400);
        imageView.setFitHeight(400);

        VBox vBox = new VBox();
        vBox.setPadding(new Insets(10, 10, 10, 10));
        vBox.setSpacing(5);
        vBox.setAlignment(Pos.CENTER);

        vBox.getChildren().addAll(l1, imageView);

        Scene scene = new Scene(vBox, 420, 400);
        stage.setScene(scene);
        stage.show();

        timeline.setCycleCount(1);
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(3500),
                new KeyValue (stage.opacityProperty(), 0.0)));
        timeline.play();

        stage.setOnCloseRequest(event ->
        {
            if(!DbService.isConnected())
            {
                Blad.show("Błąd połączenia!");
                stage.close();
                System.exit(1);
            }
            stage.close();
            Logowanie.logowanie();
        });

        timeline.setOnFinished(event ->
        {
            if(!DbService.isConnected())
            {
                Blad.show("Błąd połączenia!");
                stage.close();
                System.exit(1);
            }
            stage.close();
            Logowanie.logowanie();
        });

    }
}


