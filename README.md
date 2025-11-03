# Sustav za upravljanje bolnicom

Ovaj projekt predstavlja jednostavan **sustav za upravljanje bolnicom** implementiran u Javi.  
Aplikacija omogućuje upravljanje osnovnim entitetima kao što su bolnice, doktori i pacijenti.

## Struktura projekta

Projekt je organiziran u dva paketa:

```
app/
└── Main.java

entity/
├── Person.java
├── Doctor.java
├── Patient.java
├── Hospital.java
├── Schedulable.java
├── Recordable.java
└── Record.java
```

### Klase i sučelja

#### `Person` (apstraktna klasa)
Predstavlja osnovne osobine svake osobe u sustavu.  
Sadrži atribute:
- `id` (int)
- `firstName` (String)
- `lastName` (String)
- `dateOfBirth` (LocalDate)

#### `Doctor` (nasljeđuje `Person`)
Predstavlja liječnika s dodatnim podacima:
- `specialization` (String)
- `baseSalary` (double)

#### `Patient` (nasljeđuje `Person`)
Predstavlja pacijenta s dodatnim podacima:
- `diagnosis` (String)
- `insuranceId` (String)

#### `Hospital`
Predstavlja bolnicu koja sadrži:
- naziv bolnice
- listu doktora i pacijenata

#### `Schedulable` (sučelje)
Definira metode za planiranje termina:
- `scheduleAppointment()`

#### `Recordable` (zapečaćeno sučelje)
Definira entitete koji mogu biti snimljeni u zapis.

#### `Record`
Zapis koji sadrži informacije o događaju (koristi Java `record`).

## Korištenje programa

Program se pokreće iz klase `Main` u paketu `app`.

Prilikom pokretanja, od korisnika se traži unos podataka u sljedećem redoslijedu:

### Primjer unosa
```
bol1
bol2
bol3
dok1
prez1
spec1
dok2
prez2
spec2
dok3
prez3
spec3
dok4
prez4
spec4
dok5
prez5
spec5
pac1
pac11
bolestan
osing1
pac2
pac22
bolestan
osing2
pac3
pac33
bolestan
osing3
pac4
pac44
bolestan
osing4
pac5
pac55
bolestan
osing5
```

### Funkcionalnosti

- Kreiranje bolnica, doktora i pacijenata prema korisničkom unosu.
- Ispis svih unesenih entiteta.
- Polimorfizam korišten kroz zajedničku nadklasu `Person` (doktori i pacijenti u istom polju).
- Pronalaženje **najmlađe osobe** i **najstarije osobe** u sustavu.

## Tehnologije

- Java 17+
- Objektno orijentirano programiranje (nasljeđivanje, polimorfizam, apstraktne klase, sučelja)
- Builder pattern
- Zapečaćeno sučelje (`sealed interface`)
- `record` tip uveden u Javi 16

## Pokretanje

1. Prevedi projekt:
   ```bash
   javac app/Main.java entity/*.java
   ```

2. Pokreni program:
   ```bash
   java app.Main
   ```

## Autor
Matija Aleksić — Fakultet tehničkih znanosti  
2025.
