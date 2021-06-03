package pl.wiktrans.ims.misc;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FailableResource<T> {

    private boolean success;
    private String error;
    private T resource;

    public static <T> FailableResource<T> success(T resource) {
        return new FailableResource<>(true, null, resource);
    }

    public static <T> FailableResource<T> failure(String error) {
        return new FailableResource<>(false, error, null);
    }
}
