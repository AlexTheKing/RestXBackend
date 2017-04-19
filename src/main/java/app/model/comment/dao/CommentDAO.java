package app.model.comment.dao;

import app.database.DatabaseHandler;
import app.model.comment.Comment;
import org.hibernate.Session;

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

}
