package com.supermarketcheckout.model;

public class PricingDiscount {


    private Integer unitsForDiscount;


    private Integer discountValue;

    public PricingDiscount(Integer unitsForDiscount, Integer discountValue) {
        this.unitsForDiscount = unitsForDiscount;
        this.discountValue = discountValue;
    }

    public Integer getUnitsForDiscount() {
        return unitsForDiscount;
    }

    public Integer getDiscountValue() {
        return discountValue;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((unitsForDiscount == null) ? 0 : unitsForDiscount.hashCode());
        result = prime * result + ((discountValue == null) ? 0 : discountValue.hashCode());
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
        PricingDiscount other = (PricingDiscount) obj;
        if (unitsForDiscount == null) {
            if (other.unitsForDiscount != null)
                return false;
        } else if (!unitsForDiscount.equals(other.unitsForDiscount))
            return false;
        if (discountValue == null) {
            if (other.discountValue != null)
                return false;
        } else if (!discountValue.equals(other.discountValue))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "PricingDiscount [unitsForDiscount=" + unitsForDiscount + ", discountValue=" + discountValue + "]";
    }
}
