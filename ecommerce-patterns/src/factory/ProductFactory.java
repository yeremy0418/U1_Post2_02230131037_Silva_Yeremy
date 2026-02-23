package factory;

import model.*;

/**
 * Factory Method para la creación de productos.
 *
 * JUSTIFICACIÓN: Desacopla el código cliente de las clases concretas de Product.
 * Si en el futuro se agrega un nuevo tipo (ej. "BOOKS"), solo se modifica esta clase,
 * sin tocar ningún otro componente del sistema. (Principio OCP de SOLID)
 */
public class ProductFactory {

    private ProductFactory() {
        // Clase utilitaria — no se instancia
    }

    /**
     * Crea un producto del tipo especificado con sus valores por defecto de categoría.
     *
     * @param type      Tipo de producto: "ELECTRONICS", "CLOTHING" o "FOOD"
     * @param name      Nombre del producto
     * @param price     Precio base
     * @return          Instancia concreta de Product
     * @throws IllegalArgumentException si el tipo no es reconocido
     */
    public static Product createProduct(String type, String name, double price) {
        return switch (type.toUpperCase()) {
            case "ELECTRONICS" -> new Electronics(name, price, 12);
            case "CLOTHING"    -> new Clothing(name, price, "M");
            case "FOOD"        -> new Food(name, price, 30);
            default -> throw new IllegalArgumentException(
                    "Tipo de producto desconocido: '" + type + "'. " +
                    "Tipos válidos: ELECTRONICS, CLOTHING, FOOD");
        };
    }

    /**
     * Versión extendida con parámetros adicionales por tipo.
     */
    public static Product createElectronics(String name, double price, int warrantyMonths) {
        return new Electronics(name, price, warrantyMonths);
    }

    public static Product createClothing(String name, double price, String size) {
        return new Clothing(name, price, size);
    }

    public static Product createFood(String name, double price, int expirationDays) {
        return new Food(name, price, expirationDays);
    }
}
