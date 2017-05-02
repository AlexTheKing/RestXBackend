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
    public void getAll(ICallback<List<Rate>> callback){
        DatabaseHandler.run((Session session) -> {
            List<Rate> list = (List<Rate>) session.createQuery("from Rate").list();
            callback.execute(list);
        });
    }

    public void getById(int id, ICallback<Rate> callback) {
        DatabaseHandler.run((Session session) -> {
            Query query = session.createQuery("from Rate rate where rate.mId= :id");
            query.setParameter("id", id);
            Rate rate = (Rate) query.list().get(0);
            callback.execute(rate);
        });
    }

    public boolean delete(Rate rate){
        return DatabaseHandler.run((Session session) -> session.delete(rate));
    }

    @SuppressWarnings("unchecked")
    public void getByAppInstanceId(String appInstanceId, ICallback<List<Rate>> callback) {
        DatabaseHandler.run((Session session) -> {
            Query query = session.createQuery("from Rate rate where rate.mAppInstanceId= :id");
            query.setParameter("id", appInstanceId);
            List<Rate> rates = (List<Rate>) query.list();
            callback.execute(rates);
        });
    }
}
