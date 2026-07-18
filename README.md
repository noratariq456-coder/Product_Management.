# Product Management - Spring Boot Assignment

## 1. Project Description
A simple Spring Boot application for managing **Products**. It demonstrates:
- Spring Core (Dependency Injection, `@Configuration`, `@Bean`, `@Qualifier`/`@Primary`, lifecycle methods)
- Spring Data JPA / Hibernate (one entity: `Product`)
- REST API (full CRUD + sorting + category filter)
- Global exception handling with JSON error responses
- Spring Security with database (BCrypt) authentication and role-based access
- Spring MVC + Thymeleaf (list page + add form)
- Bean Validation (`@NotBlank`, `@Min`/`@Max`, `@Pattern`, custom annotation, `@InitBinder`)
- Swagger / OpenAPI documentation
- Actuator health/info endpoints

**Architecture flow:**
```
Controller (REST / MVC)  ->  Service  ->  Repository  ->  MySQL Database
```

## 2. Entity Fields
`Product`: `id, name, category, price, quantity, status, active, code`

## 3. Database Setup
1. Install and start MySQL locally.
2. No need to manually create the schema - the app auto-creates the database
   (`createDatabaseIfNotExist=true`) and tables (`spring.jpa.hibernate.ddl-auto=update`).
3. Update credentials in `src/main/resources/application.properties` if your MySQL
   username/password differ from `root` / `root`:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/productdb?createDatabaseIfNotExist=true&useSSL=false&serverTimezone=UTC
   spring.datasource.username=root
   spring.datasource.password=root
   ```

## 4. How to Run
```bash
# Build and run with Maven
mvn spring-boot:run
```
The app starts on the custom port: **http://localhost:8081**

On first run, three default users are seeded automatically:

| Username | Password      | Role     |
|----------|--------------|----------|
| admin    | admin123     | ADMIN    |
| manager  | manager123   | MANAGER  |
| employee | employee123  | EMPLOYEE |

## 5. Useful URLs
- Swagger UI: http://localhost:8081/swagger-ui.html
- Actuator health: http://localhost:8081/actuator/health
- Actuator info: http://localhost:8081/actuator/info
- Product list (Thymeleaf): http://localhost:8081/products
- Add product form: http://localhost:8081/products/add

## 6. REST API Endpoints

| Method | Endpoint                            | Purpose                    | Allowed Roles              |
|--------|--------------------------------------|-----------------------------|-----------------------------|
| GET    | `/api/products`                     | Get all (supports `?sort=field,dir`) | EMPLOYEE, MANAGER, ADMIN |
| GET    | `/api/products/{id}`                | Get by ID                   | EMPLOYEE, MANAGER, ADMIN    |
| GET    | `/api/products/category/{category}` | Filter by category          | EMPLOYEE, MANAGER, ADMIN    |
| POST   | `/api/products`                     | Create                      | MANAGER, ADMIN              |
| PUT    | `/api/products/{id}`                | Full update                 | MANAGER, ADMIN              |
| PATCH  | `/api/products/{id}`                | Partial update              | MANAGER, ADMIN              |
| DELETE | `/api/products/{id}`                | Delete                      | ADMIN                       |

Example sorting call:
```
GET /api/products?sort=price,asc
```

### Sample Create Request Body
```json
{
  "name": "Wireless Mouse",
  "category": "Electronics",
  "price": 25.50,
  "quantity": 100,
  "status": "AVAILABLE",
  "active": true,
  "code": "PRD-101"
}
```
Valid `status` values: `AVAILABLE`, `OUT_OF_STOCK`, `DISCONTINUED`
Valid `code` pattern example: `PRD-101` (valid) vs `prd101` (invalid)

### Sample 404 Error Response
```json
{
  "status": 404,
  "message": "Product not found with id: 100"
}
```

## 7. Postman Collection
Located at `postman/ProductManagement.postman_collection.json`. Import it into Postman.
It includes: GET all, GET by id, GET by category, POST, PUT, PATCH, DELETE,
a 404 test, and a 403 test (employee attempting DELETE).

## 8. Tech Stack
Java 21 - Spring Boot 3.3.4 - Maven - MySQL - Hibernate/JPA - Thymeleaf -
Spring Security (BCrypt, DB auth) - springdoc-openapi (Swagger) - Bean Validation - Actuator
