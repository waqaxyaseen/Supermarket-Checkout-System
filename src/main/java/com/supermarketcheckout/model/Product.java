
package com.supermarketcheckout.model;

import java.util.Optional;

public class Product {

    private String productCode;
    private Integer price;
    private Optional<PricingDiscount> pricingDiscount;

    public Product(String productCode, Integer price) {
        this.productCode = productCode;
        this.price = price;
        this.pricingDiscount = Optional.empty();
    }

    public Product(String productCode, Integer price, PricingDiscount pricingDiscount) {
        this.productCode = productCode;
        this.price = price;
        if (pricingDiscount != null) {
            this.pricingDiscount = Optional.of(pricingDiscount);
        } else {
            this.pricingDiscount = Optional.empty();
        }
    }

    public String getProductCode() {
        return productCode;
    }

    public Integer getPrice() {
        return price;
    }

    public Optional<PricingDiscount> getPricingDiscount() {
        return pricingDiscount;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((pricingDiscount == null) ? 0 : pricingDiscount.hashCode());
        result = prime * result + ((productCode == null) ? 0 : productCode.hashCode());
        result = prime * result + ((price == null) ? 0 : price.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Product other = (Product) obj;
        if (pricingDiscount == null) {
            if (other.pricingDiscount != null)
                return false;
        } else if (!pricingDiscount.equals(other.pricingDiscount))
            return false;
        if (productCode == null) {
            if (other.productCode != null)
                return false;
        } else if (!productCode.equals(other.productCode))
            return false;
        if (price == null) {
            if (other.price != null)
                return false;
        } else if (!price.equals(other.price))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Product [id=" + productCode + ", price=" + price + ", pricingDiscount=" + pricingDiscount + "]";
    }

}
