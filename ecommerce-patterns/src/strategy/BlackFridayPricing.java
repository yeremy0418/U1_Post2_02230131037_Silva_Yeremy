package strategy;

/**
 * Estrategia Black Friday: 30% de descuento.
 */
public class BlackFridayPricing implements PricingStrategy {

    private static final double DISCOUNT_RATE = 0.30;

    @Override
    public double calculateFinalPrice(double originalPrice) {
        return originalPrice * (1 - DISCOUNT_RATE);
    }

    @Override
    public String getDescription() {
        return "Black Friday (30% de descuento)";
    }
}
