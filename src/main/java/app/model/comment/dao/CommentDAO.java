package app.model.comment.dao;

import app.database.DatabaseHandler;
import app.database.ICallback;
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
    public void getAll(ICallback<List<Comment>> callback){
        DatabaseHandler.run((Session session) -> {
            List<Comment> list = (List<Comment>) session.createQuery("from Comment").list();
            callback.execute(list);
        });
    }

    public void getById(int id, ICallback<Comment> callback) {
        DatabaseHandler.run((Session session) -> {
            Query query = session.createQuery("from Comment comment where comment.mId= :id");
            query.setParameter("id", id);
            Comment comment = (Comment) query.list().get(0);
            callback.execute(comment);
        });
    }

    public boolean delete(Comment comment){
        return DatabaseHandler.run((Session session) -> session.delete(comment));
    }
}
