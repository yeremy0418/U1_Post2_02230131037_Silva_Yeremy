package observer;

/**
 * Observador concreto: simula el envío de un SMS
 * cuando el estado de un pedido cambia.
 */
public class SMSNotifier implements OrderObserver {

    private final String phoneNumber;

    public SMSNotifier(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public void update(String orderId, String oldStatus, String newStatus) {
        System.out.println("[SMS]   → " + phoneNumber
                + " | Tu pedido " + orderId
                + " ahora está en estado: " + newStatus + ". (antes: " + oldStatus + ")");
    }
}
