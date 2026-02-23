# Sistema de E-Commerce — Patrones Observer, Factory y Strategy

**Universidad de Santander — UDES**  
Unidad 1 | Post-Contenido 2  
Curso: Fundamentos de Patrones de Diseño y Buenas Prácticas

---

## Diagrama UML (textual)

```
┌─────────────────────────────────────────────────────────────────────────┐
│                         PATRÓN OBSERVER                                  │
│                                                                           │
│  «interface»          «interface»                                        │
│  OrderObserver ◄──── OrderSubject                                        │
│  + update()           + subscribe()                                      │
│       ▲               + unsubscribe()                                    │
│       │               + notifyObservers()                                │
│  ┌────┴────────────┐        ▲                                            │
│  │EmailNotifier    │        │                                            │
│  │SMSNotifier      │   OrderService (implements OrderSubject)           │
│  │LogNotifier      │                                                     │
│  └─────────────────┘                                                     │
└─────────────────────────────────────────────────────────────────────────┘

┌─────────────────────────────────────────────────────────────────────────┐
│                       PATRÓN FACTORY METHOD                              │
│                                                                           │
│  «abstract»                                                               │
│  Product                                                                  │
│  + calculateShipping(): double                                            │
│  + getDescription(): String                                               │
│       ▲                                                                   │
│  ┌────┴───────────────────┐                                              │
│  │ Electronics            │  Clothing        Food                        │
│  │ - warrantyMonths: int  │  - size: String  - expirationDays: int      │
│  └────────────────────────┘                                              │
│                                                                           │
│  ProductFactory                                                           │
│  + createProduct(type, name, price): Product   ◄── cliente               │
└─────────────────────────────────────────────────────────────────────────┘

┌─────────────────────────────────────────────────────────────────────────┐
│                         PATRÓN STRATEGY                                  │
│                                                                           │
│  «interface»                                                              │
│  PricingStrategy                                                          │
│  + calculateFinalPrice(price): double                                    │
│  + getDescription(): String                                               │
│       ▲                                                                   │
│  ┌────┴──────────────────────────────────┐                               │
│  │ RegularPricing  │ MemberPricing        │                              │
│  │ BlackFridayPricing │ BulkPricing       │                              │
│  └───────────────────────────────────────┘                               │
│                                                                           │
│  OrderService ──uses──► PricingStrategy                                  │
└─────────────────────────────────────────────────────────────────────────┘
```

---

## Justificación de Patrones

### Observer
**Problema resuelto:** Los componentes de notificación (email, SMS, log) no deben conocerse entre sí ni estar acoplados al servicio de pedidos.  
**Por qué Observer:** Permite que `OrderService` solo llame a `notifyObservers()` sin importar cuántos o cuáles observadores estén suscritos. Agregar un nuevo canal de notificación (ej. WhatsApp) solo requiere crear una nueva clase `implements OrderObserver`, sin modificar el servicio. **Cumple OCP y DIP.**

### Factory Method
**Problema resuelto:** El código cliente no debe conocer las clases concretas `Electronics`, `Clothing` o `Food` para crear productos.  
**Por qué Factory:** Centraliza la lógica de creación. Si se agrega un nuevo tipo de producto, solo se modifica `ProductFactory`. **Cumple OCP y SRP.**

### Strategy
**Problema resuelto:** El sistema debe aplicar distintas políticas de descuento (regular, miembro, Black Friday, volumen) intercambiables en tiempo de ejecución.  
**Por qué Strategy:** Encapsula cada algoritmo de precio en su propia clase. `OrderService` puede cambiar la estrategia activa sin condiciones `if/switch`. **Cumple OCP y SRP.**

---

## Estructura del Proyecto

```
ecommerce-patterns/
  src/
    model/
      Product.java          ← Clase abstracta base
      Electronics.java      ← Producto: Electrónica
      Clothing.java         ← Producto: Ropa
      Food.java             ← Producto: Alimentos
      Order.java            ← Entidad Pedido
    factory/
      ProductFactory.java   ← Factory Method
    observer/
      OrderObserver.java    ← Interfaz Observer
      OrderSubject.java     ← Interfaz Subject
      EmailNotifier.java    ← Observador concreto
      SMSNotifier.java      ← Observador concreto
      LogNotifier.java      ← Observador concreto
    strategy/
      PricingStrategy.java  ← Interfaz Strategy
      RegularPricing.java   ← Sin descuento
      MemberPricing.java    ← 10% descuento
      BlackFridayPricing.java ← 30% descuento
      BulkPricing.java      ← Descuento por volumen
    service/
      OrderService.java     ← Integra los 3 patrones
    Main.java               ← Punto de entrada
  README.md
```

---

## Instrucciones de Ejecución

### Requisitos
- JDK 17 o superior
- Terminal / cmd

### Compilación

```bash
# Desde la raíz del proyecto
mkdir -p out
javac -d out -sourcepath src $(find src -name "*.java")
```

En Windows (cmd):
```cmd
mkdir out
javac -d out -sourcepath src src\Main.java src\model\*.java src\factory\*.java src\observer\*.java src\strategy\*.java src\service\*.java
```

### Ejecución

```bash
java -cp out Main
```

### Salida esperada

```
╔══════════════════════════════════════════════════╗
║   SISTEMA DE E-COMMERCE — PATRONES DE DISEÑO    ║
╚══════════════════════════════════════════════════╝

══════ [1] SUSCRIPCIÓN DE OBSERVADORES ══════
[Sistema] Observador suscrito: EmailNotifier
[Sistema] Observador suscrito: SMSNotifier
[Sistema] Observador suscrito: LogNotifier

══════ [2] CREACIÓN DE PRODUCTOS (Factory) ══════
...
══════ [4] CAMBIOS DE ESTADO DEL PEDIDO (Observer) ══════
[EMAIL] → cliente@email.com | Pedido ORD-2025-001 cambió de CREATED a PROCESSING...
[SMS]   → +57 300 123 4567  | Tu pedido ORD-2025-001 ahora está en estado: PROCESSING...
[LOG]   → [2025-xx-xx xx:xx:xx] AUDIT: Pedido ORD-2025-001 | CREATED ──► PROCESSING
...
```

---

## Principios SOLID aplicados

| Principio | Aplicación |
|-----------|------------|
| **S** — Single Responsibility | Cada clase tiene una sola razón para cambiar |
| **O** — Open/Closed | Nuevos tipos de producto, notificador o descuento no modifican clases existentes |
| **L** — Liskov Substitution | Cualquier `Product`, `PricingStrategy` u `OrderObserver` es intercambiable |
| **I** — Interface Segregation | Interfaces pequeñas y específicas (`OrderObserver`, `OrderSubject`, `PricingStrategy`) |
| **D** — Dependency Inversion | `OrderService` depende de interfaces, no de implementaciones concretas |
