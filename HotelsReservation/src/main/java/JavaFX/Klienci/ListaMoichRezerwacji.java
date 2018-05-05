package JavaFX.Klienci;

import Core.DbService;
import Core.Platnosci;
import Core.PokojeHotelowe;
import Core.Rezerwacje;
import Engine.KlientEngine;
import Engine.PracownicyEngine;
import JavaFX.Komunikaty.Blad;
import JavaFX.Komunikaty.Potwierdzenie;
import JavaFX.Logowanie.Logowanie;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Date;
import java.util.List;

public class ListaMoichRezerwacji
{
    static private TableView table;
    static private String expression = "";
    static private Stage stage;
    static private Label l2 = new Label("");

    static public void show()
    {
        if(KlientEngine.getKlienci() == null)
        {
            Blad.show("Zaloguj się!");
            stage.close();
            Logowanie.logowanie();
        }

        stage = new Stage();
        stage.setTitle("Lista Moich Rezerwacji");
        stage.setMinWidth(600);
        stage.setMinHeight(650);

        Button btn = new Button("Wróc");
        Button plac = new Button("Dokonaj Płatności");
        Label l1 = new Label("Lista Moich Rezerwacji");


        table = new TableView();
        table.setEditable(false);
        table.setMinWidth(800);

        TableColumn col1 = new TableColumn("Numer");
        col1.setMaxWidth(20);
        TableColumn col2 = new TableColumn("Pobyt od");
        col1.setMaxWidth(50);
        TableColumn col3 = new TableColumn("Pobyt do");
        col1.setMaxWidth(50);
        TableColumn col4 = new TableColumn("Kwota");
        col1.setMaxWidth(20);
        TableColumn col5 = new TableColumn("Data rezerwacji");
        col1.setMaxWidth(50);
        TableColumn col6 = new TableColumn("Platność");
        col1.setMaxWidth(100);
        TableColumn col7 = new TableColumn("Pokoje");
        col1.setMaxWidth(500);



        table.getColumns().addAll(col1, col2, col3, col4,col5,col6,col7);
        List listTmp = DbService.returnListByExpression(PokojeHotelowe.class,
                "FROM Rezerwacje where klienci.id_klient = " +KlientEngine.klienci.getId_klient()+""+expression);
        ObservableList<PokojeHotelowe> list = FXCollections.observableArrayList(listTmp);
        if(list.isEmpty())
        {
            Blad.show("Brak elementów!");
            stage.close();
            return;
        }

        col1.setCellValueFactory(new PropertyValueFactory<PokojeHotelowe,Long>("id_rezerwacji"));
        col2.setCellValueFactory(new PropertyValueFactory<PokojeHotelowe,Date>("pobyt_od"));
        col3.setCellValueFactory(new PropertyValueFactory<PokojeHotelowe,Date>("pobyt_do"));
        col4.setCellValueFactory(new PropertyValueFactory<PokojeHotelowe,Integer>("kwota"));
        col5.setCellValueFactory(new PropertyValueFactory<PokojeHotelowe,Date>("data_rez"));
        col6.setCellValueFactory(new PropertyValueFactory<PokojeHotelowe, Platnosci>("platnosci"));
        col7.setCellValueFactory(new PropertyValueFactory<PokojeHotelowe,PokojeHotelowe>("pokoje"));

        table.setItems(list);

        HBox hbox = new HBox(20);
        hbox.setAlignment(Pos.CENTER);

        HBox hBox2 = new HBox(20);
        hbox.setAlignment(Pos.CENTER);


        VBox vBox = new VBox(10);
        vBox.setPadding(new Insets(20, 20, 20, 20));
        vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().addAll(l1,l2,hbox, table,hBox2, btn,plac);

        Scene scene = new Scene(vBox, 1600, 800);
        stage.setScene(scene);
        stage.show();


        btn.setOnAction(event ->
        {
            stage.close();
        });

        table.getSelectionModel().selectedItemProperty().addListener( (v, oldValue, newValue) ->
        {
            hBox2.setVisible(true);

        });
        plac.setOnAction(event ->
        {
            czynapewno();
        });

    }
    static public void czynapewno()
    {
        if(table.getSelectionModel().getSelectedItem()!=null)
        {
            Potwierdzenie.show("Czy na pewno chcesz umieścić płatność?");
            if (Potwierdzenie.akc)
            {
                Rezerwacje rezerwacje = (Rezerwacje)table.getSelectionModel().getSelectedItem();
                Platnosci p;
                if (!rezerwacje.getPlatnosci().isDokonana())
                {
                    rezerwacje.getPlatnosci().setDokonana(true);
                    p=rezerwacje.getPlatnosci();
                    DbService.update(p);
                    table.refresh();
                } else
                {
                    Blad.show("Już dokonano płatności za tą rezerwację!");
                }
            }
        }
        else
        {
            Blad.show("Nie wybrano rezerwacji za którą mam zapłacić!");
        }
    }

    static private void setList(String expression)
    {
        stage.close();
        show();
    }
}
