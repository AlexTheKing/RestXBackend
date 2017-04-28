package app.json;

import app.model.comment.Comment;
import app.model.dish.mapping.DishMapping;
import app.model.dish.Cost;
import app.model.dish.Dish;
import app.model.rate.Rate;
import app.util.Constants;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.List;

public enum JsonWrapper {

    INSTANCE;

    public String wrapTypes(List<String> types) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode rootObj = mapper.createObjectNode();
        ObjectNode responseObj = rootObj.putObject(Constants.JSON_WRAPPER_PARTS.RESPONSE);
        ArrayNode typesArray = responseObj.putArray(Constants.JSON_WRAPPER_PARTS.TYPES);

        for (String type : types) {
            typesArray.add(type);
        }

        return rootObj.toString();
    }

    public String wrapDishes(List<Dish> dishes) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode rootObj = mapper.createObjectNode();
        ObjectNode responseObj = rootObj.putObject(Constants.JSON_WRAPPER_PARTS.RESPONSE);

        for (Dish dish : dishes) {
            String type = dish.getType();

            if (!responseObj.has(type)) {
                responseObj.putArray(type);
            }

            ArrayNode arrayNode = ((ArrayNode) responseObj.get(type));
            ObjectNode dishObj = arrayNode.addObject();
            dishObj.put(DishMapping.NAME, dish.getName());
            Cost cost = dish.getCost();
            dishObj.put(DishMapping.COST, String.format(DishMapping.COST_BUILDER, cost.getFirstOrder(), cost.getSecondOrder()));
            dishObj.put(DishMapping.CURRENCY, dish.getCurrency());
            dishObj.put(DishMapping.WEIGHT, dish.getWeight());
            dishObj.put(DishMapping.DESCRIPTION, dish.getDescription());
            dishObj.put(DishMapping.AVERAGE_ESTIMATION, dish.getAverageEstimation());
            dishObj.put(DishMapping.BITMAP_URL, dish.getBitmapUrl());
            ArrayNode ingredientsArray = dishObj.putArray(DishMapping.INGREDIENTS);

            for (String ingredient : dish.getIngredients()) {
                ingredientsArray.add(ingredient);
            }
        }

        return rootObj.toString();
    }

    public String wrapComments(List<Comment> comments) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode rootObj = mapper.createObjectNode();
        ObjectNode responseObj = rootObj.putObject(Constants.JSON_WRAPPER_PARTS.RESPONSE);
        ArrayNode commentsArray = responseObj.putArray(Constants.JSON_WRAPPER_PARTS.COMMENTS);

        for (Comment comment : comments) {
            commentsArray.add(comment.getComment());
        }

        return rootObj.toString();
    }

    public String wrapRates(List<Rate> ratings) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode rootObj = mapper.createObjectNode();
        ObjectNode responseObj = rootObj.putObject(Constants.JSON_WRAPPER_PARTS.RESPONSE);
        ArrayNode ratesArray = responseObj.putArray(Constants.JSON_WRAPPER_PARTS.COMMENTS);

        for (Rate rate : ratings) {
            ratesArray.add(rate.getRate());
        }

        return rootObj.toString();
    }
}
