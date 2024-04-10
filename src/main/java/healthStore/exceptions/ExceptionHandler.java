package healthStore.exceptions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import healthStore.DTO.HealthProductDTO;
import io.javalin.http.Handler;

import java.time.LocalDate;


public class ExceptionHandler<E extends Throwable & IExceptions> {
    private static ObjectMapper objectMapper = new ObjectMapper();

    public static <E extends Throwable & IExceptions> ObjectNode exceptionHandler(E e) {
            int status = e.getStatusCode();
            String message = e.getMessage();
            String timeStamp = String.valueOf(LocalDate.now());
            ObjectNode errorObject = objectMapper.createObjectNode();

            errorObject.put("status", status);
            errorObject.put("message", message);
            errorObject.put("timeStamp", timeStamp);
            return errorObject;
    }
}
