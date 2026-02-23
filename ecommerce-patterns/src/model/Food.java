package model;

/**
 * Producto de tipo Alimento.
 * El costo de envío incluye refrigeración: $6.50 fijo.
 * Tiene fecha de vencimiento expresada en días.
 */
public class Food extends Product {

    private int expirationDays;

    public Food(String name, double basePrice, int expirationDays) {
        super(name, basePrice, "FOOD");
        this.expirationDays = expirationDays;
    }

    @Override
    public double calculateShipping() {
        return 6.50; // Costo fijo por refrigeración/manejo especial
    }

    @Override
    public String getDescription() {
        return name + " [Alimento] - Vence en: " + expirationDays + " días";
    }

    public int getExpirationDays() { return expirationDays; }
}
