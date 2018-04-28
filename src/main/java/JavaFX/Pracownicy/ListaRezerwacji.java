package JavaFX.Pracownicy;

import Core.DbService;
import Core.Platnosci;
import Core.PokojeHotelowe;
import Core.Rezerwacje;
import Engine.PracownicyEngine;
import JavaFX.Komunikaty.Blad;
import JavaFX.Komunikaty.Potwierdzenie;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

import java.util.Date;
import java.util.List;

public class ListaRezerwacji
{
    static String expression = "";
    static Stage stage;
    static Label l2 = new Label("");
    static Rezerwacje rezerwacje;

    public static void setRezerwacje(Rezerwacje rezerwacje)
    {
        ListaRezerwacji.rezerwacje = rezerwacje;
    }

    static public void show()
    {
        if(PracownicyEngine.getPracownik() == null)
        {
            Blad.show("Zaloguj się!");
            //wróc do zaloguj
        }
        stage = new Stage();
        stage.setTitle("Lista Rezerwacji");
        stage.setMinWidth(600);
        stage.setMinHeight(650);

        Button btn = new Button("Wróc");
        Button choiceBtn = new Button("Wybierz");
        Label l1 = new Label("Lista Rezerwacji");


        ChoiceBox choiceBox = new ChoiceBox();
        choiceBox.getItems().addAll("Wszystkie","Rezerwacje rozliczone", "Rezerwacje " +
                "nierozliczone", "Rezerwacje nadchodzące", "Rezerwacje trwające", "Rezerwacje opłacone", "Rezerwacje " +
                "nieopłacone","Rezerwacje bez pojawienia sie klienta");
        choiceBox.setValue("Wszystkie");

        TableView table = new TableView();
        table.setEditable(false);
        table.setMinWidth(850);

        TableColumn col1 = new TableColumn("Numer");
        col1.setMaxWidth(50);
        TableColumn col2 = new TableColumn("Pobyt od");
        col2.setMaxWidth(200);
        TableColumn col3 = new TableColumn("Pobyt do");
        col3.setMaxWidth(200);
        TableColumn col4 = new TableColumn("Kwota");
        col4.setMaxWidth(250);
        TableColumn col5 = new TableColumn("Data rezerwacji");
        col5.setMaxWidth(200);
        TableColumn col6 = new TableColumn("Platność");
        col6.setMaxWidth(700);
        TableColumn col6b = new TableColumn("Rozliczone");
        col6b.setMaxWidth(100);
        TableColumn col7 = new TableColumn("Pokoje");
        col7.setMaxWidth(800);

        table.getColumns().addAll(col1, col2, col3, col4,col5,col6,col6b,col7);
        List listTmp = DbService.returnListByExpression(PokojeHotelowe.class,
                "FROM Rezerwacje " + expression);
        ObservableList<PokojeHotelowe> list = FXCollections.observableArrayList(listTmp);
        if(list.isEmpty())
        {
            Blad.show("Brak wolnych pokoi!");
            stage.close();
            return;
        }

        col1.setCellValueFactory(new PropertyValueFactory<PokojeHotelowe,Long>("id_rezerwacji"));
        col2.setCellValueFactory(new PropertyValueFactory<PokojeHotelowe,Date>("pobyt_od"));
        col3.setCellValueFactory(new PropertyValueFactory<PokojeHotelowe,Date>("pobyt_do"));
        col4.setCellValueFactory(new PropertyValueFactory<PokojeHotelowe,Integer>("kwota"));
        col5.setCellValueFactory(new PropertyValueFactory<PokojeHotelowe,Date>("data_rez"));
        col6.setCellValueFactory(new PropertyValueFactory<PokojeHotelowe,Platnosci>("platnosci"));
        col6b.setCellValueFactory(new PropertyValueFactory<Rezerwacje,Boolean>("rozliczony"));
        col7.setCellValueFactory(new PropertyValueFactory<PokojeHotelowe,PokojeHotelowe>("pokoje"));

        table.setItems(list);

        HBox hbox = new HBox(20);
        hbox.setAlignment(Pos.CENTER);

        HBox hBox2 = new HBox(20);
        hbox.setAlignment(Pos.CENTER);

        Button anulujBtn = new Button("Anuluj rezerwację");
        Button rozliczBtn = new Button("Rozlicz");
        Button potwierdzPlatnoscBtn = new Button("Opłacone");
        Button potwierdzPrzybycie = new Button("Potwierdź przybycie");
        Button generujFakture = new Button("Faktura");
        Label l = new Label("Wygenerowałeś fakturę!");
        l.setVisible(false);
        hBox2.getChildren().addAll(anulujBtn,rozliczBtn,potwierdzPlatnoscBtn,potwierdzPrzybycie,generujFakture);
        hBox2.setVisible(false);

        VBox vBox = new VBox(10);
        vBox.setPadding(new Insets(20, 20, 20, 20));
        vBox.setAlignment(Pos.CENTER);
        //table.setMaxWidth(1100);
        hbox.getChildren().addAll(choiceBox, choiceBtn);
        vBox.getChildren().addAll(l1,l2,hbox, table,hBox2,l, btn);

        Scene scene = new Scene(vBox, 1630, 800);
        stage.setScene(scene);
        stage.show();

        choiceBtn.setOnAction(e ->
        {
            String choice = choiceBox.getValue().toString();
            if(choice.equals("Wszystkie")) expression = "";
            else if(choice.equals("Rezerwacje rozliczone")) expression="WHERE rozliczony = 1";
            else if(choice.equals("Rezerwacje nierozliczone")) expression="WHERE rozliczony = 0";
            else if(choice.equals("Rezerwacje nadchodzace")) expression="WHERE pobyt_od > now() ";
            else if(choice.equals("Rezerwacje opłacone")) expression="WHERE pobyt_od > now() ";
            else if(choice.equals("Rezerwacje nieopłacone")) expression="WHERE platnosci.dokonana = 0 ";
            else if(choice.equals("Rezerwacje opłacone")) expression="WHERE platnosci.dokonana = 1 ";
            else if(choice.equals("Rezerwacje bez pojawienia sie klienta")) expression="WHERE platnosci.dokonana = 0 " +
                    "and pobyt_od > now() ";
            else if(choice.equals("Rezerwacje trwające")) expression="WHERE pobyt_od < now() AND pobyt_do > now() AND" +
                    " platnosci.dokonana = 1";
            l2.setText(choice);
            setList(expression);
        });

        btn.setOnAction(event ->
        {
            stage.close();
        });

        table.getSelectionModel().selectedItemProperty().addListener( (v, oldValue, newValue) ->
        {
            hBox2.setVisible(true);
            String nrRez = newValue.toString().substring(15,newValue.toString().indexOf("@")-1);
            long nrRezInt = Long.parseLong(nrRez);
            Rezerwacje rez = (Rezerwacje)DbService.get(Rezerwacje.class,nrRezInt);
            setRezerwacje(rez);
        });

        anulujBtn.setOnAction(e ->
        {
            Potwierdzenie.show("Czy jesteś pewny, że anulujesz rezerwację?");
            Rezerwacje rez = ListaRezerwacji.rezerwacje;
            if(Potwierdzenie.akc)
            {
                stage.close();
                for(PokojeHotelowe p: rez.getPokoje())
                {
                    p.setDostepny(true);
                }
                DbService.delete(ListaRezerwacji.rezerwacje);
                ListaRezerwacji.show();
            }
        });

        rozliczBtn.setOnAction(e ->
        {
            Rezerwacje rez = ListaRezerwacji.rezerwacje;
            Date data = new Date();
            long now = data.getTime();
            long d_o = rez.getPobyt_do().getTime();
            if(rezerwacje.isRozliczony())
            {
                Blad.show("Nie możesz tego zrobić!");
                return;
            }
            if(d_o > now)
            {
                Blad.show("Za wcześnie!");
                return;
            }
            if(!rez.getPlatnosci().isDokonana())
            {
                Blad.show("Klient nie zapłacił!");
                return;
            }
            Potwierdzenie.show("Czy jesteś pewny, że rozliczasz klienta?");
            if(!Potwierdzenie.akc) return;
            rez.setPracownikRozliczajacy(PracownicyEngine.getPracownik());
            for(PokojeHotelowe p: rez.getPokoje())
            {
                p.setDostepny(true);
            }
            rez.setRozliczony(true);
            DbService.update(rez);
        });

        potwierdzPlatnoscBtn.setOnAction(e ->
        {
            Potwierdzenie.show("Czy jesteś pewny, że potwierdzasz płatność?");
            if(!Potwierdzenie.akc) return;
            Rezerwacje rez = ListaRezerwacji.rezerwacje;
            rez.getPlatnosci().setDokonana(true);
            if(rezerwacje.isRozliczony())
            {
                Blad.show("Nie możesz tego zrobić!");
                return;
            }
            potwierdzPrzybycie();
            Date date = new Date();
            rez.getPlatnosci().setTime(date);
            stage.close();
            DbService.update(rez);
            ListaRezerwacji.show();
        });

        potwierdzPrzybycie.setOnAction(e ->
        {
            Potwierdzenie.show("Czy jesteś pewny, że potwierdzasz przybycie?");
            if(!Potwierdzenie.akc) return;
            Date data = new Date();
            long now = data.getTime();
            long d_o = rezerwacje.getPobyt_do().getTime();
            if(d_o > now)
            {
                Blad.show("Za wcześnie!");
                return;
            }
            if(!rezerwacje.getPlatnosci().isDokonana())
            {
                Blad.show("Najpierw przyjmij płatność!");
                return;
            }
            if(rezerwacje.isRozliczony())
            {
                Blad.show("Nie możesz tego zrobić!");
                return;
            }

            potwierdzPrzybycie();
        });

        generujFakture.setOnAction(e ->
        {
            if(!rezerwacje.getPlatnosci().isDokonana())
            {
                Blad.show("Najpierw przyjmij płatność!");
                return;
            }
            Faktura.drukujFakture(rezerwacje,PracownicyEngine.getPracownik());
            l.setVisible(true);
        });

    }
    static private void setList(String expression)
    {
        stage.close();
        show();
    }

    static private void potwierdzPrzybycie()
    {
        Rezerwacje rez = rezerwacje;
        rez.setPracownik(PracownicyEngine.getPracownik());
        for (PokojeHotelowe p: rez.getPokoje())
        {
            p.setDostepny(false);
        }
    }

}
