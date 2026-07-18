# 12-HexagonalMicroservicesLab

Laboratorio práctico para **aterrizar** arquitectura hexagonal, microservicios y Docker mediante ejemplos pequeños en Java y Spring Boot.

La intención no es construir una plataforma empresarial completa, sino aislar cada concepto para poder verlo funcionando:

- **Arquitectura hexagonal:** cómo ordenar las dependencias dentro de una aplicación.
- **Microservicios:** cómo separar un sistema en aplicaciones desplegables de forma independiente.
- **Docker Compose:** cómo ejecutar varios servicios juntos sin confundirlos con una sola aplicación.

## Tecnologías

- Java 21
- Maven 3.9+
- Spring Boot 3.5.16
- Docker y Docker Compose

## Estructura

```text
12-HexagonalMicroservicesLab
├── 01-hexagonal-orders
│   └── Una API de pedidos con puertos y adaptadores
├── 02-microservices
│   ├── catalog-service
│   └── order-service
├── docs
│   ├── 01-hexagonal.md
│   └── 02-microservices.md
├── compose.yaml
└── pom.xml
```

## 1. Ejemplo hexagonal

El primer módulo contiene una sola aplicación:

```text
HTTP
  ↓
Adaptador de entrada: OrderController
  ↓
Puerto de entrada: CreateOrderUseCase
  ↓
Aplicación: OrderService
  ↓
Puerto de salida: OrderRepository
  ↓
Adaptador de salida: InMemoryOrderRepository
```

La lógica central no conoce Spring MVC ni sabe dónde se almacenan los pedidos.

### Ejecutar

```bash
mvn -pl 01-hexagonal-orders spring-boot:run
```

Crear un pedido:

```bash
curl -X POST http://localhost:8080/api/orders \
  -H "Content-Type: application/json" \
  -d '{
    "customerName": "Sam",
    "items": [
      {
        "productCode": "BOOK-HEX",
        "quantity": 2,
        "unitPrice": 19.95
      }
    ]
  }'
```

Listar pedidos:

```bash
curl http://localhost:8080/api/orders
```

## 2. Ejemplo de microservicios

El segundo ejemplo contiene dos aplicaciones independientes:

```text
Cliente
  ↓
order-service :8082
  ↓ HTTP
catalog-service :8081
```

`order-service` no importa las clases internas de `catalog-service`. Solo conoce su contrato HTTP.

### Ejecutar con Docker

```bash
docker compose up --build
```

Consultar catálogo:

```bash
curl http://localhost:8081/api/products
```

Crear un pedido que consulta el catálogo remoto:

```bash
curl -X POST http://localhost:8082/api/orders \
  -H "Content-Type: application/json" \
  -d '{
    "sku": "HEX-BOOK",
    "quantity": 2
  }'
```

## Compilar y ejecutar pruebas

```bash
mvn clean verify
```

Los tests importantes se ejecutan sin levantar Spring:

- El caso de uso hexagonal se prueba con un repositorio falso.
- El servicio de pedidos se prueba con un catálogo falso.
- La infraestructura se sustituye sin modificar la lógica central.

## Qué observar

1. Los paquetes `domain` y `application` no dependen de controladores ni de clientes HTTP.
2. Los puertos describen capacidades: guardar pedidos o consultar productos.
3. Los adaptadores contienen los detalles: memoria, HTTP y JSON.
4. Los microservicios tienen procesos, puertos y despliegues distintos.
5. Un repositorio único no convierte los módulos en un monolito: la independencia se decide en ejecución y despliegue.

Consulta los documentos de `docs/` antes de ampliar el laboratorio.
