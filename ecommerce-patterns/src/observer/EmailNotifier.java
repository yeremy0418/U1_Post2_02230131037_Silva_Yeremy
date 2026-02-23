package observer;

/**
 * Observador concreto: simula el envío de un correo electrónico
 * cuando el estado de un pedido cambia.
 */
public class EmailNotifier implements OrderObserver {

    private final String emailAddress;

    public EmailNotifier(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    @Override
    public void update(String orderId, String oldStatus, String newStatus) {
        System.out.println("[EMAIL] → " + emailAddress
                + " | Pedido " + orderId
                + " cambió de " + oldStatus + " a " + newStatus
                + ". Revisa tu bandeja de entrada para más detalles.");
    }
}
