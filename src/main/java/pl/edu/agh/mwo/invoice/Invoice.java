package pl.edu.agh.mwo.invoice;

import pl.edu.agh.mwo.invoice.product.Product;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class Invoice {
    private Map<Product,Integer> products = new HashMap<>();

    public void addProduct(Product product) {
        if (product != null) {
            products.put(product,1);
        }else {
            throw new IllegalArgumentException("Product is null");
        }
    }

    public void addProduct(Product product, Integer quantity) {
        if (quantity > 0) {
                products.put(product,quantity);
        } else if(product != null) {
            throw new IllegalArgumentException("Product is null");
        }else {
            throw new IllegalArgumentException("Quantity must be greater than 0");
        }

    }

    public BigDecimal getSubtotal() {
        BigDecimal subtotal = BigDecimal.ZERO;
        if (this.products != null) {
            for (Product product : products.keySet()) {
                Integer quantity = products.get(product);
                subtotal = subtotal.add(product.getPrice().multiply(BigDecimal.valueOf(quantity)));
            }
            return subtotal;
        } else {
            return BigDecimal.ZERO;
        }

    }

    public BigDecimal getTax() {
        BigDecimal tax = BigDecimal.ZERO;
        for (Product product : products.keySet()) {
            Integer quantity = products.get(product);
            tax = tax.add(product.getTaxPercent().multiply(product.getPrice()).multiply(BigDecimal.valueOf(quantity)));
        }
        return tax;
    }

    public BigDecimal getTotal() {
        if (products != null) {
            return this.getSubtotal().add(this.getTax());
        }
        if (this.getTax() == null) {
            return this.getSubtotal();
        }
        return BigDecimal.ZERO;
    }

}
