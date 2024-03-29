package easyurl;

/**
 * @author Valtteri Rajalainen
 * @version 1.0.0 Jan 2, 2022
 */
public class RequestFailedException extends RuntimeException {
    
    
    private static final long serialVersionUID = 1L;
    private String info;
    
    /**
     * @param info Info message. This needs to hide implementation details
     * as this should be directly shown to the end user.
     */
    public RequestFailedException(String info) {
        super(info);
        this.info = info;
    }
    
    /**
     * @return Information on the exception. 
     * This can be directly shown to the user
     * as an error message.
     */
    public String getInfo() {
        return this.info;
    }
    
}
