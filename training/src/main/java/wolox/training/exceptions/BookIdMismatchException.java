package wolox.training.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


public class BookIdMismatchException extends RuntimeException  {

    public BookIdMismatchException() {
        super();
    }

}
