package invoice.model;

import java.math.BigDecimal;

public class Item {
	
	private String description;
	private BigDecimal price;
	private int quantity;
	private BigDecimal amount;
	
	public Item(String description, BigDecimal price, int quantity) {
		this.description = description;
		this.price = price;
		this.quantity = quantity;
		this.amount = price.multiply(BigDecimal.valueOf(quantity));
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	@Override
	public String toString() {
		return "Descricao: " + this.description + "\nPreco: " + this.price +  "\nQuantidade: " + this.quantity + "\nAmount: " + this.amount;
		
	}

}
