package database;

import org.hibernate.Session;

@FunctionalInterface
public interface IWorker {

    void work(Session session);

}
