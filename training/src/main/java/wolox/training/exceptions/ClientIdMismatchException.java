package wolox.training.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Body client id is different to query params client id")
public class ClientIdMismatchException extends RuntimeException  {

    public ClientIdMismatchException() {
        super();
    }

}
