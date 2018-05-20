package Core;

import org.mindrot.jbcrypt.BCrypt;

import javax.persistence.*;

@Entity
@Table(name="pracownicy")
@SequenceGenerator(name= "pracownik_seq", sequenceName = "SEQ_ID2", initialValue=90000001, allocationSize = 1)
final public class Pracownicy
{
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pracownik_seq")
    private long id_pracownicy;
    @Column(name="imie")
    private String imie;
    @Column(name="nazwisko")
    private String nazwisko;
    @Column(name="haslo")
    private String haslo;
    private static long counter = 90000001;

    public Pracownicy()
    {
    }

    public Pracownicy(String imie, String nazwisko, String haslo)
    {
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.id_pracownicy = counter;
        this.haslo = BCrypt.hashpw(haslo, BCrypt.gensalt());
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

    public long getId_pracownicy()
    {
        return id_pracownicy;
    }

    public void setId_pracownicy(long id_pracownicy)
    {
        this.id_pracownicy = id_pracownicy;
    }

    @Override
    public String toString()
    {
        return  " " + imie + '\n' +
                " " + nazwisko + '\n' +
                " " + id_pracownicy + '\n' +
                " " + haslo + '\n';
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Pracownicy that = (Pracownicy) o;
        return id_pracownicy == that.id_pracownicy;
    }

    @Override
    public int hashCode()
    {
        return (int) (id_pracownicy ^ (id_pracownicy >>> 32));
    }
}
