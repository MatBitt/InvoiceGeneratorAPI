package invoice.service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

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
        generateCss();

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

    public static void generateCss() throws FileNotFoundException, IOException {

        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setSuffix(".css");
        templateResolver.setTemplateMode(TemplateMode.CSS);

        TemplateEngine templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);

        String result = templateEngine.process("templates/template", new Context());

        try (
             OutputStream os = new FileOutputStream(Path.css);) {

            File file = new File(Path.css);
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));

            bw.write(result);
            bw.close();

        }
    }

    public static void deleteCopiedFiles() throws IOException {

        Files.deleteIfExists(Paths.get(Path.css));
        Files.deleteIfExists(Paths.get(Path.html));
        Files.deleteIfExists(Paths.get(Path.pdf));

    }
}
