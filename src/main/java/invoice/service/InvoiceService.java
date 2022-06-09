package invoice.service;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;

import invoice.model.Invoice;

public class InvoiceService {

    private static String templatesPath = "/Users/home/Projetos/InvoiceGeneratorAPI/src/main/resources/templates/";

    public static void generatePDF(Invoice invoice) throws FileNotFoundException, IOException {
        

        parseThymeleafTemplate(invoice);
        
        try (
             OutputStream os = new FileOutputStream("/Users/home/teste.pdf")) {

            PdfRendererBuilder builder = new PdfRendererBuilder();
            builder.useFastMode();
            builder.withUri("file://" + templatesPath +  "template.html");
            builder.toStream(os);
            builder.run();

        }

    }

    private static String parseThymeleafTemplate(Invoice invoice) {
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode(TemplateMode.HTML);

        TemplateEngine templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);

        Context context = new Context();
        context.setVariable("invoice", invoice.getDestinataryName());

        return templateEngine.process("templates/template", context);
    }

}
