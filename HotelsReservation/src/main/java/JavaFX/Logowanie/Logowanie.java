package JavaFX.Logowanie;

import Core.DbService;
import Core.Klienci;
import Core.Pracownicy;
import Engine.KlientEngine;
import Engine.PracownicyEngine;
import JavaFX.Komunikaty.Blad;
import JavaFX.Komunikaty.Potwierdzenie;
import JavaFX.Rejestracja.Rejestracja;
import com.itextpdf.text.Paragraph;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.mindrot.jbcrypt.BCrypt;

import javax.persistence.Convert;

public class Logowanie
{
    static private TextField login;
    static private PasswordField haslo;
    static private Stage stage;
    public static void logowanie()
    {

        stage = new Stage();
        stage.setTitle("Hotel - Rezerwacje");
        stage.setMinWidth(300);
        stage.setMinHeight(300);

        Button zaloguj = new Button("Zaloguj się");
        zaloguj.setOnAction(e->login());
        Button rejestruj = new Button("Rejestruj się");
        rejestruj.setOnAction(e-> Rejestracja.rejestrowanie(false));

        Label l1 = new Label("Zaloguj sie bądź zarejestruj w w naszym systemie!");
        Label l2 = new Label("Login: ");
        login = new TextField();
        login.setPromptText("Wpisz swoje id");
        Label l3 = new Label("Hasło: ");
        haslo = new PasswordField();
        haslo.setPromptText("Wpisz swoje haslo");

        VBox vBox = new VBox();
        vBox.setPadding(new Insets(10, 10, 10, 10));
        vBox.setSpacing(100);

        HBox hBox1 = new HBox();
        hBox1.setAlignment(Pos.CENTER);
        hBox1.getChildren().addAll(l1);

        VBox vBox2 = new VBox();
        vBox2.setPadding(new Insets(10, 10, 10, 10));
        vBox2.setSpacing(10);
        vBox2.getChildren().addAll(l2,login,l3,haslo);
        vBox2.setAlignment(Pos.CENTER);

        HBox hBox2 = new HBox();
        hBox2.setAlignment(Pos.CENTER);
        hBox2.getChildren().addAll(zaloguj,rejestruj);
        hBox2.setSpacing(50);


        vBox.getChildren().addAll( hBox1,vBox2,hBox2);
        Scene scene = new Scene(vBox, 400, 500);
        stage.setScene(scene);
        stage.show();

        stage.setOnCloseRequest(event ->
        {
            Potwierdzenie.show("Jesteś pewien, że chcesz wyjść z programu?");
            if(!Potwierdzenie.akc) return;
            stage.close();
        });
    }
    private static void login()
    {
        String expression="";
        Object o;

        if(login.getText().matches("[0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9]") && !(login.getText().equals("") || haslo.getText().equals("")))
        {
            if (Integer.parseInt(login.getText()) > 90000000)
            {
                expression += "FROM Pracownicy where id_pracownicy = ";
                expression += login.getText();
                if (DbService.existsByOla(expression))
                {
                    Pracownicy p = (Pracownicy) DbService.get(Pracownicy.class, Long.parseLong(login.getText()));

                    if (BCrypt.checkpw(haslo.getText(), p.getHaslo()))
                    {
                        PracownicyEngine.setPracownik(p);
                        PracownicyEngine.zaloguj();
                        stage.close();
                        haslo.setText("");
                        login.setText("");

                    } else
                    {
                        Blad.show("Błędny login lub hasło!");
                    }

                } else
                {
                    Blad.show("Błędny login lub hasło!");
                }
            } else
            {
                expression += "FROM Klienci where id_klient = ";
                expression += login.getText();
                if (DbService.existsByOla(expression))
                {
                    Klienci a = (Klienci) DbService.get(Klienci.class, Long.parseLong(login.getText()));
                    if (BCrypt.checkpw(haslo.getText(), a.getHaslo()))
                    {
                        KlientEngine.setKlienci(a);
                        KlientEngine.zaloguj();
                        stage.close();

                        haslo.setText("");
                        login.setText("");
                    } else
                    {
                        Blad.show("Błędny login lub hasło!");
                    }

                } else
                {
                    Blad.show("Błędny login lub hasło!");
                }
            }
        }
        else
        {
            Blad.show("Błędny login lub hasło!");
        }
    }
}
