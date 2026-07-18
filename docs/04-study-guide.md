# 04 — Guía de estudio

El laboratorio está pensado para leerse siguiendo el recorrido real de una petición, no paquete por paquete.

## Sesión 1 — Arquitectura hexagonal

1. Ejecuta `01-hexagonal-orders`.
2. Envía un `POST /api/orders`.
3. Sigue el recorrido desde `OrderController` hasta `InMemoryOrderRepository`.
4. Sustituye mentalmente el repositorio en memoria por JPA.
5. Ejecuta `OrderServiceTest` y comprueba que no levanta Spring.

Pregunta que debe quedar resuelta:

> ¿Por qué el caso de uso puede probarse sin conocer la infraestructura?

## Sesión 2 — Microservicios

1. Levanta `catalog-service` y `order-service` con Docker Compose.
2. Consulta un producto directamente en catálogo.
3. Crea un pedido desde `order-service`.
4. Detén `catalog-service` y repite la llamada.
5. Localiza el punto exacto donde una llamada Java se transforma en HTTP.

Pregunta que debe quedar resuelta:

> ¿Qué diferencia hay entre incluir un JAR y llamar a otra aplicación por red?

## Sesión 3 — Modificación guiada

Añade un nuevo producto a `catalog-service` y crea un pedido con él sin modificar `order-service`.

Después cambia el nombre interno de la clase `Product` en catálogo sin alterar el JSON público. El otro servicio debe seguir funcionando.

La finalidad es distinguir:

- implementación interna;
- contrato externo;
- dependencia de compilación;
- dependencia en tiempo de ejecución.
