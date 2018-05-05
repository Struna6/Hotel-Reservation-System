package JavaFX.Pracownicy;

import Core.DbService;
import Core.PokojeHotelowe;
import Core.Pracownicy;
import Core.Udogodnienia;
import Engine.PracownicyEngine;
import JavaFX.Komunikaty.Blad;
import JavaFX.Komunikaty.Potwierdzenie;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;


public class ListaPokoi
{
    static public void show()
    {
        if(PracownicyEngine.getPracownik() == null)
        {
            Blad.show("Zaloguj się!");
            //wróc do zaloguj
        }
        Stage stage = new Stage();
        stage.setTitle("Lista Pokoi");
        stage.setMinWidth(400);
        stage.setMinHeight(650);

        Button btn = new Button("Wróc");
        Button dostepny = new Button("Dostępny");
        Button niedostepny = new Button("Niedostępny");
        Label l1 = new Label("Lista pokoi");

        TableView table = new TableView();
        table.setEditable(false);

        TableColumn col1 = new TableColumn("Numer");
        col1.setMinWidth(100);
        TableColumn col2 = new TableColumn("Cena");
        col2.setMinWidth(100);
        TableColumn col3 = new TableColumn("Pojemność");
        col3.setMinWidth(100);
        TableColumn col4 = new TableColumn("Dostępny");
        col4.setMinWidth(200);
        TableColumn col5 = new TableColumn("Udogodnienia");
        col5.setMinWidth(660);

        table.getColumns().addAll(col1,col2,col3,col4,col5);

        List listTmp = DbService.returnListByExpression(PokojeHotelowe.class,"FROM PokojeHotelowe");
        ObservableList<PokojeHotelowe> list = FXCollections.observableArrayList(listTmp);


        col1.setCellValueFactory(new PropertyValueFactory<PokojeHotelowe,Long>("id"));
        col2.setCellValueFactory(new PropertyValueFactory<PokojeHotelowe,Float>("cena"));
        col3.setCellValueFactory(new PropertyValueFactory<PokojeHotelowe,Integer>("osob"));
        col4.setCellValueFactory(new PropertyValueFactory<PokojeHotelowe,Boolean>("dostepny"));
        col5.setCellValueFactory(new PropertyValueFactory<PokojeHotelowe,List<Udogodnienia>>("udogodnienia"));

        table.setItems(list);
        table.setMinHeight(600);

        HBox hBox = new HBox(50);
        hBox.setAlignment(Pos.CENTER);
        hBox.getChildren().addAll(dostepny,niedostepny);

        VBox vBox = new VBox(10);
        vBox.setPadding(new Insets(20,20,20,20));
        vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().addAll(l1,table,hBox,btn);

        Scene scene = new Scene(vBox,1200,800);
        stage.setScene(scene);
        stage.show();

        btn.setOnAction(e ->
        {
            stage.close();
        });

        dostepny.setOnAction(event ->
        {
            PokojeHotelowe pokojeHotelowe = (PokojeHotelowe)table.getSelectionModel().getSelectedItem();
            if(pokojeHotelowe==null)
            {
                Blad.show("Nie wybrałeś pokoju!");
                return;
            }
            if(pokojeHotelowe.isDostepny())
            {
                Blad.show("Pokój jest już dostępny!");
                return;
            }
            Potwierdzenie.show("Jesteś pewien, że chcesz ustawić pokój na dostępny?");
            if(!Potwierdzenie.akc) return;
            pokojeHotelowe.setDostepny(true);
            DbService.update(pokojeHotelowe);
            table.refresh();
        });

        niedostepny.setOnAction(event ->
        {
            PokojeHotelowe pokojeHotelowe = (PokojeHotelowe)table.getSelectionModel().getSelectedItem();
            if(pokojeHotelowe==null)
            {
                Blad.show("Nie wybrałeś pokoju!");
                return;
            }
            if(!pokojeHotelowe.isDostepny())
            {
                Blad.show("Pokój jest już niedostępny!");
                return;
            }
            Potwierdzenie.show("Jesteś pewien, że chcesz ustawić pokój na niedostępny?");
            if(!Potwierdzenie.akc) return;
            pokojeHotelowe.setDostepny(false);
            DbService.update(pokojeHotelowe);
            table.refresh();
        });


    }
}
