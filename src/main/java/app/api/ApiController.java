package app.api;

import app.database.dao.DishDAO;
import app.database.validator.DishValidator;
import app.model.Cost;
import app.model.Dish;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import app.util.Constants;

import java.util.Calendar;

@RestController
public class ApiController {

    private static DishDAO sDishDAO = new DishDAO(new DishValidator());
    private interface ACTIONS {
        String ADD = "[%s] [ADD] %s";
        String UPDATE = "[%s] [UPDATE] %s";
    }


    @RequestMapping("/app/api/add")
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
            int secondOrder = Integer.parseInt(costSplitted[1]);
            Cost costObj = new Cost(firstOrder, secondOrder);
            Dish dish = new Dish(name, weight, type, costObj, currency, description, ingredientsAsArray, bitmapUrl);
            System.out.println(String.format(ACTIONS.ADD, Calendar.getInstance().getTime().toString(), dish.toString()));
            if(sDishDAO.add(dish)){
                return Constants.JSON_RESPONSES.SUCCESS;
            } else {
                return Constants.JSON_RESPONSES.ERROR;
            }
        } catch (Exception e){
            System.err.println("[ADD] Exception: " + e);
            return Constants.JSON_RESPONSES.ERROR;
        }
    }

    @RequestMapping("/app/api/update")
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
            int secondOrder = Integer.parseInt(costSplitted[1]);
            Cost costObj = new Cost(firstOrder, secondOrder);
            Dish dish = new Dish(name, weight, type, costObj, currency, description, ingredientsAsArray, bitmapUrl);
            System.out.println(String.format(ACTIONS.UPDATE, Calendar.getInstance().getTime().toString(), dish.toString()));
            if(sDishDAO.update(dish, id)){
                return Constants.JSON_RESPONSES.SUCCESS;
            } else {
                return Constants.JSON_RESPONSES.ERROR;
            }
        } catch (Exception e){
            System.err.println("[UPDATE] Exception: " + e);
            return Constants.JSON_RESPONSES.ERROR;
        }
    }

}
