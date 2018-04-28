package Main;

import Core.*;
import Engine.KlientEngine;
import JavaFX.Logowanie.Logowanie;
import JavaFX.Rezerwuj.Rezerwuj;
import javafx.application.Application;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main extends Application
{
    static ApplicationContext context = new ClassPathXmlApplicationContext("SpringConfiguration.xml");

    public static void main(String[] args)
    {
        load();
        launch();
        DbService.close();
    }

    private static void load()
    {
      /*  Rezerwacje rez1 = (Rezerwacje)context.getBean("rez1");
        Rezerwacje rez2 = (Rezerwacje)context.getBean("rez2");
        Rezerwacje rez3 = (Rezerwacje)context.getBean("rez3");*/
        Klienci k1 = (Klienci)context.getBean("klient1");
        Klienci k2 = (Klienci)context.getBean("klient2");
        Pracownicy p1 = (Pracownicy)context.getBean("pracownik1");
        Pracownicy p2 = (Pracownicy)context.getBean("pracownik2");
        Pracownicy p3 = (Pracownicy)context.getBean("pracownik3");
        PokojeHotelowe pok1 =(PokojeHotelowe)context.getBean("Pokoj1");
        PokojeHotelowe pok2 =(PokojeHotelowe)context.getBean("Pokoj2");
        PokojeHotelowe pok3 =(PokojeHotelowe)context.getBean("Pokoj3");

        DbService.save(k1);
        DbService.save(k2);
        DbService.save(p1);
        DbService.save(p2);
        DbService.save(p3);
        DbService.save(pok1);
        DbService.save(pok2);
        DbService.save(pok3);
        /*DbService.save(rez1);
        DbService.save(rez2);
        DbService.save(rez3);*/
    }

    public void start(Stage primaryStage) throws Exception
    {
        //Pracownicy p1 = (Pracownicy)context.getBean("pracownik1");
       // PracownicyEngine.setPracownik(p1);
        //PracownicyEngine.zaloguj();
        //Klienci k1 = (Klienci)context.getBean("klient1");
        //KlientEngine.setKlienci(k1);
        //KlientEngine.zaloguj();
        Logowanie.logowanie();
        //Rezerwuj.show();
        //Rejestracja.rejestrowanie();
    }
}
