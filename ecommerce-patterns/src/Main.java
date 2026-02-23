import model.Order;
import observer.EmailNotifier;
import observer.LogNotifier;
import observer.SMSNotifier;
import service.OrderService;
import strategy.BlackFridayPricing;
import strategy.BulkPricing;
import strategy.MemberPricing;
import strategy.RegularPricing;

/**
 * Punto de entrada — demuestra los tres patrones integrados:
 *   1. Factory Method  → creación de productos
 *   2. Strategy        → cálculo de descuentos
 *   3. Observer        → notificaciones de cambio de estado
 */
public class Main {

    public static void main(String[] args) {

        System.out.println("╔══════════════════════════════════════════════════╗");
        System.out.println("║   SISTEMA DE E-COMMERCE — PATRONES DE DISEÑO    ║");
        System.out.println("╚══════════════════════════════════════════════════╝\n");

        OrderService orderService = new OrderService();

        // PASO 1 — Suscribir observadores (Patrón Observer)

        System.out.println("══════ [1] SUSCRIPCIÓN DE OBSERVADORES ══════");
        orderService.subscribe(new EmailNotifier("cliente@email.com"));
        orderService.subscribe(new SMSNotifier("+57 300 123 4567"));
        orderService.subscribe(new LogNotifier());

        // PASO 2 — Crear pedido y agregar productos (Patrón Factory)
        System.out.println("\n══════ [2] CREACIÓN DE PRODUCTOS (Factory) ══════");
        Order order = new Order("ORD-2025-001");

        // Precio regular (sin descuento)
        orderService.setPricingStrategy(new RegularPricing());
        orderService.addProductToOrder(order, "ELECTRONICS", "Laptop HP 15\"", 1200.00);
        orderService.addProductToOrder(order, "CLOTHING", "Camiseta Polo", 35.00);

        // Precio para miembros (10% dto)
        System.out.println();
        orderService.setPricingStrategy(new MemberPricing());
        orderService.addProductToOrder(order, "FOOD", "Caja de Fresas Orgánicas", 18.50);

        // Black Friday (30% dto)
        System.out.println();
        orderService.setPricingStrategy(new BlackFridayPricing());
        orderService.addProductToOrder(order, "ELECTRONICS", "Audífonos Sony WH-1000", 350.00);

        // Compra por volumen (15% dto — 6 unidades)
        System.out.println();
        orderService.setPricingStrategy(new BulkPricing(6));
        orderService.addProductToOrder(order, "CLOTHING", "Medias Deportivas x6", 12.00);

        // PASO 3 — Comparativa de estrategias de precio
        System.out.println("\n══════ [3] COMPARATIVA DE ESTRATEGIAS (Strategy) ══════");
        double testPrice = 100.0;
        System.out.printf("  Precio base de prueba: $%.2f%n", testPrice);
        System.out.printf("  %-40s → $%.2f%n", "RegularPricing",
                new RegularPricing().calculateFinalPrice(testPrice));
        System.out.printf("  %-40s → $%.2f%n", "MemberPricing (10%)",
                new MemberPricing().calculateFinalPrice(testPrice));
        System.out.printf("  %-40s → $%.2f%n", "BlackFridayPricing (30%)",
                new BlackFridayPricing().calculateFinalPrice(testPrice));
        System.out.printf("  %-40s → $%.2f%n", "BulkPricing (3 uds, 5%)",
                new BulkPricing(3).calculateFinalPrice(testPrice));
        System.out.printf("  %-40s → $%.2f%n", "BulkPricing (6 uds, 15%)",
                new BulkPricing(6).calculateFinalPrice(testPrice));

        // PASO 4 — Ciclo de vida del pedido (Observer en acción)
        System.out.println("\n══════ [4] CAMBIOS DE ESTADO DEL PEDIDO (Observer) ══════");
        System.out.println("Estado inicial: " + order.getStatus());

        orderService.advanceOrderStatus(order); // CREATED → PROCESSING
        System.out.println();
        orderService.advanceOrderStatus(order); // PROCESSING → SHIPPED
        System.out.println();
        orderService.advanceOrderStatus(order); // SHIPPED → DELIVERED
        System.out.println();
        orderService.advanceOrderStatus(order); // ya en DELIVERED

        // PASO 5 — Resumen final del pedido
        System.out.println("\n══════ [5] RESUMEN FINAL ══════");
        System.out.println(order.getSummary());

        // PASO 6 — Validación: tipo de producto inválido
        System.out.println("══════ [6] MANEJO DE EXCEPCIÓN (tipo inválido) ══════");
        try {
            orderService.addProductToOrder(order, "FURNITURE", "Silla de Oficina", 200.0);
        } catch (IllegalArgumentException e) {
            System.out.println("[ERROR esperado] " + e.getMessage());
        }

        System.out.println("\n✔ Demo completada exitosamente.");
    }
}
