package invoice.exception;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;

public class InvoiceExceptionHandler {

    public String status;
    public String time_stamp;
    public Map<String, String> errors;

    public InvoiceExceptionHandler(Exception ex, HttpStatus status) {
        this.status = status.toString();
        this.time_stamp = Instant.now().toString();
        this.errors = getErrors(ex, this.status);
    }

    private Map<String, String> getErrors(Exception ex, String stts) {
        Map<String, String> errors = new HashMap<>();
        if (stts.equals("400 BAD_REQUEST")) {
            ((BindException) ex).getBindingResult().getAllErrors().forEach((error) -> {
                String fieldName = ((FieldError) error).getField();
                String errorMessage = error.getDefaultMessage();
                errors.put(fieldName, errorMessage);
            });
        } else if (stts.equals("500 INTERNAL_SERVER_ERROR")) {
            errors.put("FILE ERROR", "FILE NOT FOUND IN THE SERVER, CHECK THE PATH FROM YOUR .PDF FILE.");
        } else if (stts.equals("406 NOT_ACCEPTABLE")) {
            errors.put("GENERAL FILE ERROR", "ERROR PARSING THE JSON FILE. CHECK THE REQUEST FORMAT");
        }

        return errors;
    }

}
