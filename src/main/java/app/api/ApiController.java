package app.api;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Calendar;

@RestController
public class ApiController {

    private static DishDAO sDishDAO = new DishDAO(new DishValidator());
    private static CommentDAO sCommentDAO = new CommentDAO();
    private static RateDAO sRateDAO = new RateDAO();
    private static Calendar sCalendar = Calendar.getInstance();

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
        String LIST_RATINGS = "[%s] [LIST RATINGS] %s";
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
        try {
            Dish dish = sDishDAO.getByName(name);
            if(dish != null && sDishDAO.delete(dish)){
                return Constants.JSON_RESPONSES.SUCCESS;
            } else {
                return Constants.JSON_RESPONSES.ERROR;
            }
        } catch (Exception e){
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
        try {
            return JsonWrapper.INSTANCE.wrapDishes(sDishDAO.getAll());
        } catch (Exception e) {
            System.err.println(String.format(ACTIONS.LIST_DISHES, sCalendar.getTime().toString(), e));

            return Constants.JSON_RESPONSES.ERROR;
        }
    }

    @RequestMapping("/app/api/add/comment")
    public String addComment(@RequestParam(name = "appid") String appId,
                             @RequestParam(name = "comment") String comment,
                             @RequestParam(name = "dishname") String dishName) {
        try {
            Dish dish = sDishDAO.getByName(dishName);
            Comment commentObj = new Comment(appId, comment, dish);

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
        try {
            Comment comment = sCommentDAO.getById(id);

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
        try {
            return JsonWrapper.INSTANCE.wrapComments(sCommentDAO.getAll());
        } catch (Exception e) {
            System.err.println(String.format(ACTIONS.LIST_COMMENTS, sCalendar.getTime().toString(), e));

            return Constants.JSON_RESPONSES.ERROR;
        }
    }

    @RequestMapping("/app/api/add/rate")
    public String addRate(@RequestParam(name = "appid") String appId,
                          @RequestParam(name = "rate") int rate,
                          @RequestParam(name = "dishname") String dishName) {
        try {
            Dish dish = sDishDAO.getByName(dishName);
            Rate rateObj = new Rate(appId, rate, dish);

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
        try {
            Rate rate = sRateDAO.getById(id);

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

    @RequestMapping("/app/api/list/ratings")
    public String listRatings() {
        try {
            return JsonWrapper.INSTANCE.wrapRatings(sRateDAO.getAll());
        } catch (Exception e) {
            System.err.println(String.format(ACTIONS.LIST_RATINGS, sCalendar.getTime().toString(), e));

            return Constants.JSON_RESPONSES.ERROR;
        }
    }


}
