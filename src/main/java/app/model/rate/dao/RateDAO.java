package app.model.rate.dao;

import app.database.DatabaseHandler;
import app.model.rate.Rate;
import org.hibernate.Session;

import java.util.List;

public class RateDAO {

    public boolean add(Rate rate){
        return DatabaseHandler.run((Session session) -> session.save(rate));
    }

    @SuppressWarnings("unchecked")
    public List<Rate> getAll(){
        final List[] list = new List[1];
        DatabaseHandler.run((Session session) -> {
            list[0] = (List<Rate>) session.createQuery("from rate").list();
        });

        return list[0];
    }

}
