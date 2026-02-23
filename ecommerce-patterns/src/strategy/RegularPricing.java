package strategy;

/**
 * Estrategia de precio regular: sin descuento.
 */
public class RegularPricing implements PricingStrategy {

    @Override
    public double calculateFinalPrice(double originalPrice) {
        return originalPrice;
    }

    @Override
    public String getDescription() {
        return "Precio regular (sin descuento)";
    }
}
