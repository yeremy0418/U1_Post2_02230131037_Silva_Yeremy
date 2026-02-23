package model;

/**
 * Clase base abstracta para todos los productos del sistema.
 * Aplica el principio Open/Closed: abierta para extensión, cerrada para modificación.
 */
public abstract class Product {

    protected String name;
    protected double basePrice;
    protected String category;

    public Product(String name, double basePrice, String category) {
        this.name = name;
        this.basePrice = basePrice;
        this.category = category;
    }

    public abstract double calculateShipping();
    public abstract String getDescription();

    public String getName() { return name; }
    public double getBasePrice() { return basePrice; }
    public String getCategory() { return category; }

    @Override
    public String toString() {
        return getDescription() + " | Precio base: $" + String.format("%.2f", basePrice)
                + " | Envío: $" + String.format("%.2f", calculateShipping());
    }
}
