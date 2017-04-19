package app.model.comment;

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

    //TODO : add foreign key annotation
    @Column(name = "COMMENTS_DISH_ID")
    private int mDishId;

    public Comment(String appInstanceId, String comment, int dishId) {
        mAppInstanceId = appInstanceId;
        mComment = comment;
        mDishId = dishId;
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

    public int getDishId() {
        return mDishId;
    }

    public void setDishId(int dishId) {
        mDishId = dishId;
    }
}
