package invoice.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

public class Invoice {
	
	private ArrayList<Item> itens = new ArrayList<Item>();
	private String			companyName;
	private String			adress;
	private String			postal;
	private String			destinataryName;
	private String			destinataryAdress;
	private String			destinataryPostal;
	private String			termsAndConditions;
	private int				id;
	private Date			date = new Date();
	private BigDecimal		discount;
	private BigDecimal		taxRate;
	private BigDecimal		tax;

	
	public Invoice(ArrayList<Item> itens, String companyName, String adress, String postal, String destinataryName,
			String destinataryAdress, String destinataryPostal, String termsAndConditions, Date date,
			BigDecimal discount, BigDecimal taxRate, BigDecimal tax) {
		
		this.itens = itens;
		this.companyName = companyName;
		this.adress = adress;
		this.postal = postal;
		this.destinataryName = destinataryName;
		this.destinataryAdress = destinataryAdress;
		this.destinataryPostal = destinataryPostal;
		this.termsAndConditions = termsAndConditions;
		this.date = date;
		this.discount = discount;
		this.taxRate = taxRate;
		this.tax = tax;
	}

	public ArrayList<Item> getItens() {
		return itens;
	}
	
	public void addItem(Item item) {
		this.itens.add(item);
	}
	
	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getAdress() {
		return adress;
	}

	public void setAdress(String adress) {
		this.adress = adress;
	}

	public String getPostal() {
		return postal;
	}

	public void setPostal(String postal) {
		this.postal = postal;
	}

	public String getDestinataryName() {
		return destinataryName;
	}

	public void setDestinataryName(String destinataryName) {
		this.destinataryName = destinataryName;
	}

	public String getDestinataryAdress() {
		return destinataryAdress;
	}

	public void setDestinataryAdress(String destinataryAdress) {
		this.destinataryAdress = destinataryAdress;
	}

	public String getDestinataryPostal() {
		return destinataryPostal;
	}

	public void setDestinataryPostal(String destinataryPostal) {
		this.destinataryPostal = destinataryPostal;
	}

	public String getTermsAndConditions() {
		return termsAndConditions;
	}

	public void setTermsAndConditions(String termsAndConditions) {
		this.termsAndConditions = termsAndConditions;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public BigDecimal getDiscount() {
		return discount;
	}

	public void setDiscount(BigDecimal discount) {
		this.discount = discount;
	}

	public BigDecimal getTaxRate() {
		return taxRate;
	}

	public void setTaxRate(BigDecimal vatRate) {
		this.taxRate = vatRate;
	}

	public BigDecimal getTax() {
		this.tax = this.taxRate.multiply(getSubtotal()).divide(new BigDecimal("100"));
		return tax;
	}

	public BigDecimal getSubtotal() {
		
		BigDecimal subtotal = BigDecimal.ZERO;
		
		for (Item item : this.itens) {
			subtotal = subtotal.add(item.getAmount());
		}
		return subtotal;
	}

	public BigDecimal getTotal() {
		BigDecimal total = BigDecimal.ZERO;
		
		this.tax = getTax();
		
		total = getSubtotal().add(tax).subtract(discount);
		
		if(total.compareTo(BigDecimal.ZERO)>=0) {
			return total;
		}
		return BigDecimal.ZERO;
	}
	
}
