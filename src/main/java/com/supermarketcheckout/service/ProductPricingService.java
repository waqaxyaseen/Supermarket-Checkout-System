package com.supermarketcheckout.service;


import com.supermarketcheckout.exception.InvalidCSVFormatException;
import com.supermarketcheckout.exception.InvalidPricingRulesException;
import com.supermarketcheckout.exception.PricingRulesNotFoundException;
import com.supermarketcheckout.model.PricingDiscount;
import com.supermarketcheckout.model.Product;
import com.supermarketcheckout.model.ProductPricingRules;
import com.supermarketcheckout.utils.StringUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import static com.supermarketcheckout.utils.StringUtils.isNotBlank;
import static java.lang.Integer.parseInt;


public class ProductPricingService {

    private static final String CSV_SPLIT_BY = ",";

    private File pricingRulesFile;

    public ProductPricingService(File pricingRulesFile) throws PricingRulesNotFoundException {
        if (!pricingRulesFile.exists()) {
            throw new PricingRulesNotFoundException();
        }
        this.pricingRulesFile = pricingRulesFile;
    }


    public ProductPricingRules getPricingRules() {

        ProductPricingRules pricingRules = new ProductPricingRules();

        String line = StringUtils.EMPTY;

        try (BufferedReader br = new BufferedReader(new FileReader(pricingRulesFile))) {


            validateCsvHeader(br.readLine());

            while ((line = br.readLine()) != null) {
                String[] itemTokens = line.split(CSV_SPLIT_BY);

                PricingDiscount discount = getDiscount(itemTokens);
                Product product = new Product(itemTokens[0], parseInt(itemTokens[1]), discount);

                pricingRules.put(itemTokens[0], product);
            }

        } catch (Exception e) {
            throw new InvalidPricingRulesException(
                    String.format("Error loading pricing rules: %s", e.getMessage()));
        }

        return pricingRules;
    }

    private PricingDiscount getDiscount(String[] itemTokens) {
        PricingDiscount discount = null;

        if (itemTokens.length == 4 && isNotBlank(itemTokens[2]) && isNotBlank(itemTokens[3])) {
            discount = new PricingDiscount(parseInt(itemTokens[2]), parseInt(itemTokens[3]));
        }

        return discount;
    }

    private void validateCsvHeader(String header) {
        String[] headerTokens = header.split(CSV_SPLIT_BY);
        if (headerTokens.length != 4
                || !"product_code".equals(headerTokens[0])
                || !"unit_price".equals(headerTokens[1])
                || !"special_offer_count".equals(headerTokens[2])
                || !"special_offer_discount".equals(headerTokens[3])) {
            throw new InvalidCSVFormatException("CSV does not match product_code,unit_price,special_offer_count,special_offer_price");
        }
    }

}

