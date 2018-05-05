package Core;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import javax.persistence.TemporalType;
import java.util.Date;
import java.util.List;

public final class DbService
{
    static final SessionFactory sessionFactory = new Configuration()
            .configure("hibernate2.cfg.xml")
            .addAnnotatedClass(Klienci.class)
            .addAnnotatedClass(Pracownicy.class)
            .addAnnotatedClass(PokojeHotelowe.class)
            .addAnnotatedClass(Rezerwacje.class)
            .addAnnotatedClass(Platnosci.class)
            .buildSessionFactory();

    public static void save(Object o)
    {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(o);
        session.getTransaction().commit();
        session.close();
    }

    public static void update(Object o)
    {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.update(o);
        session.getTransaction().commit();
        session.close();
    }

    public static boolean isConnected()
    {
        Session session = sessionFactory.openSession();
        return session.isConnected();
    }

    public static void saveOrUpdate(Object o)
    {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.saveOrUpdate(o);
        session.getTransaction().commit();
        session.close();
    }
    public static void delete(Object o)
    {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.delete(o);
        session.getTransaction().commit();
        session.close();
    }

    public static void merge(Object o)
    {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.merge(o);
        session.getTransaction().commit();
        session.close();
    }

    public static Object get(Class c, long id)
    {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Object o = session.get(c,id);
        session.getTransaction().commit();
        session.close();
        return o;
    }

    public static boolean existsByExpression(Object o, String expression)
    {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Query query = session.createQuery(expression);
        List list = query.getResultList();
        session.getTransaction().commit();
        session.close();
        if(list.isEmpty()) return false;
        else return true;
    }
    public static boolean existsByOla(String expression)
    {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Query query = session.createQuery(expression);
        List list = query.getResultList();
        session.getTransaction().commit();
        session.close();
        if(list.isEmpty()) return false;
        else return true;
    }

    public static boolean existsById(Object o, long id)
    {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Object tmp = session.get(o.getClass(),id);
        if(tmp==null) return false;
        else return true;
    }

    public static Object objectById(Object o, long id)
    {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Object tmp = session.get(o.getClass(),id);
        return tmp;
    }

    public static List returnListByExpression(Object o, String expression)
    {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Query query = session.createQuery(expression);
        List list = query.getResultList();
        session.getTransaction().commit();
        session.close();
        return list;
    }


    public static void close()
    {
        sessionFactory.close();
    }
}
