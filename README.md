# TacoNube API

Sistema de gestión en la nube para taquerías y negocios de comida rápida, con enfoque **multi-tenant**: cada negocio administra de forma independiente sus productos, categorías, clientes, usuarios, pedidos y ventas.

## ¿De qué trata?

TacoNube nace para digitalizar la operación diaria de una taquería: toma de pedidos, seguimiento en cocina y control de ventas, todo desde un mismo sistema accesible vía web y app móvil.

Cada venta genera dos tickets:
- **Ticket cliente**: comprobante de la compra.
- **Ticket cocina**: con estados de seguimiento (pendiente, en preparación, listo, pagado, etc.).

## Stack tecnológico

| Capa            | Tecnología                     |
|-----------------|---------------------------------|
| Backend         | Spring Boot + Java 17          |
| Frontend        | React.js + TypeScript          |
| Mobile          | Flutter + Dart                 |
| Base de datos   | MySQL                          |
| DevOps          | Docker + Kubernetes            |
| Cloud           | Vercel                         |
| Documentación   | Swagger                        |
| CI/CD           | Git + GitHub Actions           |

## Módulos principales

- Productos y Categorías
- Clientes
- Ventas y Detalle de venta
- Generación de tickets (cliente y cocina)
- Usuarios y roles
- Negocios (multi-tenant)
- Pedidos con control de estados

## 📂 Estructura del proyecto

```text
src/main/java/com/dev/apitaconube/
├── config/                 # Configuración de la aplicación
│   ├── app/                # Configuración general (CORS, Jackson, Security)
│   ├── doc/                # Configuración de Swagger/OpenAPI
│   ├── jwt/                # Componentes relacionados con JWT
│   ├── mail/               # Configuración de correo
│   └── security/           # Seguridad y autenticación
│
├── controller/             # Controladores REST
├── domain/
│   ├── entity/             # Entidades JPA
│   └── enums/              # Enumeraciones del dominio
│
├── dto/
│   ├── request/            # DTOs de entrada
│   └── response/           # DTOs de salida
│
├── exception/              # Excepciones personalizadas y manejo global
├── repository/             # Interfaces Spring Data JPA
├── report/                 # Generación de reportes (PDF, Excel, Tickets)
├── service/
│   ├── interfaces/         # Contratos de servicios
│   └── impl/               # Implementaciones de servicios
│
├── storage/                # Almacenamiento de archivos
├── util/                   # Clases utilitarias
└── ApitaconubeApplication.java
```

## Estado del desarrollo

_(Ir actualizando conforme avance el proyecto)_

- [x] Diseño del esquema de base de datos (MySQL)
- [x] arquitectura del sistema
- [x] Entidades JPA
- [x] Repositorios (Spring Data JPA)
- [ ] Servicios
- [ ] Controladores / API REST
- [x] Autenticación (JWT)
- [x] Documentación Swagger
- [ ] Dockerización
