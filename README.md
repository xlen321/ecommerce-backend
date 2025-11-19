# 🚀 Ecommerce Backend (Spring Boot + PostgreSQL + Neon)

A modern **eCommerce backend** built using **Spring Boot 3**, **Java 21**, **PostgreSQL (Neon)**, and **REST APIs**. This backend is designed to be clean, scalable, and production-ready.

---

## 📦 Features

### ✅ Category Management

* Create category
* Update category
* Soft delete
* Fetch all categories
* Parent–child category hierarchy
* Auto slug generation
* JSONB metadata support
* Active/inactive filtering

### ✅ Architecture

* Controller → Service → Repository
* DTO + MapStruct
* Jakarta Validation
* Unified API response
* Soft delete & timestamps

### 🎯 Tech Stack

* Java 21
* Spring Boot 3.5
* PostgreSQL (Neon)
* Hibernate / JPA
* Maven
* MapStruct
* Jackson
* Lombok
* SLF4J Logging

---

## 🗂 Project Structure

```
ecom/
 ├── src/main/java/com/ecommerce/ecom/
 │    ├── controller/
 │    ├── service/
 │    ├── model/
 │    ├── dto/
 │    ├── mapper/
 │    ├── repository/
 │    └── exception/
 ├── src/main/resources/
 │    ├── application.properties
 ├── pom.xml
 └── README.md
```

---

## 🛠 Setup Instructions

### 1️⃣ Clone Repository

```
git clone https://github.com/xlen321/econnerce-backend.git
cd econnerce-backend
```

### 2️⃣ Create `.env` file

```
DB_URI=jdbc:postgresql://<your-neon-endpoint>
DB_USER=<your-db-user>
DB_PASSWORD=<your-db-password>
```

### 3️⃣ Run the application

```
mvn spring-boot:run
```

Server starts at:

```
http://localhost:8085
```

---

## 📘 API Endpoints

### 🔹 Public Endpoints

| Method | Endpoint                                    | Description                          |
| ------ | ------------------------------------------- | ------------------------------------ |
| GET    | `/api/public/categories`                    | Fetch all categories                 |
| GET    | `/api/public/categories?active=true`        | Fetch only active categories         |
| GET    | `/api/public/categories?active=false`       | Fetch only inactive categories       |
| GET    | `/api/public/categories/search?q={keyword}` | Search by name, slug, or description |

### 🔹 Admin Endpoints

| Method | Endpoint                                               | Description                  |
| ------ | ------------------------------------------------------ | ---------------------------- |
| POST   | `/api/admin/category`                                  | Create a category            |
| PUT    | `/api/admin/categories/{id}`                           | Update category              |
| PATCH  | `/api/admin/categories/{id}/active?value={true/false}` | Activate/Deactivate category |
| PATCH  | `/api/admin/categories/{id}/metadata`                  | Update metadata (JSON)       |
| PATCH  | `/api/admin/categories/{id}/position?value=4`          | Update category position     |
| DELETE | `/api/admin/categories/{id}`                           | Soft delete category         |

---

## 🧾 Example Create Category Request

```json
{
  "name": "Electronics",
  "description": "All electronic items",
  "position": 1,
  "metadata": {
    "color": "blue"
  },
  "active": true,
  "parentId": null
}
```

---

## 🧪 Testing

```
mvn test
```

---

## 🔐 Environment Variables

| Variable      | Description       |
| ------------- | ----------------- |
| `DB_URI`      | JDBC database URL |

`.env` is included in `.gitignore`.

---

## 📤 Deployment (Future Options)

This project is **not deployed yet**. Below are recommended options for future deployment:

### 🔹 Backend Hosting Options

* **Render** – Easiest free hosting for Spring Boot
* **Railway** – Fast, simple deployments
* **AWS EC2** – Full control, best for production
* **Azure App Service** – Managed deployment
* **Google Cloud Run** – Deploy Dockerized apps

### 🔹 Database Hosting

* **Neon PostgreSQL** (already used locally)

### 🔹 Docker (Optional)

You can containerize the backend for easier deployment:

```
docker build -t ecommerce-backend .
docker run -p 8085:8085 ecommerce-backend
```

Deployment will be added once hosting is chosen.

---

## 🤝 Contributing

Open issues and PRs are welcome.

---

## 📜 License

MIT License (or your choice).

---

## ⭐ Support

If this project helped you, give it a **⭐ on GitHub**!