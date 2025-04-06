package pl.edu.agh.mwo.invoice;

import pl.edu.agh.mwo.invoice.product.Product;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Invoice {
    private Map<Product, Integer> products = new HashMap<>();

    private final String invoiceNumber = "INV" + LocalDate.now().getYear() + "-"
            + LocalDate.now().getMonthValue() + "-"
            + LocalDate.now().getDayOfMonth() + LocalDateTime.now().getHour()
            + LocalDateTime.now().getMinute() + LocalDateTime.now().getSecond();

    public void addProduct(Product product) {
        addProduct(product, 1);
    }

    public void addProduct(Product product, Integer quantity) {
        if (product == null || quantity <= 0) {
            throw new IllegalArgumentException();
        }
        products.put(product, quantity);
    }

    public BigDecimal getNetTotal() {
        BigDecimal totalNet = BigDecimal.ZERO;
        for (Product product : products.keySet()) {
            BigDecimal quantity = new BigDecimal(products.get(product));
            totalNet = totalNet.add(product.getPrice().multiply(quantity));
        }
        return totalNet;
    }

    public BigDecimal getTaxTotal() {
        return getGrossTotal().subtract(getNetTotal());
    }

    public BigDecimal getGrossTotal() {
        BigDecimal totalGross = BigDecimal.ZERO;
        for (Product product : products.keySet()) {
            BigDecimal quantity = new BigDecimal(products.get(product));
            totalGross = totalGross.add(product.getPriceWithTax().multiply(quantity));
        }
        return totalGross;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public String printInvoice() {
        StringBuilder invoice = new StringBuilder();
        List<String> list = products.keySet().stream()
                .map(t -> t.getName() + "\t" + products.get(t) + "\t" + getNetTotal() + "\n")
                .toList();
        for (String s : list) {
            invoice.append(s);
        }
        return "Invoice number: " + invoiceNumber + "\n" + invoice + "\n"
                + "Liczba pozycji: " + products.size();
    }
}
