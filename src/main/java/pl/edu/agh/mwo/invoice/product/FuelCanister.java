package pl.edu.agh.mwo.invoice.product;

import java.math.BigDecimal;

public class FuelCanister extends Product {
    public FuelCanister(String name, BigDecimal price) {
        super(name, price, new BigDecimal(0));
    }

    @Override
    public BigDecimal getPriceWithTax() {
        return super.getPriceWithTax().add(new BigDecimal("5.65"));
    }
}
