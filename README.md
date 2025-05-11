# 💰 Spring Boot Expense Tracker

This is a role-based expense tracker API built with **Spring Boot**, supporting operations for:
- Users (Register, View, Update, Delete)
- Expenses
- Payment Methods
- Categories

## 🚀 Technologies Used
- Spring Boot
- Spring Security (with Role-Based Access Control)
- JPA/Hibernate
- H2/MySQL/PostgreSQL (your choice)
- Lombok
- Maven

## 🧠 Entities
- **User**: Admin and User roles.
- **Expense**: Tracks spending for users.
- **Category**: Grouping for expenses, addable by both roles.
- **PaymentMethod**: Represents methods like UPI, Card, Cash.

## 🔐 Role-Based Access Control

### ✅ User Endpoints
| Method | Endpoint                 | Role       |
|--------|--------------------------|------------|
| GET    | `/users/all`             | ADMIN      |
| GET    | `/users/get/{id}`        | ADMIN, USER|
| POST   | `/users/register`        | PUBLIC     |
| PUT    | `/users/update/{id}`     | ADMIN      |
| DELETE | `/users/delete/{id}`     | ADMIN      |

### ✅ Expense Endpoints
| Method | Endpoint                         | Role       |
|--------|----------------------------------|------------|
| GET    | `/expenses/getAll`               | ADMIN      |
| GET    | `/expenses/user/{userId}`        | USER, ADMIN|
| GET    | `/expenses/get/{id}`             | ADMIN      |
| POST   | `/expenses/add`                  | USER, ADMIN|
| PUT    | `/expenses/update/{id}`          | ADMIN      |
| DELETE | `/expenses/delete/{id}`          | ADMIN      |

### ✅ Payment Method Endpoints
| Method | Endpoint                             | Role       |
|--------|--------------------------------------|------------|
| GET    | `/payment-methods/all`               | USER, ADMIN|
| GET    | `/payment-methods/get/{id}`          | USER, ADMIN|
| POST   | `/payment-methods/add`               | ADMIN      |
| PUT    | `/payment-methods/update/{id}`       | ADMIN      |
| DELETE | `/payment-methods/delete/{id}`       | ADMIN      |

### ✅ Category Endpoints
| Method | Endpoint                           | Role       |
|--------|------------------------------------|------------|
| GET    | `/api/categories/all`              | USER, ADMIN|
| GET    | `/api/categories/view/{id}`        | USER, ADMIN|
| POST   | `/api/categories/add`              | USER, ADMIN|
| PUT    | `/api/categories/update/{id}`      | ADMIN      |
| DELETE | `/api/categories/delete/{id}`      | ADMIN      |

## 📦 Running the Project

```bash
# Clone the repo
git clone https://github.com/your-username/springboot-expense-tracker.git
cd springboot-expense-tracker

# Run with Maven
./mvnw spring-boot:run
