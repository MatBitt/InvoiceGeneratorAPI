package invoice.service;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;

import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;

import invoice.model.Invoice;



public class InvoiceService {
	

	public static void generateHTML(Invoice invoice) throws IOException{
 
		    BufferedWriter bw = new BufferedWriter(new FileWriter("/Users/home/Projetos/invoice/src/main/resources/templates/teste/index.html"));
		    bw.write("<html><head><title>New Page</title></head><body><h3>Helasdfasdfasdrld!</h3></body></html>");
		    bw.close();
	    
	}
	
	public static void generatePDF(Invoice invoice) throws FileNotFoundException, IOException{
		
		InvoiceService.generateHTML(invoice);
		
        try (OutputStream os = new FileOutputStream("/Users/home/teste.pdf")) {
			
			
            PdfRendererBuilder builder = new PdfRendererBuilder();
            builder.useFastMode();
            builder.withUri("file:///Users/home/Projetos/invoice/src/main/resources/templates/teste/template.html");
            builder.toStream(os);
            builder.run();
            
            
            
        }
        
        
        
	}
	
	
}
