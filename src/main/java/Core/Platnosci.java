package Core;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "platnosci")
final public class Platnosci
{
    @Id
    @Column(name="id_platnosci")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id_platnosci;
    @Enumerated(EnumType.STRING)
    @Column(name="typ_platnosci")
    private Typy_platnosci platnosc;
    @Column(name="kwota")
    private int kwota;
    @Column(name="data_dokonania")
    private Date time;
    @Column(name="czydokonana")
    @Access(AccessType.PROPERTY)
    private boolean dokonana;
    private static int i = 1;

    public Platnosci()
    {
    }

    public Platnosci(Typy_platnosci platnosc, Rezerwacje rezerwacje)
    {
        this.id_platnosci = i;
        this.platnosc = platnosc;
        this.kwota = rezerwacje.getKwota();
        dokonana = false;
        i++;
    }

    public boolean isDokonana()
    {
        return dokonana;
    }

    public void setDokonana(boolean dokonana)
    {
        this.dokonana = dokonana;
        Date date = new Date();
        this.time = date;
    }

    public long getId_platnosci()
    {
        return id_platnosci;
    }

    public void setId_platnosci(int id_platnosci)
    {
        this.id_platnosci = id_platnosci;
    }

    public Typy_platnosci getPlatnosc()
    {
        return platnosc;
    }

    public void setPlatnosc(Typy_platnosci platnosc)
    {
        this.platnosc = platnosc;
    }

    public int getKwota()
    {
        return kwota;
    }

    public void setKwota(int kwota)
    {
        this.kwota = kwota;
    }

    public Date getTime()
    {
        return time;
    }

    public void setTime(Date time)
    {
        this.time = time;
    }

    @Override
    public String toString()
    {
        return "id_platnosci=" + id_platnosci +
                ", platnosc=" + platnosc +
                ", kwota=" + kwota +
                ", time=" + time +
                ", dokonana=" + dokonana;
    }

    public String doFaktury()
    {
        return "id_platnosci=" + id_platnosci +
                ", platnosc=" + platnosc +
                ", kwota=" + kwota +
                ", time=" + time;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Platnosci platnosci = (Platnosci) o;

        return id_platnosci == platnosci.id_platnosci;
    }

    @Override
    public int hashCode()
    {
        return (int) (id_platnosci ^ (id_platnosci >>> 32));
    }
}
