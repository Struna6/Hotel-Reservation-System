package JavaFX.Rejestracja;

import Core.DbService;
import Core.Klienci;
import Core.Pracownicy;
import JavaFX.Komunikaty.Blad;
import JavaFX.Komunikaty.Potwierdzenie;
import JavaFX.Logowanie.Logowanie;
import JavaFX.Pracownicy.PracownikMenu;
import JavaFX.Rezerwuj.Rezerwuj;
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

import java.util.List;

public class Rejestracja
{

    private static TextField imie,nazwisko,adres,telefon;
    private static PasswordField haslo,powtorzhaslo;
    private static Stage stage;
    private static Label blad;
    private static boolean trybPracownika = false;

    public static void rejestrowanie(boolean trybPracownika)
    {
        Rejestracja.trybPracownika = trybPracownika;
        stage = new Stage();
        stage.setTitle("Hotel - Rejestracja");
        stage.setMinWidth(300);
        stage.setMinHeight(300);


        Button anuluj = new Button("Anuluj");
        anuluj.setOnAction(e->anulujRejestracje());
        Button rejestruj = new Button("Rejestruj się");
        rejestruj.setOnAction(e->rejestracjaKlienta());


        Label limie = new Label("Imię:");
        imie= new TextField();
        imie.setPromptText("Wpisz swoje imię");

        Label lnazwisko = new Label("Nazwisko:");
        nazwisko= new TextField();
        nazwisko.setPromptText("Wpisz swoje nazwisko");

        Label lhaslo = new Label("Hasło:");
        haslo= new PasswordField();
        haslo.setPromptText("Wpisz swoje hasło");

        Label lpowtorzhaslo = new Label("Powtorz hasło:");
        powtorzhaslo= new PasswordField();
        powtorzhaslo.setPromptText("Powtórz swoje hasło");

        Label ladres = new Label("Adres:");
        adres= new TextField();
        adres.setPromptText("Wpisz swój adres");

        Label ltelefon = new Label("Telefon:");
        telefon= new TextField();
        telefon.setPromptText("Wpisz swój numer telefonu");

        blad = new Label("");
        blad.setStyle("-fx-text-fill: red; -fx-font-size: 16;");

        VBox vBox = new VBox();
        vBox.setPadding(new Insets(12, 10, 10, 10));
        vBox.setSpacing(100);

        HBox hBox1 = new HBox();
        hBox1.setAlignment(Pos.CENTER);
        hBox1.getChildren().addAll(rejestruj,anuluj);
        hBox1.setSpacing(20);

        VBox vBox2 = new VBox();
        vBox2.setPadding(new Insets(10, 10, 10, 10));
        vBox2.setSpacing(10);
        if(!trybPracownika)
        {
            vBox2.getChildren().addAll(limie,imie,lnazwisko,nazwisko,lhaslo,haslo,lpowtorzhaslo,powtorzhaslo,ladres,adres,ltelefon,telefon,blad);
        }
        else vBox2.getChildren().addAll(limie,imie,lnazwisko,nazwisko,ladres,adres,ltelefon,telefon,blad);
        vBox2.setAlignment(Pos.CENTER);

        vBox.getChildren().addAll(vBox2,hBox1);
        Scene scene = new Scene(vBox, 400, 580);
        stage.setScene(scene);
        stage.show();

        stage.setOnCloseRequest(event ->
        {
            anulujRejestracje();
        });

    }


    private static void anulujRejestracje()
    {
        Potwierdzenie.show("Czy na pewno chcesz przerwać proces rejestrowania się?");
        stage.close();
        if(trybPracownika) PracownikMenu.start();
        else Logowanie.logowanie();
    }
    private static void rejestracjaKlienta()
    {
        List<Klienci> lista = DbService.returnListByExpression(Klienci.class,"FROM Klienci");

        if((haslo.getText().equals("") && !trybPracownika) || imie.getText().equals("") || nazwisko.getText().equals("") || adres.getText
                ().equals("") || telefon.getText().equals("") || (powtorzhaslo.getText().equals("") && !trybPracownika))
        {
            blad.setText("Pozostaly nieuzupełnione pola!");
        }

        else if(haslo.getText().length()<5 && !trybPracownika)
        {
            blad.setText("Podane przez Ciebie hasło jest za krótkie!");
            haslo.clear();
            powtorzhaslo.clear();
        }

        else if(!haslo.getText().equals(powtorzhaslo.getText()) && !trybPracownika)
        {
            blad.setText("Podane przez Ciebie hasła nie są identyczne!");
            haslo.clear();
            powtorzhaslo.clear();
        }

        else if(!imie.getText().matches("[AaĄąBbCcĆćDdEeĘęFfGgHhIiJjKkLlŁłMmNnŃńOoÓóPpRrSsŚśTtUuWwYyZzŹźŻż]+"))
        {
            blad.setText("Błędne imię");
            imie.clear();
        }

        else if(imie.getText().length()<2)
        {
            blad.setText("Za krótkie imię");
            imie.clear();
        }
        else if(!nazwisko.getText().matches("[AaĄąBbCcĆćDdEeĘęFfGgHhIiJjKkLlŁłMmNnŃńOoÓóPpRrSsŚśTtUuWwYyZzŹźŻż][AaĄąBbCcĆćDdEeĘęFfGgHhIiJjKkLlŁłMmNnŃńOoÓóPpRrSsŚśTtUuWwYyZzŹźŻż][AaĄąBbCcĆćDdEeĘęFfGgHhIiJjKkLlŁłMmNnŃńOoÓóPpRrSsŚśTtUuWwYyZzŹźŻż-]+"))
        {
            blad.setText("Błędne nazwisko");
            nazwisko.clear();
        }
        
        else if(!telefon.getText().matches("[0-9]+"))
        {
            blad.setText("Błędny telefon");
            telefon.clear();
        }
        else if(telefon.getText().length()<6)
        {
            blad.setText("Za krótki numer telefonu");
            telefon.clear();
        }
        else if(!adres.getText().matches("[0123456789()/.,-AaĄąBbCcĆćDdEeĘęFfGgHhIiJjKkLlŁłMmNnŃńOoÓóPpRrSsŚśTtUuWwYyZzŹźŻż ]+"))
        {
            blad.setText("Błędny adres");
            adres.clear();
        }
        else if(adres.getText().length()<6)
        {
            blad.setText("Za krótki adres");
            adres.clear();
        }
        else if(lista.size() >= 79999999)
        {
            Blad.show("Spróbuj zarejestrować się później!");
        }
        else
        {
            Klienci k;
            if(trybPracownika)
            {
                k = new Klienci(imie.getText(),nazwisko.getText(),adres.getText(),telefon.getText(),null);
            }
            else
            {
                k = new Klienci(imie.getText(),nazwisko.getText(),adres.getText(),telefon.getText(),haslo.getText
                        ());
            }
            DbService.save(k);
            blad.setVisible(true);
            blad.setText("Poprawna rejestracja!");
            stage.close();
            if(trybPracownika)
            {
                stage.close();
                Rezerwuj.setKlient(k);
                Rezerwuj.show();
            }
            else
            {
                stage.close();
                Logowanie.logowanie();
            }
        }

    }

}
