package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Representa un pedido en el sistema.
 * Contiene la lista de productos y el estado actual del pedido.
 */
public class Order {

    public enum Status {
        CREATED, PROCESSING, SHIPPED, DELIVERED
    }

    private final String orderId;
    private final List<Product> products;
    private Status status;
    private double totalPrice;

    public Order(String orderId) {
        this.orderId = orderId;
        this.products = new ArrayList<>();
        this.status = Status.CREATED;
        this.totalPrice = 0.0;
    }

    public void addProduct(Product product) {
        products.add(product);
        totalPrice += product.getBasePrice() + product.calculateShipping();
    }

    public String getOrderId() { return orderId; }
    public List<Product> getProducts() { return products; }
    public Status getStatus() { return status; }
    public double getTotalPrice() { return totalPrice; }

    public void setStatus(Status newStatus) {
        this.status = newStatus;
    }

    public String getSummary() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n========== RESUMEN DEL PEDIDO ==========\n");
        sb.append("Pedido ID : ").append(orderId).append("\n");
        sb.append("Estado    : ").append(status).append("\n");
        sb.append("Productos :\n");
        for (Product p : products) {
            sb.append("  - ").append(p).append("\n");
        }
        sb.append("Total     : $").append(String.format("%.2f", totalPrice)).append("\n");
        sb.append("========================================\n");
        return sb.toString();
    }
}
