package it.enuwa.sfdc.utils;

/**
 * Created by Michele Festini on 4/2/2016.
 */
@FunctionalInterface
public interface Validator<T> {
    public boolean validate(T t);
}
