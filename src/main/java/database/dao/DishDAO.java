package database.dao;

import database.DatabaseHandler;
import database.validator.DishValidator;
import model.Dish;
import org.hibernate.Session;

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

}
