package healthStore.exceptions;

public class UserNameInUseException  extends Exception{
    public UserNameInUseException(String msg){
        super(msg);
    }
}
