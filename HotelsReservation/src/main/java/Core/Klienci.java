package Core;

import org.mindrot.jbcrypt.BCrypt;

import javax.persistence.*;

@Entity
@Table(name = "klienci")
@SequenceGenerator(name= "klient_seq", sequenceName = "SEQ_ID", initialValue=10000001, allocationSize = 1)
final public class Klienci
{
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "klient_seq")
    private long id_klient;
    @Column(name="imie")
    private String imie;
    @Column(name="nazwisko")
    private String nazwisko;
    @Column(name="adres")
    private String adres;
    @Column(name="telefon")
    private String telefon;
    @Column(name="haslo")
    private String haslo;
    private static long counter = 10000001;

    public Klienci()
    {
    }

    public Klienci(String imie, String nazwisko, String adres, String telefon, String haslo)
    {
        this.id_klient = counter;
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.adres = adres;
        this.telefon = telefon;
        if(haslo!=null) this.haslo = BCrypt.hashpw(haslo, BCrypt.gensalt());
        else this.haslo = haslo;
        counter++;
    }

    public String getHaslo()
    {
        return haslo;
    }

    public void setHaslo(String haslo)
    {
        this.haslo = haslo;
    }

    public long getId_klient()
    {
        return id_klient;
    }

    public void setId_klient(long id_klient)
    {
        this.id_klient = id_klient;
    }

    public String getImie()
    {
        return imie;
    }

    public void setImie(String imie)
    {
        this.imie = imie;
    }

    public String getNazwisko()
    {
        return nazwisko;
    }

    public void setNazwisko(String nazwisko)
    {
        this.nazwisko = nazwisko;
    }

    public String getAdres()
    {
        return adres;
    }

    public void setAdres(String adres)
    {
        this.adres = adres;
    }

    public String getTelefon()
    {
        return telefon;
    }

    public void setTelefon(String telefon)
    {
        this.telefon = telefon;
    }

    @Override
    public String toString()
    {
        return  " " + imie + '\n' +
                " " + nazwisko + '\n' +
                " " + adres + '\n' +
                " " + telefon + '\n';
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Klienci klienci = (Klienci) o;
        return id_klient == klienci.id_klient;
    }

    @Override
    public int hashCode()
    {
        return (int) (id_klient ^ (id_klient >>> 32));
    }
}
