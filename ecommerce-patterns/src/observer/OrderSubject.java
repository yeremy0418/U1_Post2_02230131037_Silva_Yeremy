package observer;

/**
 * Interfaz Subject del patrón Observer.
 * Define el contrato para gestionar y notificar observadores.
 */
public interface OrderSubject {
    void subscribe(OrderObserver observer);
    void unsubscribe(OrderObserver observer);
    void notifyObservers(String orderId, String oldStatus, String newStatus);
}
