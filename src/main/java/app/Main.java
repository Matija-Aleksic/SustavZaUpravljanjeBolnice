package app;

import entity.*;
import exception.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * The type Main.
 */
public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        Hospital[] hospitals = new Hospital[3];
        Doctor[] doctors = new Doctor[5];
        Patient[] patients = new Patient[5];
        Appointment[] appointments = new Appointment[20];
        int appointmentCount = 0;

        for (int i = 0; i < hospitals.length; i++) {
            System.out.print("Upišite ime " + (i + 1) + ". bolnice: ");
            String name = input.nextLine();
            hospitals[i] = new Hospital(i + 1, name);
        }

        for (int i = 0; i < doctors.length; i++) {
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

                doctors[i] = new Doctor(i + 1, firstName, lastName, dob, specialization, baseSalary);
                hospitals[i % hospitals.length].addDoctor(doctors[i]);

            } catch (Exception e) {
                System.out.println("Greška: " + e.getMessage());
                i--;
            }
        }

        for (int i = 0; i < patients.length; i++) {
            try {
                System.out.print("Ime pacijenta: ");
                String firstName = input.nextLine();

                System.out.print("Prezime pacijenta: ");
                String lastName = input.nextLine();

                LocalDate dob = readDate(input, "Datum rođenja pacijenta (yyyy-MM-dd): ");

                System.out.print("Stanje pacijenta: ");
                String condition = input.nextLine();

                System.out.print("Broj osiguranja: ");
                String insurance = input.nextLine();

                patients[i] = new Patient.Builder(i + 1, firstName, lastName, dob)
                        .condition(condition)
                        .insuranceNumber(insurance)
                        .build();

                hospitals[i % hospitals.length].addPatient(patients[i]);

            } catch (Exception e) {
                System.out.println("Greška: " + e.getMessage());
                i--;
            }
        }

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        while (true) {
            System.out.println("\n--- IZBORNIK ---");
            System.out.println("1. Zakaži pregled");
            System.out.println("2. Prikaži pacijenta po imenu");
            System.out.println("3. Prikaži preglede po doktoru");
            System.out.println("4. Prikaži sve preglede");
            System.out.println("5. Izađi");
            System.out.print("Odaberite opciju: ");

            int choice;
            try {
                choice = readInt(input);
            } catch (InvalidNumberInputException e) {
                System.out.println(e.getMessage());
                continue;
            }

            switch (choice) {
                case 1 -> {
                    System.out.println("Odabir doktora:");
                    for (int i = 0; i < doctors.length; i++) {
                        System.out.println((i + 1) + ". " + doctors[i]);
                    }

                    int docId;
                    try {
                        docId = readIndex(input, doctors.length);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                        continue;
                    }

                    System.out.println("Odabir pacijenta:");
                    for (int i = 0; i < patients.length; i++) {
                        System.out.println((i + 1) + ". " + patients[i]);
                    }

                    int patId;
                    try {
                        patId = readIndex(input, patients.length);
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

                    if (appointmentCount >= appointments.length) {
                        System.out.println("Maksimalan broj pregleda!");
                        continue;
                    }

                    appointments[appointmentCount++] = new Appointment(
                            appointmentCount,
                            doctors[docId],
                            patients[patId],
                            dateTime
                    );

                    System.out.println("Pregled zakazan!");
                }

                case 2 -> {
                    System.out.print("Ime i prezime pacijenta: ");
                    String name = input.nextLine().trim();
                    boolean found = false;

                    for (Patient p : patients) {
                        if (p.getFullName().equalsIgnoreCase(name)) {
                            System.out.println(p);
                            found = true;
                        }
                    }

                    if (!found) throw new EntityNotFoundException("Pacijent nije pronađen.");
                }

                case 3 -> {
                    System.out.println("Odaberite doktora:");
                    for (int i = 0; i < doctors.length; i++)
                        System.out.println((i + 1) + ". " + doctors[i]);

                    int dId;
                    try {
                        dId = readIndex(input, doctors.length);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                        continue;
                    }

                    boolean hasAppointments = false;
                    for (int i = 0; i < appointmentCount; i++) {
                        if (appointments[i].getDoctor().equals(doctors[dId])) {
                            System.out.println(appointments[i]);
                            hasAppointments = true;
                        }
                    }

                    if (!hasAppointments)
                        System.out.println("Nema pregleda za tog doktora.");
                }

                case 4 -> {
                    if (appointmentCount == 0)
                        System.out.println("Nema pregleda.");
                    else
                        for (int i = 0; i < appointmentCount; i++)
                            System.out.println(appointments[i]);
                }

                case 5 -> {
                    System.out.println("👋 Izlaz iz programa...");
                    return;
                }

                default -> System.out.println("Opcija ne postoji!");
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
