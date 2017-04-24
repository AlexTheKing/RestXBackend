package app.model.comment;

import app.model.dish.Dish;

import javax.persistence.*;

@Entity
@Table(name = "COMMENTS")
public class Comment {

    @Id
    @GeneratedValue
    @Column(name = "COMMENTS_ID", nullable = false, unique = true)
    private int mId;

    @Column(name = "COMMENTS_APP_ID", nullable = false)
    private String mAppInstanceId;

    @Column(name = "COMMENTS_COMMENT", nullable = false)
    private String mComment;

    @ManyToOne(fetch = FetchType.EAGER)
    private Dish mDish;

    public Comment(String appInstanceId, String comment, Dish dish) {
        mAppInstanceId = appInstanceId;
        mComment = comment;
        mDish = dish;
    }

    public Comment() {

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

    public String getComment() {
        return mComment;
    }

    public void setComment(String comment) {
        mComment = comment;
    }

    public Dish getDish() {
        return mDish;
    }

    public void setDish(Dish dish) {
        mDish = dish;
    }
}
