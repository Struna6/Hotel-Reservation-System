package Core;

public enum Udogodnienia
{
    BASEN("Możesz tam pływać!"),
    SAUNA("Możesz się tam pocić"),
    SPA("Możesz się tam zrelaksować"),
    TV("Dostępne ponad 100 kanałów!"),
    PRYWATNE_WC("Prywatna, osobna łazienka"),
    WSPOLNE_WC("Współdzielona z innymi mieszkańcami łazienka");

    private String opis;

    Udogodnienia(String napis)
    {
        opis = napis;
    }

    public String getOpis()
    {
        return opis;
    }

    public void setOpis(String opis)
    {
        this.opis = opis;
    }

    Udogodnienia()
    {
        opis = "NA";
    }


}
