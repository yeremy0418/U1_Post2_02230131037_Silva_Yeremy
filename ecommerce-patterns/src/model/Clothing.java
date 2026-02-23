package model;

/**
 * Producto de tipo Ropa.
 * El costo de envío es fijo de $3.99 (liviano).
 */
public class Clothing extends Product {

    private String size;

    public Clothing(String name, double basePrice, String size) {
        super(name, basePrice, "CLOTHING");
        this.size = size;
    }

    @Override
    public double calculateShipping() {
        return 3.99;
    }

    @Override
    public String getDescription() {
        return name + " [Ropa] - Talla: " + size;
    }

    public String getSize() { return size; }
}
