package app.database.dao;

import app.database.DatabaseHandler;
import app.database.validator.Validator;
import app.model.Dish;
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
    public List<Dish> getAll(){
        final List[] list = new List[1];
        DatabaseHandler.run((Session session) -> {
            list[0] = (List<Dish>) session.createQuery("from Dish").list();
        });

        return ((List<Dish>) list[0]);
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

    public Dish getByName(String name) {
        final Dish[] dish = new Dish[1];
        DatabaseHandler.run((Session session) -> {
            Query query = session.createQuery("from Dish where name :paramName");
            query.setParameter("paramName", name);
            dish[0] = (Dish) query.list().get(0);
        });

        return dish[0];
    }

    @SuppressWarnings("unchecked")
    public List<String> getTypes() {
        final List[] list = new List[1];
        DatabaseHandler.run((Session session) -> {
            list[0] = (List<String>) session.createQuery("select distinct Dish.Type from Dish");
        });

        return list[0];
    }

}
