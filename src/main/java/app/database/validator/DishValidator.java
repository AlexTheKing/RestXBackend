package app.database.validator;

import app.model.entities.dish.Cost;
import app.model.entities.dish.Dish;

public class DishValidator extends Validator<Dish>{

    public DishValidator() {
        mRules.add((Dish dish) -> {
            Cost cost = dish.getCost();
            return cost.getFirstOrder() >= 0 || cost.getSecondOrder() >= 0 || cost.getSecondOrder() <= 100;
        });
        mRules.add((Dish dish) -> {
            try {
                return Float.parseFloat(dish.getWeight()) > 0;
            } catch (NumberFormatException e) {
                return false;
            }
        });
    }

}
