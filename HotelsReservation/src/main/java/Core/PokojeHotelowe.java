package Core;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

@Entity
@Table(name="pokoje")

final public class PokojeHotelowe
{
        @Id
        @Column(name="id")
        @GeneratedValue(strategy = GenerationType.SEQUENCE)
        private long id;
        @Column(name="cena")
        private float cena;
        @Column(name="iluosobowy")
        private int osob;
        @Column(name="rozliczony")
        @Access(AccessType.PROPERTY)
        boolean dostepny;
        @Enumerated(EnumType.STRING)
        @ElementCollection(fetch = FetchType.EAGER)
        List<Udogodnienia> udogodnienia;
        private static long counter = 1;
        boolean wTrakcieRez = false;

        PokojeHotelowe()
        {
            udogodnienia = new LinkedList<Udogodnienia>();
        }

        PokojeHotelowe(int osob)
        {
            this.id=counter;
            udogodnienia = new LinkedList<Udogodnienia>();
            this.osob=osob;
            cena=100+osob*50;
            dostepny = true;
            counter++;
        }

    public boolean iswTrakcieRez()
    {
        return wTrakcieRez;
    }

    public void setwTrakcieRez(boolean wTrakcieRez)
    {
        this.wTrakcieRez = wTrakcieRez;
    }

    public boolean isDostepny()
        {
            return dostepny;
        }

        public void setDostepny(boolean dostepny)
        {
            this.dostepny = dostepny;
        }

        public float getCena()
        {
            return cena;
        }

        public void setCena(float cena)
        {
            this.cena = cena;
        }

        public int getOsob()
        {
            return osob;
        }

        public void setOsob(int osob)
        {
            this.osob = osob;
        }

        public List<Udogodnienia> getUdogodnienia()
        {
            return udogodnienia;
        }

        public void addUdogodnienie(Udogodnienia udogodnienie)
        {
            udogodnienia.add(udogodnienie);
            cena+=25;
        }

        public void delUdogodnienie(Udogodnienia udogodnienie)
        {
            udogodnienia.remove(udogodnienie);
            cena-=25;
        }

        public void setUdogodnienia(List<Udogodnienia> udogodnienia)
        {
            this.udogodnienia = new LinkedList<Udogodnienia>();
            this.udogodnienia = udogodnienia;
            int ile = udogodnienia.size();
            cena+=(ile*25);
        }

    @Override
    public String toString()
    {
        return "Numer pokoju: " + id +
                "cena: " + cena +
                "osobowy: " + osob +
                "\nudogodnienia=" + udogodnienia + "";
    }

    public long getId()
    {
        return id;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PokojeHotelowe that = (PokojeHotelowe) o;
        return id == that.id;
    }

    @Override
    public int hashCode()
    {
        return (int) (id ^ (id >>> 32));
    }

}


