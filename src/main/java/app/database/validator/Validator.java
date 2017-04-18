package app.database.validator;

import java.util.ArrayList;
import java.util.List;

public class Validator<T> {

    List<IRule<T>> mRules = new ArrayList<>();

    public Validator() {

    }

    public Validator(List<IRule<T>> rules) {
        mRules = rules;
    }

    public List<IRule<T>> getRules() {
        return mRules;
    }

    public void setRules(List<IRule<T>> rules) {
        mRules = rules;
    }

    public boolean validate(T t){
        for(IRule<T> rule : mRules){
            if(!rule.check(t)){
                return false;
            }
        }
        return true;
    }
}
