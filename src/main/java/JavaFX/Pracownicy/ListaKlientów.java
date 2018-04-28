package JavaFX.Pracownicy;

import Core.DbService;
import Core.PokojeHotelowe;
import Engine.PracownicyEngine;
import JavaFX.Komunikaty.Blad;
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

import java.util.List;

public class ListaKlientów
{
    static String expression = "";
    static Stage stage;
    static Label l2 = new Label("");

    static public void show()
    {
        if(PracownicyEngine.getPracownik() == null)
        {
            Blad.show("Zaloguj się!");
            //wróc do zaloguj
        }
        stage = new Stage();
        stage.setTitle("Lista Klientów");
        stage.setMinWidth(400);
        stage.setMinHeight(650);

        Button btn = new Button("Wróc");
        Button choiceBtn = new Button("Wybierz");
        Label l1 = new Label("Lista klientów");


        ChoiceBox choiceBox = new ChoiceBox();
        choiceBox.getItems().addAll("Wszyscy klienci","Klienci zarejestrowani", "Klienci " +
                "niezarejestrowani");
        choiceBox.setValue("Wszyscy klienci");

        TableView table = new TableView();
        table.setEditable(false);
        table.setMinWidth(400);

        TableColumn col1 = new TableColumn("Imię");
        col1.setMinWidth(100);
        TableColumn col2 = new TableColumn("Nazwisko");
        col1.setMinWidth(100);
        TableColumn col3 = new TableColumn("Adres");
        col1.setMinWidth(100);
        TableColumn col4 = new TableColumn("Telefon");
        col1.setMinWidth(100);


        table.getColumns().addAll(col1, col2, col3, col4);
        List listTmp = DbService.returnListByExpression(PokojeHotelowe.class,
                "FROM Klienci " + expression);
        ObservableList<PokojeHotelowe> list = FXCollections.observableArrayList(listTmp);
        if(list.isEmpty())
        {
            Blad.show("Brak elementów!");
            stage.close();
            return;
        }

        col1.setCellValueFactory(new PropertyValueFactory<PokojeHotelowe,String>("imie"));
        col2.setCellValueFactory(new PropertyValueFactory<PokojeHotelowe,String>("nazwisko"));
        col3.setCellValueFactory(new PropertyValueFactory<PokojeHotelowe,String>("adres"));
        col4.setCellValueFactory(new PropertyValueFactory<PokojeHotelowe,String>("telefon"));

        table.setItems(list);


        HBox hbox = new HBox(20);
        hbox.setAlignment(Pos.CENTER);
        VBox vBox = new VBox(10);
        vBox.setPadding(new Insets(20, 20, 20, 20));
        vBox.setAlignment(Pos.CENTER);
        table.setMaxWidth(420);
        hbox.getChildren().addAll(choiceBox, choiceBtn);
        vBox.getChildren().addAll(l1,l2,hbox, table, btn);

        Scene scene = new Scene(vBox, 600, 800);
        stage.setScene(scene);
        stage.show();

        choiceBtn.setOnAction(e ->
        {
            String choice = choiceBox.getValue().toString();
            if(choice.equals("Wszyscy klienci")) expression = "";
            else if(choice.equals("Klienci zarejestrowani")) expression="WHERE haslo IS NOT NULL";
            else if(choice.equals("Klienci niezarejestrowani")) expression="WHERE haslo IS NULL";
            l2.setText(choice);
            setList(expression);
        });

        btn.setOnAction(event ->
        {
            stage.close();
        });

    }
        static private void setList(String expression)
        {
            stage.close();
            show();
        }

}
