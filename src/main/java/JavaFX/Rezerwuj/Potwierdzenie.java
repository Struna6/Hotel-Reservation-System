package JavaFX.Rezerwuj;

import Core.*;
import JavaFX.Komunikaty.Blad;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;

public class Potwierdzenie
{
    private static Stage stage;
    static private Typy_platnosci typ;
    static private List<PokojeHotelowe> lista;
    static Rezerwacje rez;

    public static void setLista(List<PokojeHotelowe> lista)
    {
        Potwierdzenie.lista = lista;
    }

    public static void setTyp(Typy_platnosci typ)
    {
        Potwierdzenie.typ = typ;
    }

    public static void show(long chceOd, long chceDo, Klienci k1)
    {
        stage = new Stage();
        stage.setTitle("Wybierz typ płatności");
        stage.setMinWidth(600);
        stage.setMinHeight(650);
        Button choiceBtn = new Button("Wybierz");

        ChoiceBox choiceBox = new ChoiceBox();
        choiceBox.getItems().addAll("Płatność kartą", "Płatność gotówką");
        choiceBox.setValue("Płatność kartą");

        VBox vBox = new VBox(10);
        vBox.setPadding(new Insets(20, 20, 20, 20));
        vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().addAll(choiceBox,choiceBtn);

        Scene scene = new Scene(vBox, 400, 200);
        stage.setScene(scene);

        if(lista.isEmpty())
        {
            Blad.show("Błąd!");
            stage.close();
            return;
        }
        rez = new Rezerwacje(chceOd,chceDo,k1);
        DbService.save(rez);
        System.out.println(lista.size());
        rez.setPokoje(lista);
        System.out.println(rez.getPokoje().size());
        DbService.update(rez);
        stage.show();

        choiceBtn.setOnAction(e ->
        {
            String choice = choiceBox.getValue().toString();
            if(choice.equals("Płatność kartą")) setTyp(Typy_platnosci.KARTA);
            else if(choice.equals("Płatność gotówką"))  setTyp(Typy_platnosci.GOTOWKA);
            long od = rez.getPobyt_odLong(), d_o = rez.getPobyt_doLong();
            long dni = d_o-od;
            int przel = ((int)dni)/(1000*60*60*24);
            rez.setKwota(rez.getKwota()*przel);
            Platnosci platnosc = new Platnosci(typ,rez);
            DbService.save(platnosc);
            rez.setPlatnosc(platnosc);
            DbService.merge(rez);
            stage.close();
        });

        stage.setOnCloseRequest(event ->
        {
            for (PokojeHotelowe p:
                    lista)
            {
                p.setwTrakcieRez(false);
                DbService.update(p);
            }
        });
    }
}
