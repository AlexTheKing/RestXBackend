package app.model.dish.dao;

import app.database.DatabaseHandler;
import app.database.ICallback;
import app.database.validator.Validator;
import app.model.dish.Dish;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class DishDAO {

    private Validator<Dish> mValidator;

    public DishDAO(Validator<Dish> validator) {
        mValidator = validator;
    }

    public Validator<Dish> getValidator() {
        return mValidator;
    }

    public boolean add(Dish dish){
        return mValidator.validate(dish) && DatabaseHandler.run((Session session) -> session.save(dish));
    }

    @SuppressWarnings("unchecked")
    public void getAll(ICallback<List<Dish>> callback){
        DatabaseHandler.run((Session session) -> {
            final List<Dish> list = (List<Dish>) session.createQuery("from Dish").list();
            callback.execute(list);
        });
    }

    public boolean update(Dish dish, int id){
        return mValidator.validate(dish) && DatabaseHandler.run((Session session) -> {
            dish.setId(id);
            session.update(dish);
        });
    }

    public boolean delete(Dish dish){
        return DatabaseHandler.run((Session session) -> session.delete(dish));
    }

    public void getByName(String name, ICallback<Dish> callback) {
        DatabaseHandler.run((Session session) -> {
            Query query = session.createQuery("from Dish dish where dish.mName= :name");
            query.setParameter("name", name);
            final Dish dish = (Dish) query.list().get(0);
            callback.execute(dish);
        });
    }

    @SuppressWarnings("unchecked")
    public List<String> getTypes() {
        final List[] list = new List[1];
        DatabaseHandler.run((Session session) -> {
            list[0] = (List<String>) session.createQuery("select distinct dish.mType from Dish dish").list();
        });

        return list[0];
    }
}
