package app.util;

public class Response<T> {

    private T mContent;

    public T getContent() {
        return mContent;
    }

    public void setContent(T content) {
        mContent = content;
    }
}
