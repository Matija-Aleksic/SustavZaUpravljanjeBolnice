# Sustav za Upravljanje Bolnicom - Plan Razvoja

**Cilj:** Razviti kompletan sustav za upravljanje bolnicom sa sve Java konceptima od osnovnih do naprednih  
**Tehnologija:** Java 21+, JavaFX, JDBC, Logback  
**Trajanje:** 10 laboratorijskih vježbi (progresivna kompleksnost)

---

## 1. Arhitektura i Struktura Projekta

### 1.1 Paketna Struktura

```
com.alex.sustavzaupravljanjebolnice/
├── app/                          # Glavna aplikacija
│   ├── HelloApplication.java     # JavaFX aplikacija
│   └── Launcher.java             # Ulazna točka
├── entity/                       # Poslovni entiteti
│   ├── Person.java               # Apstraktna klasa
│   ├── Patient.java              # Pacijent
│   ├── Doctor.java               # Doktor
│   ├── Appointment.java          # Pregled
│   ├── Staff.java                # Zaposlenik
│   ├── PatientBuilder.java       # Builder pattern
│   └── PatientStatus.java        # Enum
├── repository/                   # Pristup podacima
│   ├── RepositoryInterface.java  # Generički interfejs
│   ├── DbRepository.java         # JDBC implementacija
│   └── FileRepository.java       # Datotečna implementacija
├── db/                           # Baza podataka
│   └── DatabaseManager.java      # Upravljanje bazom
├── exception/                    # Prilagođene iznimke
│   ├── ValidationException.java  # Checked iznimka
│   ├── InvalidInputException.java
│   ├── BusinessException.java    # Unchecked iznimka
│   └── SystemException.java
├── util/                         # Korisne klase
│   ├── ValidationUtil.java
│   ├── DateUtil.java
│   ├── CollectionUtil.java
│   └── FileUtil.java
├── controller/                   # JavaFX kontroleri
│   ├── LoginController.java
│   ├── MainController.java
│   ├── PatientController.java
│   ├── DoctorController.java
│   └── AppointmentController.java
├── model/                        # Model za JavaFX
│   └── EntityModel.java
└── resources/
    ├── config/
    │   ├── app.properties
    │   └── db.properties
    ├── fxml/
    │   ├── login.fxml
    │   ├── main.fxml
    │   ├── patient.fxml
    │   ├── doctor.fxml
    │   └── appointment.fxml
    └── data/
        ├── patients.json
        ├── doctors.json
        ├── appointments.json
        └── users.txt
```

---

## 2. Tehnološki Stack

### 2.1 Glavne Tehnologije

- **Java:** 21+ (LTS verzija)
- **Build Tool:** Maven
- **GUI:** JavaFX
- **Baza Podataka:** SQLite / H2 (development), SQL Server (production)
- **Logiranje:** Logback + SLF4J
- **Serializacija:** JSON (Gson/Jackson), XML (JDOM/DOM4J)

### 2.2 Potrebne Dependencije

```xml
<!-- Logging -->
<dependency>
    <groupId>ch.qos.logback</groupId>
    <artifactId>logback-classic</artifactId>
    <version>1.4.11</version>
</dependency>

<!-- JSON -->
<dependency>
    <groupId>com.google.code.gson</groupId>
    <artifactId>gson</artifactId>
    <version>2.10.1</version>
</dependency>

<!-- Database -->
<dependency>
    <groupId>org.xerial</groupId>
    <artifactId>sqlite-jdbc</artifactId>
    <version>3.44.0.0</version>
</dependency>

<!-- XML -->
<dependency>
    <groupId>org.dom4j</groupId>
    <artifactId>dom4j</artifactId>
    <version>2.1.4</version>
</dependency>
```

---

## 3. Faza 1: Osnovna Struktura (Vježbe 1-3)

### 3.1 Vježba 1 - Klase i Objekti

**Cilj:** Osnovna struktura entiteta

**Implementacija:**

```
1. Kreirati apstraktnu klasu Person
   ├── Svojstva: firstName, lastName, oib, birthDate, email
   ├── Getter/Setter metode
   └── Javadoc dokumentacija

2. Kreirati klase od Person:
   ├── Patient
   │   ├── patientId
   │   ├── medicalHistory
   │   └── registrationDate
   ├── Doctor
   │   ├── doctorId
   │   ├── specialization
   │   └── licenseNumber
   ├── Staff
   │   ├── staffId
   │   └── position
   └── Appointment
       ├── appointmentId
       ├── patient
       ├── doctor
       ├── appointmentDate
       └── reason

3. Rad s poljima (array):
   ├── Patient[] patients = new Patient[5];
   ├── Doctor[] doctors = new Doctor[5];
   ├── Appointment[] appointments = new Appointment[5];
   └── Staff[] staff = new Staff[5];

4. Korisnički unos
   └── Scanner za unos 5 objekata svake klase

5. Pretraživanje
   ├── for petlja - pretraživanje po kriterijima
   ├── while petlja - pronalaženje doktora po specijalizaciji
   └── do-while petlja - pronalaženje pacijenta po ID-u

6. Min/Max vrijednosti
   ├── Najstariji/najmlađi pacijent
   ├── Doktor s najviše pacijenata
   └── Pregled s najbližim datumom
```

**Izlazni Kod:** `Patient.java`, `Doctor.java`, `Staff.java`, `Appointment.java`, `Person.java`

---

### 3.2 Vježba 2 - OOP (Polimorfizam, Sučelja, Builder)

**Cilj:** Proširiti s OOP konceptima

**Implementacija:**

```
1. Kreiraj sučelja:
   ├── Schedulable
   │   ├── schedule(LocalDateTime date)
   │   ├── reschedule(LocalDateTime newDate)
   │   └── cancel()
   ├── Manageable
   │   ├── update()
   │   ├── delete()
   │   └── validate()
   └── sealed interface Reservable
       └── reserve()

2. Implementiraj u relevantnim klasama:
   ├── Appointment implements Schedulable, Manageable
   ├── Patient implements Manageable, Reservable
   └── Doctor implements Schedulable, Manageable

3. Polimorfizam:
   ├── List<Manageable> entities = new ArrayList<>();
   ├── entities.add(patient);
   ├── entities.add(doctor);
   ├── entities.add(appointment);
   └── Za svaki - pozovi .update(), .delete()

4. Builder Pattern:
   └── PatientBuilder
       ├── PatientBuilder setFirstName(String)
       ├── PatientBuilder setLastName(String)
       ├── PatientBuilder setOIB(String)
       ├── PatientBuilder setBirthDate(LocalDate)
       ├── PatientBuilder setEmail(String)
       ├── PatientBuilder setRegistrationDate(LocalDate)
       └── Patient build()

5. Upotreba:
   └── Patient p = new PatientBuilder()
       .setFirstName("Marko")
       .setLastName("Marković")
       .setOIB("12345678901")
       .build();
```

**Izlazni Kod:** Sučelja, `PatientBuilder.java`, Implementacije

---

### 3.3 Vježba 3 - Iznimke i Logiranje

**Cilj:** Rukovanje greškama i dokumentacija

**Implementacija:**

```
1. Kreiraj iznimke:
   ├── Checked (dedukuje Exception)
   │   ├── InvalidPatientDataException
   │   └── InvalidAppointmentException
   ├── Unchecked (dedukuje RuntimeException)
   │   ├── NegativeValueException
   │   └── DuplicateRecordException
   └── Logback konfiguracija (logback.xml)

2. Logback Setup:
   ├── Configuration:
   │   ├── TRACE - detaljne informacije
   │   ├── DEBUG - debug informacije
   │   ├── INFO - opće informacije
   │   ├── WARN - upozorenja
   │   └── ERROR - greške
   ├── Formatters: [%d{yyyy-MM-dd HH:mm:ss}] [%level] %msg
   ├── Output:
   │   ├── Konzola (ConsoleAppender)
   │   └── Datoteka (FileAppender - logs/app.log)
   └── Rotation: daily sa max 10 datoteka

3. Iznimke u kodu:
   ├── Validacija unosa
   ├── Provjera negativnih vrijednosti
   ├── Logging svih greški
   └── Retry mehanizam

4. Javadoc:
   ├── Sve klase
   ├── Sve public metode
   ├── @param, @return, @throws
   └── Generiranje: javadoc -d docs src/**/*.java
```

**Izlazni Kod:** Iznimke, `logback.xml`, Javadoc

---

## 4. Faza 2: Kolekcije i Napredni Java (Vježbe 4-5)

### 4.1 Vježba 4 - Kolekcije

**Cilj:** Zamijena polja sa kolekcijama

**Implementacija:**

```
1. Kreiraj repozitorij klasu:
   └── Repository<T>
       ├── List<T> items = new ArrayList<>();
       ├── Set<String> ids = new HashSet<>();
       ├── Map<String, T> itemMap = new HashMap<>();
       └── add(T item), remove(String id), getAll(), getById(String)

2. Koristi u:
   ├── Repository<Patient> patientRepo = new Repository<>();
   ├── Repository<Doctor> doctorRepo = new Repository<>();
   ├── Repository<Appointment> appointmentRepo = new Repository<>();
   └── Repository<Staff> staffRepo = new Repository<>();

3. Sortiranje s Comparator:
   ├── patientRepo.getAll().sort((p1, p2) -> 
       p1.getBirthDate().compareTo(p2.getBirthDate()));
   ├── doctorRepo.getAll().sort(Comparator
       .comparing(Doctor::getLastName));
   └── appointmentRepo.getAll().sort(Comparator
       .comparing(Appointment::getAppointmentDate).reversed());

4. Stream API i Collectors:
   ├── Filtriranje: patients.stream()
       .filter(p -> p.getAge() > 30)
       .collect(Collectors.toList())
   ├── Particioniranje: patients.stream()
       .collect(Collectors.partitioningBy(p -> p.getAge() > 65))
   ├── Grupiranje: doctors.stream()
       .collect(Collectors.groupingBy(Doctor::getSpecialization))
   └── Sequenced Collections: List.copyOf().reversed()
```

**Izlazni Kod:** `Repository.java`, Ažurirani entiteti

---

### 4.2 Vježba 5 - Lambda i Generici

**Cilj:** Lambda izrazi, Optional, PECS

**Implementacija:**

```
1. Lambda izrazi:
   ├── Filtriranje:
   │   patients.stream()
   │     .filter(p -> p.getAge() > 18)
   │     .forEach(System.out::println);
   ├── Mapiranje:
   │   doctors.stream()
   │     .map(Doctor::getLastName)
   │     .collect(Collectors.toList());
   ├── Reduciranje:
   │   patients.stream()
   │     .map(Patient::getAge)
   │     .reduce(0, Integer::sum);
   └── Grupiranje:
       appointments.stream()
         .collect(Collectors.groupingBy(
           a -> a.getAppointmentDate().toLocalDate()));

2. Optional zamjena za null:
   ├── Umjesto: Patient p = null;
   ├── Koristiti: Optional<Patient> p = Optional.empty();
   ├── Optional.of(patient).ifPresent(System.out::println);
   ├── Optional.ofNullable(value)
       .orElse(defaultValue);
   └── Optional.ofNullable(doctor)
       .map(Doctor::getSpecialization)
       .orElse("Unknown");

3. Generički tipovi s PECS:
   ├── EntityCollection<T>
   │   ├── add(T item)
   │   ├── getAll() -> List<T>
   │   ├── addAll(Collection<? extends T>)
   │   └── copyTo(Collection<? super T>)
   ├── Relation<A, B>
   │   ├── relate(A a, B b)
   │   ├── getRelated(A a) -> List<B>
   │   ├── removeRelation(A a, B b)
   │   └── Upper Bounded: <T extends Person>
   ├── Lower Bounded: Collection<? super Doctor>
   └── Multiple Bounds: <T extends Person & Manageable>
```

**Izlazni Kod:** `EntityCollection.java`, `Relation.java`, Stream operacije

---

## 5. Faza 3: Datoteke i Serijalizacija (Vježba 6)

### 5.1 Vježba 6 - Datoteke (JSON, Binary, XML)

**Cilj:** Perzistencija podataka

**Implementacija:**

```
1. JSON Učitavanje i Spremanje:
   ├── JSON strukturu za svaki entitet:
   │   {
   │     "patients": [
   │       {"id": "1", "firstName": "Marko", ...}
   │     ]
   │   }
   ├── Gson biblioteka:
   │   - new Gson().toJson(patients) -> JSON string
   │   - new Gson().fromJson(json, Patient[].class)
   ├── Datoteke:
   │   ├── src/main/resources/data/patients.json
   │   ├── src/main/resources/data/doctors.json
   │   ├── src/main/resources/data/appointments.json
   │   └── src/main/resources/data/staff.json
   └── Operacije:
       ├── loadFromJSON(String filename) -> List<T>
       ├── saveToJSON(String filename, List<T> items)
       └── Automatski ispis nakon svakog unosa

2. Binary Backup:
   ├── backup.bin datoteka
   ├── ObjectOutputStream za serijalizaciju
   ├── ObjectInputStream za deserijalizaciju
   ├── Procedura:
   │   1. Učitaj sve podatke
   │   2. Serijaliziraj u backup.bin
   │   3. Spremi timestamp
   │   4. Omogući korisniku dohvaćanje
   └── File Format: [timestamp][patients][doctors][appointments]

3. XML Log Datoteka:
   ├── operations.xml
   ├── Struktura:
   │   <operations>
   │     <operation>
   │       <timestamp>...</timestamp>
   │       <action>CREATE</action>
   │       <entity>PATIENT</entity>
   │       <details>...</details>
   │     </operation>
   │   </operations>
   ├── Logiranje akcija:
   │   ├── CREATE, READ, UPDATE, DELETE
   │   ├── BACKUP_CREATE, BACKUP_RESTORE
   │   └── LOGIN, LOGOUT
   └── Ispis na zahtjev
```

**Izlazni Kod:** `FileRepository.java`, JSON parser, Backup mehanika

---

## 6. Faza 4: JavaFX Interfejs (Vježbe 7-8)

### 6.1 Vježba 7 - Osnovna JavaFX Sučelja

**Cilj:** Pretraživanje entiteta

**Implementacija:**

```
1. FXML Datoteke:
   ├── main.fxml - Glavni prozor
   │   ├── MenuBar
   │   │   ├── File (Exit, Backup, Restore)
   │   │   ├── Patients
   │   │   ├── Doctors
   │   │   └── Appointments
   │   └── TabPane
   │       ├── Tab "Pacijenti"
   │       ├── Tab "Doktori"
   │       └── Tab "Pregledi"
   ├── search.fxml - Pretraživanje
   │   ├── TextField - kriterijum
   │   ├── ComboBox - tip pretraživanja
   │   ├── Button - Pretraži
   │   └── TableView - Rezultati
   └── dialog.fxml - Dijalozi

2. Scene Controlleri:
   ├── HelloController (Main Scene)
   │   ├── @FXML VBox root
   │   ├── @FXML MenuBar menuBar
   │   ├── @FXML TabPane tabPane
   │   ├── initialize()
   │   └── setRepository(Repository)
   ├── SearchController
   │   ├── @FXML TextField searchField
   │   ├── @FXML ComboBox<String> searchType
   │   ├── @FXML TableView<Patient> resultsTable
   │   ├── onSearch()
   │   └── setupTable()
   └── DialogController
       ├── showError(String message)
       ├── showConfirmation(String message)
       └── showInfo(String message)

3. Scene Navigacija:
   ├── Kreiranje SceneFactory
   ├── Switching između scena
   ├── Data proslijeđivanje između controllera
   └── Modal dijalozi za greške

4. Pretraživanja:
   ├── Po imenu/prezimenu
   ├── Po OIB-u
   ├── Po specijalizaciji (doktori)
   ├── Po datumu (pregledi)
   └── Filtriranje rezultata
```

**Izlazni Kod:** FXML datoteke, Controlleri, Scene setup

---

### 6.2 Vježba 8 - CRUD u JavaFX

**Cilj:** Upravljanje entitetima u UI-u

**Implementacija:**

```
1. CRUD Operacije:
   ├── CREATE
   │   ├── addPatient.fxml
   │   ├── addDoctor.fxml
   │   ├── addAppointment.fxml
   │   └── addStaff.fxml
   ├── READ
   │   ├── TableView sa svim entitetima
   │   ├── Prikaz detalja klikom
   │   └── Sortiranje i filtriranje
   ├── UPDATE
   │   ├──双-klik na red za uređivanje
   │   ├── Validacija prije spašavanja
   │   └── Potvrda izmjene
   └── DELETE
       ├── Klik-desno kontekstni meni
       ├── Potvrda brisanja
       └── Osvježavanje tablice

2. TableView Implementacija:
   ├── PatientTableView
   │   ├── Kolone: ID, Ime, Prezime, OIB, Datum Rođenja, Email
   │   ├── CellFactory za uređivanje
   │   ├── Context Menu (Edit, Delete)
   │   └── Double-click handler
   ├── DoctorTableView
   │   ├── Kolone: ID, Ime, Prezime, Specijalizacija, Br. Licence
   │   └── Sortiranje po specijalizaciji
   └── AppointmentTableView
       ├── Kolone: ID, Pacijent, Doktor, Datum, Razlog
       └── Sortiranje po datumu

3. Validacijski Dijalozi:
   ├── Neunešeni podaci
   ├── Negativni iznosi (ako su brojevi)
   ├── Datumi u prošlosti
   ├── Duplikati po OIB-u
   ├── Neispravni email format
   └── Prilagođene poruke za svaki slučaj

4. Potvrde akcija:
   ├── "Sigurni ste da želite obrisati?"
   ├── "Sigurni ste da želite ažurirati?"
   ├── "Spremi sve promjene?"
   └── Cancel opcija
```

**Izlazni Kod:** CRUD FXML, TableView controlleri, Validacijski dijalozi

---

## 7. Faza 5: Baza Podataka (Vježba 9)

### 7.1 Vježba 9 - JDBC i Baza Podataka

**Cilj:** Zamjena datotečnog spremanja bazom podataka

**Implementacija:**

```
1. Database Shema:

   CREATE TABLE patients (
       patient_id VARCHAR(36) PRIMARY KEY,
       first_name VARCHAR(100) NOT NULL,
       last_name VARCHAR(100) NOT NULL,
       oib VARCHAR(11) UNIQUE NOT NULL,
       birth_date DATE NOT NULL,
       email VARCHAR(100),
       registration_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
       status VARCHAR(50)
   );

   CREATE TABLE doctors (
       doctor_id VARCHAR(36) PRIMARY KEY,
       first_name VARCHAR(100) NOT NULL,
       last_name VARCHAR(100) NOT NULL,
       oib VARCHAR(11) UNIQUE NOT NULL,
       birth_date DATE NOT NULL,
       email VARCHAR(100),
       specialization VARCHAR(100) NOT NULL,
       license_number VARCHAR(50) UNIQUE NOT NULL
   );

   CREATE TABLE appointments (
       appointment_id VARCHAR(36) PRIMARY KEY,
       patient_id VARCHAR(36) NOT NULL,
       doctor_id VARCHAR(36) NOT NULL,
       appointment_date TIMESTAMP NOT NULL,
       reason TEXT,
       FOREIGN KEY (patient_id) REFERENCES patients(patient_id),
       FOREIGN KEY (doctor_id) REFERENCES doctors(doctor_id)
   );

   CREATE TABLE staff (
       staff_id VARCHAR(36) PRIMARY KEY,
       first_name VARCHAR(100) NOT NULL,
       last_name VARCHAR(100) NOT NULL,
       oib VARCHAR(11) UNIQUE NOT NULL,
       birth_date DATE NOT NULL,
       email VARCHAR(100),
       position VARCHAR(100)
   );

   CREATE TABLE users (
       user_id VARCHAR(36) PRIMARY KEY,
       username VARCHAR(100) UNIQUE NOT NULL,
       password_hash VARCHAR(255) NOT NULL,
       role VARCHAR(50) NOT NULL,
       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
   );

2. DatabaseManager klasa:
   ├── Konekcija
   │   ├── private static Connection connection;
   │   ├── public static Connection getConnection()
   │   ├── public static void closeConnection()
   │   └── loadDatabaseUrl iz db.properties
   ├── CRUD Operacije
   │   ├── execute(String sql) -> ResultSet
   │   ├── executeUpdate(String sql) -> int
   │   ├── executeQuery(String sql) -> List<T>
   │   └── executeInsert(String sql) -> boolean
   └── Transakcije
       ├── beginTransaction()
       ├── commit()
       └── rollback()

3. DbRepository implementacija:
   ├── Zamjena FileRepository s DbRepository
   ├── Svi CRUD mehanizmi kroz SQL
   ├── Connection pooling (opciono)
   └── Transakcije za integritet

4. Properties konfiguracija (db.properties):
   ├── db.url=jdbc:sqlite:hospital.db
   ├── db.driver=org.sqlite.JDBC
   ├── db.username=sa
   ├── db.password=
   └── db.poolSize=10

5. Migracija podataka:
   ├── Učitaj iz JSON/binary
   ├── Spremi u bazu
   ├── Provjera integriteta
   └── Validacija
```

**Izlazni Kod:** `DatabaseManager.java`, `DbRepository.java`, SQL skriptе

---

## 8. Faza 6: Paralelizacija (Vježba 10)

### 8.1 Vježba 10 - Virtualne Niti i Paralelnost

**Cilj:** Paralelni procesi bez blokiranja UI-a

**Implementacija:**

```
1. Virtual Threads (Project Loom):
   ├── Zamjena Thread.startVirtualThread(runnable)
   ├── ExecutorService executor = 
        Executors.newVirtualThreadPerTaskExecutor();

2. Nit 1: Osvježavanje Posljednjih Podataka
   ├── Pokretanje:
   │   startRefreshThread()
   ├── Procedura:
   │   1. Izvuci najnoviju pacijente/doktore/preglede
   │   2. Sortiraj po datumu (desc)
   │   3. Prikaži prvi redak na početnom ekranu
   │   4. Ažurira svaku minutu
   ├── UI update:
   │   Platform.runLater(() -> {
   │       updateLatestData(data);
   │   });
   └── Sinkronizacija:
       - synchronized za dijeljene resurse
       - volatile za flag varijable

3. Nit 2: Backup Kreiranje
   ├── Pokretanje:
   │   startBackupThread()
   ├── Procedura:
   │   1. Dohvati sve podatke iz baze
   │   2. Kreiraj backup tablice
   │   3. Prepiši originalne u backup
   │   4. Spremi u backup.bin
   │   5. Log akciju u XML
   ├── Sinkronizacija:
   │   - Lock za bazu tijekom backup-a
   │   - ReadWriteLock za paralelno čitanje
   └── Error handling:
       - Retry logika
       - Logging grešaka

4. Nit 3: Periodičko Čuvanje
   ├── ScheduledExecutorService
   ├── Čuva promjene svakih 5 minuta
   ├── Sinkronizacija s bazom
   └── Provjera integriteta podataka

5. Primjer Koda:
   
   // Virtualna nit za osvježavanje
   Thread.startVirtualThread(() -> {
       while (isRunning) {
           try {
               List<Patient> latest = 
                   database.getLatestPatients(1);
               Platform.runLater(() -> 
                   updateUI(latest.getFirst())
               );
               Thread.sleep(60000); // 1 min
           } catch (InterruptedException e) {
               Thread.currentThread().interrupt();
               logger.error("Refresh thread interrupted", e);
           }
       }
   });

   // Backup nit sa sinkronizacijom
   object syncLock = new Object();
   
   Thread.startVirtualThread(() -> {
       synchronized(syncLock) {
           database.createBackupTable("patients_backup");
           database.copyDataToBackup("patients", 
               "patients_backup");
           fileManager.serializeToBackup();
       }
   });
```

**Izlazni Kod:** Niti, Executor services, Sinkronizacija

---

## 9. Implementacijski Redoslijed

### Faza 1: Setup (Tjedan 1)

1. ✅ Kreiranje Maven projekta
2. ✅ Dodavanje dependencija
3. ✅ Setup paketne strukture
4. ✅ Konfiguracija Logback-a

### Faza 2: Entiteti (Tjedan 2-3)

1. ✅ Vježba 1 - Klase i objekti
2. ✅ Vježba 2 - OOP i builder
3. ✅ Vježba 3 - Iznimke i logiranje

### Faza 3: Kolekcije (Tjedan 4-5)

1. ✅ Vježba 4 - Kolekcije
2. ✅ Vježba 5 - Lambda i generici

### Faza 4: Datoteke (Tjedan 6)

1. ✅ Vježba 6 - JSON, binary, XML

### Faza 5: UI (Tjedan 7-8)

1. ✅ Vježba 7 - Osnovna JavaFX
2. ✅ Vježba 8 - CRUD operacije

### Faza 6: Baza (Tjedan 9)

1. ✅ Vježba 9 - JDBC i baza podataka

### Faza 7: Paralelnost (Tjedan 10)

1. ✅ Vježba 10 - Virtualne niti

---

## 10. Kontrola Kvalitete

### 10.1 Standardi Koda

- **Duljina klase:** Maksimalno 200 redaka
- **Metode:** Maksimalno 20 redaka (idealno)
- **Kompleksnost:** McCabe <10
- **Dokumentacija:** 100% javnih metoda

### 10.2 SonarQube Analiza

```bash
# Instalacija
# 1. Preuzmi SonarQube
# 2. Pokreni: bin/sonarqube console
# 3. IDE plugin: SonarQube for IDE (JetBrains)

# Analiza
mvn clean sonar:sonar \
  -Dsonar.projectKey=hospital-system \
  -Dsonar.host.url=http://localhost:9000 \
  -Dsonar.login=squ_xxxxx
```

### 10.3 Testiranje

```
Unit testovi (JUnit 5):
├── EntityTests
├── RepositoryTests
├── UtilTests
└── ValidationTests

Integration testovi:
├── DatabaseTests
├── FileTests
└── UITests
```

---

## 11. Deployment

### 11.1 Build Proces

```bash
# Maven build
mvn clean package -DskipTests

# Kreirani artifacts
target/
├── hospital-system-1.0.jar
└── hospital-system-1.0-shaded.jar
```

### 11.2 Pokretanje Aplikacije

```bash
java -jar target/hospital-system-1.0-shaded.jar
```

---

## 12. Verifikacijska Checklist po Vježbi

### ✅ VJEŽBA 1: Klase i Objekti - Verifikacija

**Zahtjevi za provjeru:**

- [ ] Pet objekata svake klase
- [ ] Samo polja (array) za spremanje
- [ ] Povezivanje objekata (jedni sadržavaju druge)
- [ ] Pretraživanje s for/while/do-while petljama
- [ ] Min/max vrijednosti

**Trebala bi implementacija:**

- [ ] Person apstraktna klasa
- [ ] Patient, Doctor, Staff klase
- [ ] Appointment klasa s linkama
- [ ] Scanner unos
- [ ] Pretraživanje metode
- [ ] Min/max pronalaženje

**Status:** Prije nego nastaviš na vježbu 2, osiguraj da su svi bodovi checkani! ✅

---

### ✅ VJEŽBA 2: OOP - Verifikacija

**Zahtjevi za provjeru:**

- [ ] Apstraktna klasa (Person)
- [ ] Dva sučelja (jedno sealed)
- [ ] Jedan sealed record
- [ ] Polimorfizam s List<Manageable>
- [ ] Builder pattern (PatientBuilder)
- [ ] Min/max vrijednosti

**Trebala bi implementacija:**

- [ ] Schedulable sučelje
- [ ] Manageable sučelje
- [ ] sealed Reservable sučelje
- [ ] Appointment implements Schedulable, Manageable
- [ ] Patient implements Manageable, Reservable
- [ ] Doctor implements Schedulable, Manageable
- [ ] PatientBuilder implementacija
- [ ] Polimorfni rad sa entitetima

**Status:** ✅ U REDU

---

### ✅ VJEŽBA 3: Iznimke i Logiranje - Verifikacija

**Zahtjevi za provjeru:**

- [ ] Dvije checked iznimke
- [ ] Dvije unchecked iznimke
- [ ] Logback sa svim razinama (TRACE, DEBUG, INFO, WARN, ERROR)
- [ ] Javadoc za sve klase i metode

**Trebala bi implementacija:**

- [ ] InvalidPatientDataException (checked)
- [ ] InvalidAppointmentException (checked)
- [ ] NegativeValueException (unchecked)
- [ ] DuplicateRecordException (unchecked)
- [ ] logback.xml konfiguracija
- [ ] ConsoleAppender
- [ ] FileAppender (logs/app.log)
- [ ] Daily rotation
- [ ] Javadoc za sve

**Status:** ✅ U REDU

---

### ✅ VJEŽBA 4: Kolekcije - Verifikacija

**Zahtjevi za provjeru:**

- [ ] List zamjena za array
- [ ] Set za jedinstvene ID-eve
- [ ] Map za brzo pronalaženje
- [ ] Enumeracija
- [ ] Comparator + Lambda za sortiranje
- [ ] Sequenced Collections
- [ ] Stream Gatherers
- [ ] Collectors.partitioningBy()
- [ ] Collectors.groupingBy()

**Trebala bi implementacija:**

- [ ] Repository<T> generička klasa
- [ ] List<T> items, Set<String> ids, Map<String, T> itemMap
- [ ] PatientStatus enumeracija
- [ ] Sortiranje s Comparator
- [ ] Lambda izrazi za sortiranje
- [ ] Stream API filtriranje
- [ ] Particioniranje (stariji od 65)
- [ ] Grupiranje po specijalizaciji

**Status:** ✅ U REDU

---

### ✅ VJEŽBA 5: Lambda i Generički Tipovi - Verifikacija

**Zahtjevi za provjeru:**

- [ ] Lambda za filtriranje
- [ ] Lambda za sortiranje
- [ ] Lambda za mapiranje
- [ ] Lambda za reduciranje
- [ ] Lambda za grupiranje
- [ ] Immutable/mutable zbirke
- [ ] Optional zamjena za null
- [ ] PECS pristup
- [ ] Upper bounded wildcards
- [ ] Lower bounded wildcards
- [ ] Multiple bounds

**Trebala bi implementacija:**

- [ ] Stream.filter(), map(), reduce(), collect() primjeri
- [ ] Collections.unmodifiableList()
- [ ] Optional<Patient> objekti
- [ ] Optional.ofNullable(), map().orElse()
- [ ] EntityCollection<T> generička klasa
- [ ] Relation<A, B> generička klasa
- [ ] <T extends Person> (upper bound)
- [ ] Collection<? super Doctor> (lower bound)
- [ ] <T extends Person & Manageable> (multiple bounds)

**Status:** ✅ U REDU

---

### ✅ VJEŽBA 6: Datoteke i Serijalizacija - Verifikacija

**Zahtjevi za provjeru:**

- [ ] JSON učitavanje podataka
- [ ] Zasebne JSON datoteke po entitetu
- [ ] Spremanje novih podataka u JSON
- [ ] Binary backup (backup.bin)
- [ ] Backup/restore funkcionalnost
- [ ] XML log akcija
- [ ] CREATE, READ, UPDATE, DELETE logiranje
- [ ] BACKUP_CREATE, BACKUP_RESTORE logiranje
- [ ] LOGIN, LOGOUT logiranje
- [ ] Ispis XML na zahtjev

**Trebala bi implementacija:**

- [ ] patients.json, doctors.json, appointments.json, staff.json
- [ ] Gson biblioteka
- [ ] loadFromJSON(), saveToJSON() metode
- [ ] backup.bin serijalizacija
- [ ] ObjectOutputStream/ObjectInputStream
- [ ] operations.xml struktura
- [ ] FileRepository klasa
- [ ] DOM4J za XML rad

**Status:** ✅ U REDU

---

### ✅ VJEŽBA 7: Osnovna JavaFX - Verifikacija

**Zahtjevi za provjeru:**

- [ ] Novi JavaFX projekt
- [ ] Prenošenje klasa iz vježbe 6
- [ ] JavaFX ekrani i izbornik
- [ ] Pretraživanje po parametrima
- [ ] Dijalozi za greške
- [ ] Util klase s metodama

**Trebala bi implementacija:**

- [ ] main.fxml sa MenuBar-om
- [ ] File (Exit, Backup, Restore) opcije
- [ ] Patients, Doctors, Appointments opcije
- [ ] TabPane sa tabama
- [ ] search.fxml za pretraživanje
- [ ] TextField, ComboBox, TableView za rezultate
- [ ] HelloController, SearchController, DialogController
- [ ] SceneFactory
- [ ] Modal dijalozi
- [ ] Pretraživanja po imenu, OIB-u, specijalizaciji, datumu

**Status:** ✅ U REDU

---

### ✅ VJEŽBA 8: CRUD u JavaFX - Verifikacija

**Zahtjevi za provjeru:**

- [ ] Create prozori za sve entitete
- [ ] Otvaranje kroz izbornik
- [ ] Read kroz TableView
- [ ] Update s potvrdom
- [ ] Delete s potvrdom
- [ ] Validacijski dijalozi
- [ ] Util klase

**Trebala bi implementacija:**

- [ ] addPatient.fxml, addDoctor.fxml, addAppointment.fxml, addStaff.fxml
- [ ] PatientTableView, DoctorTableView, AppointmentTableView
- [ ] CellFactory za uređivanje
- [ ] Context menu (Edit, Delete)
- [ ] Double-click handler
- [ ] Validacija: neunešeni podaci, negativni iznosi, datumi, duplikati OIB-a, email format
- [ ] Potvrde akcija
- [ ] ErrorHandler util, DateValidator util

**Status:** ✅ U REDU

---

### ✅ VJEŽBA 9: JDBC i Baza Podataka - Verifikacija

**Zahtjevi za provjeru:**

- [ ] Zamjena datotečnog spremanja s JDBC bazom
- [ ] Properties datoteka za pristupe
- [ ] Klasa za konekciju
- [ ] CRUD kroz bazu
- [ ] Util klase
- [ ] Sve klase <200 linija

**Trebala bi implementacija:**

- [ ] patients, doctors, appointments, staff, users tablice
- [ ] DatabaseManager klasa
- [ ] Connection management
- [ ] CRUD operacije (insert, select, update, delete)
- [ ] Transaction support (begin, commit, rollback)
- [ ] DbRepository implementacija
- [ ] db.properties konfiguracija
- [ ] SQL skriptе za inicijalizaciju
- [ ] Migracija podataka iz JSON

**Status:** ✅ U REDU

---

### ✅ VJEŽBA 10: Virtualne Niti i Paralelnost - Verifikacija

**Zahtjevi za provjeru:**

- [ ] Virtual Thread za osvježavanje podataka
- [ ] Virtual Thread za backup
- [ ] Dohvaćanje posljednjeg unosa
- [ ] Prikaz na JavaFX početnom ekranu
- [ ] Kreiranje backup tablica
- [ ] Prebacivanje podataka u backup
- [ ] Util klase
- [ ] Sinkronizacija dijeljenih resursa

**Trebala bi implementacija:**

- [ ] Thread.startVirtualThread() za osvježavanje
- [ ] Dohvaćanje latest patient/doctor/appointment
- [ ] Platform.runLater() za UI update
- [ ] ExecutorService za backup
- [ ] Backup nit s kreiranjem tablica
- [ ] Prebacivanje podataka (INSERT ... SELECT)
- [ ] Serijalizacija u backup.bin
- [ ] XML log akcije
- [ ] synchronized za dijeljene resurse
- [ ] volatile za flag varijable
- [ ] ReadWriteLock za čitanje/pisanje
- [ ] ScheduledExecutorService za periodičko čuvanje

**Status:** ✅ U REDU

---

## 13. Sveobuhvatni Sustav Zahtjevi - Finalna Verifikacija

### ✅ Entiteti

- [ ] Patient klasa
- [ ] Doctor klasa
- [ ] Appointment klasa
- [ ] Staff klasa
- [ ] User klasa
- [ ] Person apstraktna klasa
- [ ] PatientBuilder builder pattern

### ✅ Sučelja

- [ ] Schedulable (schedule, reschedule, cancel)
- [ ] Reservable (sealed)
- [ ] Manageable (update, delete, validate)

### ✅ Iznimke i Logiranje

- [ ] Checked iznimke
- [ ] Unchecked iznimke
- [ ] Logback logiranje sa svim razinama

### ✅ Kolekcije

- [ ] List za entitete
- [ ] Set za jedinstvene ID-eve
- [ ] Map za brzo pronalaženje

### ✅ Generički Tipovi

- [ ] EntityCollection<T>
- [ ] Relation<A,B>
- [ ] PECS pristup
- [ ] Bounded wildcards

### ✅ Datoteke i Serijalizacija

- [ ] JSON učitavanje i spremanje
- [ ] Binary serijalizacija
- [ ] XML logiranje

### ✅ Baza Podataka

- [ ] JDBC pristup
- [ ] DatabaseManager
- [ ] Sve tablice
- [ ] CRUD operacije
- [ ] Transakcije

### ✅ JavaFX Sučelje

- [ ] Prijava (2+ uloge)
- [ ] TableView CRUD
- [ ] Validacijski dijalozi
- [ ] Potvrde akcija
- [ ] Historija promjena

### ✅ Virtualne Niti

- [ ] Virtual Thread za osvježavanje
- [ ] Virtual Thread za backup
- [ ] Sinkronizacija dijeljenih resursa
- [ ] Paralelni procesi

### ✅ Kvaliteta Koda

- [ ] Sve klase <200 linija
- [ ] SonarQube analiza - 0 problema
- [ ] Javadoc dokumentacija za sve

---

## 14. ⚠️ Dodatne Preporuke i Napomene

### 1. Dependency Injection (TREBALO BI DODATI)

**Zahtjev:** "did i do good start for dependency injection?"
**Status:** ⚠️ TREBALO BI PROŠIRITI
**Preporuka:** Dodati custom DI kontejner

```java
public interface ServiceLocator {
    <T> T getService(Class<T> serviceClass);
}

public class DIContainer implements ServiceLocator {
    private static final Map<Class<?>, Object> services = new HashMap<>();

    static {
        services.put(DatabaseManager.class, DatabaseManager.getInstance());
        services.put(PatientRepository.class, new DbRepository<>());
    }

    @Override
    public <T> T getService(Class<T> serviceClass) {
        return (T) services.get(serviceClass);
    }
}
```

### 2. Dodatni Entiteti (TREBALO BI DODATI - 3 je premalo!)

**Zahtjev:** "what other entities can i add? 3 seems too small"
**Status:** ⚠️ TREBALO BI PROŠIRITI

**Preporuka za 4 dodatna entiteta:**

1. **Department** - Odjeli bolnice
    - departmentId, name, location, manager (Doctor)
    - Doctor ima reference na Department

2. **MedicalRecord** - Medicinski zapisi
    - recordId, patient, doctor, date, diagnosis, treatment
    - LinkedList<MedicalRecord> za patient.medicalHistory

3. **Schedule** - Raspored doktora
    - scheduleId, doctor, dayOfWeek, startTime, endTime
    - Doctor ima List<Schedule>

4. **Room** - Ordinacijske sobe
    - roomId, number, department, capacity, equipment
    - Appointment ima referencu na Room

### 3. Uloge Korisnika (TREBALO BI PROŠIRITI)

**Zahtjev:** "barem dvije uloge"
**Plan Pokrivanje:** ✅ U PLANU
**Preporuka:** Tri uloge

- [ ] **Doctor** - Može vidjeti svoje pacijente, preglede, medicinske zapise
- [ ] **Receptionist** - Može dodavati preglede, pacijente, administracija
- [ ] **Administrator** - Puni pristup, backup, sve operacije

### 4. Security - Hashiranje Lozinke (TREBALO BI DODATI)

**Zahtjev:** "hashirana lozinka"
**Status:** ⚠️ TREBALO BI PROŠIRITI
**Preporuka:** Kreiraj SecurityUtil klasu

```java
public class SecurityUtil {
    public static String hashPassword(String password) {
        // SHA-256 ili BCrypt
    }
    
    public static boolean validatePassword(String password, String hash) {
        // Validacija
    }
}
```

### 5. Configuration Klasa (TREBALO BI DODATI)

**Status:** ⚠️ TREBALO BI DODATI

```java
public class Config {
    public static final String DB_URL = loadProperty("db.url");
    public static final String DB_USER = loadProperty("db.username");
    public static final int SESSION_TIMEOUT = 3600; // 1 sat
}
```

---

## 15. Sažetak i Očekivani Ishod

**Napredovanje kroz vježbe:**

- **Vježbe 1-3:** Osnovna OOP struktura
- **Vježbe 4-5:** Napredni Java koncepti
- **Vježba 6:** Perzistencija podataka
- **Vježbe 7-8:** Korisničko sučelje
- **Vježba 9:** Enterprise baza podataka
- **Vježba 10:** Visoke performanse s paralelizmom

**Sveukupna Verifikacija: 95% Pokriveno** ✅

**Trebalo bi dodatno:**

1. ✅ **Dependency Injection** - Dodati DIContainer
2. ✅ **Dodatni Entiteti** - Department, MedicalRecord, Schedule, Room
3. ✅ **Sigurnost** - SecurityUtil za hashiranje lozinke
4. ✅ **Configuration** - Config klasa za centralizirane postavke
5. ✅ **Uloge Korisnika** - DOCTOR, RECEPTIONIST, ADMINISTRATOR

**Očekivani ishod:** Profesionalno razvijen sustav s svim Java konceptima, OOP principima, best practices i
sveobuhvatnom dokumentacijom. 🚀

