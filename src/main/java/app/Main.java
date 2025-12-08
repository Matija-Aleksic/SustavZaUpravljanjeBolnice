package app;

import entity.*;
import exception.EntityNotFoundException;
import exception.InvalidDateFormatException;
import exception.InvalidNumberInputException;
import exception.NegativeValueException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.BackupManager;
import util.DataManager;
import util.XmlLogger;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;


public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        List<Hospital> hospitals = new ArrayList<>();
        Set<Doctor> doctors = new LinkedHashSet<>();
        List<Patient> patients = new ArrayList<>();
        List<Appointment> appointments = new ArrayList<>();

        logger.info("Application started...");
        XmlLogger.logAction("APPLICATION_START", "Hospital Management System started");

        // Try to load existing data from JSON files
        System.out.println("Loading data from JSON files...");
        DataManager.AllData loadedData = DataManager.loadAllData();

        if (!loadedData.hospitals().isEmpty() || !loadedData.doctors().isEmpty()) {
            hospitals.addAll(loadedData.hospitals());
            doctors.addAll(loadedData.doctors());
            patients.addAll(loadedData.patients());
            appointments.addAll(loadedData.appointments());

            System.out.println("Data loaded successfully!");
            System.out.printf("Hospitals: %d, Doctors: %d, Patients: %d, Appointments: %d%n",
                    hospitals.size(), doctors.size(), patients.size(), appointments.size());
            XmlLogger.logAction("DATA_LOAD", "Data loaded from JSON files");
        } else {
            System.out.println("No existing data found. Starting with sample data...");
            initializeSampleData(input, hospitals, doctors, patients);
            DataManager.saveAllData(hospitals, new ArrayList<>(doctors), patients, appointments);
            XmlLogger.logAction("INITIAL_DATA", "Sample data initialized");
        }

        List<Hospital> hospitalsReadOnly = List.copyOf(hospitals);
        List<Doctor> doctorListImmutable = List.copyOf(doctors);
        List<Patient> patientListImmutable = List.copyOf(patients);

        List<Doctor> mutableDoctorSink = new ArrayList<>();
        addAllDoctors(mutableDoctorSink, doctorListImmutable);
        logger.debug("Doctors copied to mutable sink (PECS example): {}", mutableDoctorSink.size());

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        menuLoop:
        while (true) {
            displayMainMenu();
            System.out.print("Choose option: ");

            int choice;
            try {
                choice = readInt(input);
            } catch (InvalidNumberInputException e) {
                logger.warn("Invalid menu option input: {}", e.getMessage());
                System.out.println(e.getMessage());
                XmlLogger.logAction("INVALID_INPUT", "Invalid menu selection");
                continue;
            }

            switch (choice) {
                case 1 -> scheduleAppointment(input, doctors, patients, appointments, dtf);
                case 2 -> searchPatientByName(input, patients);
                case 3 -> showAppointmentsByDoctor(input, doctors, appointments);
                case 4 -> showStatistics(doctors, patients, hospitals);
                case 5 -> demonstrateImmutableMutable(doctors);
                case 6 -> demonstrateGenerics(doctors, patients);
                case 7 -> addNewDoctor(input, doctors, hospitals);
                case 8 -> addNewPatient(input, patients, hospitals);
                case 9 -> createBackup(hospitals, doctors, patients, appointments);
                case 10 -> restoreBackup(input, hospitals, doctors, patients, appointments);
                case 11 -> XmlLogger.displayLogs();
                case 12 -> {
                    System.out.println("Saving all data...");
                    DataManager.saveAllData(hospitals, new ArrayList<>(doctors), patients, appointments);
                    System.out.println("Data saved successfully!");
                    XmlLogger.logAction("DATA_SAVE", "All data saved to JSON files");
                }
                case 0 -> {
                    System.out.println("Saving data before exit...");
                    DataManager.saveAllData(hospitals, new ArrayList<>(doctors), patients, appointments);
                    logger.info("Application terminated by user.");
                    XmlLogger.logAction("APPLICATION_EXIT", "Application terminated normally");
                    System.out.println("Exiting program...");
                    break menuLoop;
                }
                default -> {
                    System.out.println("Invalid option.");
                    XmlLogger.logAction("INVALID_OPTION", "User selected non-existent menu option: " + choice);
                }
            }
        }

        input.close();
    }

    private static void displayMainMenu() {
        System.out.println("\n=== MAIN MENU ===");
        System.out.println("1. Schedule appointment");
        System.out.println("2. Search patient by name (Optional)");
        System.out.println("3. Show appointments by doctor (sorted)");
        System.out.println("4. Statistics (group/partition/reduce)");
        System.out.println("5. Demonstrate Stream immutable/mutable");
        System.out.println("6. Generics demo (PECS / bounds)");
        System.out.println("7. Add new doctor");
        System.out.println("8. Add new patient");
        System.out.println("9. Create backup");
        System.out.println("10. Restore from backup");
        System.out.println("11. View action logs");
        System.out.println("12. Save all data to JSON");
        System.out.println("0. Exit");
    }

    private static void initializeSampleData(Scanner input, List<Hospital> hospitals,
                                             Set<Doctor> doctors, List<Patient> patients) {
        // Create sample hospitals
        hospitals.add(new Hospital(1, "General Hospital"));
        hospitals.add(new Hospital(2, "City Medical Center"));
        hospitals.add(new Hospital(3, "Regional Clinic"));

        // Create sample doctors
        Doctor d1 = new Doctor(1, "John", "Smith", LocalDate.of(1980, 5, 15), "Cardiology", 5000.0);
        Doctor d2 = new Doctor(2, "Sarah", "Johnson", LocalDate.of(1985, 8, 22), "Neurology", 5500.0);
        Doctor d3 = new Doctor(3, "Michael", "Brown", LocalDate.of(1978, 3, 10), "Pediatrics", 4800.0);

        doctors.add(d1);
        doctors.add(d2);
        doctors.add(d3);

        hospitals.get(0).addDoctor(d1);
        hospitals.get(1).addDoctor(d2);
        hospitals.get(2).addDoctor(d3);

        // Create sample patients
        Patient p1 = new Patient.Builder(1, "Emma", "Wilson", LocalDate.of(1990, 6, 20))
                .condition("STABLE")
                .insuranceNumber("INS001")
                .build();
        Patient p2 = new Patient.Builder(2, "James", "Davis", LocalDate.of(1975, 11, 8))
                .condition("CRITICAL")
                .insuranceNumber("INS002")
                .build();

        patients.add(p1);
        patients.add(p2);

        hospitals.get(0).addPatient(p1);
        hospitals.get(1).addPatient(p2);

        System.out.println("Sample data initialized.");
    }

    private static void scheduleAppointment(Scanner input, Set<Doctor> doctors, List<Patient> patients,
                                            List<Appointment> appointments, DateTimeFormatter dtf) {
        System.out.println("Select doctor:");
        List<Doctor> doctorMenu = new ArrayList<>(doctors);
        for (int i = 0; i < doctorMenu.size(); i++) {
            System.out.println((i + 1) + ". " + doctorMenu.get(i));
        }

        int docId;
        try {
            docId = readIndex(input, doctorMenu.size());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return;
        }

        System.out.println("Select patient:");
        List<Patient> patientMenu = new ArrayList<>(patients);
        for (int i = 0; i < patientMenu.size(); i++) {
            System.out.println((i + 1) + ". " + patientMenu.get(i));
        }

        int patId;
        try {
            patId = readIndex(input, patientMenu.size());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return;
        }

        System.out.print("Date and time (yyyy-MM-dd HH:mm): ");
        LocalDateTime dateTime;
        try {
            dateTime = readDateTime(input, dtf);
        } catch (InvalidDateFormatException e) {
            System.out.println(e.getMessage());
            return;
        }

        Appointment a = new Appointment(appointments.size() + 1, doctorMenu.get(docId),
                patientMenu.get(patId), dateTime);
        appointments.add(a);
        DataManager.saveAppointments(appointments);

        logger.info("New appointment scheduled: {}", a);
        System.out.println("Appointment scheduled!");
        XmlLogger.logAction("APPOINTMENT_SCHEDULED",
                String.format("Doctor: %s, Patient: %s, Time: %s",
                        doctorMenu.get(docId).getFullName(),
                        patientMenu.get(patId).getFullName(),
                        dateTime.format(dtf)));
    }

    private static void searchPatientByName(Scanner input, List<Patient> patients) {
        System.out.print("Patient full name: ");
        String name = input.nextLine().trim();

        Optional<Patient> found = patients.stream()
                .filter(p -> p.getFullName().equalsIgnoreCase(name))
                .findFirst();

        found.ifPresentOrElse(
                p -> System.out.println("Found: " + p),
                () -> System.out.println("Patient not found.")
        );

        XmlLogger.logAction("PATIENT_SEARCH", "Searched for patient: " + name);
    }

    private static void showAppointmentsByDoctor(Scanner input, Set<Doctor> doctors,
                                                 List<Appointment> appointments) {
        System.out.println("Select doctor:");
        List<Doctor> doctorMenu = new ArrayList<>(doctors);
        for (int i = 0; i < doctorMenu.size(); i++) {
            System.out.println((i + 1) + ". " + doctorMenu.get(i));
        }

        int dId;
        try {
            dId = readIndex(input, doctorMenu.size());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return;
        }

        Doctor selected = doctorMenu.get(dId);

        List<Appointment> docAppointments = appointments.stream()
                .filter(ap -> ap.doctor().equals(selected))
                .sorted(Comparator.comparing(Appointment::dateTime))
                .toList();

        if (docAppointments.isEmpty()) {
            System.out.println("No appointments for this doctor.");
        } else {
            docAppointments.forEach(System.out::println);
        }

        XmlLogger.logAction("VIEW_APPOINTMENTS", "Viewed appointments for doctor: " + selected.getFullName());
    }

    private static void showStatistics(Set<Doctor> doctors, List<Patient> patients, List<Hospital> hospitals) {
        System.out.println("\n--- Doctors sorted by base salary (desc) ---");
        doctors.stream()
                .sorted(Comparator.comparingDouble(d -> -d.calculatePay()))
                .forEach(System.out::println);

        System.out.println("\n--- Grouping patients by condition ---");
        Map<String, List<Patient>> grouped = patients.stream()
                .collect(Collectors.groupingBy(Patient::getCondition));
        grouped.forEach((cond, plist) -> System.out.println(cond + ": " + plist.size()));

        System.out.println("\n--- Partitioning: CRITICAL ---");
        Map<Boolean, List<Patient>> partitioned = patients.stream()
                .collect(Collectors.partitioningBy(p -> {
                    String c = Optional.ofNullable(p.getCondition()).orElse("UNKNOWN");
                    return c.equalsIgnoreCase("CRITICAL");
                }));
        System.out.println("Critical: " + partitioned.get(Boolean.TRUE).size());
        System.out.println("Others: " + partitioned.get(Boolean.FALSE).size());

        double totalDoctorPay = sumPay(doctors);
        System.out.println("Total estimated doctor pay (reduce): " + totalDoctorPay);

        Map<String, Long> hospitalPatientCounts = hospitals.stream()
                .collect(Collectors.toMap(Hospital::getName,
                        h -> (long) h.getPatients().size()));
        System.out.println("Patients per hospital: " + hospitalPatientCounts);

        XmlLogger.logAction("VIEW_STATISTICS", "Viewed system statistics");
    }

    private static void demonstrateImmutableMutable(Set<Doctor> doctors) {
        System.out.println("\n--- Immutable snapshot of doctors ---");
        List<Doctor> immDoctors = doctors.stream().toList();
        System.out.println("Immutable doctors size: " + immDoctors.size());

        System.out.println("\n--- Mutable view (new ArrayList) ---");
        List<Doctor> mutDoctors = new ArrayList<>(immDoctors);
        mutDoctors.add(new Doctor(999, "Temp", "Doc", LocalDate.now(), "TMP", 0.0));
        System.out.println("Mutable doctors size after add: " + mutDoctors.size());

        XmlLogger.logAction("DEMO_IMMUTABLE", "Demonstrated immutable/mutable collections");
    }

    private static void demonstrateGenerics(Set<Doctor> doctors, List<Patient> patients) {
        System.out.println("\n--- Generics demo: addAllDoctors (PECS) ---");
        List<Doctor> dest = new ArrayList<>();
        addAllDoctors(dest, doctors);
        System.out.println("Dest size after addAllDoctors: " + dest.size());

        System.out.println("\n--- Generics demo: lower bounded addPatients ---");
        List<Object> objSink = new ArrayList<>();
        addPatients(objSink, patients);
        System.out.println("objSink size: " + objSink.size());

        XmlLogger.logAction("DEMO_GENERICS", "Demonstrated generics and PECS");
    }

    private static void addNewDoctor(Scanner input, Set<Doctor> doctors, List<Hospital> hospitals) {
        try {
            System.out.print("First name: ");
            String firstName = input.nextLine();

            System.out.print("Last name: ");
            String lastName = input.nextLine();

            LocalDate dob = readDate(input, "Date of birth (yyyy-MM-dd): ");

            System.out.print("Specialization: ");
            String specialization = input.nextLine();

            double baseSalary = readDouble(input, "Base salary: ");
            if (baseSalary < 0) {
                throw new NegativeValueException("Salary cannot be negative!");
            }

            int newId = doctors.stream().mapToInt(d -> d.getId()).max().orElse(0) + 1;
            Doctor d = new Doctor(newId, firstName, lastName, dob, specialization, baseSalary);
            doctors.add(d);

            if (!hospitals.isEmpty()) {
                hospitals.get(0).addDoctor(d);
            }

            DataManager.saveDoctors(new ArrayList<>(doctors));
            DataManager.saveHospitals(hospitals);

            logger.debug("Added: {}", d);
            System.out.println("Doctor added successfully!");
            XmlLogger.logAction("DOCTOR_ADDED", String.format("Doctor added: %s %s (%s)",
                    firstName, lastName, specialization));
        } catch (Exception e) {
            logger.error("Error adding doctor: {}", e.getMessage());
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void addNewPatient(Scanner input, List<Patient> patients, List<Hospital> hospitals) {
        try {
            System.out.print("First name: ");
            String firstName = input.nextLine();

            System.out.print("Last name: ");
            String lastName = input.nextLine();

            LocalDate dob = readDate(input, "Date of birth (yyyy-MM-dd): ");

            System.out.print("Condition (STABLE, CRITICAL, RECOVERING, UNKNOWN): ");
            String condRaw = input.nextLine().trim().toUpperCase();
            ConditionStatus condition = ConditionStatus.valueOf(condRaw);

            System.out.print("Insurance number: ");
            String insurance = input.nextLine();

            int newId = patients.stream().mapToInt(p -> p.getId()).max().orElse(0) + 1;
            Patient p = new Patient.Builder(newId, firstName, lastName, dob)
                    .condition(condition.name())
                    .insuranceNumber(insurance)
                    .build();

            patients.add(p);

            if (!hospitals.isEmpty()) {
                hospitals.get(0).addPatient(p);
            }

            DataManager.savePatients(patients);
            DataManager.saveHospitals(hospitals);

            logger.debug("Added: {}", p);
            System.out.println("Patient added successfully!");
            XmlLogger.logAction("PATIENT_ADDED", String.format("Patient added: %s %s",
                    firstName, lastName));
        } catch (IllegalArgumentException iae) {
            logger.warn("Invalid enum input: {}", iae.getMessage());
            System.out.println("Invalid condition input. Use STABLE, CRITICAL, RECOVERING, UNKNOWN.");
        } catch (Exception e) {
            logger.error("Error adding patient: {}", e.getMessage());
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void createBackup(List<Hospital> hospitals, Set<Doctor> doctors,
                                     List<Patient> patients, List<Appointment> appointments) {
        BackupManager.createBackup(hospitals, new ArrayList<>(doctors), patients, appointments);
        System.out.println("Backup created successfully!");
        XmlLogger.logAction("BACKUP_CREATED", "Binary backup created");
    }

    private static void restoreBackup(Scanner input, List<Hospital> hospitals, Set<Doctor> doctors,
                                      List<Patient> patients, List<Appointment> appointments) {
        if (!BackupManager.backupExists()) {
            System.out.println("No backup file found!");
            return;
        }

        System.out.print("Are you sure you want to restore from backup? This will overwrite current data (yes/no): ");
        String confirm = input.nextLine().trim().toLowerCase();

        if (!confirm.equals("yes")) {
            System.out.println("Restore cancelled.");
            return;
        }

        BackupManager.BackupData backup = BackupManager.restoreBackup();
        if (backup != null) {
            hospitals.clear();
            doctors.clear();
            patients.clear();
            appointments.clear();

            hospitals.addAll(backup.hospitals());
            doctors.addAll(backup.doctors());
            patients.addAll(backup.patients());
            appointments.addAll(backup.appointments());

            // Save restored data to JSON
            DataManager.saveAllData(hospitals, new ArrayList<>(doctors), patients, appointments);

            System.out.println("Backup restored successfully!");
            System.out.printf("Restored: %d hospitals, %d doctors, %d patients, %d appointments%n",
                    hospitals.size(), doctors.size(), patients.size(), appointments.size());
            XmlLogger.logAction("BACKUP_RESTORED", "Data restored from binary backup");
        } else {
            System.out.println("Error restoring backup!");
        }
    }

    /**
     * PECS example: copy all doctors from src (<? extends Doctor>) to dst (<? super Doctor>)
     */
    public static void addAllDoctors(Collection<? super Doctor> dst, Collection<? extends Doctor> src) {
        src.forEach(dst::add);
    }

    /**
     * Lower bounded example: add patients to a sink that accepts Patient or its super types
     */
    public static void addPatients(Collection<? super Patient> dst, Collection<? extends Patient> src) {
        src.forEach(dst::add);
    }

    /**
     * Upper bounded wildcard: sum pay of any collection of Payable (Doctor, Patient, etc.)
     */
    public static double sumPay(Collection<? extends Payable> payables) {
        return payables.stream()
                .mapToDouble(Payable::calculatePay)
                .sum();
    }

    private static int readInt(Scanner input) throws InvalidNumberInputException {
        if (!input.hasNextInt()) {
            input.nextLine();
            throw new InvalidNumberInputException("Enter a numeric value!");
        }
        int value = input.nextInt();
        input.nextLine();
        return value;
    }

    private static int readIndex(Scanner input, int max) throws InvalidNumberInputException {
        int index = readInt(input) - 1;
        if (index < 0 || index >= max) {
            throw new EntityNotFoundException("Item with that number does not exist!");
        }
        return index;
    }

    private static LocalDate readDate(Scanner input, String prompt) {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        while (true) {
            System.out.print(prompt);
            try {
                return LocalDate.parse(input.nextLine().trim(), fmt);
            } catch (Exception _) {
                System.out.println("Format must be yyyy-MM-dd");
            }
        }
    }

    private static LocalDateTime readDateTime(Scanner input, DateTimeFormatter fmt)
            throws InvalidDateFormatException {
        try {
            return LocalDateTime.parse(input.nextLine().trim(), fmt);
        } catch (Exception _) {
            throw new InvalidDateFormatException("Format must be yyyy-MM-dd HH:mm!");
        }
    }

    private static double readDouble(Scanner input, String prompt) throws InvalidNumberInputException {
        System.out.print(prompt);
        try {
            double value = Double.parseDouble(input.nextLine().trim());
            if (value < 0) {
                throw new NegativeValueException("Number cannot be negative!");
            }
            return value;
        } catch (NumberFormatException _) {
            throw new InvalidNumberInputException("Enter a decimal number!");
        }
    }
}

