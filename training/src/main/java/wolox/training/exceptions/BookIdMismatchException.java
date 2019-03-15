package wolox.training.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Body book id is different to query params book id")
public class BookIdMismatchException extends RuntimeException  {

    public BookIdMismatchException() {
        super();
    }

}
