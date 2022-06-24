package invoice.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.io.Files;

import invoice.exception.InvoiceExceptionHandler;
import invoice.model.Invoice;
import invoice.service.InvoiceService;
import invoice.utils.Path;

@RestController
@RequestMapping("/")
public class InvoceController {

    @PostMapping()
    public static ResponseEntity<InputStreamResource> POSTEndpoint(@Valid @RequestBody Invoice invoice, HttpServletResponse httpResponse) throws IOException {

        File pdfFile = new File(Path.pdf);

        InvoiceService.generatePDF(invoice);

        ResponseEntity<InputStreamResource> response = ResponseEntity
                .ok()
                .contentLength(pdfFile.length())
                .contentType(MediaType.parseMediaType("application/pdf"))
                .body(new InputStreamResource(new FileInputStream(pdfFile)));

        File downloadFile = new File(Path.user);

        Files.copy(pdfFile , downloadFile);
        
        InvoiceService.deleteCopiedFiles();

        return response;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<InvoiceExceptionHandler> handleValidationExceptions(MethodArgumentNotValidException ex) {

        InvoiceExceptionHandler response = new InvoiceExceptionHandler(ex, HttpStatus.BAD_REQUEST);

        return ResponseEntity
                .ok()
                .body(response);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(FileNotFoundException.class)
    public ResponseEntity<InvoiceExceptionHandler> handleExceptionsFileNotFound(FileNotFoundException ex) {

        InvoiceExceptionHandler response = new InvoiceExceptionHandler(ex, HttpStatus.INTERNAL_SERVER_ERROR);

        return ResponseEntity
                .ok()
                .body(response);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(IOException.class)
    public ResponseEntity<InvoiceExceptionHandler> handleExceptionsIOException(IOException ex) {

        InvoiceExceptionHandler response = new InvoiceExceptionHandler(ex, HttpStatus.NOT_ACCEPTABLE);

        return ResponseEntity
                .ok()
                .body(response);
    }

}
