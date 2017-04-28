package app.database;

@FunctionalInterface
public interface ICallback<T> {

    void execute(T object);

}
