# 01 — Arquitectura hexagonal

## La pregunta que responde

La arquitectura hexagonal responde a esta pregunta:

> ¿Cómo evito que mi lógica dependa de Struts, Spring, JPA, Oracle, SOAP o cualquier otro detalle externo?

No exige dibujar un hexágono. Exige controlar la dirección de las dependencias.

## Caso de uso del ejemplo

Crear y consultar pedidos.

```mermaid
flowchart LR
    HTTP[Petición HTTP] --> Controller[OrderController]
    Controller --> InPort[CreateOrderUseCase]
    InPort --> Service[OrderService]
    Service --> OutPort[OrderRepository]
    OutPort --> Memory[InMemoryOrderRepository]
```

## Traducción a clases

| Concepto | Clase |
|---|---|
| Dominio | `Order`, `OrderItem` |
| Puerto de entrada | `CreateOrderUseCase`, `QueryOrderUseCase` |
| Caso de uso | `OrderService` |
| Puerto de salida | `OrderRepository` |
| Adaptador de entrada | `OrderController` |
| Adaptador de salida | `InMemoryOrderRepository` |
| Cableado | `OrderConfiguration` |

## La línea importante

```text
OrderService -> OrderRepository
```

`OrderService` depende de una interfaz que expresa lo que necesita. La implementación concreta depende de esa interfaz:

```text
InMemoryOrderRepository -> OrderRepository
```

La base de datos, un servicio SOAP o un fichero podrían reemplazar al adaptador en memoria sin cambiar el caso de uso.

## Relación con un proyecto por capas

Una arquitectura por capas habitual puede ser:

```text
Controller -> Service -> JpaRepository -> Base de datos
```

Funciona, pero el servicio conoce directamente el mecanismo de persistencia.

La versión hexagonal introduce un límite propio:

```text
Controller -> Caso de uso -> Puerto propio <- Adaptador JPA
```

El objetivo no es añadir interfaces por ceremonia. El puerto merece existir cuando protege una frontera que podría cambiar o que conviene probar de forma aislada.

## Prueba que demuestra la idea

`OrderServiceTest` no levanta Spring ni una base de datos. Introduce un repositorio falso y prueba la lógica directamente.

Ese reemplazo sencillo es la demostración práctica de que el núcleo está aislado.
