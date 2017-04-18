package database.dao;

import database.DatabaseHandler;
import database.validator.DishValidator;
import model.Dish;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class DishDAO {

    public boolean add(Dish dish, DishValidator validator){
        return validator.validate(dish) && DatabaseHandler.run((Session session) -> session.save(dish));
    }

    public List<Dish> getAll(){
        final List[] list = new List[1];
        DatabaseHandler.run((Session session) -> {
            list[0] = (List<Dish>) session.createQuery("from Dish").list();
        });
        return ((List<Dish>) list[0]);
    }

    public boolean update(Dish dish, DishValidator validator){
        return validator.validate(dish) && DatabaseHandler.run((Session session) -> session.update(dish));
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

}
