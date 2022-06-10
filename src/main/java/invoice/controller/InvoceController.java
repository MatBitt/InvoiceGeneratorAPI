package invoice.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import invoice.model.Invoice;
import invoice.service.InvoiceService;
import invoice.utils.Path;

@RestController
@RequestMapping("/")
public class InvoceController {

    @PostMapping()
    public static ResponseEntity<?> POSTEndpoint(@Valid @RequestBody Invoice invoice) throws IOException {

        File pdfFile = new File(Path.pdf);

        InvoiceService.generatePDF(invoice);
        return ResponseEntity
                .ok()
                .contentLength(pdfFile.length())
                .contentType(MediaType.parseMediaType("application/pdf"))
                .body(new InputStreamResource(new FileInputStream(pdfFile)));
    }
    
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {        
        Map<String, String> errors = new HashMap<>();
        errors.put("Status", HttpStatus.BAD_REQUEST.toString());
        
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

}
