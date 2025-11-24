package app;

import entity.*;
import exception.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        List<Hospital> hospitals = new ArrayList<>();
        Set<Doctor> doctors = new LinkedHashSet<>();
        List<Patient> patients = new ArrayList<>();
        List<Appointment> appointments = new ArrayList<>();

        logger.info("Pokretanje aplikacije...");

        for (int i = 0; i < 3; i++) {
            System.out.print("Ime bolnice: ");
            String name = input.nextLine().trim();
            hospitals.add(new Hospital(i + 1, name));
        }

        for (int i = 0; i < 5; i++) {
            try {
                System.out.print("Ime doktora: ");
                String firstName = input.nextLine();

                System.out.print("Prezime doktora: ");
                String lastName = input.nextLine();

                LocalDate dob = readDate(input, "Datum rođenja doktora (yyyy-MM-dd): ");

                System.out.print("Specijalizacija: ");
                String specialization = input.nextLine();

                double baseSalary = readDouble(input, "Osnovna plaća: ");
                if (baseSalary < 0) throw new NegativeValueException("Plaća ne može biti negativna!");

                Doctor d = new Doctor(i + 1, firstName, lastName, dob, specialization, baseSalary);
                doctors.add(d);
                hospitals.get(i % hospitals.size()).addDoctor(d);

                logger.debug("Dodano: {}", d);
            } catch (Exception e) {
                logger.error("Greška kod unosa doktora: {}", e.getMessage());
                System.out.println("Pogrešan unos: " + e.getMessage());
                i--;
            }
        }

        for (int i = 0; i < 5; i++) {
            try {
                System.out.print("Ime pacijenta: ");
                String firstName = input.nextLine();

                System.out.print("Prezime pacijenta: ");
                String lastName = input.nextLine();

                LocalDate dob = readDate(input, "Datum rođenja pacijenta (yyyy-MM-dd): ");

                System.out.print("Stanje (STABLE, CRITICAL, RECOVERING, UNKNOWN): ");
                String condRaw = input.nextLine().trim().toUpperCase();
                ConditionStatus condition = ConditionStatus.valueOf(condRaw);

                System.out.print("Broj osiguranja: ");
                String insurance = input.nextLine();

                Patient p = new Patient.Builder(i + 1, firstName, lastName, dob)
                        .condition(condition.name())
                        .insuranceNumber(insurance)
                        .build();

                patients.add(p);
                hospitals.get(i % hospitals.size()).addPatient(p);

                logger.debug("Dodano: {}", p);
            } catch (IllegalArgumentException iae) {
                logger.warn("Neispravan enum unos: {}", iae.getMessage());
                System.out.println("Neispravan unos stanja. Koristite STABLE, CRITICAL, RECOVERING, UNKNOWN.");
                i--;
            } catch (Exception e) {
                logger.error("Greška kod unosa pacijenta: {}", e.getMessage());
                System.out.println("Pogrešan unos: " + e.getMessage());
                i--;
            }
        }

        List<Hospital> hospitalsReadOnly = List.copyOf(hospitals);
        List<Doctor> doctorListImmutable = List.copyOf(doctors);
        List<Patient> patientListImmutable = List.copyOf(patients);

        // Demonstrate generic PECS helper usage
        List<Doctor> mutableDoctorSink = new ArrayList<>();
        addAllDoctors(mutableDoctorSink, doctorListImmutable);
        logger.debug("Doctors copied to mutable sink (PECS example): {}", mutableDoctorSink.size());

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        menuLoop:
        while (true) {
            System.out.println("\n--- IZBORNIK ---");
            System.out.println("1. Zakaži pregled");
            System.out.println("2. Prikaži pacijenta po imenu (Optional)");
            System.out.println("3. Prikaži preglede po doktoru (sortirano)");
            System.out.println("4. Statistika (group/partition/reduce)");
            System.out.println("5. Demonstracija Stream immutable/mutable");
            System.out.println("6. Generics demo (PECS / bounds)");
            System.out.println("7. Izađi");
            System.out.print("Odaberite opciju: ");

            int choice;
            try {
                choice = readInt(input);
            } catch (InvalidNumberInputException e) {
                logger.warn("Neispravan unos opcije: {}", e.getMessage());
                System.out.println(e.getMessage());
                continue;
            }

            switch (choice) {
                case 1 -> { // schedule appointment
                    System.out.println("Odaberite doktora:");
                    List<Doctor> doctorMenu = List.copyOf(doctors).stream().toList();
                    for (int i = 0; i < doctorMenu.size(); i++)
                        System.out.println((i + 1) + ". " + doctorMenu.get(i));

                    int docId;
                    try {
                        docId = readIndex(input, doctorMenu.size());
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                        continue;
                    }

                    System.out.println("Odaberite pacijenta:");
                    List<Patient> patientMenu = List.copyOf(patients);
                    for (int i = 0; i < patientMenu.size(); i++)
                        System.out.println((i + 1) + ". " + patientMenu.get(i));

                    int patId;
                    try {
                        patId = readIndex(input, patientMenu.size());
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                        continue;
                    }

                    System.out.print("Datum i vrijeme (yyyy-MM-dd HH:mm): ");
                    LocalDateTime dateTime;
                    try {
                        dateTime = readDateTime(input, dtf);
                    } catch (InvalidDateFormatException e) {
                        System.out.println(e.getMessage());
                        continue;
                    }

                    Appointment a = new Appointment(appointments.size() + 1, doctorMenu.get(docId), patientMenu.get(patId), dateTime);
                    appointments.add(a);
                    logger.info("Zakazan novi pregled: {}", a);
                    System.out.println("Pregled zakazan!");
                }

                case 2 -> { // search patient by name
                    System.out.print("Ime i prezime pacijenta: ");
                    String name = input.nextLine().trim();

                    Optional<Patient> found = patients.stream()
                            .filter(p -> p.getFullName().equalsIgnoreCase(name))
                            .findFirst();

                    found.ifPresentOrElse(
                            p -> System.out.println("Pronađeno: " + p),
                            () -> System.out.println("Pacijent nije pronađen.")
                    );
                }

                case 3 -> { // show appointments by doctor
                    System.out.println("Odaberite doktora:");
                    List<Doctor> doctorMenu = List.copyOf(doctors).stream().toList();
                    for (int i = 0; i < doctorMenu.size(); i++)
                        System.out.println((i + 1) + ". " + doctorMenu.get(i));

                    int dId;
                    try {
                        dId = readIndex(input, doctorMenu.size());
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                        continue;
                    }

                    Doctor selected = doctorMenu.get(dId);

                    List<Appointment> docAppointments = appointments.stream()
                            .filter(ap -> ap.doctor().equals(selected))
                            .sorted(Comparator.comparing(Appointment::dateTime))
                            .collect(Collectors.toUnmodifiableList());

                    if (docAppointments.isEmpty()) {
                        System.out.println("Nema pregleda za ovog doktora.");
                    } else {
                        docAppointments.forEach(System.out::println);
                    }
                }

                case 4 -> { // statistics: grouping, partitioning, reducing
                    System.out.println("\n--- Doktori sortirani po osnovnoj plaći (desc) ---");
                    doctors.stream()
                            .sorted(Comparator.comparingDouble(d -> -d.calculatePay()))
                            .forEach(System.out::println);

                    System.out.println("\n--- Grupiranje pacijenata po stanju ---");
                    Map<String, List<Patient>> grouped = patients.stream()
                            .collect(Collectors.groupingBy(Patient::getCondition));
                    grouped.forEach((cond, plist) -> System.out.println(cond + ": " + plist.size()));

                    System.out.println("\n--- Particioniranje: CRITICAL ---");
                    Map<Boolean, List<Patient>> partitioned = patients.stream()
                            .collect(Collectors.partitioningBy(p -> {
                                String c = Optional.ofNullable(p.getCondition()).orElse("UNKNOWN");
                                return c.equalsIgnoreCase("CRITICAL");
                            }));
                    System.out.println("Kritični: " + partitioned.get(true).size());
                    System.out.println("Ostali: " + partitioned.get(false).size());

                    double totalDoctorPay = sumPay(doctors);
                    System.out.println("Ukupna procijenjena plaća doktora (reduce): " + totalDoctorPay);

                    Map<String, Long> hospitalPatientCounts = hospitals.stream()
                            .collect(Collectors.toMap(Hospital::getName,
                                    h -> (long) h.getPatients().size()));
                    System.out.println("Pacijenti po bolnici: " + hospitalPatientCounts);
                }

                case 5 -> { // demonstrate immutable vs mutable
                    System.out.println("\n--- Immutable snapshot of doctors ---");
                    List<Doctor> immDoctors = doctors.stream().collect(Collectors.toUnmodifiableList());
                    System.out.println("Immutable doctors size: " + immDoctors.size());

                    System.out.println("\n--- Mutable view (new ArrayList) ---");
                    List<Doctor> mutDoctors = new ArrayList<>(immDoctors);
                    mutDoctors.add(new Doctor(999, "Temp", "Doc", LocalDate.now(), "TMP", 0.0));
                    System.out.println("Mutable doctors size after add: " + mutDoctors.size());
                }

                case 6 -> { // generics demo
                    System.out.println("\n--- Generics demo: addAllDoctors (PECS) ---");
                    List<Doctor> dest = new ArrayList<>();
                    addAllDoctors(dest, doctors); // ? extends Doctor source
                    System.out.println("Dest size after addAllDoctors: " + dest.size());

                    System.out.println("\n--- Generics demo: printAges (upper bounded, multiple bounds) ---");
                    List<Person> people = Stream.concat(doctors.stream(), patients.stream()).collect(Collectors.toList());
                    printAges(people);

                    System.out.println("\n--- Generics demo: lower bounded addPatients ---");
                    List<Object> objSink = new ArrayList<>();
                    addPatients(objSink, patients); // ? super Patient
                    System.out.println("objSink size: " + objSink.size());
                }

                case 7 -> {
                    logger.info("Aplikacija završena od strane korisnika.");
                    System.out.println("Izlaz iz programa...");
                    break menuLoop;
                }

                default -> System.out.println("Nepostojeća opcija.");
            }
        }

        input.close();
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
     * Upper bounded + multiple bounds example: print ages of Persons who are Ageable
     */
    public static <T extends Person & Ageable> void printAges(Collection<T> people) {
        people.forEach(p -> System.out.println(p.getFullName() + " - " + p.getAge()));
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
            throw new InvalidNumberInputException("Unesite brojčanu vrijednost!");
        }
        int value = input.nextInt();
        input.nextLine();
        return value;
    }

    private static int readIndex(Scanner input, int max) throws InvalidNumberInputException {
        int index = readInt(input) - 1;
        if (index < 0 || index >= max)
            throw new EntityNotFoundException("Ne postoji stavka pod tim brojem!");
        return index;
    }

    private static LocalDate readDate(Scanner input, String prompt) {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        while (true) {
            System.out.print(prompt);
            try {
                return LocalDate.parse(input.nextLine().trim(), fmt);
            } catch (Exception e) {
                System.out.println("Format mora biti yyyy-MM-dd");
            }
        }
    }

    private static LocalDateTime readDateTime(Scanner input, DateTimeFormatter fmt)
            throws InvalidDateFormatException {
        try {
            return LocalDateTime.parse(input.nextLine().trim(), fmt);
        } catch (Exception e) {
            throw new InvalidDateFormatException("Format mora biti yyyy-MM-dd HH:mm!");
        }
    }

    private static double readDouble(Scanner input, String prompt) throws InvalidNumberInputException {
        System.out.print(prompt);
        try {
            double value = Double.parseDouble(input.nextLine().trim());
            if (value < 0) throw new NegativeValueException("Broj ne može biti negativan!");
            return value;
        } catch (NumberFormatException e) {
            throw new InvalidNumberInputException("Unesite decimalni broj!");
        }
    }
}
