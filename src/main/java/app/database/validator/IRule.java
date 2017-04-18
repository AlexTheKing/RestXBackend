package app.database.validator;

@FunctionalInterface
public interface IRule<T> {

    boolean check(T obj);

}
