package com.supermarketcheckout.service;


import com.supermarketcheckout.exception.InvalidCartException;
import com.supermarketcheckout.model.CheckoutResult;
import com.supermarketcheckout.model.Product;
import com.supermarketcheckout.model.ShoppingCart;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class CheckoutProcessingServiceTest {

    @InjectMocks
    private CheckoutProcessingService checkoutService;


    @Test
    public void shouldCalculateTotalForCartWithProducts() {
        ShoppingCart cart = createCartWithProducts(productA(), productB());

        CheckoutResult result = checkoutService.checkout(cart);

        assertThat(result.getToPay()).isEqualTo(80);
    }

    @Test
    public void shouldCalculateTotalForEmptyCart() {
        ShoppingCart cart = new ShoppingCart();

        CheckoutResult result = checkoutService.checkout(cart);

        assertThat(result.getToPay()).isEqualTo(0);
    }

    @Test
    public void shouldIgnoreNullProductsInCart() {
        ShoppingCart cart = createCartWithProducts(productA(), null);

        CheckoutResult result = checkoutService.checkout(cart);

        assertThat(result.getToPay()).isEqualTo(50);
    }

    @Test
    public void shouldThrowExceptionWhenCartIsNull() {
        Assertions.assertThrows(InvalidCartException.class,() -> checkoutService.checkout(null));
    }

    private ShoppingCart createCartWithProducts(Product... products) {
        ShoppingCart cart = new ShoppingCart();
        for (Product product : products) {
            cart.add(product);
        }
        return cart;
    }

    private Product productA() {
        return new Product("A", 50);
    }

    private Product productB() {
        return new Product("B", 30);
    }
}
