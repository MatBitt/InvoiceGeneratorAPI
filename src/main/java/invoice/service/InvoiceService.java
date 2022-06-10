package invoice.service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;

import invoice.model.Invoice;
import invoice.utils.Path;

public class InvoiceService {

    public static void generatePDF(Invoice invoice) throws FileNotFoundException, IOException {

        String result = parseThymeleafTemplate(invoice);
        try (
             OutputStream os = new FileOutputStream(Path.pdf);) {

            File file = new File(Path.html);
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));

            bw.write(result);
            bw.close();

            PdfRendererBuilder builder = new PdfRendererBuilder();
            builder.useFastMode();
            builder.withUri("file://" + Path.html);
            builder.toStream(os);
            builder.run();

        }

    }

    public static String parseThymeleafTemplate(Invoice invoice) {
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode(TemplateMode.HTML);

        TemplateEngine templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);

        Context context = new Context();
        context.setVariable("invoice", invoice);

        return templateEngine.process("templates/template", context);
    }

}
