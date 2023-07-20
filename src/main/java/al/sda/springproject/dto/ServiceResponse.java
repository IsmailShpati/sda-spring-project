package al.sda.springproject.dto;

import lombok.Getter;
import lombok.ToString;

import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

@Getter
@ToString
public class ServiceResponse<T> {

    private T responseObject;
    private List<String> errors;

    private ServiceResponse() {}

    public static <T> ServiceResponse<T> of(T responseObject) {
        ServiceResponse<T> response = new ServiceResponse<>();
        response.responseObject = responseObject;
        return response;
    }


    public static <T> ServiceResponse<T> ofError(String error) {
        ServiceResponse<T> response = new ServiceResponse<>();
        response.errors = Collections.singletonList(error);
        return response;
    }

    public static <T> ServiceResponse<T> ofErrors(String... errors) {
        ServiceResponse<T> response = new ServiceResponse<>();
        response.errors = List.of(errors);
        return response;
    }

    public boolean hasErrors() {
        return errors != null && !errors.isEmpty();
    }

    public void ifHasErrors(Consumer<List<String>> errorConsumer) {
        if (!hasErrors()) {
            return;
        }
        errorConsumer.accept(this.errors);
    }

    public T get() {
        return this.responseObject;
    }


}
