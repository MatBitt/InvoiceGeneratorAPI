package invoice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import invoice.model.Invoice;

@SpringBootApplication
public class InvoiceApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(InvoiceApplication.class, args);
		Invoice invoice = new Invoice(null, null, null, null, null, null, null, null, null, null, null);
		invoice.setDate(null);
	}

	
	
}
