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
bolnica1
bolnica2
bolnica3
doktor1
prezime1
1980-01-01
spec1
2500.00
doktor2
prezime2
1985-02-02
spec2
2600.00
doktor3
prezime3
1990-03-03
spec3
2700.00
doktor4
prezime4
1995-04-04
spec4
2800.00
doktor5
prezime5
2000-05-05
spec5
2900.00
pacijent1
prezpac1
2000-01-01
STABLE
osig1
pacijent2
prezpac2
2001-02-02
CRITICAL
osig2
pacijent3
prezpac3
2002-03-03
RECOVERING
osig3
pacijent4
prezpac4
2003-04-04
UNKNOWN
osig4
pacijent5
prezpac5
2004-05-05
STABLE
osig5
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
