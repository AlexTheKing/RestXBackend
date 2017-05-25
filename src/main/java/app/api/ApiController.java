package app.api;

import app.json.JsonWrapper;
import app.model.dto.DishDTO;
import app.model.entities.comment.Comment;
import app.model.dao.CommentDAO;
import app.model.entities.dish.Cost;
import app.model.entities.dish.Dish;
import app.model.dao.DishDAO;
import app.model.entities.rate.Rate;
import app.model.dao.RateDAO;
import app.util.Constants;
import app.model.DataEnvironment;
import app.util.Response;
import app.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.crypto.Data;
import java.util.Calendar;

@RestController
public class ApiController {

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
            final DishDAO dishDAO = DataEnvironment.getDishDAO();
            final DishDTO dishDTO = new DishDTO(name, weight, type, cost, currency, description, ingredients, bitmapUrl);
            final Dish dish = dishDTO.convert();
            final Response<String> response = new Response<>();
            DataEnvironment.run((Void) -> {
                if (dishDAO.add(dish)) {
                    System.out.println(String.format(ACTIONS.ADD_DISH, sCalendar.getTime().toString(), dish.toString()));
                    response.setContent(Constants.JSON_RESPONSES.SUCCESS);
                } else {
                    System.out.println(String.format(ACTIONS.ADD_DISH, sCalendar.getTime(), Constants.LOG.ERROR + dish.toString()));
                    response.setContent(Constants.JSON_RESPONSES.ERROR);
                }
            });

            return response.getContent();
        } catch (Exception e) {
            System.err.println(String.format(ACTIONS.ADD_DISH, sCalendar.getTime().toString(), e));
            e.printStackTrace();

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
            final DishDAO dishDAO = DataEnvironment.getDishDAO();
            final DishDTO dishDTO = new DishDTO(name, weight, type, cost, currency, description, ingredients, bitmapUrl);
            final Dish dish = dishDTO.convert();
            final Response<String> response = new Response<>();
            DataEnvironment.run((Void) -> {
                if (dishDAO.update(dish, id)) {
                    System.out.println(String.format(ACTIONS.UPDATE_DISH, sCalendar.getTime().toString(), dish.toString()));
                    response.setContent(Constants.JSON_RESPONSES.SUCCESS);
                } else {
                    System.out.println(String.format(ACTIONS.UPDATE_DISH, sCalendar.getTime(), Constants.LOG.ERROR + dish.toString()));
                    response.setContent(Constants.JSON_RESPONSES.ERROR);
                }
            });

            return response.getContent();
        } catch (Exception e) {
            System.err.println(String.format(ACTIONS.UPDATE_DISH, sCalendar.getTime().toString(), e));
            e.printStackTrace();

            return Constants.JSON_RESPONSES.ERROR;
        }
    }

    @RequestMapping("/app/api/delete/dish")
    public String deleteDish(@RequestParam(name = "name") String name) {
        try {
            final DishDAO dishDAO = DataEnvironment.getDishDAO();
            final Response<String> response = new Response<>();
            DataEnvironment.run((Void) -> {
                final Dish dish = dishDAO.getByName(name);

                if (dish != null && dishDAO.delete(dish)) {
                    System.out.println(String.format(ACTIONS.DELETE_DISH, sCalendar.getTime(), dish.toString()));
                    response.setContent(Constants.JSON_RESPONSES.SUCCESS);
                } else {
                    System.out.println(String.format(ACTIONS.DELETE_DISH, sCalendar.getTime(), Constants.LOG.ERROR));
                    response.setContent(Constants.JSON_RESPONSES.ERROR);
                }
            });

            return response.getContent();
        } catch (Exception e) {
            System.err.println(String.format(ACTIONS.DELETE_DISH, sCalendar.getTime().toString(), e));
            e.printStackTrace();

            return Constants.JSON_RESPONSES.ERROR;
        }
    }

    @RequestMapping("/app/api/list/types")
    public String listTypes() {
        try {
            final DishDAO dishDAO = DataEnvironment.getDishDAO();
            final Response<String> response = new Response<>();
            DataEnvironment.run((Void) -> response.setContent(JsonWrapper.INSTANCE.wrapTypes(dishDAO.getTypes())));

            return response.getContent();
        } catch (Exception e) {
            System.err.println(String.format(ACTIONS.LIST_TYPES, sCalendar.getTime().toString(), e));
            e.printStackTrace();

            return Constants.JSON_RESPONSES.ERROR;
        }
    }

    @RequestMapping("/app/api/list/dishes")
    public String listDishes() {
        try {
            final DishDAO dishDAO = DataEnvironment.getDishDAO();
            final Response<String> response = new Response<>();
            DataEnvironment.run((Void) -> response.setContent(JsonWrapper.INSTANCE.wrapDishes(dishDAO.getAll())));

            return response.getContent();
        } catch (Exception e) {
            System.err.println(String.format(ACTIONS.LIST_DISHES, sCalendar.getTime().toString(), e));
            e.printStackTrace();

            return Constants.JSON_RESPONSES.ERROR;
        }
    }

    @RequestMapping("/app/api/add/comment")
    public String addComment(@RequestParam(name = "appid") String appId,
                             @RequestParam(name = "comment") String comment,
                             @RequestParam(name = "dishname") String dishName) {
        try {
            final DishDAO dishDAO = DataEnvironment.getDishDAO();
            final CommentDAO commentDAO = DataEnvironment.getCommentDAO();
            final Response<String> response = new Response<>();
            DataEnvironment.run((Void) -> {
                final Dish dish = dishDAO.getByName(dishName);
                final Comment commentObj = new Comment(appId, comment, dish);

                if (dish != null && commentDAO.add(commentObj)) {
                    response.setContent(Constants.JSON_RESPONSES.SUCCESS);
                } else {
                    response.setContent(Constants.JSON_RESPONSES.ERROR);
                }
            });

            return listComments(dishName);
        } catch (Exception e) {
            System.err.println(String.format(ACTIONS.ADD_COMMENT, sCalendar.getTime().toString(), e));
            e.printStackTrace();

            return Constants.JSON_RESPONSES.ERROR;
        }
    }

    @RequestMapping("/app/api/delete/comment")
    public String deleteComment(@RequestParam(name = "id") int id){
        try {
            final CommentDAO commentDAO = DataEnvironment.getCommentDAO();
            final Response<String> response = new Response<>();
            DataEnvironment.run((Void) -> {
                final Comment comment = commentDAO.getById(id);

                if(comment != null && commentDAO.delete(comment)){
                    response.setContent(Constants.JSON_RESPONSES.SUCCESS);
                } else {
                    response.setContent(Constants.JSON_RESPONSES.ERROR);
                }
            });

            return response.getContent();
        } catch (Exception e){
            System.err.println(String.format(ACTIONS.DELETE_COMMENT, sCalendar.getTime().toString(), e));
            e.printStackTrace();

            return Constants.JSON_RESPONSES.ERROR;
        }
    }

    @RequestMapping("/app/api/list/comments")
    public String listComments(@RequestParam(name = "dishname", defaultValue = StringUtils.EMPTY) String dishName) {
        try {
            final CommentDAO commentDAO = DataEnvironment.getCommentDAO();
            final Response<String> response = new Response<>();
            DataEnvironment.run((Void) -> {
                if(dishName.equals(StringUtils.EMPTY)) {
                    response.setContent(JsonWrapper.INSTANCE.wrapComments(commentDAO.getAll()));
                } else {
                    response.setContent(JsonWrapper.INSTANCE.wrapComments(commentDAO.getByName(dishName)));
                }
            });

            return response.getContent();
        } catch (Exception e) {
            System.err.println(String.format(ACTIONS.LIST_COMMENTS, sCalendar.getTime().toString(), e));
            e.printStackTrace();

            return Constants.JSON_RESPONSES.ERROR;
        }
    }

    @RequestMapping("/app/api/add/rate")
    public String addRate(@RequestParam(name = "appid") String appId,
                          @RequestParam(name = "rate") int rate,
                          @RequestParam(name = "dishname") String dishName) {
        try {
            final DishDAO dishDAO = DataEnvironment.getDishDAO();
            final RateDAO rateDAO = DataEnvironment.getRateDAO();
            final Response<String> response = new Response<>();
            DataEnvironment.run((Void) -> {
                final Dish dish = dishDAO.getByName(dishName);
                final Rate rateObj = new Rate(appId, rate, dish);

                if (dish != null && rateDAO.add(rateObj)) {
                    rateDAO.add(rateObj);
                    dish.calcAverageEstimation();
                    response.setContent(String.format("%.2f", dish.getAverageEstimation()));
                } else {
                    response.setContent(Constants.JSON_RESPONSES.ERROR);
                }
            });

            return response.getContent();
        } catch (Exception e) {
            System.err.println(String.format(ACTIONS.ADD_RATE, sCalendar.getTime().toString(), e));
            e.printStackTrace();

            return Constants.JSON_RESPONSES.ERROR;
        }
    }

    @RequestMapping("/app/api/delete/rate")
    public String deleteRate(@RequestParam(name = "id") int id){
        try {
            final RateDAO rateDAO = DataEnvironment.getRateDAO();
            final Response<String> response = new Response<>();
            DataEnvironment.run((Void) -> {
                final Rate rate = rateDAO.getById(id);

                if(rate != null && rateDAO.delete(rate)){
                    response.setContent(Constants.JSON_RESPONSES.SUCCESS);
                } else {
                    response.setContent(Constants.JSON_RESPONSES.ERROR);
                }
            });

            return response.getContent();
        } catch (Exception e){
            System.err.println(String.format(ACTIONS.DELETE_RATE, sCalendar.getTime().toString(), e));
            e.printStackTrace();

            return Constants.JSON_RESPONSES.ERROR;
        }
    }

    @RequestMapping("/app/api/list/rates")
    public String listRates() {
        try {
            final RateDAO rateDAO = DataEnvironment.getRateDAO();
            final Response<String> response = new Response<>();
            DataEnvironment.run((Void) -> response.setContent(JsonWrapper.INSTANCE.wrapRates(rateDAO.getAll())));

            return response.getContent();
        } catch (Exception e) {
            System.err.println(String.format(ACTIONS.LIST_RATES, sCalendar.getTime().toString(), e));
            e.printStackTrace();

            return Constants.JSON_RESPONSES.ERROR;
        }
    }

    @RequestMapping("/app/api/list/recommendations")
    public String listRecommendations(@RequestParam(name = "appid") String appId){
        try {
            final DishDAO dishDAO = DataEnvironment.getDishDAO();
            final RateDAO rateDAO = DataEnvironment.getRateDAO();
            final Response<String> response = new Response<>();
            DataEnvironment.run((Void) -> {
                RecommendationHandler handler = new RecommendationHandler(dishDAO, rateDAO);
                response.setContent(JsonWrapper.INSTANCE.wrapDishes(handler.getRecommendations(appId), true));
            });

            return response.getContent();
        } catch (Exception e) {
            System.err.println(String.format(ACTIONS.LIST_RECOMMENDATIONS, sCalendar.getTime().toString(), e));
            e.printStackTrace();

            return Constants.JSON_RESPONSES.ERROR;
        }
    }
}
