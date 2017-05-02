package app.util;

public class Response {

    private String mResponseString;

    public Response() {

    }

    public Response(String responseString) {
        mResponseString = responseString;
    }

    @Override
    public String toString() {
        return mResponseString;
    }

    public void setResponseString(String responseString) {
        mResponseString = responseString;
    }
}
