package JavaFX.Rezerwuj;

import Core.*;
import Engine.KlientEngine;
import JavaFX.Komunikaty.Blad;
import JavaFX.Komunikaty.Potwierdzenie;
import com.mysql.cj.x.protobuf.MysqlxCrud;
import javafx.animation.PauseTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Duration;
import org.omg.CORBA.DATA_CONVERSION;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalField;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class Rezerwuj
{
    static private DatePicker checkInDatePicker,checkOutDatePicker;
    static private String expr = "";
    static private List<PokojeHotelowe> lista = new ArrayList<>();
    static private Klienci klient;

    public static void setKlient(Klienci klient)
    {
        Rezerwuj.klient = klient;
    }

    private static void setExpr(String expr)
    {
        Rezerwuj.expr = expr;
    }

    private static void addLista(PokojeHotelowe p)
    {
        lista.add(p);
    }

    static public void show()
    {
        Stage stage = new Stage();
        stage.setTitle("Rezerwacja pokoju");
        stage.setMinWidth(400);
        stage.setMinHeight(100);
        stage.initModality(Modality.APPLICATION_MODAL);

        Button btn = new Button("Wyswietl dostępne pokoje");
        Label l1 = new Label("Na kiedy chciałbyś zarezerwować pokój?");
        VBox vBox = new VBox(20);
        vBox.setAlignment(Pos.CENTER);

        checkInDatePicker = new DatePicker();
        checkOutDatePicker = new DatePicker();
        checkInDatePicker.setValue(LocalDate.now());
        final Callback<DatePicker, DateCell> dayCellFactory;
        dayCellFactory = new Callback<DatePicker, DateCell>() {
            @Override
            public DateCell call(final DatePicker datePicker) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item.isBefore(
                                checkInDatePicker.getValue().plusDays(1))
                                ) {
                            setDisable(true);
                            setStyle("-fx-background-color: #ffc0cb;");
                        }
                        long p = ChronoUnit.DAYS.between(
                                checkInDatePicker.getValue(), item
                        );
                        setTooltip(new Tooltip(
                                "You're about to stay for " + p + " days")
                        );
                    }
                };
            }
        };
        final Callback<DatePicker, DateCell> dayCellFactory2;
        dayCellFactory2 = new Callback<DatePicker, DateCell>() {
            @Override
            public DateCell call(final DatePicker datePicker) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);
                        if(checkOutDatePicker.getValue() == null)
                        {
                            if(item.isBefore(LocalDate.now()))
                            {
                                setDisable(true);
                                setStyle("-fx-background-color: #ffc0cb;");
                            }
                            return;
                        }
                        if (item.isAfter(checkOutDatePicker.getValue().minusDays(1))) {
                            setDisable(true);
                            setStyle("-fx-background-color: #ffc0cb;");
                        }
                        if(item.isBefore(LocalDate.now()))
                        {
                            setDisable(true);
                            setStyle("-fx-background-color: #ffc0cb;");
                        }

                    }
                };
            }
        };
        checkOutDatePicker.setDayCellFactory(dayCellFactory);
        checkInDatePicker.setDayCellFactory(dayCellFactory2);

        Label wolne = new Label("Lista wolnych rezerwacji: ");
        wolne.setVisible(false);

        TableView table = new TableView();
        table.setEditable(false);

        TableColumn col1 = new TableColumn("Numer");
        col1.setMinWidth(100);
        TableColumn col2 = new TableColumn("Cena");
        col2.setMinWidth(100);
        TableColumn col3 = new TableColumn("Pojemność");
        col3.setMinWidth(100);
        TableColumn col5 = new TableColumn("Udogodnienia");
        col5.setMinWidth(480);

        table.getColumns().addAll(col1,col2,col3,col5);

        Label wybrane = new Label("Pokoje wybrane do zarezerwowania:");
        wybrane.setVisible(false);

        TableView table2 = new TableView();
        table2.setEditable(false);

        TableColumn col12 = new TableColumn("Numer");
        col12.setMinWidth(100);
        TableColumn col22 = new TableColumn("Cena");
        col22.setMinWidth(100);
        TableColumn col32 = new TableColumn("Pojemność");
        col32.setMinWidth(100);
        TableColumn col52 = new TableColumn("Udogodnienia");
        col52.setMinWidth(480);

        table2.getColumns().addAll(col12,col22,col32,col52);

        ObservableList<PokojeHotelowe> list2 = FXCollections.observableList(lista);

        col12.setCellValueFactory(new PropertyValueFactory<PokojeHotelowe,Long>("id"));
        col22.setCellValueFactory(new PropertyValueFactory<PokojeHotelowe,Float>("cena"));
        col32.setCellValueFactory(new PropertyValueFactory<PokojeHotelowe,Integer>("osob"));
        col52.setCellValueFactory(new PropertyValueFactory<PokojeHotelowe,List<Udogodnienia>>("udogodnienia"));

        table2.setVisible(false);
        table2.setEditable(false);
        table2.setItems(list2);

        table.setMaxHeight(200);
        table2.setMaxHeight(200);

        table.setVisible(false);
        table2.setVisible(false);

        Button dodaj = new Button("Dodaj");
        Button usun = new Button("Usuń");
        Button rezerwuj = new Button("Zarezerwuj");
        Button wroc = new Button("Wróc");

        HBox h = new HBox(100);
        h.setAlignment(Pos.CENTER);
        h.getChildren().addAll(dodaj,usun);

        HBox h2 = new HBox(100);
        h2.setAlignment(Pos.CENTER);
        h2.getChildren().addAll(rezerwuj,wroc);

        dodaj.setVisible(false);
        usun.setVisible(false);
        rezerwuj.setVisible(false);
        wroc.setVisible(false);

        vBox.getChildren().addAll(l1,checkInDatePicker,checkOutDatePicker,btn);
        VBox vb = new VBox(10);

        vb.getChildren().addAll(wolne,table,h,wybrane,table2,h2);

        VBox vbig = new VBox(10);
        vbig.getChildren().addAll(vBox,vb);
        vbig.setPadding(new Insets(10,10,10,10));
        Scene scene = new Scene(vbig, 800, 750);
        stage.setScene(scene);

        PauseTransition delay = new PauseTransition(Duration.minutes(30));
        delay.setOnFinished( event ->
        {
            stage.close();
        } );
        delay.play();

        btn.setOnAction(e ->
        {
            if(checkInDatePicker.getValue()==null || checkOutDatePicker.getValue()==null)
            {
                Blad.show("Wybierz datę!");
                return;
            }
            vBox.setVisible(false);
            table.setVisible(true);
            table2.setVisible(true);
            dodaj.setVisible(true);
            usun.setVisible(true);
            rezerwuj.setVisible(true);
            wroc.setVisible(true);
            wolne.setVisible(true);
            wybrane.setVisible(true);

            LocalDate localDate1 = checkInDatePicker.getValue();
            Date dateOd = new Date(localDate1.atStartOfDay(ZoneId.systemDefault()).toEpochSecond() * 1000);
            long chceOd = dateOd.getTime();

            LocalDate localDate2 = checkOutDatePicker.getValue();
            Date dateDo = new Date(localDate2.atStartOfDay(ZoneId.systemDefault()).toEpochSecond() * 1000);
            long chceDo = dateDo.getTime();

            expr = "SELECT pokoje" +
                    " FROM Rezerwacje WHERE ( pobyt_doLong BETWEEN " + chceOd + " AND " + chceDo + " ) OR (" +
                    " pobyt_odLong BETWEEN " + chceOd + " AND " + chceDo + ") OR ( pobyt_odLong < " + chceOd
                    + " AND pobyt_doLong > " + chceDo+" )";

            List<PokojeHotelowe> listTmp = DbService.returnListByExpression(PokojeHotelowe.class,"From " +
                "PokojeHotelowe WHERE dostepny=1");
            List<PokojeHotelowe> zajete = DbService.returnListByExpression(Rezerwacje.class,expr);

            for (PokojeHotelowe p: zajete)
            {
                listTmp.remove(p);
            }

            ObservableList<PokojeHotelowe> list = FXCollections.observableArrayList(listTmp);

            if(list.isEmpty()) Blad.show("Brak elementów!");

            col1.setCellValueFactory(new PropertyValueFactory<PokojeHotelowe,Long>("id"));
            col2.setCellValueFactory(new PropertyValueFactory<PokojeHotelowe,Float>("cena"));
            col3.setCellValueFactory(new PropertyValueFactory<PokojeHotelowe,Integer>("osob"));
            col5.setCellValueFactory(new PropertyValueFactory<PokojeHotelowe,List<Udogodnienia>>("udogodnienia"));

            table.setVisible(true);
            table.setItems(list);

            table.setRowFactory(tv -> new TableRow<PokojeHotelowe>() {
                private Tooltip tooltip = new Tooltip();
                @Override
                public void updateItem(PokojeHotelowe pokoj, boolean empty) {
                    super.updateItem(pokoj, empty);
                    if (pokoj == null)
                    {
                        setTooltip(null);
                    }
                    else
                    {
                        String text = "";
                        for(Udogodnienia u:
                            pokoj.getUdogodnienia())
                        {
                            text += u.getOpis()+"\n";
                        }
                        tooltip.setText(text);
                        setTooltip(tooltip);
                    }
                }
            });

        });

        usun.setOnAction(event ->
        {
            PokojeHotelowe pokojeHotelowe = (PokojeHotelowe)table2.getSelectionModel().getSelectedItem();
            if(!lista.contains(pokojeHotelowe))
            {
                Blad.show("Nie ma co usuwać!");
                return;
            }
            if(pokojeHotelowe==null)
            {
                Blad.show("Zaznacz pokój!");
                return;
            }
            pokojeHotelowe.setwTrakcieRez(false);
            DbService.update(pokojeHotelowe);
            lista.remove(pokojeHotelowe);
            table2.refresh();
        });

        dodaj.setOnAction(event ->
        {
            PokojeHotelowe pokojeHotelowe = (PokojeHotelowe)table.getSelectionModel().getSelectedItem();
            if(pokojeHotelowe==null)
            {
                Blad.show("Zaznacz pokój!");
                return;
            }
            if(lista.contains(pokojeHotelowe))
            {
                Blad.show("Już wybrałeś ten pokój!");
                return;
            }
            if(pokojeHotelowe.iswTrakcieRez())
            {
                Blad.show("Pokój już jest rezerwowany!");
                return;
            }
            pokojeHotelowe.setwTrakcieRez(true);
            DbService.update(pokojeHotelowe);
            lista.add(pokojeHotelowe);
            table2.refresh();
        });

        wroc.setOnAction(event -> {
            for (PokojeHotelowe p:
                 lista)
            {
                p.setwTrakcieRez(false);
                DbService.update(p);
            }
            vBox.setVisible(true);
            table.setVisible(false);
            table2.setVisible(false);
            dodaj.setVisible(false);
            usun.setVisible(false);
            rezerwuj.setVisible(false);
            wroc.setVisible(false);
            wolne.setVisible(false);
            wybrane.setVisible(false);
            lista.clear();
            table2.refresh();
        });

        stage.setOnCloseRequest(event ->
        {
            for (PokojeHotelowe p:
                    lista)
            {
                p.setwTrakcieRez(false);
                DbService.update(p);
            }
            lista.clear();
            table2.refresh();
        });

        rezerwuj.setOnAction(event ->
        {
            if(lista.isEmpty())
            {
                Blad.show("Nie wybrałeś żadnego pokoju!");
                return;
            }
            Potwierdzenie.show("Jesteś pewny, że chcesz dokonać tej rezerwacji?");
            if(!Potwierdzenie.akc) return;
            Date date = new Date();

            LocalDate localDate1 = checkInDatePicker.getValue();
            Date dateOd = new Date(localDate1.atStartOfDay(ZoneId.systemDefault()).toEpochSecond() * 1000);
            long chceOd = dateOd.getTime();

            LocalDate localDate2 = checkOutDatePicker.getValue();
            Date dateDo = new Date(localDate2.atStartOfDay(ZoneId.systemDefault()).toEpochSecond() * 1000);
            long chceDo = dateDo.getTime();

            Klienci k;

            if(KlientEngine.klienci==null) k = klient;
            else k = KlientEngine.getKlienci();

            for (PokojeHotelowe p:
                    lista)
            {
                p.setwTrakcieRez(false);
                DbService.update(p);
            }
            JavaFX.Rezerwuj.Potwierdzenie.setLista(lista);
            JavaFX.Rezerwuj.Potwierdzenie.show(chceOd,chceDo,k);
            lista.clear();
            table2.refresh();
            stage.close();
        });
        stage.show();
    }
}
