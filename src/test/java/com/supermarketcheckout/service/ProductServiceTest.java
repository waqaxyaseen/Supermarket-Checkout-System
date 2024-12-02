package com.supermarketcheckout.service;

import com.supermarketcheckout.exception.InvalidPricingRulesException;
import com.supermarketcheckout.exception.ProductNotFoundException;
import com.supermarketcheckout.model.Product;
import com.supermarketcheckout.model.ProductPricingRules;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)

public class ProductServiceTest {
    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductPricingRules pricingRules;



    @Test
    public void findItemById() throws ProductNotFoundException {
        when(pricingRules.containsKey(eq("A"))).thenReturn(true);
        when(pricingRules.get(eq("A"))).thenReturn(productA());

        Product product = productService.find("A");

        assertThat(product).isEqualTo(productA());
    }

    @Test
    public void failWhenItemNotFound() {
        when(pricingRules.containsKey(eq("Z"))).thenReturn(false);

        Assertions.assertThrows(ProductNotFoundException.class,() -> productService.find("Z"));
    }

    @Test
    public void failWhenPricingRulesNotValid() {

        Assertions.assertThrows(InvalidPricingRulesException.class,() -> new ProductService(null));
    }

    private Product productA() {
        return new Product("A", 50);
    }
}
