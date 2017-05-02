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
import app.util.RecommendationHandler;
import app.util.Response;
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
        try {
            final Response response = new Response();
            sDishDAO.getByName(name, (Dish dish) -> {
                if(dish != null && sDishDAO.delete(dish)){
                    response.setResponseString(Constants.JSON_RESPONSES.SUCCESS);
                } else {
                    response.setResponseString(Constants.JSON_RESPONSES.ERROR);
                }
            });

            return response.toString();
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
            final Response response = new Response();
            sDishDAO.getAll((List<Dish> dishes) -> {
                response.setResponseString(JsonWrapper.INSTANCE.wrapDishes(dishes));
            });

            return response.toString();
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
            final Response response = new Response();
            sDishDAO.getByName(dishName, (Dish dish) -> {
                Comment commentObj = new Comment(appId, comment, dish);

                if (sCommentDAO.add(commentObj)) {
                    response.setResponseString(Constants.JSON_RESPONSES.SUCCESS);
                } else {
                    response.setResponseString(Constants.JSON_RESPONSES.ERROR);
                }
            });

            return response.toString();
        } catch (Exception e) {
            System.err.println(String.format(ACTIONS.ADD_COMMENT, sCalendar.getTime().toString(), e));

            return Constants.JSON_RESPONSES.ERROR;
        }
    }

    @RequestMapping("/app/api/delete/comment")
    public String deleteComment(@RequestParam(name = "id") int id){
        try {
            final Response response = new Response();
            sCommentDAO.getById(id, (Comment comment) -> {
                if(comment != null && sCommentDAO.delete(comment)){
                    response.setResponseString(Constants.JSON_RESPONSES.SUCCESS);
                } else {
                    response.setResponseString(Constants.JSON_RESPONSES.ERROR);
                }
            });

            return response.toString();
        } catch (Exception e){
            System.err.println(String.format(ACTIONS.DELETE_COMMENT, sCalendar.getTime().toString(), e));

            return Constants.JSON_RESPONSES.ERROR;
        }
    }

    @RequestMapping("/app/api/list/comments")
    public String listComments() {
        try {
            final Response response = new Response();
            sCommentDAO.getAll((List<Comment> comments) -> {
                response.setResponseString(JsonWrapper.INSTANCE.wrapComments(comments));
            });

            return response.toString();
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
            final Response response = new Response();
            sDishDAO.getByName(dishName, (Dish dish) -> {
                Rate rateObj = new Rate(appId, rate, dish);

                if (sRateDAO.add(rateObj)) {
                    response.setResponseString(Constants.JSON_RESPONSES.SUCCESS);
                } else {
                    response.setResponseString(Constants.JSON_RESPONSES.ERROR);
                }
            });

            return response.toString();
        } catch (Exception e) {
            System.err.println(String.format(ACTIONS.ADD_RATE, sCalendar.getTime().toString(), e));

            return Constants.JSON_RESPONSES.ERROR;
        }
    }

    @RequestMapping("/app/api/delete/rate")
    public String deleteRate(@RequestParam(name = "id") int id){
        try {
            final Response response = new Response();
            sRateDAO.getById(id, (Rate rate) -> {
                if(rate != null && sRateDAO.delete(rate)){
                    response.setResponseString(Constants.JSON_RESPONSES.SUCCESS);
                } else {
                    response.setResponseString(Constants.JSON_RESPONSES.ERROR);
                }
            });

            return response.toString();
        } catch (Exception e){
            System.err.println(String.format(ACTIONS.DELETE_RATE, sCalendar.getTime().toString(), e));

            return Constants.JSON_RESPONSES.ERROR;
        }
    }

    @RequestMapping("/app/api/list/rates")
    public String listRates() {
        try {
            final Response response = new Response();
            sRateDAO.getAll((List<Rate> rates) -> {
                response.setResponseString(JsonWrapper.INSTANCE.wrapRates(rates));
            });

            return response.toString();
        } catch (Exception e) {
            System.err.println(String.format(ACTIONS.LIST_RATES, sCalendar.getTime().toString(), e));

            return Constants.JSON_RESPONSES.ERROR;
        }
    }

    @RequestMapping("/app/api/list/recommendations")
    public String listRecommendations(@RequestParam(name = "appid") String appId){
        try {
            List<Dish> recommendedDishes = sRecommendationHandler.getRecommendations(appId);

            return JsonWrapper.INSTANCE.wrapDishes(recommendedDishes);
        } catch (Exception e) {
            System.err.println(String.format(ACTIONS.LIST_RECOMMENDATIONS, sCalendar.getTime().toString(), e));

            return Constants.JSON_RESPONSES.ERROR;
        }
    }
}
