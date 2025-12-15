# Hospital Management System

A JavaFX-based hospital management application demonstrating advanced Java programming concepts and best practices.

## Purpose

This project showcases various Java capabilities including:
- Object-oriented design patterns
- JavaFX GUI development
- File I/O (JSON, XML, Binary)
- Stream API and functional programming
- Generic types and collections
- Exception handling
- Unit testing

## Features

### JavaFX User Interface

- **Main Menu**: Central navigation hub for all application features
- **Search Screens**: Advanced search functionality for hospitals, doctors, patients, and appointments
- **Input Validation**: Real-time validation with informative error dialogs
- **Responsive Tables**: Display and filter data using TableView components

### Data Management

- **JSON Persistence**: Automatic data loading/saving
  - `hospitals.json` - Hospital records
  - `doctors.json` - Doctor information
  - `patients.json` - Patient data
  - `appointments.json` - Appointment schedules

- **Backup System**: Binary serialization for data backup
  - Create: Save complete system state to `backup.bin`
  - Restore: Recover data from previous backups

- **Activity Logging**: XML-based user action tracking
  - Logs all user operations to `user_actions.xml`
  - View formatted logs without XML tags

### Design Patterns

- **Builder Pattern**: Patient object construction
- **Record Pattern**: Immutable appointment data
- **Singleton-like Utilities**: Static utility classes for common operations
- **MVC Pattern**: JavaFX controllers separate UI from business logic

## Technology Stack

- **Java 25** - Modern Java features
- **JavaFX 21** - Rich GUI framework
- **Maven** - Build and dependency management
- **Gson** - JSON serialization
- **SLF4J + Logback** - Logging
- **JUnit 5** - Testing framework

## Running the Application

### Prerequisites
- Java 25 or higher
- Maven 3.6+


## Application Screens

### Main Menu
- Search Hospitals
- Search Doctors  
- Search Patients
- Search Appointments
- Create Backup
- Restore Backup
- View Activity Logs
- Exit

### Search Functionality

Each search screen allows filtering by:
- **Hospitals**: Name, ID
- **Doctors**: Name, specialization, ID  
- **Patients**: Name, condition, ID
- **Appointments**: Doctor name, patient name, date/time, ID

## Data Files

The application uses these files:
- `hospitals.json` - Hospital data
- `doctors.json` - Doctor records
- `patients.json` - Patient information
- `appointments.json` - Appointment schedules
- `backup.bin` - Binary backup
- `user_actions.xml` - Activity log
- `app.log` - Application log

## Project Structure

```
src/main/java/
├── entity/          # Domain models (Doctor, Patient, Hospital, Appointment)
├── exception/       # Custom exceptions
├── util/            # Utility classes (DataManager, BackupManager, XmlLogger)
└── org/example/demo/ # JavaFX controllers and application

src/main/resources/
└── org/example/demo/ # FXML view files

src/test/java/       # Unit tests
```

## Code Quality

- All classes under 200 lines
- SonarQube compliant
- Comprehensive JavaDoc comments
- Unit test coverage
- No code smells or security vulnerabilities
