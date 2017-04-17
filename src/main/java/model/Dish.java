package model;

import javax.persistence.*;

@Entity
@Table(name = "DISHES")
public class Dish {

    @Id
    @GeneratedValue
    private int mId;

    @Column(name = "DISH_NAME", nullable = false)
    private String mName;

    @Column(name = "DISH_WEIGHT", nullable = false)
    private String mWeight;

    @Column(name = "DISH_TYPE", nullable = false)
    private String mType;

    @Column(name = "DISH_COST", nullable = false)
    private float mCost;

    @Column(name = "DISH_CURRENCY", nullable = false)
    private String mCurrency;

    @Column(name = "DISH_DESCRIPTION", nullable = false)
    private String mDescription;

    @Column(name = "DISH_AVERAGE_ESTIMATION", nullable = false)
    private float mAverageEstimation;

    @Column(name = "DISH_INGREDIENTS", nullable = false)
    private String[] mIngredients;

    @Column(name = "DISH_BITMAP_URL", nullable = false)
    private String mBitmapUrl;

    public Dish() {

    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getWeight() {
        return mWeight;
    }

    public void setWeight(String weight) {
        mWeight = weight;
    }

    public String getType() {
        return mType;
    }

    public void setType(String type) {
        mType = type;
    }

    public float getCost() {
        return mCost;
    }

    public void setCost(float cost) {
        mCost = cost;
    }

    public String getCurrency() {
        return mCurrency;
    }

    public void setCurrency(String currency) {
        mCurrency = currency;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public float getAverageEstimation() {
        return mAverageEstimation;
    }

    public void setAverageEstimation(float averageEstimation) {
        mAverageEstimation = averageEstimation;
    }

    public String[] getIngredients() {
        return mIngredients;
    }

    public void setIngredients(String[] ingredients) {
        mIngredients = ingredients;
    }

    public String getBitmapUrl() {
        return mBitmapUrl;
    }

    public void setBitmapUrl(String bitmapUrl) {
        mBitmapUrl = bitmapUrl;
    }
}
