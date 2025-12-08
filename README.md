# Hospital Management System

A comprehensive Java application for managing hospital operations including doctors, patients, appointments, and medical
records.

## Features

### Core Functionality

- **Hospital Management**: Manage multiple hospitals with their associated doctors and patients
- **Doctor Management**: Add, view, and manage doctor information including specializations and salaries
- **Patient Management**: Track patient information, conditions, and insurance details
- **Appointment Scheduling**: Schedule and manage appointments between doctors and patients

### Data Persistence

- **JSON Storage**: All data is automatically saved to and loaded from JSON files
    - `hospitals.json` - Hospital data
    - `doctors.json` - Doctor information
    - `patients.json` - Patient records
    - `appointments.json` - Appointment schedules

- **Binary Backup**: Create and restore complete system backups
    - Save all data to `backup.bin` for emergency recovery
    - Restore previous system states from backup files

- **XML Activity Logging**: All user actions are logged to XML format
    - Automatic logging of all system operations
    - View logs without XML tags for easy reading
    - Stored in `user_actions.xml`

### Advanced Features

- **Stream API**: Demonstrates Java Stream operations (map, filter, reduce, collect)
- **Generics**: PECS (Producer Extends Consumer Super) pattern implementation
- **Immutable Collections**: Safe data handling with unmodifiable collections
- **Builder Pattern**: Flexible patient object creation
- **Custom Exceptions**: Robust error handling with custom exception types
- **Logging**: SLF4J with Logback for application logging

### Statistics & Analytics

- Sort doctors by salary
- Group patients by medical condition
- Partition critical vs non-critical patients
- Calculate total payroll costs
- View patient distribution across hospitals

## Technology Stack

- **Java 25**: Latest Java features and APIs
- **Maven**: Dependency management and build automation
- **Gson**: JSON serialization/deserialization
- **SLF4J + Logback**: Logging framework
- **JUnit 5**: Unit testing framework
- **SonarQube**: Code quality analysis

## Usage

### Main Menu Options

1. **Schedule appointment** - Create a new appointment between a doctor and patient
2. **Search patient by name** - Find a patient using Optional pattern
3. **Show appointments by doctor** - View all appointments for a specific doctor (sorted by date)
4. **Statistics** - View various analytics and reports
5. **Demonstrate Stream immutable/mutable** - See collection handling examples
6. **Generics demo** - Demonstrate PECS and bounded types
7. **Add new doctor** - Register a new doctor in the system
8. **Add new patient** - Register a new patient in the system
9. **Create backup** - Save all data to binary backup file
10. **Restore from backup** - Restore data from previous backup
11. **View action logs** - Display all logged user actions
12. **Save all data to JSON** - Manually save all data to JSON files
0. **Exit** - Save and exit the application

### Data Files

The application creates and manages the following files:

- `hospitals.json` - Hospital data
- `doctors.json` - Doctor information
- `patients.json` - Patient records
- `appointments.json` - Scheduled appointments
- `backup.bin` - Binary backup file
- `user_actions.xml` - User activity log
- `app.log` - Application log file

## Design Patterns

### Builder Pattern

Used in `Patient` class for flexible object construction:

```java
Patient p = new Patient.Builder(1, "John", "Doe", LocalDate.of(1990, 1, 1))
        .condition("STABLE")
        .insuranceNumber("INS001")
        .build();
```

### PECS (Producer Extends Consumer Super)

Generic methods demonstrating type bounds:

```java
public static void addAllDoctors(Collection<? super Doctor> dst,
                                 Collection<? extends Doctor> src)
```

### Record Pattern

`Appointment` uses Java record for immutable data:

```java
public record Appointment(int id, Doctor doctor, Patient patient, LocalDateTime dateTime)
```

## Exception Handling

Custom exceptions provide clear error messages:

- `EntityNotFoundException` - Entity not found in collection
- `InvalidDateFormatException` - Date parsing errors
- `InvalidNumberInputException` - Invalid numeric input
- `NegativeValueException` - Negative values where not allowed

## Code Quality

The project is configured for SonarQube analysis with:

- No code smells
- Proper exception handling
- Magic number extraction
- Proper documentation
- Test coverage


