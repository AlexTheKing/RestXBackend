package app.model.dao;

import app.database.DatabaseHandler;
import app.model.entities.rate.Rate;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class RateDAO extends AbstractDAO {

    public boolean add(Rate rate){
        return DatabaseHandler.run((Session session) -> session.save(rate));
    }

    @SuppressWarnings("unchecked")
    public List<Rate> getAll(){
        final List[] list = new List[1];
        final Session session = getSession();

        DatabaseHandler.run(session, (Void) -> {
            list[0] = (List<Rate>) session.createQuery("from Rate").list();
        });

        return list[0];
    }

    public Rate getById(int id) {
        final Rate[] rate = new Rate[1];
        final Session session = getSession();

        DatabaseHandler.run(session, (Void) -> {
            Query query = session.createQuery("from Rate rate where rate.mId= :id");
            query.setParameter("id", id);
            rate[0] = (Rate) query.list().get(0);
        });

        return rate[0];
    }

    public boolean delete(Rate rate){
        return DatabaseHandler.run((Session session) -> session.delete(rate));
    }

    @SuppressWarnings("unchecked")
    public List<Rate> getByAppInstanceId(String appInstanceId) {
        final List[] list = new List[1];
        final Session session = getSession();

        DatabaseHandler.run(session, (Void) -> {
            Query query = session.createQuery("from Rate rate where rate.mAppInstanceId= :id");
            query.setParameter("id", appInstanceId);
            list[0] = (List<Rate>) query.list();
        });

        return list[0];
    }
}
