package JavaFX.Klienci;

import Core.Klienci;
import Engine.KlientEngine;
import Engine.PracownicyEngine;
import JavaFX.Komunikaty.Blad;
import JavaFX.Logowanie.Logowanie;
import JavaFX.Pracownicy.ListaKlientów;
import JavaFX.Pracownicy.ListaPokoi;
import JavaFX.Pracownicy.ListaRezerwacji;
import JavaFX.Rezerwuj.Rezerwuj;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class KlienciMenu
{
    private static Stage stage;
    public static void start()
    {
        if(KlientEngine.getKlienci() == null)
        {
            Blad.show("Zaloguj się!");
            stage.close();
            Logowanie.logowanie();
        }


        stage = new Stage();
        stage.setTitle("Menu klienta");
        stage.setMinWidth(300);
        stage.setMinHeight(300);

        Button mojeRezerwacje = new Button("Moje rezerwacje");
        Button nowaRezerwacja = new Button("Utwórz nową rezerwację");
        Button wyloguj = new Button("Wyloguj");

        String imie = "";
        String nazwisko = "";
        imie+=KlientEngine.klienci.getImie();
        nazwisko+=KlientEngine.klienci.getNazwisko();
        Label l1 = new Label("Zalogowales sie jako "+ imie + " " + nazwisko);

        VBox vBox = new VBox();
        vBox.setPadding(new Insets(10,10,10,10));
        vBox.setSpacing(100);

        HBox hBox1 = new HBox();
        hBox1.setAlignment(Pos.TOP_RIGHT);
        hBox1.getChildren().addAll(wyloguj);

        HBox hBox2 = new HBox();
        hBox2.setAlignment(Pos.CENTER);
        hBox2.getChildren().addAll(mojeRezerwacje,nowaRezerwacja);
        hBox2.setSpacing(50);


        vBox.getChildren().addAll(l1,hBox1,hBox2);
        Scene scene = new Scene(vBox,400,300);
        stage.setScene(scene);
        stage.show();

        stage.setOnCloseRequest(e->
        {
            stage.close();
            KlientEngine.setKlienci(null);
            Logowanie.logowanie();
        });

       wyloguj.setOnAction(e ->
        {
            stage.close();
            KlientEngine.setKlienci(null);
            Logowanie.logowanie();
        });

        mojeRezerwacje.setOnAction(e ->
        {
            ListaMoichRezerwacji.show();
        });
        nowaRezerwacja.setOnAction(e->
        {
            Rezerwuj.show();
        });

    }
}
