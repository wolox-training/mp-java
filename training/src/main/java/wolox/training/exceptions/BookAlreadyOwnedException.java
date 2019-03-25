package wolox.training.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "This user already owned this book")
public class BookAlreadyOwnedException extends RuntimeException  {
    public BookAlreadyOwnedException() {
        super();
    }
}

