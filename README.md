# TrackingPackages

Aplicație web pentru urmărirea coletelor. Include un backend realizat în Java (Spring Boot) și un frontend construit cu Vue.js.

---

## 🔧 Tehnologii folosite

### Backend
- Java 17+
- Spring Boot
- Spring Data JPA
- MySQL (dezvoltat în Workbench)
- Maven

### Frontend
- Vue.js (CLI)
- JavaScript

---

## 📦 Structura proiectului

```
TrackingPackages/
│
├── backend/           # Codul Java Spring Boot
│
├── frontend/          # Aplicația Vue.js
│
└── README.md
```

---

## 🚀 Cum rulezi aplicația local

### 1. Configurare MySQL

1. Creează o bază de date în MySQL Workbench:
   ```sql
   CREATE DATABASE tracking_packages;
   ```
2. Asigură-te că aplicația backend e configurată corect în `application.properties` sau `application.yml`, cu datele de conectare la MySQL.

Exemplu `application.properties`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/tracking_packages
spring.datasource.username=YOUR_USERNAME
spring.datasource.password=YOUR_PASSWORD
spring.jpa.hibernate.ddl-auto=update
```

---

### 2. Pornirea backendului

1. Deschide un terminal în folderul backend.
2. Rulează comanda:
   ```bash
   ./mvnw spring-boot:run
   ```
   sau dintr-un IDE (IntelliJ, Eclipse), rulează clasa `ServerApplication.java`.

---

### 3. Pornirea frontendului

1. Deschide un terminal în folderul `frontend` (unde e `package.json`).
2. Rulează comenzile:
   ```bash
   npm install
   npm run serve
   ```
3. Aplicația va rula implicit pe [http://localhost:8080](http://localhost:8080)

---

## 🔗 API-uri disponibile (Backend)

### Exemplu endpoint-uri:
- `GET /packages` – Listează toate coletele
- `POST /packages` – Creează un colet
- `PUT /packages/{id}` – Actualizează un colet
- `DELETE /packages/{id}` – Șterge un colet

Există și endpoint-uri pentru curieri (`/couriers`), respectiv pentru statusuri.

---

## 📄 Notițe

- Asigură-te că backendul pornește înaintea frontendului.
- Aplicația Vue.js comunică cu backendul prin `http://localhost:8080` sau alt port, în funcție de configurație.
- Dacă folosești CORS, adaugă permisiuni în configurația Spring Boot pentru a permite cereri din frontend.

---

## 📬 Contact

Pentru întrebări, sugestii sau erori, te rog deschide un issue sau trimite un email.
