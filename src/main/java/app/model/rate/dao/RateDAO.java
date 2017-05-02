package app.model.rate.dao;

import app.database.DatabaseHandler;
import app.database.ICallback;
import app.model.comment.Comment;
import app.model.rate.Rate;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class RateDAO {

    public boolean add(Rate rate){
        return DatabaseHandler.run((Session session) -> session.save(rate));
    }

    @SuppressWarnings("unchecked")
    public List<Rate> getAll(Session session){
        final List[] list = new List[1];
        DatabaseHandler.run(session, (Void) -> {
            list[0] = (List<Rate>) session.createQuery("from Rate").list();
        });

        return list[0];
    }

    public Rate getById(Session session, int id) {
        final Rate[] rate = new Rate[1];
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
    public List<Rate> getByAppInstanceId(Session session, String appInstanceId) {
        final List[] list = new List[1];
        DatabaseHandler.run(session, (Void) -> {
            Query query = session.createQuery("from Rate rate where rate.mAppInstanceId= :id");
            query.setParameter("id", appInstanceId);
            list[0] = (List<Rate>) query.list();
        });

        return list[0];
    }
}
