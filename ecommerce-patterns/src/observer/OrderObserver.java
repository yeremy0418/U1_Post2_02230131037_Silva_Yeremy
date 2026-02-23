package observer;

/**
 * Interfaz Observer del patrón Observer.
 * Todo observador de pedidos debe implementar este contrato.
 */
public interface OrderObserver {
    void update(String orderId, String oldStatus, String newStatus);
}
