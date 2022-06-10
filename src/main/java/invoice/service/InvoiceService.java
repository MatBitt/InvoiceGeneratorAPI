package invoice.service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;

import invoice.model.Invoice;

public class InvoiceService {

    private static String pdfPath = "/Users/home/teste.pdf";

    public static void generatePDF(Invoice invoice) throws FileNotFoundException, IOException {
        

        String result = parseThymeleafTemplate(invoice);
        try (
             OutputStream os = new FileOutputStream(pdfPath);) {

            File file = new File("/Users/home/teste.html");
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));

            bw.write(result);
            bw.close();

            PdfRendererBuilder builder = new PdfRendererBuilder();
            builder.useFastMode();
            builder.withUri("file:///Users/home/teste.html");
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
