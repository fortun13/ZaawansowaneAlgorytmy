package pl.edu.agh.parallel.matrices.utils;

import java.util.function.*;

/**
 * Created by Jakub Fortunka on 15.11.2016.
 */
public class LambdaExceptionUtils {
    @FunctionalInterface
    public interface Function_WithExceptions<T, R, E extends Exception> {
        R apply(T t) throws E;
    }

    @FunctionalInterface
    public interface BinaryOperator_WithExceptions<T,E extends Exception> {
        T apply(T t1, T t2) throws E;
    }

    public static <T, E extends Exception> BinaryOperator<T> rethrowBinaryOperator(BinaryOperator_WithExceptions<T,E> binaryOperator) throws E {
        return (t1,t2) -> {
            try {
                return binaryOperator.apply(t1, t2);
            } catch (Exception exception) {
                throwActualException(exception);
                return null;
            }
        };
    }

    public static <T, R, E extends Exception> Function<T, R> rethrowFunction(Function_WithExceptions<T, R, E> function) throws E  {
        return t -> {
            try {
                return function.apply(t);
            } catch (Exception exception) {
                throwActualException(exception);
                return null;
            }
        };
    }

    @SuppressWarnings("unchecked")
    private static <E extends Exception> void throwActualException(Exception exception) throws E {
        throw (E) exception;
    }

}
