# Sustav za Upravljanje Bolnicom (Hospital Management System)

Java + JavaFX desktop application for managing hospital records, developed for the **"Programiranje u Javi" course at TVZ (2025/2026)**.

The project simulates a real hospital management system with role-based access, CRUD operations, and database persistence.

---

## What it does

* Login system with role-based access (ADMIN, DOCTOR, NURSE, etc.)
* Separate modules for managing:

  * Doctors
  * Nurses
  * Patients
  * Prescriptions
  * Hospitals and departments
* Add / edit / delete operations through popup dialogs
* Confirmation dialogs before destructive actions
* Automatic table refresh after changes
* Detailed patient and hospital overview screens
* Activity logging for all important actions (who did what and when)
* Background database operations using virtual threads (UI stays responsive)

---

## Screens

* **Login screen** – authentication and role selection
* **Dashboard** – main navigation hub after login
* **Doctors module** – manage doctor profiles
* **Nurses module** – manage nurses and assignments
* **Patients module** – patient records, status, and assigned staff
* **Prescriptions module** – medication tracking
* **Hospital overview** – hospital details, departments, staff overview
* **Activity log (internal)** – system tracking of user actions

---

## Tech stack

* Java 21 / 25 (depending on runtime)
* JavaFX (FXML UI)
* JDBC (H2 database)
* Maven build system
* Logback logging
* Virtual Threads (Project Loom)
* noVNC (for web container access)

---

## Project structure

```
src/main/java/com/alex/sustavzaupravljanjebolnice/
├── app/          # application entry point
├── entity/       # domain models (Doctor, Nurse, Patient, etc.)
├── controller/   # JavaFX controllers (UI logic)
│   ├── popup/    # dialog windows
├── repository/   # database access layer (JDBC)
├── util/         # helpers (window manager, session, logging, etc.)
```

---

## Running locally

### Build project

```bash
mvn clean package
```

### Run JavaFX app

```bash
mvn javafx:run
```

### Database config

Database connection settings are located in:

```
src/main/resources/db.properties
```

---

## Logging system

All critical actions are logged with:

* user identity (from session)
* timestamp
* action type (add/edit/delete)

Logs are written asynchronously to avoid blocking the UI.

---

## Architecture notes

This project was developed incrementally during coursework, so some parts intentionally share repeated patterns across entities.

Key design decisions:

* Direct JDBC usage instead of ORM (for learning purposes)
* Controllers handle UI logic directly (simplified architecture)
* Reusable popup system using `WindowManager`
* Async database execution to prevent UI freezing
* Role-based UI visibility and access control

---

## Docker (Web App Version)

This project can also be run as a **web-accessible desktop application** using Docker.

Instead of rewriting the JavaFX app into a web framework, the UI is exposed through a browser using:

* Xvfb (virtual display server)
* x11vnc (VNC server)
* noVNC (browser interface)
* websockify (WebSocket bridge)

This allows the JavaFX desktop application to be used like a web app.

---

### Build Docker image

```bash
docker build -t hospital-app .
```

---

### Run container

```bash
docker run -p 6080:6080 hospital-app
```

---

### Open in browser

```
http://localhost:6080
```
