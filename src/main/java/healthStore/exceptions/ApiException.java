package healthStore.exceptions;

import io.javalin.http.HttpStatus;
import lombok.Getter;

@Getter
public class ApiException extends Exception implements IExceptions {

    HttpStatus statusCode;
    public ApiException(HttpStatus statusCode,String msg){
        super(msg);
        this.statusCode = statusCode;
    }

    public HttpStatus getStatusCode() {
        return statusCode;
    }
}
