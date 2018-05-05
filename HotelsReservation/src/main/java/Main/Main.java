package Main;

import Core.*;
import JavaFX.Logowanie.Intro;
import javafx.application.Application;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main extends Application
{
    static final ApplicationContext context = new ClassPathXmlApplicationContext("SpringConfiguration.xml");

    public static void main(String[] args)
    {
        //load(); ONLY FIRST LAUNCH!!!
        launch();
        DbService.close();
    }

    private static void load()
    {
        Klienci k1 = (Klienci)context.getBean("klient1");
        Klienci k2 = (Klienci)context.getBean("klient2");
        Pracownicy p1 = (Pracownicy)context.getBean("pracownik1");
        Pracownicy p2 = (Pracownicy)context.getBean("pracownik2");
        Pracownicy p3 = (Pracownicy)context.getBean("pracownik3");
        Pracownicy p4 = (Pracownicy)context.getBean("pracownik4");
        Pracownicy p5 = (Pracownicy)context.getBean("pracownik5");
        PokojeHotelowe pok1 =(PokojeHotelowe)context.getBean("Pokoj1");
        PokojeHotelowe pok2 =(PokojeHotelowe)context.getBean("Pokoj2");
        PokojeHotelowe pok3 =(PokojeHotelowe)context.getBean("Pokoj3");
        PokojeHotelowe pok4 =(PokojeHotelowe)context.getBean("Pokoj4");
        PokojeHotelowe pok5 =(PokojeHotelowe)context.getBean("Pokoj5");
        PokojeHotelowe pok6 =(PokojeHotelowe)context.getBean("Pokoj6");
        PokojeHotelowe pok7 =(PokojeHotelowe)context.getBean("Pokoj7");
        PokojeHotelowe pok8 =(PokojeHotelowe)context.getBean("Pokoj8");
        PokojeHotelowe pok9 =(PokojeHotelowe)context.getBean("Pokoj9");
        PokojeHotelowe pok10 =(PokojeHotelowe)context.getBean("Pokoj10");
        PokojeHotelowe pok11 =(PokojeHotelowe)context.getBean("Pokoj11");
        PokojeHotelowe pok12 =(PokojeHotelowe)context.getBean("Pokoj12");
        PokojeHotelowe pok13 =(PokojeHotelowe)context.getBean("Pokoj13");
        PokojeHotelowe pok14 =(PokojeHotelowe)context.getBean("Pokoj14");
        PokojeHotelowe pok15 =(PokojeHotelowe)context.getBean("Pokoj15");
        PokojeHotelowe pok16 =(PokojeHotelowe)context.getBean("Pokoj16");
        PokojeHotelowe pok17 =(PokojeHotelowe)context.getBean("Pokoj17");
        PokojeHotelowe pok18 =(PokojeHotelowe)context.getBean("Pokoj18");
        PokojeHotelowe pok19 =(PokojeHotelowe)context.getBean("Pokoj19");
        PokojeHotelowe pok20 =(PokojeHotelowe)context.getBean("Pokoj20");


        DbService.save(k1);
        DbService.save(k2);
        DbService.save(p1);
        DbService.save(p2);
        DbService.save(p3);
        DbService.save(p4);
        DbService.save(p5);
        DbService.save(pok1);
        DbService.save(pok2);
        DbService.save(pok3);
        DbService.save(pok4);
        DbService.save(pok5);
        DbService.save(pok6);
        DbService.save(pok7);
        DbService.save(pok8);
        DbService.save(pok9);
        DbService.save(pok10);
        DbService.save(pok11);
        DbService.save(pok12);
        DbService.save(pok13);
        DbService.save(pok14);
        DbService.save(pok15);
        DbService.save(pok16);
        DbService.save(pok17);
        DbService.save(pok18);
        DbService.save(pok19);
        DbService.save(pok20);
    }

    public void start(Stage primaryStage) throws Exception
    {
        Intro.show();
    }
}
