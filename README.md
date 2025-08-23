# Spring Security Project 🚀

This project is a **Spring Boot application** that demonstrates a **role-based authentication and authorization system** using **Spring Security**.  
It provides a clean, modular approach for securing REST APIs with custom login/logout flow, user management, and item management features.


## 📌 Features
- Custom **Authentication** (login/logout with tokens).
- Role-based **Authorization** (USER / ADMIN).
- **Password hashing** with Spring Security’s `PasswordEncoder`.
- Active/inactive user management.
- Swagger UI integration for easy API exploration.
- In-memory token cache for session management.


## 🛠️ Tech Stack
- **Java 17+**
- **Spring Boot 3+**
- **Spring Security**
- **Jakarta Persistence (JPA)**
- **H2 / PostgreSQL** (configurable)
- **Swagger / OpenAPI**
- **Maven**


## 🔐 Security Flow
1. A user logs in with username & password.
2. System validates credentials and user status.
3. On success, a random **token** (UUID) is generated and cached.
4. Subsequent requests must send the token via the **Authorization header**:
5. Authorization: Bearer <token>
6. Logout removes the token from cache.

## 📖 API Endpoints

### 🔑 Auth APIs
- **POST** `/api/auth/login` → Login with username/password → returns `TokenDto`.
- **POST** `/api/auth/logout` → Logout (requires Authorization header with token).

### 👤 User APIs
- **GET** `/api/users` → List all users (supports pagination).
- **POST** `/api/users` → Create new user (`UserCreateRequest`).
- **GET** `/api/users/{id}` → Get user by ID.
- **PUT** `/api/users/{id}` → Update user.
- **POST** `/api/users/{id}/promote` → Promote user to admin.
- **POST** `/api/users/{id}/deactivate` → Deactivate user.
- **POST** `/api/users/{id}/activate` → Activate user.
- **POST** `/api/users/change-password` → Change current user’s password.
- **GET** `/api/users/me` → Get currently authenticated user.

### 📦 Item APIs
- **GET** `/api/items` → List all items (with pagination & userId filter).
- **POST** `/api/items` → Create an item.
- **GET** `/api/items/{id}` → Get item by ID.
- **PUT** `/api/items/{id}` → Update item.
- **DELETE** `/api/items/{id}` → Delete item.
- **POST** `/api/items/{id}/approve` → Approve item.
- **POST** `/api/items/{id}/reject` → Reject item.
 

⚠️ **Important Notes**

- Tokens are stored in-memory. If the server restarts, all sessions are lost.  
  👉 For production, integrate with **Redis** or **JWT**.  

- Default security configuration disables **CSRF** for APIs.  

- Role-based access is enforced via `@PreAuthorize` annotations.  

📌 **Future Enhancements**

- Replace in-memory token store with **JWT** or **Redis**.  
- Add **refresh token** mechanism.  
- Integrate with **Keycloak / OAuth2** for enterprise-level security.  


