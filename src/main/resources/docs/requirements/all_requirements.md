# Sustav za Upravljanje Bolnicom - Zahtjevi

**Predmet:** Programiranje u Javi 2025/2026  
**Studij:** Prijediplomski studij – smjer računarstvo  
**Institucija:** Tehničko veleučilište u Zagrebu  
**Autor:** doc.dr.sc. Aleksander Radovan, prof.struč.stud.

---

## Sadržaj

- [Opći Zahtjevi](#opći-zahtjevi)
- [Laboratorijske Vježbe](#laboratorijske-vježbe)
    - [1. Laboratorijska Vježba - Klase i Objekti](#1-laboratorijska-vježba---klase-i-objekti)
    - [2. Laboratorijska Vježba - OOP](#2-laboratorijska-vježba---objektno-orijentirano-programiranje)
    - [3. Laboratorijska Vježba - Iznimke i Dokumentacija](#3-laboratorijska-vježba---iznimke-i-dokumentacija)
    - [4. Laboratorijska Vježba - Kolekcije](#4-laboratorijska-vježba---kolekcije)
    - [5. Laboratorijska Vježba - Lambda i Generici](#5-laboratorijska-vježba---lambda-i-generički-tipovi)
    - [6. Laboratorijska Vježba - Datoteke](#6-laboratorijska-vježba---datoteke)
    - [7. Laboratorijska Vježba - JavaFX](#7-laboratorijska-vježba---javafx)
    - [8. Laboratorijska Vježba - JavaFX UI](#8-laboratorijska-vježba---javafx-ui)
    - [9. Laboratorijska Vježba - JDBC](#9-laboratorijska-vježba---jdbc)
    - [10. Laboratorijska Vježba - Višenitnost](#10-laboratorijska-vježba---višenitnost)
- [Sustav za Upravljanje Bolnicom](#sustav-za-upravljanje-bolnicom---detaljni-zahtjevi)

---

## Opći Zahtjevi

### Zajedničke Zahtjeve za Sve Laboratorijske Vježbe

- **Blic Test:** Na početku laboratorijskih vježbi potrebno je riješiti „blic" test na Moodleu (50% bodova)
- **Klase i Paketi:** Entiteti u paketu `entity`, glavna klasa u paketu `app`
- **Duljina Klasa:** Nijedna klasa ne smije imati više od 200 linija koda
- **Analiza Koda:** Programski kod skenirati SonarQube alatom i ispraviti sve pogreške
- **Dokumentacija:** Javadoc dokumentacija za sve klase i metode
- **Rad s Podacima:** Korisniku omogućiti pretraživanje i pronalaženje min/max vrijednosti

---

## Laboratorijske Vježbe

### 1. Laboratorijska Vježba - Klase i Objekti

**Tema:** Klase, objekti i osnovne strukture podataka

**Zahtjevi:**

1. Od korisnika zatražiti unos podataka za **pet objekata svake klase**
2. Koristiti samo **polja (array)** za spremanje objekata
3. Povezati objekte na način da jedni sadržavaju druge
4. Omogućiti pretraživanje objekata prema zadanim kriterijima
    - Koristiti `for`, `while` i `do-while` petlje
5. Omogućiti pronalaženje objekata s maksimalnim i minimalnim vrijednostima

---

### 2. Laboratorijska Vježba - Objektno Orijentirano Programiranje

**Tema:** Apstraktne klase, sučelja, polimorfizam, builder pattern

**Zahtjevi:**

1. Proširiti rješenje 1. vježbe sa:
    - **Jednom apstraktnom klasu** (npr. `Person`)
    - **Dva sučelja** (jedno zapečaćeno)
    - **Jednim zapisom** (sealed class)
2. Prilagoditi program za rad s novim entitetima
3. Implementirati **polimorfizam** - objekti podklasa u poljima zajedničke nadklase
4. Dodati **builder pattern** za barem jednu klasu
5. Pronalaženje min/max vrijednosti prema parametrima

---

### 3. Laboratorijska Vježba - Iznimke i Dokumentacija

**Tema:** Rukovanje greškama, logiranje, dokumentacija

**Zahtjevi:**

1. Proširiti rješenje 2. vježbe s:
    - **Dvije checked iznimke** (npr. neispravni unos - string umjesto broja)
    - **Dvije unchecked iznimke** (npr. negativni iznosi)
2. Konfigurirati **Logback biblioteku** za kreiranje log datoteka sa svim razinama:
    - `TRACE`, `DEBUG`, `INFO`, `WARN`, `ERROR`
3. Generirati **Javadoc dokumentaciju** za sve klase i metode

---

### 4. Laboratorijska Vježba - Kolekcije

**Tema:** List, Set, Map i enumeracije

**Zahtjevi:**

1. Proširiti rješenje 3. vježbe sa **kolekcijama**:
    - `List`
    - `Set`
    - `Map`
    - **Enumeracija**
2. Zamijeniti sva polja kolekcijama
3. Implementirati sortiranje korištenjem:
    - Sučelja `Comparator`
    - **Lambda izraza**
4. Koristiti tehnike iz predavanja:
    - **Sequenced Collections**
    - **Stream Gatherers**
    - `Collectors.partitioningBy()`
    - `Collectors.groupingBy()`

---

### 5. Laboratorijska Vježba - Lambda i Generički Tipovi

**Tema:** Lambda izrazi, Optional, generici sa PECS pristupom

**Zahtjevi:**

1. Proširiti rješenje 4. vježbe s **lambda izrazima** za:
    - Filtriranje
    - Sortiranje
    - Mapiranje
    - Reduciranje
    - Grupiranje
    - Korištenje immutable i mutable zbirki
2. Zamijeniti sve `null` vrijednosti s **`Optional` tipom podatka**
3. Implementirati **generičke tipove**:
    - PECS pristup
    - Upper bounded wildcards
    - Lower bounded wildcards
    - Multiple bounds

---

### 6. Laboratorijska Vježba - Datoteke

**Tema:** JSON, serijalizacija, XML, backup

**Zahtjevi:**

1. Učitavanje podataka iz **JSON datoteka** umjesto korisničkog unosa
    - Zasebne JSON datoteke za svaki entitet
    - Novi podaci se spremi u JSON
2. Serijalizacija u **binarne datoteke** (`backup.bin`)
    - Backup funkcionalnost
    - Dohvaćanje podataka iz backupa
3. Spremi korisničke akcije u **XML format** kao log:
    - Odabir iz izbornika
    - Kreiranje backupa
    - Dohvaćanje backupa

---

### 7. Laboratorijska Vježba - JavaFX

**Tema:** Osnovna JavaFX sučelja i pretraživanje

**Zahtjevi:**

1. Kreirati novi **JavaFX projekt** u IntelliJ-u
2. Prenijeti korisne klase iz 6. vježbe
3. Kreirati **JavaFX ekrane i izbornik** za pretraživanje entiteta
4. Prikazivati **dijaloge** za greške s informativnim porukama
5. Uvesti **util klase** sa korisnim metodama

---

### 8. Laboratorijska Vježba - JavaFX UI

**Tema:** CRUD operacije u JavaFX-u

**Zahtjevi:**

1. Za svaki entitet dodati **prozor za unos novih podataka**
2. Omogućiti otvaranje prozora kroz **izbornik**
3. Prikazivati **dijaloge za validaciju** (npr. negativni iznosi, datumi)
4. Implementirati **CRUD operacije**:
    - Create
    - Read
    - Update (s potvrdom)
    - Delete (s potvrdom)
5. Uvesti **util klase** sa korisnim metodama

---

### 9. Laboratorijska Vježba - JDBC

**Tema:** Baza podataka, JDBC, properties datoteke

**Zahtjevi:**

1. Zamijeniti datotečno spremanje sa **JDBC bazom podataka**
2. Prebaciti pristupne podatke u **properties datoteku**
3. Implementirati klasu za konekciju i izvršavanje upita
4. Uvesti **util klase** sa korisnim metodama
5. Sve klase moraju biti <200 linija koda

---

### 10. Laboratorijska Vježba - Višenitnost

**Tema:** Virtualne niti, paralelni procesi, sinkronizacija

**Zahtjevi:**

1. Implementirati **virtualnu nit** za:
    - Dohvaćanje podataka iz baze (posljednji uneseni podatak)
    - Prikaz na JavaFX početnom ekranu
2. Implementirati **virtualnu nit** za:
    - Kreiranje backup tablice (`_BACKUP`)
    - Prebacivanje podataka iz originalne tablice
3. Uvesti **util klase** sa korisnim metodama
4. Koristiti **sinkronizaciju dijeljenih resursa**

---

## Sustav za Upravljanje Bolnicom - Detaljni Zahtjevi

### Opće Informacije

Aplikacija omogućuje upravljanje entitetima i operacijama specifičnim za područje **Sustav za upravljanje bolnicom**.

### Struktura Aplikacije

#### Entiteti

- Lokacija: paket `entity`
- Primjeri: `Patient`, `Doctor`, `Appointment`, `Staff`, `User`
- Apstraktna klasa: `Person` (za zajedničke podatke)
- Implementacija: **Builder pattern** za najmanje jedan entitet (npr. `PatientBuilder`)

#### Sučelja

- Primjeri: `Schedulable`, `Reservable`, `Manageable`
- Po potrebi domene

#### Glavna Klasa

- Lokacija: paket `app`
- Primjer: `HelloApplication`, `Launcher`

### Tehnički Zahtjevi

#### Iznimke

- **Marked (checked):** Prilagođene iznimke za poslovne slučajeve
- **Unmarked (unchecked):** Za logičke greške
- **Logiranje:** Logback biblioteka sa svim razinama (`TRACE`, `DEBUG`, `INFO`, `WARN`, `ERROR`)

#### Kolekcije i Lambda

- `List`, `Set`, `Map` za spremanje podataka
- **Lambda izrazi** za filtriranje i sortiranje entiteta
- Stream API sa `collect()`, `filter()`, `map()` itd.

#### Generički Tipovi

- `EntityCollection<T>` - generička kolekcija
- `Relation<A,B>` - relacije između entiteta
- PECS pristup, bounded wildcards

#### Datoteke i Serijalizacija

- **Prijava:** Korisničko ime i **hashirana lozinka** iz tekstualne datoteke
- **Promjene:** Serijalizacija u **binarne datoteke**
- **Properties:** Konfiguracija iz `.properties` datoteka

#### Baza Podataka

- **Tablice:** Povezane s entitetima aplikacije
- **Klasa za bazu:** Konekcija, izvršavanje upita, zatvaranje konekcije
- **JDBC API** za rad s bazom

#### JavaFX Ekrani

##### Prijava

- **Barem dvije uloge** (npr. doktor, recepcioner, administrator)
- Validacija korisničkog imena i lozinke

##### Upravljanje Entitetima

- **TableView** sa svim entitetima
- **CRUD operacije**:
    - Create - novi entitet
    - Read - prikaz u tablici
    - Update - izmjena s potvrdom
    - Delete - brisanje s potvrdom
- **Dijalozi za validaciju** i potvrdu akcija

#####历史Promjena

- Prikaz iz **binarne datoteke**
- Logiranje svih promjena

#### Niti i Paralelni Procesi

- **Virtualne niti (Virtual Threads)** za paralelne procese
- Primjeri:
    - Osvježavanje podataka
    - Spremanje promjena
    - Dohvaćanje iz baze
- **Sinkronizacija** dijeljenih resursa

### Kvaliteta Koda

- ✅ **Sve klase <200 linija koda**
- ✅ **SonarQube - bez problema**
- ✅ **Javadoc dokumentacija** za sve javne metode i klase

---

## Dodatne Napomene

- Sve vježbe proširuju prethodne (vježba 2 > vježba 1, vježba 3 > vježba 2, itd.)
- Fokus je na implementaciji Java koncepata, OOP principa i best practices
- Testiranje putem Blic testova i koda SonarQube alatom
  Deseta laboratorijska vježba vezana je uz predavanja i auditorne vježbe iz gradiva
  višenitnosti, vezana uz implementaciju paralelnih aktivnosti u JavaFX aplikaciji. Na
  početku laboratorijskih vježbi potrebno je riješiti „blic“ test na sustavu „Moodle“ iz tog
  gradiva. „Blic“ testovi moraju se rješavati na računalima u laboratoriju (ne vlastitim
  laptopima). Samo oni studenti koji ostvare 50% bodova iz „blic“ testa mogu pristupiti
  ostatku laboratorijske vježbe.
  Za pripremu desete laboratorijske vježbe kroz rješavanje projektnog zadatka
  potrebno je implementirati sljedeće:

1. Implementirati virtualnu nit koje će omogućavati dohvaćanje podataka iz baze
   vezanu uz željeni entitet koji se odnose na posljednje uneseni podatak u bazu.
   Dohvaćeni podatak potrebno je ispisati na početnom ekranu JavaFX
   aplikacije.
2. Implementirati virtualnu nit koja će kreirati sigurnosnu kopiju željene tablice te
   je preimenovati u „_BACKUP“ tablicu. U nju je potrebno prebaciti sve podatke
   iz originalne tablice. Tablicu je moguće odabrati po želji.
3. Uvesti „util“ klase koje će sadržavati korisne metode koje se pozivaju unutar
   aplikacije kao što je prikazano na predavanjima.
4. Nijedna klasa ne smije imati više od 200 linija koda.
5. Programski kod potrebno je skenirati pomoću SonarQube alata te ispraviti sve
   prijavljene pogreške.

Autor: doc.dr.sc. Aleksander Radovan, prof.struč.stud., e-mail: aradovan@tvz.hr

Osma laboratorijska vježba iz predmeta „Programiranje u Javi“ 2025/2026
Prijediplomski studij – smjer računarstvo
Tehničko veleučilište u Zagrebu

DEVETA LABORATORIJSKA VJEŽBA
Deveta laboratorijska vježba vezana je uz predavanja i auditorne vježbe iz gradiva
JDBC-a, vezana za pohranjivanje podataka u bazu, umjesto datoteka. Na početku
laboratorijskih vježbi potrebno je riješiti „blic“ test na sustavu „Moodle“ iz tog gradiva.
„Blic“ testovi moraju se rješavati na računalima u laboratoriju (ne vlastitim laptopima).
Samo oni studenti koji ostvare 50% bodova iz „blic“ testa mogu pristupiti ostatku
laboratorijske vježbe.
Za pripremu devete laboratorijske vježbe kroz rješavanje projektnog zadatka
potrebno je implementirati sljedeće:

1. Dijelove koda koji dohvaćaju i spremaju podatke u datoteku potrebno je
   zamijeniti modulima koji dohvaćaju i spremaju podatke u bazu podataka po
   uzoru na primjer s predavanja.
2. Pristupne podatke za bazu podataka potrebno je prebaciti u „properties“
   datoteku.
3. Uvesti „util“ klase koje će sadržavati korisne metode koje se pozivaju unutar
   aplikacije kao što je prikazano na predavanjima.
4. Nijedna klasa ne smije imati više od 200 linija koda.
5. Programski kod potrebno je skenirati pomoću SonarQube alata te ispraviti sve
   prijavljene pogreške.
   Više informacija o SonarQube alatu, načinu instaliranja i korištenju moguće je pronaći
   na sljedećim stranicama:
   https://plugins.jetbrains.com/plugin/7973-sonarqube-for-ide
   docs.sonarsource.com/sonarqube-for-intellij/getting-started/installation
   youtube.com/watch?v=mu3WnjyrAx4

Autor: doc.dr.sc. Aleksander Radovan, prof.struč.stud., e-mail: aradovan@tvz.hr

Druga laboratorijska vježba iz predmeta „Programiranje u Javi“ 2025/2026
Prijediplomski studij – smjer računarstvo
Tehničko veleučilište u Zagrebu

DRUGA LABORATORIJSKA VJEŽBA
Druga laboratorijska vježba vezana je uz predavanja i auditorne vježbe iz gradiva
objektno orijentiranog programiranja u Javi. Na početku laboratorijskih vježbi
potrebno je riješiti „blic“ test na sustavu „Moodle“ iz tog gradiva. „Blic“ testovi moraju
se rješavati na računalima u laboratoriju (ne vlastitim laptopima). Samo oni studenti
koji ostvare 50% bodova iz „blic“ testa mogu pristupiti ostatku laboratorijske vježbe.
Za pripremu druge laboratorijske vježbe kroz rješavanje projektnog zadatka
potrebno je implementirati sljedeće:

1. Proširiti rješenje prve laboratorijske vježbe s jednom apstraktnom klasu, dva
   sučelja od kojeg jedno mora biti zapečaćeno te jednim zapisom.
2. Prilagoditi ostatak programa kako bi uspješno od korisnika tražio i dodatne
   podatke koji su uvedeni sučeljima i zapisom.
3. U barem jednom primjeru potrebno je koristiti koncepte polimorfizma te u
   jedno polje dodati objekte podklasa koji imaju jednu zajedničku nadklasu te
   odrediti vrijednosti s maksimalnim i minimalnim vrijednostima prema
   odabranim parametrima, na primjer, najmlađu osobu, artikl s najvećom
   cijenom i slično.
4. Dodati „builder pattern“ za barem jednu klasu koja se koristi u aplikaciji i
   iskoristiti ga.
5. Od korisnika zatražiti unos podataka za pet objekata svake klase koji su
   dodijeljeni za projektni zadatak.
6. Dozvoljeno je koristiti samo polja (engl. array) za spremanje objekata.
7. Povezati objekte na način da jedni sadržavaju druge prema logici projektnog
   zadatka.
8. Korisniku omogućiti pretraživanje objekata prema zadanim kriterijima. Za
   pretraživanje je potrebno koristiti „for“, „while“ i „do-while“ petlje.

Autor: doc.dr.sc. Aleksander Radovan, prof.struč.stud., e-mail: aradovan@tvz.hr

Osma laboratorijska vježba iz predmeta „Programiranje u Javi“ 2025/2026
Prijediplomski studij – smjer računarstvo
Tehničko veleučilište u Zagrebu

OSMA LABORATORIJSKA VJEŽBA
Osma laboratorijska vježba vezana je uz predavanja i auditorne vježbe iz gradiva
JavaFX-a, vezana za unos i spremanje podataka u aplikaciju. Na početku
laboratorijskih vježbi potrebno je riješiti „blic“ test na sustavu „Moodle“ iz tog gradiva.
„Blic“ testovi moraju se rješavati na računalima u laboratoriju (ne vlastitim laptopima).
Samo oni studenti koji ostvare 50% bodova iz „blic“ testa mogu pristupiti ostatku
laboratorijske vježbe.
Za pripremu osme laboratorijske vježbe kroz rješavanje projektnog zadatka potrebno
je implementirati sljedeće:

1. Za svaki entitet potrebno je dodati prozor koji će omogućavati spremanje novih
   podataka. Prikaz tih dodatnih prozora potrebno je omogućiti kroz dodatne
   opcije u izborniku.
2. Prikazivati dijaloge u slučaju neispravnog popunjavanja podataka u aplikaciju
   (npr. Neispravan unos ili neunošenje podataka, negativni iznosi, datumi u
   prošlosti ili budućnosti, nekompatibilni podaci i slično).
3. Uvesti „util“ klase koje će sadržavati korisne metode koje se pozivaju unutar
   aplikacije kao što je prikazano na predavanjima.
4. Nijedna klasa ne smije imati više od 200 linija koda.
5. Programski kod potrebno je skenirati pomoću SonarQube alata te ispraviti sve
   prijavljene pogreške.
   Više informacija o SonarQube alatu, načinu instaliranja i korištenju moguće je pronaći
   na sljedećim stranicama:
   https://plugins.jetbrains.com/plugin/7973-sonarqube-for-ide
   docs.sonarsource.com/sonarqube-for-intellij/getting-started/installation
   youtube.com/watch?v=mu3WnjyrAx4

Autor: doc.dr.sc. Aleksander Radovan, prof.struč.stud., e-mail: aradovan@tvz.hr

Peta laboratorijska vježba iz predmeta „Programiranje u Javi“ 2025/2026
Prijediplomski studij – smjer računarstvo
Tehničko veleučilište u Zagrebu

PETA LABORATORIJSKA VJEŽBA
Peta laboratorijska vježba vezana je uz predavanja i auditorne vježbe iz gradiva
lambdi i generičkog programiranja u Javi. Na početku laboratorijskih vježbi potrebno
je riješiti „blic“ test na sustavu „Moodle“ iz tog gradiva. „Blic“ testovi moraju se
rješavati na računalima u laboratoriju (ne vlastitim laptopima). Samo oni studenti koji
ostvare 50% bodova iz „blic“ testa mogu pristupiti ostatku laboratorijske vježbe.
Za pripremu pete laboratorijske vježbe kroz rješavanje projektnog zadatka potrebno
je implementirati sljedeće:

1. Proširiti rješenje četvrte laboratorijske vježbe lambda izrazima u radu sa
   korištenim zbirkama: listama, setovima i mapama po pitanju filtriranja,
   sortiranja, mapiranja, reduciranja i grupiranja. Koristiti pristup temeljen na
   „immutable“ i „mutable“ zbirkama.
2. Na svim mjestima gdje se koristila „null“ vrijednost u prethodnim vježbama
   potrebno je koristiti tip podatka „Optional“. Ako nigdje nije korištena „null“
   vrijednost, iskoristiti opciju korištenja tipa „Optional“ u slučajevima kad lambda
   izrazi vraćaju vrijednost „Optional“ (na primjer, u slučaju „getFirst“ i sličnih
   metoda).
3. Proširiti rješenje četvrte laboratorijske vježbe generičkim tipovima koji
   obuhvaćaju korištenje PECS pristupa, „upper bounded wildcards“, „lower
   bounded wildcards“ i „multiple bounds“ implementacija.

Autor: doc.dr.sc. Aleksander Radovan, prof.struč.stud., e-mail: aradovan@tvz.hr

Prva laboratorijska vježba iz predmeta „Programiranje u Javi“ 2025/2026
Prijediplomski studij – smjer računarstvo
Tehničko veleučilište u Zagrebu

PRVA LABORATORIJSKA VJEŽBA
Prva laboratorijska vježba vezana je uz predavanja i auditorne vježbe iz gradiva
klasa i objekata. Na početku laboratorijskih vježbi potrebno je riješiti „blic“ test na
sustavu „Moodle“ iz tog gradiva. „Blic“ testovi moraju se rješavati na računalima u
laboratoriju (ne vlastitim laptopima). Samo oni studenti koji ostvare 50% bodova iz
„blic“ testa mogu pristupiti ostatku laboratorijske vježbe.
Za pripremu laboratorijske vježbe kroz rješavanje projektnog zadatka potrebno je
implementirati sljedeće:

1. Od korisnika zatražiti unos podataka za pet objekata svake klase koji su
   dodijeljeni za projektni zadatak.
2. Dozvoljeno je koristiti samo polja (engl. array) za spremanje objekata.
3. Povezati objekte na način da jedni sadržavaju druge prema logici projektnog
   zadatka.
4. Korisniku omogućiti pretraživanje objekata prema zadanim kriterijima. Za
   pretraživanje je potrebno koristiti „for“, „while“ i „do-while“ petlje.
5. Korisniku omogućiti pronalaženje objekata s maksimalnim i minimalnim
   vrijednostima prema odabranim parametrima, na primjer, najmlađu osobu,
   artikl s najvećom cijenom i slično.

Autor: doc.dr.sc. Aleksander Radovan, prof.struč.stud., e-mail: aradovan@tvz.hr

Sedma laboratorijska vježba iz predmeta „Programiranje u Javi“ 2025/2026
Prijediplomski studij – smjer računarstvo
Tehničko veleučilište u Zagrebu

SEDMA LABORATORIJSKA VJEŽBA
Sedma laboratorijska vježba vezana je uz predavanja i auditorne vježbe iz gradiva
JavaFX-a. Na početku laboratorijskih vježbi potrebno je riješiti „blic“ test na sustavu
„Moodle“ iz tog gradiva. „Blic“ testovi moraju se rješavati na računalima u laboratoriju
(ne vlastitim laptopima). Samo oni studenti koji ostvare 50% bodova iz „blic“ testa
mogu pristupiti ostatku laboratorijske vježbe.
Za pripremu sedme laboratorijske vježbe kroz rješavanje projektnog zadatka
potrebno je implementirati sljedeće:

1. Kreirati novi JavaFX projekt u okruženju IntelliJ te prenijeti sve korisne klase i
   datoteke iz projekta šeste laboratorijske vježbe u taj projekt.
2. Kreirati JavaFX ekrane i izbornik kao što je prikazano na predavanjima i
   auditornim vježbama koji će omogućavati pretraživanje entiteta po
   parametrima koje entiteti sadrže.
3. Prikazivati dijaloge u slučaju neispravnog popunjavanja kriterija pretraživanja s
   informativnim porukama o pogreškama.
4. Uvesti „util“ klase koje će sadržavati korisne metode koje se pozivaju unutar
   aplikacije kao što je prikazano na predavanjima.
5. Nijedna klasa ne smije imati više od 200 linija koda.
6. Programski kod potrebno je skenirati pomoću SonarQube alata te ispraviti sve
   prijavljene pogreške.
   Više informacija o SonarQube alatu, načinu instaliranja i korištenju moguće je pronaći
   na sljedećim stranicama:
   https://plugins.jetbrains.com/plugin/7973-sonarqube-for-ide
   docs.sonarsource.com/sonarqube-for-intellij/getting-started/installation
   youtube.com/watch?v=mu3WnjyrAx4

Autor: doc.dr.sc. Aleksander Radovan, prof.struč.stud., e-mail: aradovan@tvz.hr

Druga laboratorijska vježba iz predmeta „Programiranje u Javi“ 2025/2026
Prijediplomski studij – smjer računarstvo
Tehničko veleučilište u Zagrebu

Treća LABORATORIJSKA VJEŽBA
Treća laboratorijska vježba vezana je uz predavanja i auditorne vježbe iz gradiva
iznimaka u Javi i Javadoc dokumentacije. Na početku laboratorijskih vježbi potrebno
je riješiti „blic“ test na sustavu „Moodle“ iz tog gradiva. „Blic“ testovi moraju se
rješavati na računalima u laboratoriju (ne vlastitim laptopima). Samo oni studenti koji
ostvare 50% bodova iz „blic“ testa mogu pristupiti ostatku laboratorijske vježbe.
Za pripremu treće laboratorijske vježbe kroz rješavanje projektnog zadatka potrebno
je implementirati sljedeće:

1. Proširiti rješenje druge laboratorijske vježbe s dvije „checked“ iznimke u
   dijelovima koda (kod neispravnog unosa podataka, na primjer string vrijednosti
   umjesto brojčanih, nakon čega korisnik mora ponoviti unos).
2. Proširiti rješenje druge laboratorijske vježbe s dvije „unchecked“ iznimke i
   dijelovima koda (kod logičkih pogrešaka gdje to nije primjereno, na primjer kod
   unosa negativnih iznosa gdje mogu biti samo pozitivni ili kod isteka
   maksimalnog vremena čekanja na korisnički unos podataka).
3. Konfigurirati Logback biblioteku koja će omogućavati kreiranje „log“ datoteka i
   zapisivati („logirati“) najvažnije događaje u aplikacije sa svih pet razina
   logiranja (TRACE, DEBUG, INFO, WARN i ERROR).
4. Za svaku metodu i klasu generiranje Javadoc dokumentacije.

Autor: doc.dr.sc. Aleksander Radovan, prof.struč.stud., e-mail: aradovan@tvz.hr

Četvrta laboratorijska vježba iz predmeta „Programiranje u Javi“ 2025/2026
Prijediplomski studij – smjer računarstvo
Tehničko veleučilište u Zagrebu

ČETVRTA LABORATORIJSKA VJEŽBA
Četvrta laboratorijska vježba vezana je uz predavanja i auditorne vježbe iz gradiva
zbirki u Javi. Na početku laboratorijskih vježbi potrebno je riješiti „blic“ test na sustavu
„Moodle“ iz tog gradiva. „Blic“ testovi moraju se rješavati na računalima u laboratoriju
(ne vlastitim laptopima). Samo oni studenti koji ostvare 50% bodova iz „blic“ testa
mogu pristupiti ostatku laboratorijske vježbe.
Za pripremu četvrte laboratorijske vježbe kroz rješavanje projektnog zadatka
potrebno je implementirati sljedeće:

1. Proširiti rješenje treće laboratorijske vježbe s dodatnim zbirkama: listama,
   setovima i mapama te enumeracijom. S navedenim zbirkama potrebno je
   zamijeniti sva mjesta u kodu gdje se koriste polja.
2. Omogućiti sortiranje podataka korištenjem sučelja „Comparator“ i lambda
   izraza.
3. U aplikaciji koristiti tehnike i metode objašnjene u predavanjima i auditornim
   vježbama: Sequenced Collections, Stream Gatherers,
   Collectors.partitioningBy() i Collectors.groupingBy().

Autor: doc.dr.sc. Aleksander Radovan, prof.struč.stud., e-mail: aradovan@tvz.hr

Šesta laboratorijska vježba iz predmeta „Programiranje u Javi“ 2025/2026
Prijediplomski studij – smjer računarstvo
Tehničko veleučilište u Zagrebu

ŠESTA LABORATORIJSKA VJEŽBA
Šesta laboratorijska vježba vezana je uz predavanja i auditorne vježbe iz gradiva
datoteka u Javi. Na početku laboratorijskih vježbi potrebno je riješiti „blic“ test na sustavu
„Moodle“ iz tog gradiva. „Blic“ testovi moraju se rješavati na računalima u laboratoriju
(ne vlastitim laptopima). Samo oni studenti koji ostvare 50% bodova iz „blic“ testa
mogu pristupiti ostatku laboratorijske vježbe.
Za pripremu šeste laboratorijske vježbe kroz rješavanje projektnog zadatka potrebno
je implementirati sljedeće:

1. Proširiti rješenje pete laboratorijske vježbe na način da se umjesto unošenja
   podataka o strane korisnika podaci učitavaju iz JSON datoteka. Potrebno je
   kreirati zasebne JSON datoteke za svaki entitet u aplikaciji. Svaki novokreirani
   podatak također je potrebno spremiti u JSON datoteku.
2. Proširiti rješenje pete laboratorijske vježbe na način da se svi učitani podaci iz
   JSON datoteke serijaliziraju u jednu „backup.bin“ datoteku koja će imati
   funkciju pričuvne kopije na zahtjev korisnika. Nakon toga potrebno je
   omogućiti korisniku da dohvati te podatke i „pregazi“ trenutne podatke u
   aplikaciji s podacima iz pričuvne kopije.
3. Proširiti rješenje pete laboratorijske vježbe na način da se podaci koje unosi
   korisnik pri odabiru opcija koje nudi aplikacija (na primjer, odabir opcije iz
   izbornika, kreiranje pričuvne kopije, dohvaćanje podataka iz pričuvne kopije i
   slično) spreme u XML formatu kao svojevrsni „log“. Na zahtjev korisnika
   potrebno je ispisati podatke spremljene u XML formatu (samo bez „tagova“ za
   formatiranje XML-a).

Autor: doc.dr.sc. Aleksander Radovan, prof.struč.stud., e-mail: aradovan@tvz.hr

Sustav za upravljanje bolnicom
Aplikacija omogućuje upravljanje entitetima i operacijama specifičnim za područje "Sustav za upravljanje bolnicom".
Entiteti (npr. User, Booking, Record, Item) moraju biti u paketu `entity`, dok se glavna klasa nalazi u paketu `app`.
Treba definirati apstraktnu klasu (npr. Person) i sučelja (npr. Reservable, Schedulable) prema potrebi domene.
Jedan od entiteta mora koristiti builder pattern za konstrukciju objekata.

Potrebno je implementirati označene i neoznačene iznimke koje se logiraju korištenjem Logback biblioteke.
Kolekcije List, Set i Map koriste se za spremanje podataka, a lambda izrazi za filtriranje i sortiranje entiteta.

Generičke klase, npr. EntityCollection<T> i Relation<A,B>, moraju biti implementirane.
Podaci za prijavu (korisničko ime i hashirana lozinka) čitaju se iz tekstualne datoteke, dok se promjene entiteta
serijaliziraju u binarne datoteke.

JavaFX ekrani moraju omogućiti prijavu korisnika (barem dvije uloge), upravljanje entitetima kroz TableView (CRUD
operacije uz potvrdu izmjena i brisanja)
te prikaz povijesti promjena učitanih iz binarne datoteke.

Baza podataka mora sadržavati tablice povezane s entitetima aplikacije te klasu za konekciju, izvršavanje upita i
zatvaranje konekcije.
Niti se koriste za paralelne procese (npr. istovremeno osvježavanje podataka i spremanje promjena), uz sinkronizaciju
dijeljenih resursa.

Sve klase moraju biti kraće od 200 linija koda, bez SonarQube problema i s Javadoc dokumentacijom.

