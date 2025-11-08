# ⚙️ Mobile Prepaid Backend

This is the **Spring Boot backend** for the Mobile Prepaid Application, handling authentication, user management, recharge operations, and email notifications.

---

## 🚀 Features

### 🔐 Authentication
- JWT-based authentication.
- Role-based access control for **Admin** and **User**.
- Admin-created users (no open registration).
- Passwords securely stored with BCrypt hashing.

### 👑 Admin Capabilities
- Approve/reject new user registration requests.
- View, update, and delete existing users.
- Manage recharge plans (create, edit, delete).
- Process account update and delete requests from users.

### 👤 User Capabilities
- Login with credentials provided by admin.
- Perform recharges with dummy payment flow.
- View recharge history.
- Request profile update or account deletion.
- Receive confirmation emails for recharge and admin responses.

---

## 🛠️ Tech Stack

| Layer | Technology |
|-------|-------------|
| Backend Framework | Spring Boot 3.x |
| Language | Java 17+ |
| Security | Spring Security + JWT |
| ORM | JPA / Hibernate |
| Database | MySQL |
| Mail | Spring Boot Starter Mail |
| Build Tool | Maven |

---

## 📁 Project Structure

```
mobileprepaid-backend/
├── src/main/java/com/aits/mobileprepaid/
│   ├── controller/      # REST Controllers
│   ├── entity/          # JPA Entities
│   ├── repo/            # Spring Data Repositories
│   ├── security/        # JWT and Security Config
│   ├── service/         # Business Logic
│   └── MobilePrepaidApplication.java
├── src/main/resources/
│   ├── application.properties
│   └── templates/ (optional email templates)
└── pom.xml
```

---

## ⚙️ Setup & Installation

### 1️⃣ Clone the repository
```bash
git clone https://github.com/yourusername/mobileprepaid-backend.git
cd mobileprepaid-backend
```

### 2️⃣ Configure MySQL Database
Edit `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/mobileprepaid?createDatabaseIfNotExist=true
spring.datasource.username=root
spring.datasource.password=YOUR_PASSWORD

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

### 3️⃣ Configure Mail (for notifications)
```properties
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=9908135565ks@gmail.com
spring.mail.password=YOUR_APP_PASSWORD
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true

admin.email=9908135565ks@gmail.com
```

> ⚠️ Use an **App Password** from Gmail (not your regular password).

### 4️⃣ Build and Run the project
```bash
mvn clean install
mvn spring-boot:run
```

Backend will start at:
```
http://localhost:8080
```

---

## 🔑 Default Admin

An admin is preloaded during startup:

| Email | Password | Role |
|--------|-----------|------|
| admin@mobileprepaid.com | Admin@123 | ADMIN |

---

## 🔗 API Endpoints

### 🔐 Authentication
| Method | Endpoint | Description |
|--------|-----------|-------------|
| POST | `/auth/login` | Login and get JWT token |
| POST | `/auth/register` | (For Admin testing only) Register manually |

### 👑 Admin Routes
| Method | Endpoint | Description |
|--------|-----------|-------------|
| GET | `/register/admin/pending` | View pending registration requests |
| POST | `/register/admin/approve/{id}` | Approve registration |
| DELETE | `/register/admin/reject/{id}` | Reject registration |
| GET | `/users` | View all users |
| PUT | `/admin/users/{id}` | Update user info |
| DELETE | `/admin/users/{id}` | Delete user |
| GET | `/plans` | Get all recharge plans |
| POST | `/plans` | Create new plan |
| PUT | `/plans/{id}` | Update existing plan |
| DELETE | `/plans/{id}` | Delete plan |
| GET | `/admin/delete-requests` | View delete account requests |
| GET | `/admin/update-requests` | View update account requests |
| POST | `/admin/delete-requests/{id}/approve` | Approve delete request |
| POST | `/admin/delete-requests/{id}/reject` | Reject delete request |
| POST | `/admin/update-requests/{id}/approve` | Approve update request |
| POST | `/admin/update-requests/{id}/reject` | Reject update request |

### 👤 User Routes
| Method | Endpoint | Description |
|--------|-----------|-------------|
| POST | `/recharge` | Perform recharge |
| GET | `/recharge/history/{userId}` | View recharge history |
| POST | `/user/update-request` | Send update account request |
| POST | `/user/delete-request` | Send delete account request |

---

## 🧾 Entity Overview

### `User`
- id, name, email, mobile, password, role

### `RechargePlan`
- id, name, category, price, description, validityInDays

### `RechargeHistory`
- id, user, plan, rechargeDate, paymentMethod, expiryDate

### `RegistrationRequest`
- id, name, email, mobile, message, approved

### `DeleteAccountRequest` / `UpdateAccountRequest`
- id, userId, email/newEmail, reason, processed, requestDate

---

## 🧠 Security Overview

- `JwtFilter` validates token on each request.
- `JwtUtil` handles token generation & verification.
- Endpoints restricted using roles in `SecurityConfig`:
  ```java
  .requestMatchers("/auth/**").permitAll()
  .requestMatchers("/admin/**").hasRole("ADMIN")
  .requestMatchers("/recharge/**", "/plans/**").hasAnyRole("USER", "ADMIN")
  .anyRequest().authenticated()
  ```

---

## 📬 Email Notifications

Email is sent automatically when:
- A recharge is completed.
- A registration request is approved/rejected.
- An account delete/update request is processed.

---

## 🧾 Build JAR for Deployment

```bash
mvn clean package
java -jar target/mobileprepaid-0.0.1-SNAPSHOT.jar
```

---

## 🧠 Developer Tips
- Test with Postman using JWT tokens.
- Ensure MySQL service is running before launching.
- CORS is enabled for localhost:5173 (frontend).

---

## ❤️ Credits

Developed by **Sasidhar Kanakinti**  
© 2025 — All rights reserved.
