package app;

import entity.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class Main {
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
            System.out.print("Upišite ime " + (i + 1) + ". doktora: ");
            String firstName = input.nextLine();
            System.out.print("Upišite prezime " + (i + 1) + ". doktora: ");
            String lastName = input.nextLine();

            LocalDate dob = readDate(input, "Upišite datum rođenja doktora (format: yyyy-MM-dd): ");

            System.out.print("Upišite specijalizaciju: ");
            String specialization = input.nextLine();

            double baseSalary = readDouble(input, "Upišite osnovnu plaću doktora (npr. 2500.00): ");

            doctors[i] = new Doctor(i + 1, firstName, lastName, dob, specialization, baseSalary);
            hospitals[i % hospitals.length].addDoctor(doctors[i]);
        }

        for (int i = 0; i < patients.length; i++) {
            System.out.print("Upišite ime " + (i + 1) + ". pacijenta: ");
            String firstName = input.nextLine();
            System.out.print("Upišite prezime " + (i + 1) + ". pacijenta: ");
            String lastName = input.nextLine();

            LocalDate dob = readDate(input, "Upišite datum rođenja pacijenta (format: yyyy-MM-dd): ");

            System.out.print("Upišite stanje pacijenta: ");
            String condition = input.nextLine();
            System.out.print("Upišite broj osiguranja: ");
            String insurance = input.nextLine();

            patients[i] = new Patient.Builder(i + 1, firstName, lastName, dob)
                    .condition(condition)
                    .insuranceNumber(insurance)
                    .build();

            hospitals[i % hospitals.length].addPatient(patients[i]);
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

            if (!input.hasNextInt()) {
                System.out.println("Neispravan unos! Molimo unesite broj opcije.");
                input.nextLine();
                continue;
            }

            int choice = input.nextInt();
            input.nextLine();

            switch (choice) {
                case 1 -> {
                    System.out.println("Odaberite doktora:");
                    for (int i = 0; i < doctors.length; i++) {
                        System.out.println((i + 1) + ". " + doctors[i]);
                    }
                    System.out.print("Unesite broj: ");
                    int docId = safeIndex(input, doctors.length);
                    if (docId == -1) continue;

                    System.out.println("Odaberite pacijenta:");
                    for (int i = 0; i < patients.length; i++) {
                        System.out.println((i + 1) + ". " + patients[i]);
                    }
                    System.out.print("Unesite broj: ");
                    int patId = safeIndex(input, patients.length);
                    if (patId == -1) continue;

                    System.out.print("Upišite datum i vrijeme (format: yyyy-MM-dd HH:mm): ");
                    String dateTimeStr = input.nextLine();
                    LocalDateTime dateTime;
                    try {
                        dateTime = LocalDateTime.parse(dateTimeStr, dtf);
                    } catch (DateTimeParseException e) {
                        System.out.println("Neispravan format datuma! Pokušajte ponovo (npr. 2025-10-20 14:30)");
                        continue;
                    }

                    if (appointmentCount >= appointments.length) {
                        System.out.println("Maksimalan broj pregleda dosegnut!");
                        continue;
                    }

                    appointments[appointmentCount++] = new Appointment(
                            appointmentCount,
                            doctors[docId],
                            patients[patId],
                            dateTime
                    );
                    System.out.println("Pregled uspješno zakazan!");
                }

                case 2 -> {
                    System.out.print("Upišite ime i prezime pacijenta: ");
                    String name = input.nextLine().trim();
                    boolean found = false;
                    for (Patient p : patients) {
                        if (p.getFullName().equalsIgnoreCase(name)) {
                            System.out.println(p);
                            found = true;
                        }
                    }
                    if (!found) System.out.println("Pacijent nije pronađen.");
                }

                case 3 -> {
                    System.out.println("Odaberite doktora:");
                    for (int i = 0; i < doctors.length; i++) {
                        System.out.println((i + 1) + ". " + doctors[i]);
                    }
                    System.out.print("Unesite broj: ");
                    int dId = safeIndex(input, doctors.length);
                    if (dId == -1) continue;

                    boolean hasAppointments = false;
                    for (int i = 0; i < appointmentCount; i++) {
                        if (appointments[i].getDoctor().equals(doctors[dId])) {
                            System.out.println(appointments[i]);
                            hasAppointments = true;
                        }
                    }
                    if (!hasAppointments) System.out.println("Nema zakazanih pregleda za tog doktora.");
                }

                case 4 -> {
                    if (appointmentCount == 0) {
                        System.out.println("Nema zakazanih pregleda.");
                    } else {
                        for (int i = 0; i < appointmentCount; i++) {
                            System.out.println(appointments[i]);
                        }
                    }
                }

                case 5 -> {
                    System.out.println("Izlaz iz programa...");
                    input.close();
                    return;
                }

                default -> System.out.println("Neispravan unos, pokušajte ponovno.");
            }
        }
    }

    private static int safeIndex(Scanner input, int max) {
        if (!input.hasNextInt()) {
            System.out.println("Unos mora biti broj!");
            input.nextLine();
            return -1;
        }
        int index = input.nextInt() - 1;
        input.nextLine();
        if (index < 0 || index >= max) {
            System.out.println("Neispravan broj, pokušajte ponovno!");
            return -1;
        }
        return index;
    }

    private static LocalDate readDate(Scanner input, String prompt) {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        while (true) {
            System.out.print(prompt);
            String s = input.nextLine().trim();
            try {
                return LocalDate.parse(s, fmt);
            } catch (DateTimeParseException e) {
                System.out.println("Neispravan format datuma. Upotrijebite yyyy-MM-dd (npr. 1990-05-23).");
            }
        }
    }

    private static double readDouble(Scanner input, String prompt) {
        while (true) {
            System.out.print(prompt);
            String s = input.nextLine().trim();
            try {
                return Double.parseDouble(s);
            } catch (NumberFormatException e) {
                System.out.println("Neispravan broj. Pokušajte ponovno (npr. 2500.00).");
            }
        }
    }
}
