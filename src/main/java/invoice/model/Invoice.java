package invoice.model;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.validation.Valid;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

public class Invoice {

    @NotNull(message = "is mandatory")
    @Valid()
    private ArrayList<Item> itens = new ArrayList<Item>();
    @NotNull(message = "is mandatory")
    private String companyName;
    @NotNull(message = "is mandatory")
    private String adress;
    @NotNull(message = "is mandatory")
    private String postal;
    @NotNull(message = "is mandatory")
    private String destinataryName;
    @NotNull(message = "is mandatory")
    private String destinataryAdress;
    @NotNull(message = "is mandatory")
    private String destinataryPostal;
    private String termsAndConditions;
    private int id;
    private String date;
    @NotNull(message = "is mandatory")
    @DecimalMin(value = "0", message = "should be greater or equal to zero")
    private BigDecimal discount;
    @DecimalMin(value = "0", message = "should be greater or equal to zero")
    @NotNull(message = "is mandatory")
    private BigDecimal taxRate;
    private BigDecimal tax;

    public Invoice(ArrayList<Item> itens, String companyName, String adress, String postal, String destinataryName,
            String destinataryAdress, String destinataryPostal, String termsAndConditions,
            BigDecimal discount, BigDecimal taxRate) {

        this.itens = itens;
        this.companyName = companyName;
        this.adress = adress;
        this.postal = postal;
        this.destinataryName = destinataryName;
        this.destinataryAdress = destinataryAdress;
        this.destinataryPostal = destinataryPostal;
        this.termsAndConditions = termsAndConditions;
        this.discount = discount;
        this.taxRate = taxRate;
    }
    
    public void addItem(Item item) {
        this.itens.add(item);
    }

    public ArrayList<Item> getItens() {
        return itens;
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

    public String getDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date currentDate = new Date();
        this.date = formatter.format(currentDate);
        return this.date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public BigDecimal getDiscount() {
        return discount ;
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

        if (total.compareTo(BigDecimal.ZERO) >= 0) {
            return total;
        }
        return BigDecimal.ZERO;
    }

}
