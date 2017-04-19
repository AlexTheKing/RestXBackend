package app.model.rate;

import javax.persistence.*;

@Entity
@Table(name = "RATINGS")
public class Rate {

    @Id
    @GeneratedValue
    @Column(name = "RATINGS_ID", nullable = false, unique = true)
    private int mId;

    @Column(name = "RATINGS_APP_ID", nullable = false)
    private String mAppInstanceId;

    @Column(name = "RATINGS_RATE", nullable = false)
    private int mRate;

    //TODO : add foreign key annotation
    @Column(name = "RATINGS_DISH_ID")
    private int mDishId;

    public Rate(String appInstanceId, int rate, int dishId) {
        mAppInstanceId = appInstanceId;
        mRate = rate;
        mDishId = dishId;
    }

    public Rate() {

    }

    public int getId() {

        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getAppInstanceId() {
        return mAppInstanceId;
    }

    public void setAppInstanceId(String appInstanceId) {
        mAppInstanceId = appInstanceId;
    }

    public int getRate() {
        return mRate;
    }

    public void setRate(int rate) {
        mRate = rate;
    }

    public int getDishId() {
        return mDishId;
    }

    public void setDishId(int dishId) {
        mDishId = dishId;
    }
}
