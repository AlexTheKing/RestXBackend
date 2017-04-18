package app.util;

public class Constants {

    public interface URI {
        String INGREDIENTS_SPLITTER = ",";
        String COST_SPLITTER = "\\.";
    }

    public interface JSON_RESPONSES {
        String ERROR = "{\"response\":{\"error\":\"Some error encountered during action\"}}";
        String SUCCESS = "{\"response\":{\"success\":\"All actions has been performed\"}}";
    }

}
