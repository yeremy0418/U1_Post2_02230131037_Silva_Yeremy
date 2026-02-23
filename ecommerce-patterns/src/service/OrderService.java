package service;

import factory.ProductFactory;
import model.Order;
import model.Product;
import observer.OrderObserver;
import observer.OrderSubject;
import strategy.PricingStrategy;
import strategy.RegularPricing;

import java.util.ArrayList;
import java.util.List;

/**
 * Servicio central de pedidos.
 *
 * Integra los tres patrones:
 *  - Factory Method : delega la creación de productos a ProductFactory
 *  - Observer       : implementa OrderSubject para notificar cambios de estado
 *  - Strategy       : usa PricingStrategy para calcular precios finales
 */
public class OrderService implements OrderSubject {

    private final List<OrderObserver> observers = new ArrayList<>();
    private PricingStrategy pricingStrategy;

    public OrderService() {
        this.pricingStrategy = new RegularPricing(); // estrategia por defecto
    }

    // Patrón Observer

    @Override
    public void subscribe(OrderObserver observer) {
        observers.add(observer);
        System.out.println("[Sistema] Observador suscrito: " + observer.getClass().getSimpleName());
    }

    @Override
    public void unsubscribe(OrderObserver observer) {
        observers.remove(observer);
        System.out.println("[Sistema] Observador eliminado: " + observer.getClass().getSimpleName());
    }

    @Override
    public void notifyObservers(String orderId, String oldStatus, String newStatus) {
        for (OrderObserver observer : observers) {
            observer.update(orderId, oldStatus, newStatus);
        }
    }

    // Patrón Strategy

    public void setPricingStrategy(PricingStrategy strategy) {
        this.pricingStrategy = strategy;
        System.out.println("[Sistema] Estrategia de precio activa: " + strategy.getDescription());
    }

    public double applyPricing(double basePrice) {
        return pricingStrategy.calculateFinalPrice(basePrice);
    }

    // Patrón Factory + lógica de negocio

    /**
     * Crea un producto usando ProductFactory y lo agrega al pedido.
     */
    public void addProductToOrder(Order order, String type, String name, double price) {
        Product product = ProductFactory.createProduct(type, name, price);
        double finalPrice = applyPricing(price);
        // Creamos un nuevo product con precio ajustado para mostrar el efecto del strategy
        Product discountedProduct = ProductFactory.createProduct(type, name, finalPrice);
        order.addProduct(discountedProduct);
        System.out.println("[Factory] Producto creado → " + product.getDescription()
                + " | Precio original: $" + String.format("%.2f", price)
                + " → Precio final: $" + String.format("%.2f", finalPrice)
                + " (" + pricingStrategy.getDescription() + ")");
    }

    /**
     * Avanza el pedido al siguiente estado y notifica a todos los observadores.
     */
    public void advanceOrderStatus(Order order) {
        Order.Status currentStatus = order.getStatus();
        Order.Status nextStatus = resolveNextStatus(currentStatus);

        if (nextStatus == null) {
            System.out.println("[Sistema] El pedido " + order.getOrderId()
                    + " ya está en estado final: " + currentStatus);
            return;
        }

        order.setStatus(nextStatus);
        notifyObservers(order.getOrderId(), currentStatus.name(), nextStatus.name());
    }

    private Order.Status resolveNextStatus(Order.Status current) {
        return switch (current) {
            case CREATED    -> Order.Status.PROCESSING;
            case PROCESSING -> Order.Status.SHIPPED;
            case SHIPPED    -> Order.Status.DELIVERED;
            case DELIVERED  -> null;
        };
    }
}
