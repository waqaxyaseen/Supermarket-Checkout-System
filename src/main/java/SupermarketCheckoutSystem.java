import com.supermarketcheckout.controller.SupermarketCheckoutController;
import com.supermarketcheckout.exception.PricingRulesNotFoundException;
import com.supermarketcheckout.service.BarcodeScannerService;
import com.supermarketcheckout.service.ProductService;
import com.supermarketcheckout.service.ProductPricingService;
import com.supermarketcheckout.service.CheckoutProcessingService;
import com.supermarketcheckout.model.ProductPricingRules;
import java.io.File;
import java.util.Scanner;

/**
 * Main class for the supermarket checkout system.
 */
public class SupermarketCheckoutSystem {

    public static void main(String[] args) throws PricingRulesNotFoundException {
        ProductPricingService pricingService = initPricingService(args);
        ProductPricingRules pricingRules = pricingService.getPricingRules();
        ProductService productService = new ProductService(pricingRules);
        BarcodeScannerService barcodeScannerService = new BarcodeScannerService();
        CheckoutProcessingService checkoutProcessingService = new CheckoutProcessingService();

        Scanner sc = new Scanner(System.in);

        SupermarketCheckoutController checkoutController =
                new SupermarketCheckoutController(sc, productService, barcodeScannerService, checkoutProcessingService);

        checkoutController.handleCheckoutTransaction();

        sc.close();
    }

    private static ProductPricingService initPricingService(String[] args)
            throws PricingRulesNotFoundException {
        String pricingRulesFilePath = "src/main/resources/pricing_rules.csv";

        if (args.length > 0) {
            System.out.println(String.format("Pricing rules location: %s", args[0]));
            pricingRulesFilePath = args[0];
        }

        File pricingRulesFile = new File(pricingRulesFilePath);
        return new ProductPricingService(pricingRulesFile);
    }
}
