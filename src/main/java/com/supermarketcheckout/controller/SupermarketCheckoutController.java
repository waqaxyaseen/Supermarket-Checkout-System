package com.supermarketcheckout.controller;

import com.supermarketcheckout.exception.ProductNotFoundException;
import com.supermarketcheckout.model.ShoppingCart;
import com.supermarketcheckout.model.Product;
import com.supermarketcheckout.model.CheckoutResult;
import com.supermarketcheckout.service.CheckoutProcessingService;
import com.supermarketcheckout.service.ProductService;
import com.supermarketcheckout.service.BarcodeScannerService;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import static java.lang.String.format;


public class SupermarketCheckoutController {

    private static final String END = "end";

    private Scanner sc;
    private ProductService productService;
    private BarcodeScannerService scannerService;
    private CheckoutProcessingService checkoutService;

    public SupermarketCheckoutController(Scanner sc, ProductService productService, BarcodeScannerService scannerService,
                                         CheckoutProcessingService checkoutService) {
        this.sc = sc;
        this.productService = productService;
        this.scannerService = scannerService;
        this.checkoutService = checkoutService;
    }

    /**
     * Handles the checkout transaction process for the shopping cart. This method orchestrates
     * the scanning of products, calculates running totals before and after discounts, and provides
     * the final checkout result. It processes each product code inputted, updates item counts,
     * tracks the individual product quantities, and handles discount calculations.
     *
     * @return The result of the checkout transaction, containing the total price, discounts applied,
     *         and the amount to be paid.
     */

    public CheckoutResult handleCheckoutTransaction() {
        ShoppingCart cart = new ShoppingCart();
        int itemCount = 0;
        int runningTotal = 0;
        int runningTotalAfterDiscount = 0;
        Map<String, Integer> productScanCount = new HashMap<>();

        String productCode = first();

        Product firstProduct = findProduct(productCode);
        if (firstProduct != null) {
            scannerService.scan(firstProduct, cart);
            runningTotal = checkoutService.calculateRunningTotal(cart);
            runningTotalAfterDiscount = checkoutService.calculateRunningTotalAfterDiscount(cart);
            itemCount++;
            updateProductScanCount(productScanCount, firstProduct);
            System.out.println("Running Total (before discounts): " + runningTotal);
            System.out.println("Running Total (after discounts): " + runningTotalAfterDiscount);
        }

        productCode = next();
        while (!END.equalsIgnoreCase(productCode.trim())) {
            Product product = findProduct(productCode);
            if (product != null) {
                scannerService.scan(product, cart);
                runningTotal = checkoutService.calculateRunningTotal(cart);
                runningTotalAfterDiscount = checkoutService.calculateRunningTotalAfterDiscount(cart);
                itemCount++;
                updateProductScanCount(productScanCount, product);
                System.out.println("Running Total (before discounts): " + runningTotal);
                System.out.println("Running Total (after discounts): " + runningTotalAfterDiscount);
            }
            productCode = next();
        }

        CheckoutResult result = checkoutService.checkout(cart);
        printBill(result, itemCount, productScanCount);

        return result;
    }

    private String first() {
        System.out.println("Welcome to Supermarket Checkout System");
        System.out.println("Scan first product to begin: ");
        return sc.nextLine();
    }

    private String next() {
        System.out.println("Scan another product or type 'END' to complete your checkout.");
        return sc.nextLine();
    }

    private void printBill(CheckoutResult result, int productCount, Map<String, Integer> productScanCount) {
        System.out.println("\nIndividual Product Counts:");
        productScanCount.forEach((productCode, count) -> {
            System.out.println(productCode + ": " + count);
        });
        System.out.println("Total products scanned: " + productCount);
        System.out.println("Total: " + result.getTotal());
        if (result.getDiscount() != 0) {
            System.out.println("Discount: " + result.getDiscount());
        }
        System.out.println("Amount to pay: " + result.getToPay());


    }

    private void updateProductScanCount(Map<String, Integer> productScanCount, Product product) {
        productScanCount.put(product.getProductCode(), productScanCount.getOrDefault(product.getProductCode(), 0) + 1);
    }

    private Product findProduct(String productCode) {
        Product product = null;
        try {
            product = productService.find(productCode);
        } catch (ProductNotFoundException e) {
            System.err.println(format("Product %s not found", productCode));
        }
        return product;
    }
}
