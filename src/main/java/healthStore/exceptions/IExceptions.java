package healthStore.exceptions;

import io.javalin.http.HttpStatus;

public interface IExceptions {
    public HttpStatus getStatusCode();
}
