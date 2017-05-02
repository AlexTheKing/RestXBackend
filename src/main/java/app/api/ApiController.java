package app.api;

import app.database.DatabaseHandler;
import app.database.validator.DishValidator;
import app.json.JsonWrapper;
import app.model.comment.Comment;
import app.model.comment.dao.CommentDAO;
import app.model.dish.Cost;
import app.model.dish.Dish;
import app.model.dish.dao.DishDAO;
import app.model.rate.Rate;
import app.model.rate.dao.RateDAO;
import app.util.Constants;
import app.util.RecommendationHandler;
import app.util.Response;
import org.hibernate.Session;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Calendar;
import java.util.List;

@RestController
public class ApiController {

    private static final DishDAO sDishDAO = new DishDAO(new DishValidator());
    private static final CommentDAO sCommentDAO = new CommentDAO();
    private static final RateDAO sRateDAO = new RateDAO();
    private static final RecommendationHandler sRecommendationHandler = new RecommendationHandler(sDishDAO, sRateDAO);
    private static final Calendar sCalendar = Calendar.getInstance();

    private interface ACTIONS {
        String ADD_DISH = "[%s] [ADD DISH] %s";
        String ADD_COMMENT = "[%s] [ADD COMMENT] %s";
        String ADD_RATE = "[%s] [ADD RATE] %s";
        String UPDATE_DISH = "[%s] [UPDATE DISH] %s";
        String DELETE_DISH = "[%s] [DELETE DISH] %s";
        String DELETE_COMMENT = "[%s] [DELETE COMMENT] %s";
        String DELETE_RATE = "[%s] [DELETE RATE] %s";
        String LIST_TYPES = "[%s] [LIST TYPES] %s";
        String LIST_DISHES = "[%s] [LIST DISHES] %s";
        String LIST_COMMENTS = "[%s] [LIST COMMENTS] %s";
        String LIST_RATES = "[%s] [LIST RATINGS] %s";
        String LIST_RECOMMENDATIONS = "[%s] [LIST RECOMMENDATIONS] %s";
    }


    @RequestMapping("/app/api/add/dish")
    public String addDish(@RequestParam(name = "name") String name,
                          @RequestParam(name = "weight") String weight,
                          @RequestParam(name = "type") String type,
                          @RequestParam(name = "cost") String cost,
                          @RequestParam(name = "currency") String currency,
                          @RequestParam(name = "description") String description,
                          @RequestParam(name = "ingredients") String ingredients,
                          @RequestParam(name = "bitmapurl") String bitmapUrl) {
        try {
            String[] costSplitted = cost.split(Constants.URI.COST_SPLITTER);
            String[] ingredientsAsArray = ingredients.split(Constants.URI.INGREDIENTS_SPLITTER);
            int firstOrder = Integer.parseInt(costSplitted[0]);
            int secondOrder = 0;
            if(costSplitted.length == 2){
                secondOrder = Integer.parseInt(costSplitted[1]);
            }
            Cost costObj = new Cost(firstOrder, secondOrder);
            Dish dish = new Dish(name, weight, type, costObj, currency, description, ingredientsAsArray, bitmapUrl);
            System.out.println(String.format(ACTIONS.ADD_DISH, sCalendar.getTime().toString(), dish.toString()));

            if (sDishDAO.add(dish)) {
                return Constants.JSON_RESPONSES.SUCCESS;
            } else {
                return Constants.JSON_RESPONSES.ERROR;
            }
        } catch (Exception e) {
            System.err.println(String.format(ACTIONS.ADD_DISH, sCalendar.getTime().toString(), e));

            return Constants.JSON_RESPONSES.ERROR;
        }
    }

    @RequestMapping("/app/api/update/dish")
    public String updateDish(@RequestParam(name = "id") int id,
                             @RequestParam(name = "name") String name,
                             @RequestParam(name = "weight") String weight,
                             @RequestParam(name = "type") String type,
                             @RequestParam(name = "cost") String cost,
                             @RequestParam(name = "currency") String currency,
                             @RequestParam(name = "description") String description,
                             @RequestParam(name = "ingredients") String ingredients,
                             @RequestParam(name = "bitmapurl") String bitmapUrl) {
        try {
            String[] costSplitted = cost.split(Constants.URI.COST_SPLITTER);
            String[] ingredientsAsArray = ingredients.split(Constants.URI.INGREDIENTS_SPLITTER);
            int firstOrder = Integer.parseInt(costSplitted[0]);
            int secondOrder = 0;
            if(costSplitted.length == 2){
                secondOrder = Integer.parseInt(costSplitted[1]);
            }
            Cost costObj = new Cost(firstOrder, secondOrder);
            Dish dish = new Dish(name, weight, type, costObj, currency, description, ingredientsAsArray, bitmapUrl);
            System.out.println(String.format(ACTIONS.UPDATE_DISH, sCalendar.getTime().toString(), dish.toString()));

            if (sDishDAO.update(dish, id)) {
                return Constants.JSON_RESPONSES.SUCCESS;
            } else {
                return Constants.JSON_RESPONSES.ERROR;
            }
        } catch (Exception e) {
            System.err.println(String.format(ACTIONS.UPDATE_DISH, sCalendar.getTime().toString(), e));

            return Constants.JSON_RESPONSES.ERROR;
        }
    }

    @RequestMapping("/app/api/delete/dish")
    public String deleteDish(@RequestParam(name = "name") String name) {
        try (final Session session = DatabaseHandler.getSession()) {
            final Dish dish = sDishDAO.getByName(session, name);

            if (dish != null && sDishDAO.delete(dish)) {
                return Constants.JSON_RESPONSES.SUCCESS;
            } else {
                return Constants.JSON_RESPONSES.ERROR;
            }
        } catch (Exception e) {
            System.err.println(String.format(ACTIONS.DELETE_DISH, sCalendar.getTime().toString(), e));

            return Constants.JSON_RESPONSES.ERROR;
        }
    }

    @RequestMapping("/app/api/list/types")
    public String listTypes() {
        try {
            return JsonWrapper.INSTANCE.wrapTypes(sDishDAO.getTypes());
        } catch (Exception e) {
            System.err.println(String.format(ACTIONS.LIST_TYPES, sCalendar.getTime().toString(), e));

            return Constants.JSON_RESPONSES.ERROR;
        }
    }

    @RequestMapping("/app/api/list/dishes")
    public String listDishes() {
        try (final Session session = DatabaseHandler.getSession()) {
            final List<Dish> dishes = sDishDAO.getAll(session);

            return JsonWrapper.INSTANCE.wrapDishes(dishes);
        } catch (Exception e) {
            System.err.println(String.format(ACTIONS.LIST_DISHES, sCalendar.getTime().toString(), e));

            return Constants.JSON_RESPONSES.ERROR;
        }
    }

    @RequestMapping("/app/api/add/comment")
    public String addComment(@RequestParam(name = "appid") String appId,
                             @RequestParam(name = "comment") String comment,
                             @RequestParam(name = "dishname") String dishName) {
        try (final Session session = DatabaseHandler.getSession()) {
            final Dish dish = sDishDAO.getByName(session, dishName);
            final Comment commentObj = new Comment(appId, comment, dish);

            if (sCommentDAO.add(commentObj)) {
                return Constants.JSON_RESPONSES.SUCCESS;
            } else {
                return Constants.JSON_RESPONSES.ERROR;
            }
        } catch (Exception e) {
            System.err.println(String.format(ACTIONS.ADD_COMMENT, sCalendar.getTime().toString(), e));

            return Constants.JSON_RESPONSES.ERROR;
        }
    }

    @RequestMapping("/app/api/delete/comment")
    public String deleteComment(@RequestParam(name = "id") int id){
        try (final Session session = DatabaseHandler.getSession()) {
            final Comment comment = sCommentDAO.getById(session, id);

            if(comment != null && sCommentDAO.delete(comment)){
                return Constants.JSON_RESPONSES.SUCCESS;
            } else {
                return Constants.JSON_RESPONSES.ERROR;
            }
        } catch (Exception e){
            System.err.println(String.format(ACTIONS.DELETE_COMMENT, sCalendar.getTime().toString(), e));

            return Constants.JSON_RESPONSES.ERROR;
        }
    }

    @RequestMapping("/app/api/list/comments")
    public String listComments() {
        try (final Session session = DatabaseHandler.getSession()) {
            final List<Comment> comments = sCommentDAO.getAll(session);

            return JsonWrapper.INSTANCE.wrapComments(comments);
        } catch (Exception e) {
            System.err.println(String.format(ACTIONS.LIST_COMMENTS, sCalendar.getTime().toString(), e));

            return Constants.JSON_RESPONSES.ERROR;
        }
    }

    @RequestMapping("/app/api/add/rate")
    public String addRate(@RequestParam(name = "appid") String appId,
                          @RequestParam(name = "rate") int rate,
                          @RequestParam(name = "dishname") String dishName) {
        try (final Session session = DatabaseHandler.getSession()) {
            final Dish dish = sDishDAO.getByName(session, dishName);
            final Rate rateObj = new Rate(appId, rate, dish);

            if (sRateDAO.add(rateObj)) {
                return Constants.JSON_RESPONSES.SUCCESS;
            } else {
                return Constants.JSON_RESPONSES.ERROR;
            }
        } catch (Exception e) {
            System.err.println(String.format(ACTIONS.ADD_RATE, sCalendar.getTime().toString(), e));

            return Constants.JSON_RESPONSES.ERROR;
        }
    }

    @RequestMapping("/app/api/delete/rate")
    public String deleteRate(@RequestParam(name = "id") int id){
        try (final Session session = DatabaseHandler.getSession()) {
            final Rate rate = sRateDAO.getById(session, id);

            if(rate != null && sRateDAO.delete(rate)){
                return Constants.JSON_RESPONSES.SUCCESS;
            } else {
                return Constants.JSON_RESPONSES.ERROR;
            }
        } catch (Exception e){
            System.err.println(String.format(ACTIONS.DELETE_RATE, sCalendar.getTime().toString(), e));

            return Constants.JSON_RESPONSES.ERROR;
        }
    }

    @RequestMapping("/app/api/list/rates")
    public String listRates() {
        try (final Session session = DatabaseHandler.getSession()) {
            final List<Rate> rates = sRateDAO.getAll(session);

            return JsonWrapper.INSTANCE.wrapRates(rates);
        } catch (Exception e) {
            System.err.println(String.format(ACTIONS.LIST_RATES, sCalendar.getTime().toString(), e));

            return Constants.JSON_RESPONSES.ERROR;
        }
    }

    @RequestMapping("/app/api/list/recommendations")
    public String listRecommendations(@RequestParam(name = "appid") String appId){
        try (final Session session = DatabaseHandler.getSession()){
            List<Dish> recommendedDishes = sRecommendationHandler.getRecommendations(session, appId);

            return JsonWrapper.INSTANCE.wrapDishes(recommendedDishes);
        } catch (Exception e) {
            System.err.println(String.format(ACTIONS.LIST_RECOMMENDATIONS, sCalendar.getTime().toString(), e));

            return Constants.JSON_RESPONSES.ERROR;
        }
    }
}
