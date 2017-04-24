package app.model.comment.dao;

import app.database.DatabaseHandler;
import app.model.comment.Comment;
import app.model.dish.Dish;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class CommentDAO {

    public boolean add(Comment comment){
        return DatabaseHandler.run((Session session) -> session.save(comment));
    }

    @SuppressWarnings("unchecked")
    public List<Comment> getAll(){
        final List[] list = new List[1];
        DatabaseHandler.run((Session session) -> {
            list[0] = (List<Comment>) session.createQuery("from Comment").list();
        });

        return list[0];
    }

    public Comment getById(int id) {
        final Comment[] comment = new Comment[1];
        DatabaseHandler.run((Session session) -> {
            Query query = session.createQuery("from Comment comment where comment.mId= :id");
            query.setParameter("id", id);
            comment[0] = (Comment) query.list().get(0);
        });

        return comment[0];
    }

    public boolean delete(Comment comment){
        return DatabaseHandler.run((Session session) -> session.delete(comment));
    }
}
