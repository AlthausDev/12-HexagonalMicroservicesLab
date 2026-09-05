# Hexagonal Microservices Lab

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
│   ├── 02-microservices.md
│   ├── 03-roadmap.md
│   └── 04-study-guide.md
├── .github/workflows/build.yml
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

Antes de hacer la llamada remota, el caso de uso valida los datos que puede comprobar localmente. Así evita consultar otro servicio cuando la petición ya es inválida.

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
- Se comprueba que una petición inválida no llegue a llamar al catálogo remoto.
- La infraestructura se sustituye sin modificar la lógica central.

## Integración continua

GitHub Actions ejecuta dos comprobaciones:

1. Compilación y pruebas con Maven.
2. Construcción de las imágenes Docker y una prueba de humo real entre `order-service` y `catalog-service` mediante Docker Compose.

## Principios que demuestra

1. Las dependencias apuntan hacia el dominio y los casos de uso, no hacia los frameworks.
2. Los puertos expresan capacidades y contratos; los adaptadores resuelven detalles concretos.
3. Separar repositorios no crea microservicios, y compartir repositorio no crea un monolito: la independencia real está en procesos, contratos y despliegue.
4. La validación local debe ocurrir antes de depender de una llamada remota cuando sea posible.
5. La infraestructura debe poder sustituirse sin reescribir la lógica central.

Consulta la guía [`docs/04-study-guide.md`](docs/04-study-guide.md) para recorrer los ejemplos en orden.

## Estado

Laboratorio activo de aprendizaje. El objetivo es añadir conceptos de forma incremental sin convertirlo en una demostración artificialmente grande.

## Licencia y atribución

Publicado bajo la [Licencia MIT](LICENSE).

Si reutilizas una parte sustancial del laboratorio, conserva el aviso de licencia y agradezco una referencia a **Sam Althaus / AlthausDev** y al repositorio original.
