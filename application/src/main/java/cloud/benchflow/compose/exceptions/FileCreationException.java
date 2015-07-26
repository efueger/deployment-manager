package cloud.benchflow.compose.exceptions;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import cloud.benchflow.compose.responses.JSONExceptionMessageContainer;

@SuppressWarnings("serial")
public class FileCreationException extends WebApplicationException {
    /**
     * Create a HTTP 500 (Internal Server Error) exception.
     */
    public FileCreationException() {
        super(Status.INTERNAL_SERVER_ERROR);
    }

    /**
     * Create a HTTP 500 (Internal Server Error) exception.
     * @param message the String that is the exception message of the 500 response.
     */
    public FileCreationException(String message) {
        super(Response.status(Status.INTERNAL_SERVER_ERROR).
                entity(new JSONExceptionMessageContainer(message)).type(MediaType.APPLICATION_JSON).build());
    }    
}
