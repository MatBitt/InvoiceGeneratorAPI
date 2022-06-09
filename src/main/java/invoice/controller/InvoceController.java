package invoice.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import invoice.model.Invoice;
import invoice.service.InvoiceService;


@RestController
@RequestMapping("/")
public class InvoceController {
   
	
	
	@RequestMapping(value = "/teste", method = RequestMethod.POST, produces = "application/pdf")
	public static ResponseEntity<InputStreamResource> teste(Model model, @RequestBody Invoice invoice) throws IOException {
		
		InvoiceService.generatePDF(invoice);
		File pdfFile = new File("/Users/home/teste.pdf");
		//ClassPathResource pdfFile = new ClassPathResource("generatedPDFs/teste.pdf");

	    return ResponseEntity
	            .ok()
	            .contentLength(pdfFile.length())
	            .contentType(MediaType.parseMediaType("application/pdf"))
	            .body(new InputStreamResource(new FileInputStream(pdfFile)));
	}
	

	
}
