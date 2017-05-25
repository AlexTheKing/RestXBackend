package app.json;

import app.model.entities.comment.Comment;
import app.model.entities.dish.mapping.DishMapping;
import app.model.entities.dish.Cost;
import app.model.entities.dish.Dish;
import app.model.entities.rate.Rate;
import app.util.Constants;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.List;
import java.util.Locale;

public enum JsonWrapper {

    INSTANCE;

    private static final ObjectMapper mMapper = new ObjectMapper();

    public String wrapTypes(List<String> types) {
        ObjectNode rootObj = mMapper.createObjectNode();
        ObjectNode responseObj = rootObj.putObject(Constants.JSON_WRAPPER_PARTS.RESPONSE);
        ArrayNode typesArray = responseObj.putArray(Constants.JSON_WRAPPER_PARTS.TYPES);

        for (String type : types) {
            typesArray.add(type);
        }

        return rootObj.toString();
    }

    public String wrapDishes(List<Dish> dishes) {
        return wrapDishes(dishes, false);
    }

    public String wrapDishes(List<Dish> dishes, boolean isRecommendations) {
        ObjectNode rootObj = mMapper.createObjectNode();
        ObjectNode responseObj = rootObj.putObject(Constants.JSON_WRAPPER_PARTS.RESPONSE);
        ArrayNode arrayNode = null;

        if(isRecommendations) {
            arrayNode = responseObj.putArray(Constants.JSON_WRAPPER_PARTS.RECOMMENDATIONS);
        }

        for (Dish dish : dishes) {
            String type = dish.getType();

            if(!isRecommendations) {
                if (!responseObj.has(type)) {
                    responseObj.putArray(type);
                }

                arrayNode = ((ArrayNode) responseObj.get(type));
            }

            ObjectNode dishObj = arrayNode.addObject();
            addDishMapping(dishObj, dish);
        }

        return rootObj.toString();
    }

    public String wrapComments(List<Comment> comments) {
        ObjectNode rootObj = mMapper.createObjectNode();
        ObjectNode responseObj = rootObj.putObject(Constants.JSON_WRAPPER_PARTS.RESPONSE);
        ArrayNode commentsArray = responseObj.putArray(Constants.JSON_WRAPPER_PARTS.COMMENTS);

        for (Comment comment : comments) {
            commentsArray.add(comment.getComment());
        }

        return rootObj.toString();
    }

    public String wrapRates(List<Rate> ratings) {
        ObjectNode rootObj = mMapper.createObjectNode();
        ObjectNode responseObj = rootObj.putObject(Constants.JSON_WRAPPER_PARTS.RESPONSE);
        ArrayNode ratesArray = responseObj.putArray(Constants.JSON_WRAPPER_PARTS.COMMENTS);

        for (Rate rate : ratings) {
            ratesArray.add(rate.getRate());
        }

        return rootObj.toString();
    }

    private void addDishMapping(ObjectNode dishObj, Dish dish) {
        dishObj.put(DishMapping.NAME, dish.getName());
        Cost cost = dish.getCost();
        dishObj.put(DishMapping.TYPE, dish.getType());
        dishObj.put(DishMapping.COST, String.format(DishMapping.COST_BUILDER, cost.getFirstOrder(), cost.getSecondOrder()));
        dishObj.put(DishMapping.CURRENCY, dish.getCurrency());
        dishObj.put(DishMapping.WEIGHT, dish.getWeight());
        dishObj.put(DishMapping.DESCRIPTION, dish.getDescription());
        dishObj.put(DishMapping.AVERAGE_ESTIMATION, String.format(Locale.US, "%.2f", dish.calcAverageEstimation()));
        dishObj.put(DishMapping.BITMAP_URL, dish.getBitmapUrl());
        ArrayNode ingredientsArray = dishObj.putArray(DishMapping.INGREDIENTS);

        for (String ingredient : dish.getIngredients()) {
            ingredientsArray.add(ingredient);
        }
    }
}
