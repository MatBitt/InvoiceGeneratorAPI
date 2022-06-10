package invoice;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import invoice.model.Invoice;
import invoice.model.Item;


@SpringBootTest
class InvoiceApplicationTests {
	
	private Invoice invoice;
	private Item item1;
	private Item item2;
	private Item item3;
	
	@BeforeEach
	void init() {
		invoice = new Invoice(new ArrayList<Item>(), null, null, null, null, null, null, null, null, null);
		item1 = new Item("a", new BigDecimal(10), 1);
		item2 = new Item("b", new BigDecimal(20), 1);
		item3 = new Item("c", new BigDecimal(30), 1);
	}

	@Test
	void comparesSubtotalToValueExpectedOver1Item() {
		invoice.addItem(item1);
		assertEquals(new BigDecimal(10), invoice.getSubtotal());
	}
	
	@Test
	void comparesSubtotalToValueExpectedOver3Itens() {
		
		invoice.addItem(item1);
		invoice.addItem(item2);
		invoice.addItem(item3);
		
		assertEquals(new BigDecimal(60), invoice.getSubtotal());
	}
	
	@Test
	void comparesTotalToValueGreaterThan0() {
		
		invoice.addItem(item1);
		invoice.addItem(item2);
		invoice.addItem(item3);
		
		invoice.setDiscount(new BigDecimal(20));
		invoice.setTaxRate(new BigDecimal(10));
		
		assertEquals(new BigDecimal(46), invoice.getTotal());
	}
	
	@Test
	void comparesTotalToValueEqualsTo0() {
		
		invoice.addItem(item1);
		invoice.addItem(item2);
		invoice.addItem(item3);
		
		invoice.setDiscount(new BigDecimal(66));
		invoice.setTaxRate(new BigDecimal(10));
		
		assertEquals(new BigDecimal(0), invoice.getTotal());
	}
	
	@Test
	void comparesTotalToValueSmallerThan0() {
		
		invoice.addItem(item1);
		invoice.addItem(item2);
		invoice.addItem(item3);
		
		invoice.setDiscount(new BigDecimal(67));
		invoice.setTaxRate(new BigDecimal(10));
		
		assertEquals(new BigDecimal(0), invoice.getTotal());
	}

}
