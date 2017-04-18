package app.database;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class DatabaseHandler {

    private static final SessionFactory sFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try {
            // Create the SessionFactory from hibernate.cfg.xml
            return new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            // Make sure you log the exception, as it might be swallowed
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static void shutdown() {
        // Close caches and connection pools
        sFactory.close();
    }

    public static boolean run(IWorker worker){
        Session session = sFactory.openSession();
        try {
            session.beginTransaction();
            worker.work(session);
            session.getTransaction().commit();
            session.close();

            return true;
        }
        catch (Exception e){
            System.err.println(e);
            session.getTransaction().rollback();
            session.close();

            return false;
        }
    }

}
