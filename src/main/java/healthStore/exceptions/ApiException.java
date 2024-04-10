package healthStore.exceptions;

import lombok.Getter;

@Getter
public class ApiException extends Throwable implements IExceptions {
    int statusCode;
    public ApiException(int statusCode,String msg){
        super(msg);
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
