package model;

/**
 * Producto de tipo Electrónica.
 * El costo de envío es el 5% del precio base (frágil y pesado).
 */
public class Electronics extends Product {

    private int warrantyMonths;

    public Electronics(String name, double basePrice, int warrantyMonths) {
        super(name, basePrice, "ELECTRONICS");
        this.warrantyMonths = warrantyMonths;
    }

    @Override
    public double calculateShipping() {
        return basePrice * 0.05;
    }

    @Override
    public String getDescription() {
        return name + " [Electrónica] - Garantía: " + warrantyMonths + " meses";
    }

    public int getWarrantyMonths() { return warrantyMonths; }
}
