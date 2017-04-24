package app.model.rate;

import app.model.dish.Dish;

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

    @ManyToOne(fetch = FetchType.EAGER)
    private Dish mDish;

    public Rate(String appInstanceId, int rate, Dish dish) {
        mAppInstanceId = appInstanceId;
        mRate = rate;
        mDish = dish;
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

    public Dish getDish() {
        return mDish;
    }

    public void setDish(Dish dish) {
        mDish = dish;
    }
}
