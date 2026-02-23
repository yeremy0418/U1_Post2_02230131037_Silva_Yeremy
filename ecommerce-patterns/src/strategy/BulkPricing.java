package strategy;

/**
 * Estrategia de descuento por volumen (Bulk).
 * A mayor cantidad de productos, mayor descuento.
 *
 * 1–2  productos: sin descuento adicional
 * 3–5  productos: 5% de descuento
 * 6+   productos: 15% de descuento
 */
public class BulkPricing implements PricingStrategy {

    private final int quantity;

    public BulkPricing(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public double calculateFinalPrice(double originalPrice) {
        double discountRate = resolveDiscountRate();
        return originalPrice * (1 - discountRate);
    }

    private double resolveDiscountRate() {
        if (quantity >= 6) return 0.15;
        if (quantity >= 3) return 0.05;
        return 0.0;
    }

    @Override
    public String getDescription() {
        return "Precio por volumen (" + quantity + " unidades — descuento: "
                + (int)(resolveDiscountRate() * 100) + "%)";
    }
}
