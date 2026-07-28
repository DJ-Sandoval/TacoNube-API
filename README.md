# TacoNube API

Sistema de gestión en la nube para taquerías y negocios de comida rápida, con enfoque **multi-tenant**: cada negocio administra de forma independiente sus productos, categorías, clientes, usuarios, pedidos y ventas.

## ¿De qué trata?

TacoNube nace para digitalizar la operación diaria de una taquería: toma de pedidos, seguimiento en cocina y control de ventas, todo desde un mismo sistema accesible vía web y app móvil.

Cada venta genera dos tickets:
- **Ticket cliente**: comprobante de la compra.
- **Ticket cocina**: con estados de seguimiento (pendiente, en preparación, listo, pagado, etc.).

## Stack tecnológico

| Capa            | Tecnología            |
|-----------------|-----------------------|
| Backend         | Spring Boot + Java 17 |
| Frontend        | React.js + TypeScript |
| Mobile          | Flutter + Dart        |
| Base de datos   | MySQL                 |
| DevOps          | Docker + Kubernetes   |
| Cloud           | DigitalOcean          |
| Documentación   | Swagger               |
| CI/CD           | Git + GitHub Actions  |

## Módulos principales

- Productos y Categorías
- Clientes
- Ventas y Detalle de venta
- Generación de tickets (cliente y cocina)
- Usuarios y roles
- Negocios (multi-tenant)
- Pedidos con control de estados

## Estructura del proyecto

```
src/main/java/com/taconube/
├── model/              # Entidades JPA
│   └── enums/           # Enums de dominio (estados, tipos)
└── repository/           # Interfaces Spring Data JPA
```

## Estado del desarrollo

_(Ir actualizando conforme avance el proyecto)_

- [x] Diseño del esquema de base de datos (MySQL)
- [x] Entidades JPA
- [x] Repositorios (Spring Data JPA)
- [ ] Servicios
- [ ] Controladores / API REST
- [ ] Autenticación (JWT)
- [ ] Documentación Swagger
- [ ] Dockerización