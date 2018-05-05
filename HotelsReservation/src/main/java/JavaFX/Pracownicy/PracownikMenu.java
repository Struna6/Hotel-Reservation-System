package JavaFX.Pracownicy;

import Core.Pracownicy;
import Engine.KlientEngine;
import Engine.PracownicyEngine;
import JavaFX.Komunikaty.Blad;
import JavaFX.Logowanie.Logowanie;
import JavaFX.Rejestracja.Rejestracja;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public final class PracownikMenu
{
    public static void start()
    {
        if(PracownicyEngine.getPracownik() == null)
        {
            Blad.show("Zaloguj się!");
            Logowanie.logowanie();
        }
        Stage stage = new Stage();
        stage.setTitle("Menu pracownika");
        stage.setMinWidth(500);
        stage.setMinHeight(500);

        Button pokojeBtn = new Button("Lista pokoi");
        Button klienciBtn = new Button("Lista klientów");
        Button rezerwacjeBtn = new Button("Lista rezerwacji");
        Button nowaRezerwacjaBtn = new Button("Utwórz nową rezerwację");

        Label l1 = new Label("Zalogowałeś się jako: " + PracownicyEngine.getPracownik().getImie() + " " +
        PracownicyEngine.getPracownik().getNazwisko()+"         ");
        Button wylogujBtn = new Button("Wyloguj");

        VBox vBox = new VBox();
        vBox.setPadding(new Insets(10,10,10,10));
        vBox.setSpacing(100);

        HBox hBox1 = new HBox();
        hBox1.setAlignment(Pos.CENTER_RIGHT);
        hBox1.getChildren().addAll(l1,wylogujBtn);

        HBox hBox2 = new HBox();
        hBox2.setAlignment(Pos.CENTER);
        hBox2.getChildren().addAll(pokojeBtn,klienciBtn,rezerwacjeBtn);
        hBox2.setSpacing(50);

        HBox hBox3 = new HBox();
        hBox3.setAlignment(Pos.CENTER);
        hBox3.getChildren().add(nowaRezerwacjaBtn);

        vBox.getChildren().addAll(hBox1,hBox2,hBox3);
        Scene scene = new Scene(vBox,600,400);
        stage.setScene(scene);
        stage.show();

        wylogujBtn.setOnAction(e ->
        {
            stage.close();
            PracownicyEngine.wyloguj();
            Logowanie.logowanie();
        });

        pokojeBtn.setOnAction(e ->
        {
            ListaPokoi.show();
        });

        klienciBtn.setOnAction(e ->
        {
            ListaKlientów.show();
        });

        rezerwacjeBtn.setOnAction(e ->
        {
            ListaRezerwacji.show();
        });

        nowaRezerwacjaBtn.setOnAction(e ->
        {
            Rejestracja.rejestrowanie(true);
        });

        stage.setOnCloseRequest(e ->
        {
            stage.close();
            PracownicyEngine.wyloguj();
        });
    }
}
