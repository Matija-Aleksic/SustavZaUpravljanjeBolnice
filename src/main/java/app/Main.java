package app;

import entity.*;
import exception.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        List<Hospital> hospitals = new ArrayList<>();
        Set<Doctor> doctors = new HashSet<>();
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
                ConditionStatus condition = ConditionStatus.valueOf(input.nextLine().trim().toUpperCase());

                System.out.print("Broj osiguranja: ");
                String insurance = input.nextLine();

                Patient p = new Patient.Builder(i + 1, firstName, lastName, dob)
                        .condition(condition.name())
                        .insuranceNumber(insurance)
                        .build();

                patients.add(p);
                hospitals.get(i % hospitals.size()).addPatient(p);
            } catch (Exception e) {
                logger.error("Greška kod unosa pacijenta: {}", e.getMessage());
                System.out.println("Pogrešan unos: " + e.getMessage());
                i--;
            }
        }

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        while (true) {
            System.out.println("\n--- IZBORNIK ---");
            System.out.println("1. Zakaži pregled");
            System.out.println("2. Prikaži pacijenta po imenu");
            System.out.println("3. Prikaži preglede po doktoru");
            System.out.println("4. Statistika");
            System.out.println("5. Izađi");
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
                case 1 -> {
                    System.out.println("Odaberite doktora:");
                    List<Doctor> doctorList = new ArrayList<>(doctors);
                    for (int i = 0; i < doctorList.size(); i++)
                        System.out.println((i + 1) + ". " + doctorList.get(i));

                    int docId;
                    try {
                        docId = readIndex(input, doctorList.size());
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                        continue;
                    }

                    System.out.println("Odaberite pacijenta:");
                    for (int i = 0; i < patients.size(); i++)
                        System.out.println((i + 1) + ". " + patients.get(i));

                    int patId;
                    try {
                        patId = readIndex(input, patients.size());
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

                    Appointment a = new Appointment(
                            appointments.size() + 1,
                            doctorList.get(docId),
                            patients.get(patId),
                            dateTime
                    );
                    appointments.add(a);
                    logger.info("Zakazan novi pregled: {}", a);
                    System.out.println("✅ Pregled zakazan!");
                }

                case 2 -> {
                    System.out.print("Ime i prezime pacijenta: ");
                    String name = input.nextLine().trim();

                    patients.stream()
                            .filter(p -> p.getFullName().equalsIgnoreCase(name))
                            .findFirst()
                            .ifPresentOrElse(
                                    System.out::println,
                                    () -> {
                                        throw new EntityNotFoundException("Pacijent nije pronađen.");
                                    }
                            );
                }

                case 3 -> {
                    System.out.println("Odaberite doktora:");
                    List<Doctor> doctorList = new ArrayList<>(doctors);
                    for (int i = 0; i < doctorList.size(); i++)
                        System.out.println((i + 1) + ". " + doctorList.get(i));

                    int dId;
                    try {
                        dId = readIndex(input, doctorList.size());
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                        continue;
                    }

                    Doctor selectedDoctor = doctorList.get(dId);

                    List<Appointment> docAppointments = appointments.stream()
                            .filter(a -> a.getDoctor().equals(selectedDoctor))
                            .sorted(Comparator.comparing(Appointment::getDateTime))
                            .toList();

                    if (docAppointments.isEmpty())
                        System.out.println("Nema pregleda za ovog doktora.");
                    else
                        docAppointments.forEach(System.out::println);
                }

                case 4 -> {
                    System.out.println("\n--- Doktori sortirani po plaći ---");
                    doctors.stream()
                            .sorted(Comparator.comparingDouble(Doctor::getBaseSalary).reversed())
                            .forEach(System.out::println);

                    System.out.println("\n--- Grupiranje pacijenata po stanju ---");
                    Map<String, List<Patient>> grouped = patients.stream()
                            .collect(Collectors.groupingBy(Patient::getCondition));
                    grouped.forEach((cond, plist) -> {
                        System.out.println(cond + ": " + plist.size() + " pacijenata");
                    });

                    System.out.println("\n--- Kritični vs ostali pacijenti ---");
                    Map<Boolean, List<Patient>> partitioned =
                            patients.stream().collect(Collectors.partitioningBy(
                                    p -> p.getCondition().equalsIgnoreCase("CRITICAL")));
                    System.out.println("Kritični: " + partitioned.get(true).size());
                    System.out.println("Ostali: " + partitioned.get(false).size());
                }

                case 5 -> {
                    logger.info("Aplikacija završena od strane korisnika.");
                    System.out.println("👋 Izlaz iz programa...");
                    return;
                }

                default -> System.out.println("Nepostojeća opcija.");
            }
        }
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
                System.out.println(" Format mora biti yyyy-MM-dd");
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
