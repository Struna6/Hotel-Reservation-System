package Core;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;


@Entity
@Table(name="rezerwacje")
final public class Rezerwacje
{
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id_rezerwacji;
    @Column(name="od")
    private Date pobyt_od;
    @Column(name="do")
    private Date pobyt_do;
    @Column(name="kwota")
    private int kwota;
    @Column(name="data_rezerwacji")
    private Date data_rez;
    @Column(name="czy_rozliczony")
    private boolean rozliczony;

    private static long i = 0;
    private static long counter = 1;
    //@Access(AccessType.PROPERTY)
    private long pobyt_odLong;
    //@Access(AccessType.PROPERTY)
    private long pobyt_doLong;

    @ManyToOne
    @JoinColumn(name= "id_platnosci", foreignKey = @ForeignKey(name = "id_platnosci"))
    private Platnosci platnosci;
    @ManyToMany(cascade = {CascadeType.ALL, CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.EAGER)
    @JoinColumn(name = "id_pokoje")
    private List<PokojeHotelowe> pokoje;
    @ManyToOne
    @JoinColumn(name = "id_klient", foreignKey = @ForeignKey(name = "id_klient"))
    private Klienci klienci;
    @ManyToOne
    @JoinColumn(name = "id_pracownicy", foreignKey = @ForeignKey(name = "id_pracownicy"))
    private Pracownicy pracownik;
    @ManyToOne
    @JoinColumn(name = "id_pracownik_roz", foreignKey = @ForeignKey(name = "id_pracownik_roz"))
    private Pracownicy pracownikRozliczajacy;

    public Rezerwacje()
    {
        pokoje = new ArrayList<>();
        kwota = 0;
    }

    public Rezerwacje(long pobyt_od, long pobyt_do, Klienci klienci)
    {
        Date date1 = new Date(pobyt_od);
        this.pobyt_od = date1;
        Date date2 = new Date(pobyt_do);
        this.pobyt_do = date2;
        this.kwota = 0;
        Date data = new Date();
        this.data_rez = data;
        this.klienci = klienci;
        this.id_rezerwacji = counter;
        rozliczony = false;
        pobyt_doLong = pobyt_do;
        pobyt_odLong = pobyt_od;
        counter++;
    }

    public long getPobyt_odLong()
    {
        return pobyt_odLong;
    }

    public void setPobyt_odLong(long pobyt_odLong)
    {
        this.pobyt_odLong = pobyt_odLong;
    }

    public long getPobyt_doLong()
    {
        return pobyt_doLong;
    }

    public void setPobyt_doLong(long pobyt_doLong)
    {
        this.pobyt_doLong = pobyt_doLong;
    }

    public Pracownicy getPracownikRozliczajacy()
    {
        return pracownikRozliczajacy;
    }

    public void setPracownikRozliczajacy(Pracownicy pracownikRozliczajacy)
    {
        this.pracownikRozliczajacy = pracownikRozliczajacy;
    }

    public boolean isRozliczony()
    {
        return rozliczony;
    }

    public void setRozliczony(boolean rozliczony)
    {
        this.rozliczony = rozliczony;
    }


    public Platnosci getPlatnosci()
    {
        return platnosci;
    }

    public void setPlatnosci(Platnosci platnosci)
    {
        this.platnosci = platnosci;
    }

    public void setPokoje(List<PokojeHotelowe> pokoje)
    {
        this.pokoje = new ArrayList<>();
        this.pokoje = pokoje;
        for(PokojeHotelowe pokoj:pokoje)
        {
            kwota+=pokoj.getCena();
        }
        long od = pobyt_od.getTime(), d_o = pobyt_do.getTime();
        long dni = d_o-od;
        int przel = ((int)dni)/(1000*60*60*24);
        kwota=(kwota*przel);
    }

    public Klienci getKlienci()
    {
        return klienci;
    }

    public void setKlienci(Klienci klienci)
    {
        this.klienci = klienci;
    }

    public Pracownicy getPracownik()
    {
        return pracownik;
    }

    public void setPracownik(Pracownicy pracownik)
    {
        this.pracownik = pracownik;
    }

    public Date getPobyt_od()
    {
        return pobyt_od;
    }

    public void setPobyt_od(Date pobyt_od)
    {
        this.pobyt_od = pobyt_od;
    }

    public Date getPobyt_do()
    {
        return pobyt_do;
    }

    public void setPobyt_do(Date pobyt_do)
    {
        this.pobyt_do = pobyt_do;
    }

    public Date getData_rez()
    {
        return data_rez;
    }

    public void setData_rez(Date data_rez)
    {
        this.data_rez = data_rez;
    }

    public int getKwota()
    {
        return kwota;
    }

    public void setKwota(int kwota)
    {
        this.kwota = kwota;
    }


    public List<PokojeHotelowe> getPokoje()
    {
        return pokoje;
    }

    public void addPokoje(PokojeHotelowe pokoj)
    {
            pokoje.add(pokoj);
            kwota+=pokoj.getCena();
    }

    public long getId_rezerwacji()
    {
        return id_rezerwacji;
    }

    public void setId_rezerwacji(long id_rezerwacji)
    {
        this.id_rezerwacji = id_rezerwacji;
    }

    public void setPlatnosc(Platnosci platnosc)
    {
        this.platnosci = platnosc;
    }

    @Override
    public String toString()
    {
        return "Rezerwacje{Nr= " + id_rezerwacji +
                " @pobyt_od=" + pobyt_od.toString() +
                ", pobyt_do=" + pobyt_do.toString() +
                ", kwota=" + kwota +
                ", data_rez=" + data_rez.toString() +
                ", pokoje=" + pokoje +
                ", klienci=" + klienci +
                ", pracownik=" + pracownik +
                ", id_rezerwacji=" + id_rezerwacji +
                ", platności=" + platnosci +
                '}';
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Rezerwacje that = (Rezerwacje) o;

        return id_rezerwacji == that.id_rezerwacji;
    }

    @Override
    public int hashCode()
    {
        return (int) (id_rezerwacji ^ (id_rezerwacji >>> 32));
    }
}
