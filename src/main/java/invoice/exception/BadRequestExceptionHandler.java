package invoice.exception;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

public class BadRequestExceptionHandler {

    public String status;
    public String time_stamp;
    public Map<String, String> errors;

    public BadRequestExceptionHandler(MethodArgumentNotValidException ex) {
        this.status = HttpStatus.BAD_REQUEST.toString();
        this.time_stamp = Instant.now().toString();
        this.errors = getErrors(ex);
    }

    private Map<String, String> getErrors(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return errors;
    }

}
