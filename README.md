# TrackingPackages

A web application for package tracking. It includes a backend built with Java (Spring Boot) and a frontend developed in Vue.js.

---

## 🔧 Technologies Used

### Backend
- Java 17+
- Spring Boot
- Spring Data JPA
- MySQL (designed in Workbench)
- Maven

### Frontend
- Vue.js (CLI)
- JavaScript

---

## 📦 Project Structure

```
TrackingPackages/
│
├── backend/           # Spring Boot Java code
│
├── frontend/          # Vue.js frontend
│
└── README.md
```

---

## 🚀 How to Run Locally

### 1. Configure MySQL

1. Create a database in MySQL Workbench:
   ```sql
   CREATE DATABASE tracking_packages;
   ```

2. Make sure your backend configuration (`application.properties` or `application.yml`) has correct database credentials.

Example `application.properties`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/tracking_packages
spring.datasource.username=YOUR_USERNAME
spring.datasource.password=YOUR_PASSWORD
spring.jpa.hibernate.ddl-auto=update
```

---

### 2. Start the Backend

1. Open a terminal inside the `backend` folder.
2. Run the command:
   ```bash
   ./mvnw spring-boot:run
   ```
   Or from an IDE (IntelliJ, Eclipse), run the `ServerApplication.java` class.

---

### 3. Start the Frontend

1. Open a terminal in the `frontend` folder (where `package.json` is).
2. Run the following:
   ```bash
   npm install
   npm run serve
   ```
3. The app will run by default at [http://localhost:8080](http://localhost:8080)

---

## 🔗 Available APIs (Backend)

### Example Endpoints:
- `GET /packages` – List all packages
- `POST /packages` – Create a new package
- `PUT /packages/{id}` – Update a package
- `DELETE /packages/{id}` – Delete a package

There are also endpoints for couriers (`/couriers`) and status tracking.

---

## 📄 Notes

- Make sure the backend is running before the frontend.
- The Vue.js app communicates with the backend via `http://localhost:8080` or as configured.
- If you use CORS, allow requests from the frontend in the Spring Boot configuration.

---

## 📬 Contact

For questions, suggestions, or bugs, feel free to open an issue or send an email.
