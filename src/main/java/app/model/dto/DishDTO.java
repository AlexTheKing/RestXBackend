package app.model.dto;

import app.model.entities.dish.Cost;
import app.model.entities.dish.Dish;
import app.util.Constants;

public class DishDTO {
    private String mName;
    private String mWeight;
    private String mType;
    private String mCost;
    private String mCurrency;
    private String mDescription;
    private String mIngredients;
    private String mBitmapUrl;

    public DishDTO() { }

    public DishDTO(String name,
                   String weight,
                   String type,
                   String cost,
                   String currency,
                   String description,
                   String ingredients,
                   String bitmapUrl) {
        this.mName = name;
        this.mWeight = weight;
        this.mType = type;
        this.mCost = cost;
        this.mCurrency = currency;
        this.mDescription = description;
        this.mIngredients = ingredients;
        this.mBitmapUrl = bitmapUrl;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public String getWeight() {
        return mWeight;
    }

    public void setWeight(String weight) {
        this.mWeight = weight;
    }

    public String getType() {
        return mType;
    }

    public void setType(String type) {
        this.mType = type;
    }

    public String getCost() {
        return mCost;
    }

    public void setCost(String cost) {
        this.mCost = cost;
    }

    public String getCurrency() {
        return mCurrency;
    }

    public void setCurrency(String currency) {
        this.mCurrency = currency;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        this.mDescription = description;
    }

    public String getIngredients() {
        return mIngredients;
    }

    public void setIngredients(String ingredients) {
        this.mIngredients = ingredients;
    }

    public String getBitmapUrl() {
        return mBitmapUrl;
    }

    public void setBitmapUrl(String bitmapUrl) {
        this.mBitmapUrl = bitmapUrl;
    }

    public Dish convert(){
        final String[] costSplitted = mCost.split(Constants.URI.COST_SPLITTER);
        final String[] ingredientsAsArray = mIngredients.split(Constants.URI.INGREDIENTS_SPLITTER);
        final int firstOrder = Integer.parseInt(costSplitted[0]);
        int secondOrder = 0;

        if(costSplitted.length == 2){
            secondOrder = Integer.parseInt(costSplitted[1]);
        }

        final Cost costObj = new Cost(firstOrder, secondOrder);

        return new Dish(mName, mWeight, mType, costObj, mCurrency, mDescription, ingredientsAsArray, mBitmapUrl);
    }
}
