package database.validator;

public interface IValidator<T> {

    boolean validate(T obj);

}
