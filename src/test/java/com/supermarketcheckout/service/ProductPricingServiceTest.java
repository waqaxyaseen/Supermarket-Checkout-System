package com.supermarketcheckout.service;


import com.supermarketcheckout.exception.InvalidPricingRulesException;
import com.supermarketcheckout.exception.PricingRulesNotFoundException;
import com.supermarketcheckout.model.PricingDiscount;
import com.supermarketcheckout.model.Product;
import com.supermarketcheckout.model.ProductPricingRules;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;

@ExtendWith(MockitoExtension.class)
public class ProductPricingServiceTest {

    private ProductPricingService productPricingService;



    @Test
    public void getProductPricingRulesFromFile() throws PricingRulesNotFoundException {
        productPricingService = new ProductPricingService(new File("src/test/resources/pricing_rules_ok.csv"));

        ProductPricingRules expectedPricingRules = new ProductPricingRules();
        expectedPricingRules.put("A", new Product("A", 50, new PricingDiscount(3, 20)));
        expectedPricingRules.put("B", new Product("B", 30, new PricingDiscount(2, 15)));
        expectedPricingRules.put("C", new Product("C", 20));
        expectedPricingRules.put("D", new Product("D", 15));
        expectedPricingRules.put("E", new Product("E", 1));

        ProductPricingRules pricingRules = productPricingService.getPricingRules();

        Assertions.assertEquals(pricingRules,expectedPricingRules);

    }

    @Test
    public void handleEmptyProductPricingRulesWithOnlyHeader() throws PricingRulesNotFoundException {
        productPricingService = new ProductPricingService(new File("src/test/resources/pricing_rules_only_header.csv"));

        ProductPricingRules expectedPricingRules = new ProductPricingRules();

        ProductPricingRules pricingRules = productPricingService.getPricingRules();

        Assertions.assertEquals(pricingRules,expectedPricingRules);
    }


    @Test
    public void failWhenProductPricingRulesNotValid() throws PricingRulesNotFoundException {
        productPricingService = new ProductPricingService(new File("src/test/resources/pricing_rules_not_valid.csv"));

        Assertions.assertThrows(InvalidPricingRulesException.class,() -> productPricingService.getPricingRules());
    }

    @Test
    public void failWhenProductPricingRulesHeaderNotValid_allWrong() throws PricingRulesNotFoundException {
        productPricingService = new ProductPricingService(new File("src/test/resources/pricing_rules_header_not_valid_1.csv"));

        Assertions.assertThrows(InvalidPricingRulesException.class,() -> productPricingService.getPricingRules());
    }

    @Test
    public void failWhenProductPricingRulesHeaderNotValid_oneWrong() throws PricingRulesNotFoundException {
        productPricingService = new ProductPricingService(new File("src/test/resources/pricing_rules_header_not_valid_4.csv"));

        Assertions.assertThrows(InvalidPricingRulesException.class,() -> productPricingService.getPricingRules());
    }




}
