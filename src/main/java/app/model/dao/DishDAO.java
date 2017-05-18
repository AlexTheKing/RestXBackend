package app.model.dao;

import app.database.DatabaseHandler;
import app.database.validator.Validator;
import app.model.entities.comment.Comment;
import app.model.entities.dish.Dish;
import app.model.entities.rate.Rate;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class DishDAO extends AbstractDAO{

    private Validator<Dish> mValidator;

    public DishDAO(Validator<Dish> validator) {
        mValidator = validator;
    }

    public Validator<Dish> getValidator() {
        return mValidator;
    }

    public boolean add(Dish dish){
        final Session session = getSession();

        return mValidator.validate(dish) && DatabaseHandler.run(session, (Void) -> session.save(dish));
    }

    public boolean update(Dish dish, int id){
        final Session session = getSession();

        return mValidator.validate(dish) && DatabaseHandler.run(session, (Void) -> {
            dish.setId(id);
            session.update(dish);
        });
    }

    public boolean delete(Dish dish){
        final Session session = getSession();

        return DatabaseHandler.run(session, (Void) -> {
            final List<Comment> comments = dish.getComments();
            final List<Rate> rates = dish.getRates();

            for(Comment comment : comments){
                session.delete(comment);
            }

            for(Rate rate : rates){
                session.delete(rate);
            }

            session.delete(dish);
        });
    }

    @SuppressWarnings("unchecked")
    public List<String> getTypes() {
        final List[] list = new List[1];
        final Session session = getSession();

        DatabaseHandler.run(session, (Void) -> {
            list[0] = (List<String>) session.createQuery("select distinct dish.mType from Dish dish").list();
        });

        return list[0];
    }

    @SuppressWarnings("unchecked")
    public List<Dish> getAll(){
        final List[] list = new List[1];
        final Session session = getSession();

        DatabaseHandler.run(session, (Void) -> {
            list[0] = (List<Dish>) session.createQuery("from Dish").list();
        });

        return list[0];
    }

    public Dish getByName(String name) {
        final Dish[] dish = new Dish[1];
        final Session session = getSession();

        DatabaseHandler.run(session, (Void) -> {
            Query query = session.createQuery("from Dish dish where dish.mName= :name");
            query.setParameter("name", name);
            dish[0] = (Dish) query.list().get(0);
        });

        return dish[0];
    }
}
