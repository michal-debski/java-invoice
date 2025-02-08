package pl.edu.agh.mwo.invoice;

import pl.edu.agh.mwo.invoice.product.Product;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Invoice {
    private List<Product> products = new ArrayList<>();

    public void addProduct(Product product) {
        if (product != null) {
            products.add(product);
        }else {
            throw new IllegalArgumentException("Product is null");
        }
    }

    public void addProduct(Product product, Integer quantity) {
        if (quantity > 0) {
            for (int i = 1; i <= quantity; i++) {
                products.add(product);
            }
        } else if(product != null) {
            throw new IllegalArgumentException("Product is null");
        }else {
            throw new IllegalArgumentException("Quantity must be greater than 0");
        }

    }

    public BigDecimal getSubtotal() {
        BigDecimal subtotal = BigDecimal.ZERO;
        if (this.products != null) {
            for (Product product : products) {
                subtotal = subtotal.add(product.getPrice());
            }
            return subtotal;
        } else {
            return BigDecimal.ZERO;
        }

    }

    public BigDecimal getTax() {
        BigDecimal tax = BigDecimal.ZERO;
        for (Product product : products) {
            tax = tax.add(product.getTaxPercent().multiply(product.getPrice()));
        }
        return tax;
    }

    public BigDecimal getTotal() {
        if (products != null) {
            BigDecimal total = BigDecimal.ZERO.add(this.getSubtotal()).add(this.getTax());
            return total;
        }
        if (this.getTax() == null) {
            return this.getSubtotal();
        }
        return BigDecimal.ZERO;
    }

}
