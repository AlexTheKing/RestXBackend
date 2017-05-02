package app.model.dao;

import app.database.DatabaseHandler;
import app.model.entities.comment.Comment;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class CommentDAO extends AbstractDAO {

    public boolean add(Comment comment){
        return DatabaseHandler.run((Session session) -> session.save(comment));
    }

    public boolean delete(Comment comment){
        return DatabaseHandler.run((Session session) -> session.delete(comment));
    }

    @SuppressWarnings("unchecked")
    public List<Comment> getAll(){
        final List[] list = new List[1];
        final Session session = getSession();

        DatabaseHandler.run(session, (Void) -> {
            list[0] = (List<Comment>) session.createQuery("from Comment").list();
        });

        return list[0];
    }

    public Comment getById(int id) {
        final Comment[] comment = new Comment[1];
        final Session session = getSession();

        DatabaseHandler.run(session, (Void) -> {
            Query query = session.createQuery("from Comment comment where comment.mId= :id");
            query.setParameter("id", id);
            comment[0] = (Comment) query.list().get(0);
        });

        return comment[0];
    }
}
