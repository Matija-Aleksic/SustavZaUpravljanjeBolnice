# Hospital Management Console App

A simple console-based Java application for managing basic hospital entities: hospitals, doctors, patients, and appointments. It showcases object-oriented concepts, modern Java features (records, sealed interfaces), generics (PECS, bounds), the Stream API, and basic error handling + logging with SLF4J / Logback.

## Technologies
- Java (POM currently targets version 25 – adjust to 21 or 17 if you need an LTS release)
- Maven (dependency management & compilation)
- Logback (SLF4J implementation) – configuration in `src/main/resources/logback.xml`
- Stream API, Optional, Collections utilities

## Project Structure
```
src/main/java/
  app/
    Main.java
  entity/
    Address.java          (record – simple address value object)
    Ageable.java          (interface exposing age)
    Appointment.java      (record – doctor + patient + date/time)
    ConditionStatus.java  (enum – patient condition states)
    Doctor.java           (final – extends Person, implements Payable)
    Employee.java         (example additional payable entity)
    Hospital.java         (holds doctor & patient lists – unmodifiable getters)
    Patient.java          (final + Builder pattern, implements Payable)
    Payable.java          (sealed interface – Doctor, Employee, Patient, Student)
    Person.java           (abstract base class)
    Student.java          (final – stipend entity implementing Payable)
  exception/
    EntityNotFoundException.java
    InvalidDateFormatException.java
    InvalidNumberInputException.java
    NegativeValueException.java
  resources/
    logback.xml           (logging configuration)
```

## Core Domain Classes
### Person (abstract)
Common fields: `id`, `firstName`, `lastName`, `dateOfBirth` + derived `getAge()`.

### Doctor
Adds: `specialization`, `baseSalary`. Pay is computed as base + age-based bonus.

### Patient
Built via Builder (`condition`, `insuranceNumber`). Examination cost depends on condition text (e.g. contains "operacija").

### Appointment (record)
Connects `Doctor`, `Patient`, and `LocalDateTime`. Includes helper `formattedDateTime(DateTimeFormatter)`.

### Hospital
Internally stores mutable lists; exposes them as unmodifiable views via getters.

### Payable (sealed interface)
Implemented by entities with monetary value or cost (`calculatePay()`): Doctor (salary), Patient (examination cost), Employee (salary), Student (scholarship).

## Generics & PECS Examples (in `Main`)
- `addAllDoctors(Collection<? super Doctor> dst, Collection<? extends Doctor> src)` – Producer Extends / Consumer Super.
- `addPatients(Collection<? super Patient> dst, Collection<? extends Patient> src)` – lower-bounded sink.
- `printAges(Collection<T extends Person & Ageable>)` – multiple bounds.
- `sumPay(Collection<? extends Payable>)` – wildcard aggregation.

## Stream API Usage
- Sort doctors by computed pay (descending).
- Group patients by condition (`groupingBy`).
- Partition patients (critical vs others).
- Reduce (sum total doctor pay).
- Map hospital to patient counts.

## Error Handling
Custom exceptions:
- `InvalidNumberInputException` – non-numeric menu or numeric input.
- `InvalidDateFormatException` – malformed date/time input.
- `EntityNotFoundException` – index out of range.
- `NegativeValueException` – negative monetary values.

Validation examples: positive IDs, non-null names, non-negative payments.

## Logging
SLF4J + Logback used for:
- INFO: startup, successful scheduling.
- DEBUG: entity creation, collection copying.
- WARN / ERROR: invalid input handling.
Configuration file: `src/main/resources/logback.xml`.

## Interactive Menu (Main)
1. Schedule appointment.
2. Find patient by full name (Optional demo).
3. List appointments by doctor (sorted by time).
4. Statistics (group / partition / reduce / mapping).
5. Immutable vs mutable collection demonstration.
6. Generics demonstration (PECS, multiple bounds, wildcards).
7. Exit.

## Example Input (abridged)
```
HospitalA
HospitalB
HospitalC
DoctorFirst
DoctorLast
1980-01-01
Cardiology
2500
...
PatientFirst
PatientLast
2000-01-01
STABLE
INS-001
...
```
## Author
Matija Aleksić — Faculty of Technical Sciences, 2025.
