package Engine;

import Core.Klienci;
import JavaFX.Klienci.KlienciMenu;
import JavaFX.Komunikaty.Blad;
import JavaFX.Logowanie.Logowanie;

public final class KlientEngine
{
    public static Klienci klienci;



    public static void zaloguj()
    {
        if(klienci != null) KlienciMenu.start();
        else
        {
            Blad.show("Zaloguj się!");
            Logowanie.logowanie();
        }
    }

    public static void  wyloguj()
    {
        klienci = null;
    }

    public static Klienci getKlienci()
    {
        return klienci;
    }
    public static void setKlienci(Klienci klienci)
    {
        KlientEngine.klienci=klienci;
    }
}
