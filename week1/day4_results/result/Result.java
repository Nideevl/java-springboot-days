package result;

import java.util.ArrayList;
import java.util.List;

public class Result<T> {
    private T value;
    private String error;

    private Result(T value, String error) {
        this.value = value;
        this.error = error;
    }

    public static <T> Result<T> success(T data) {
        return new Result<>(data,null);
    }

    public static <T> Result<T> failure(String error) {
        return new Result<>(null, error);
    }

    public boolean isSuccess() { return value != null; }

    public T getData() { return value; }
    public String getError() { return  error; }

}
