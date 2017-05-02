package app.model;

import app.database.DatabaseHandler;
import app.database.ICallback;
import app.database.validator.DishValidator;
import app.model.dao.CommentDAO;
import app.model.dao.DishDAO;
import app.model.dao.RateDAO;
import org.hibernate.Session;

public class DataEnvironment {

    private static final DishDAO sDishDAO = new DishDAO(new DishValidator());
    private static final CommentDAO sCommentDAO = new CommentDAO();
    private static final RateDAO sRateDAO = new RateDAO();

    private static void setSession(final Session session){
        sDishDAO.setSession(session);
        sCommentDAO.setSession(session);
        sRateDAO.setSession(session);
    }

    public static void run(ICallback<Void> callback){
        try (final Session session = DatabaseHandler.getSession()) {
            setSession(session);
            callback.execute(null);
        }
    }

    public static DishDAO getDishDAO() {
        return sDishDAO;
    }

    public static CommentDAO getCommentDAO() {
        return sCommentDAO;
    }

    public static RateDAO getRateDAO() {
        return sRateDAO;
    }
}
