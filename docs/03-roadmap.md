# 03 — Roadmap del laboratorio

El laboratorio se ampliará por capas, manteniendo cada paso pequeño y verificable.

## Fase 1 — Base actual

- [x] Aplicación hexagonal con adaptador en memoria.
- [x] Dos microservicios comunicados por HTTP.
- [x] Dockerfiles independientes.
- [x] Orquestación local con Docker Compose.
- [x] Tests del núcleo sin Spring.

## Fase 2 — Persistencia

- [ ] Adaptador JPA para el ejemplo hexagonal.
- [ ] PostgreSQL independiente por microservicio.
- [ ] Migraciones con Flyway.
- [ ] Tests de integración con Testcontainers.

## Fase 3 — Comunicación robusta

- [ ] Timeouts explícitos.
- [ ] Manejo de errores remotos.
- [ ] Reintentos limitados.
- [ ] Circuit breaker.
- [ ] Correlation ID y logs estructurados.

## Fase 4 — Kubernetes

- [ ] Deployment por servicio.
- [ ] Service por aplicación.
- [ ] ConfigMap para configuración.
- [ ] Secret para credenciales.
- [ ] Readiness y liveness probes.

Cada fase debe responder primero a una pregunta concreta. No se añadirá tecnología solo para aumentar el número de nombres en el repositorio.
