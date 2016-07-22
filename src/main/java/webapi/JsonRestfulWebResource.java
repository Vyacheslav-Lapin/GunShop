package webapi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import javax.ws.rs.core.Response;
import java.util.Collection;
import java.util.function.Function;

public interface JsonRestfulWebResource {

    ObjectWriter DEFAULT_PRETTY_PRINTER = new ObjectMapper()
            .writer()
            .withDefaultPrettyPrinter();

    Function<Object, String> toJsonExceptional = value -> {
        try {
            return DEFAULT_PRETTY_PRINTER.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    };

    default String toJson(Object o) {
        return toJsonExceptional.apply(o);
    }

    default Response ok(Collection<?> objects) {
        return Response.ok(toJson(objects)).build();
    }

    default Response ok(Object o) {
        return Response.ok(toJson(o)).build();
    }

    default Response noContent() {
        return Response.noContent().build();
    }
}