package com.supermarketcheckout.service;


import com.supermarketcheckout.exception.ProductNotFoundException;
import com.supermarketcheckout.exception.InvalidPricingRulesException;
import com.supermarketcheckout.model.Product;
import com.supermarketcheckout.model.ProductPricingRules;


public class ProductService {

    private ProductPricingRules pricingRules;

    public ProductService(ProductPricingRules pricingRules) {
        if (pricingRules == null) {
            throw new InvalidPricingRulesException("Pricing rules are null");
        }
        this.pricingRules = pricingRules;
    }

    public Product find(String productCode) throws ProductNotFoundException {
        if (!pricingRules.containsKey(productCode)) {
            throw new ProductNotFoundException(String.format("Product %s not found", productCode));
        }
        return pricingRules.get(productCode);
    }

}

