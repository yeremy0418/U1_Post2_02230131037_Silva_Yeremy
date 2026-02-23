package observer;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Observador concreto: registra en consola con timestamp
 * cada cambio de estado de un pedido. Útil para auditoría.
 */
public class LogNotifier implements OrderObserver {

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public void update(String orderId, String oldStatus, String newStatus) {
        String timestamp = LocalDateTime.now().format(FORMATTER);
        System.out.println("[LOG]   → [" + timestamp + "] AUDIT: Pedido " + orderId
                + " | " + oldStatus + " ──► " + newStatus);
    }
}
