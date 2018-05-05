package Engine;

import Core.Pracownicy;
import JavaFX.Komunikaty.Blad;
import JavaFX.Logowanie.Logowanie;
import JavaFX.Pracownicy.PracownikMenu;

import java.io.FileNotFoundException;

public class PracownicyEngine
{
    static private Pracownicy pracownik;

    public static void setPracownik(Pracownicy pracownik)
    {
        PracownicyEngine.pracownik = pracownik;
    }

    public static void zaloguj()
    {
        if(pracownik != null) PracownikMenu.start();
        else
        {
            Blad.show("Zaloguj się!");
            Logowanie.logowanie();
        }
    }

    public static void wyloguj()
    {
        pracownik = null;
    }

    public static Pracownicy getPracownik()
    {
        return pracownik;
    }
}
