package strategy;

/**
 * Estrategia para clientes miembros: 10% de descuento.
 */
public class MemberPricing implements PricingStrategy {

    private static final double DISCOUNT_RATE = 0.10;

    @Override
    public double calculateFinalPrice(double originalPrice) {
        return originalPrice * (1 - DISCOUNT_RATE);
    }

    @Override
    public String getDescription() {
        return "Precio para miembros (10% de descuento)";
    }
}
