package database.validator;

import model.Dish;

import java.util.ArrayList;
import java.util.List;

public class DishValidator implements IValidator<Dish> {

    List<IRule<Dish>> mRules = new ArrayList<>();

    public List<IRule<Dish>> getRules() {
        return mRules;
    }

    public void setRules(List<IRule<Dish>> rules) {
        mRules = rules;
    }

    public boolean validate(Dish dish){
        for(IRule<Dish> rule : mRules){
            if(!rule.check(dish)){
                return false;
            }
        }
        return true;
    }
}
