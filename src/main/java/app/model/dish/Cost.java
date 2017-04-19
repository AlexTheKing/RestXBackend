package app.model.dish;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Cost {

    @Column(name = "DISH_COST_FIRST_ORDER")
    private int mFirstOrder;

    @Column(name = "DISH_COST_SECOND_ORDER")
    private int mSecondOrder;

    public Cost(int firstOrder, int secondOrder) {
        this.mFirstOrder = firstOrder;
        this.mSecondOrder = secondOrder;
    }

    public Cost() {

    }

    public int getFirstOrder() {
        return mFirstOrder;
    }

    public void setFirstOrder(int firstOrder) {
        this.mFirstOrder = firstOrder;
    }

    public int getSecondOrder() {
        return mSecondOrder;
    }

    public void setSecondOrder(int secondOrder) {
        this.mSecondOrder = secondOrder;
    }
}
