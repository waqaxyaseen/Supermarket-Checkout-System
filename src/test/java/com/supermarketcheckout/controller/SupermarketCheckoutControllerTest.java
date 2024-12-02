package com.supermarketcheckout.controller;

import com.supermarketcheckout.exception.ProductNotFoundException;
import com.supermarketcheckout.model.CheckoutResult;
import com.supermarketcheckout.model.Product;
import com.supermarketcheckout.model.ShoppingCart;
import com.supermarketcheckout.service.BarcodeScannerService;
import com.supermarketcheckout.service.CheckoutProcessingService;
import com.supermarketcheckout.service.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SupermarketCheckoutControllerTest {

    @Mock
    private ProductService productService;

    @Mock
    private BarcodeScannerService barcodeScannerService;

    @Mock
    private CheckoutProcessingService checkoutProcessingService;



    @Test
    public void handleCheckoutTransaction() throws FileNotFoundException, ProductNotFoundException {

        File userInputFile = new File("src/test/resources/user_input_ok.txt");
        Scanner sc = new Scanner(userInputFile);

        SupermarketCheckoutController checkoutController = new SupermarketCheckoutController(sc, productService, barcodeScannerService, checkoutProcessingService);

        ShoppingCart cart = new ShoppingCart();
        CheckoutResult expectedResult = new CheckoutResult(50, 0, 50, cart);

        when(checkoutProcessingService.checkout(any())).thenReturn(expectedResult);

        Product product = new Product("A", 50);
        when(productService.find(any())).thenReturn(product);
        CheckoutResult result = checkoutController.handleCheckoutTransaction();

        assertThat(result).isEqualTo(expectedResult);

        verify(barcodeScannerService, times(1)).scan(any(), any());
        verify(productService, times(1)).find(any());
        verify(checkoutProcessingService, times(1)).checkout(any());

    }

    @Test
    public void handleCheckoutTransaction_multipleItems()
            throws FileNotFoundException, ProductNotFoundException {

        File userInputFile = new File("src/test/resources/user_input_ok_multiple.txt");
        Scanner sc = new Scanner(userInputFile);

        SupermarketCheckoutController checkoutController = new SupermarketCheckoutController(sc, productService, barcodeScannerService, checkoutProcessingService);

        ShoppingCart cart = new ShoppingCart();
        CheckoutResult expectedResult = new CheckoutResult(100, 0, 100, cart);

        when(checkoutProcessingService.checkout(any())).thenReturn(expectedResult);

        Product product = new Product("A", 50);
        when(productService.find(any())).thenReturn(product);


        CheckoutResult result = checkoutController.handleCheckoutTransaction();

        assertThat(result).isEqualTo(expectedResult);

        verify(barcodeScannerService, times(2)).scan(any(), any());
        verify(productService, times(2)).find(any());
        verify(checkoutProcessingService, times(1)).checkout(any());

    }

    @Test
    public void handleCheckoutTransaction_wrongProduct()
            throws FileNotFoundException, ProductNotFoundException {

        File userInputFile = new File("src/test/resources/user_input_wrong_item.txt");
        Scanner sc = new Scanner(userInputFile);

        SupermarketCheckoutController checkoutController = new SupermarketCheckoutController(sc, productService, barcodeScannerService, checkoutProcessingService);

        ShoppingCart cart = new ShoppingCart();
        CheckoutResult expectedResult = new CheckoutResult(50, 0, 50, cart);

        Product product = new Product("A", 50);
        when(productService.find(eq("L"))).thenThrow(ProductNotFoundException.class);
        when(productService.find(eq("A"))).thenReturn(product);
        when(checkoutProcessingService.checkout(any())).thenReturn(expectedResult);

        CheckoutResult result = checkoutController.handleCheckoutTransaction();

        assertThat(result).isEqualTo(expectedResult);

        verify(barcodeScannerService, times(1)).scan(any(), any());
        verify(productService, times(2)).find(any());
        verify(checkoutProcessingService, times(1)).checkout(any());

    }

}

