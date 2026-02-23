package strategy;

/**
 * Interfaz Strategy para el cálculo de precios y descuentos.
 *
 * JUSTIFICACIÓN: Permite cambiar la política de precios en tiempo de ejecución
 * sin modificar el código de OrderService. Cumple con OCP y SRP de SOLID.
 */
public interface PricingStrategy {
    double calculateFinalPrice(double originalPrice);
    String getDescription();
}
