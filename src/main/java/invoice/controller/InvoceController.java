package invoice.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import invoice.model.Invoice;
import invoice.service.InvoiceService;


@RestController
@RequestMapping("/")
public class InvoceController {
	

	
	@PostMapping()
	public HttpStatus postCompanyAndDestinataryData(@RequestBody Invoice invoice) throws Exception {
		
		InvoiceService.generatePDF(invoice);
		
		return HttpStatus.OK;
	}
	
	@RequestMapping(value = "/teste", method = RequestMethod.POST, produces = "application/pdf")
	public static ResponseEntity<InputStreamResource> teste(@RequestBody Invoice invoice) throws IOException {
		
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
