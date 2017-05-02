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
    public List<Dish> getAll(Session session){
        final List[] list = new List[1];
        DatabaseHandler.run(session, (Void) -> {
            list[0] = (List<Dish>) session.createQuery("from Dish").list();
        });

        return list[0];
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

    public Dish getByName(Session session, String name) {
        final Dish[] dish = new Dish[1];
        DatabaseHandler.run(session, (Void) -> {
            Query query = session.createQuery("from Dish dish where dish.mName= :name");
            query.setParameter("name", name);
            dish[0] = (Dish) query.list().get(0);
        });

        return dish[0];
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
