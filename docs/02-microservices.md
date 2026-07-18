# 02 — Microservicios

## La pregunta que responden

Los microservicios responden a otra pregunta:

> ¿Cómo divido un sistema en aplicaciones que puedan ejecutarse, desplegarse y evolucionar por separado?

No describen necesariamente cómo se organiza cada aplicación por dentro.

## Servicios del ejemplo

```mermaid
flowchart LR
    Client[Cliente] --> Order[order-service :8082]
    Order -->|HTTP GET /api/products/{sku}| Catalog[catalog-service :8081]
```

### `catalog-service`

Responsable de:

- productos;
- nombres;
- precios;
- disponibilidad del catálogo.

### `order-service`

Responsable de:

- crear pedidos;
- calcular el total usando el precio recibido;
- conservar sus pedidos en su propia memoria.

## Qué los hace servicios distintos

- Tienen aplicaciones Spring Boot diferentes.
- Escuchan en puertos diferentes.
- Generan JAR diferentes.
- Generan imágenes Docker diferentes.
- Se comunican por HTTP.
- Uno puede recompilarse sin recompilar el otro mientras el contrato permanezca compatible.

Estar en el mismo repositorio es una decisión de organización del código. No los convierte en un único proceso.

## Contrato frente a implementación

`order-service` conoce una respuesta con:

```json
{
  "sku": "HEX-BOOK",
  "name": "Hexagonal Architecture",
  "price": 29.90
}
```

No conoce el `Map`, las clases internas ni el modo en que `catalog-service` obtiene sus datos.

## El puerto remoto

Dentro de `order-service`, el caso de uso depende de:

```java
public interface CatalogGateway {
    Optional<ProductSnapshot> findBySku(String sku);
}
```

El adaptador HTTP implementa ese puerto. Esto muestra que hexagonal y microservicios no compiten:

- microservicios define la frontera entre aplicaciones;
- hexagonal puede ordenar el interior de cada aplicación.

## Costes que el laboratorio todavía no oculta

Una llamada remota puede fallar, tardar o devolver un contrato incompatible. En una evolución posterior se pueden añadir:

- timeouts;
- reintentos;
- circuit breaker;
- trazas distribuidas;
- contratos versionados;
- persistencia independiente;
- mensajería asíncrona.

No se añaden al primer ejercicio para no esconder la idea principal bajo infraestructura.
