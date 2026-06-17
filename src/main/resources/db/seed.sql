--- ==========================================
--- 1. SEED HOSPITALS (5 rows)
--- ==========================================
INSERT INTO hospital (name, address, phone_number, email)
SELECT
    CASE CAST(X AS INT)
        WHEN 1 THEN 'KBC Zagreb' WHEN 2 THEN 'KBC Split' WHEN 3 THEN 'KBC Rijeka'
        WHEN 4 THEN 'KB Merkur' ELSE 'KB Dubrava'
        END AS name,
    CONCAT('Ulica ', CAST(FLOOR(RAND() * 100) + 1 AS INT), ', Croatia') AS address,
    CONCAT('+385 1 ', CAST(FLOOR(RAND() * 900000 + 100000) AS INT)) AS phone_number,
    CONCAT('hospital_', X, '@hospital.hr') AS email
FROM SYSTEM_RANGE(1, 5) AS target(X);

--- ==========================================
--- 2. SEED DEPARTMENTS (~35 rows)
--- ==========================================
INSERT INTO department (name, hospital_id)
SELECT
    dept.name,
    h.id
FROM hospital h
         CROSS JOIN (
    SELECT 'Cardiology' AS name UNION ALL SELECT 'Neurology' UNION ALL
    SELECT 'Pediatrics' UNION ALL SELECT 'Orthopedics' UNION ALL
    SELECT 'Internal Medicine' UNION ALL SELECT 'Emergency Room' UNION ALL
    SELECT 'Oncology'
) dept;

--- ==========================================
--- 3. SEED STAFF (200 rows) - 100% UNIQUE NAME GRID
--- ==========================================
INSERT INTO staff (first_name, last_name, oib, birth_date, role, permissions, email, salary, phone_number, address, hospital_id)
SELECT
    -- Sequential math cycles through 15 first names cleanly
    CASE CAST(MOD(X, 15) + 1 AS INT)
        WHEN 1 THEN 'Ivan' WHEN 2 THEN 'Luka' WHEN 3 THEN 'Marko' WHEN 4 THEN 'Ana' WHEN 5 THEN 'Marija'
        WHEN 6 THEN 'Petra'
        WHEN 7 THEN 'Josip'
        WHEN 8 THEN 'Stjepan'
        WHEN 9 THEN 'Ivana'
        WHEN 10 THEN 'Martina'
        WHEN 11 THEN 'Filip'
        WHEN 12 THEN 'Karlo'
        WHEN 13 THEN 'Lucija'
        WHEN 14 THEN 'Lana'
        ELSE 'Matej'
        END AS first_name,
    -- Moves to a new last name every 15 rows, creating 210 unique pair combinations
    CASE CAST(FLOOR((X - 1) / 15) AS INT) + 1
        WHEN 1 THEN 'Horvat' WHEN 2 THEN 'Kovačević' WHEN 3 THEN 'Babić' WHEN 4 THEN 'Marić' WHEN 5 THEN 'Jurić'
        WHEN 6 THEN 'Novak'
        WHEN 7 THEN 'Kovačić'
        WHEN 8 THEN 'Knežević'
        WHEN 9 THEN 'Vuković'
        WHEN 10 THEN 'Marković'
        WHEN 11 THEN 'Petrović'
        WHEN 12 THEN 'Matić'
        WHEN 13 THEN 'Tomić'
        WHEN 14 THEN 'Kovač'
        ELSE 'Pavlović'
        END AS last_name,
    CAST(10000000000 + X AS VARCHAR) AS oib,
    DATEADD('DAY', -CAST(FLOOR(RAND() * 15000 + 9000) AS INT), CURRENT_DATE) AS birth_date,
    CASE CAST(FLOOR(RAND() * 4) + 1 AS INT)
        WHEN 1 THEN 'NURSE'
        WHEN 2 THEN 'STUDENT'
        WHEN 3 THEN 'RECEPTIONIST'
        ELSE 'DOCTOR' END AS role,
    CASE CAST(FLOOR(RAND() * 5) + 1 AS INT)
        WHEN 1 THEN 'READ'
        WHEN 2 THEN 'WRITE'
        WHEN 3 THEN 'DELETE'
        WHEN 4 THEN 'UPDATE'
        ELSE 'FULL' END AS permissions,
    CONCAT('staff.', X, '@hospital.hr') AS email,
    ROUND(RAND() * (3500 - 1200) + 1200, 2) AS salary,
    CONCAT('+385 91 ', CAST(FLOOR(RAND() * 9000000 + 1000000) AS INT)) AS phone_number,
    CONCAT('Staff Address ', X) AS address,
    (SELECT id FROM hospital ORDER BY RAND(X) LIMIT 1) AS hospital_id
FROM SYSTEM_RANGE(1, 200) AS target(X);

--- ==========================================
--- 4. SEED WARDS (~70 rows)
--- ==========================================
INSERT INTO ward (name, max_capacity, capacity, department_id, nurse_id)
SELECT
    CONCAT(w_names.name, ' - Sec ', w_sec.sec) AS name,
    30 AS max_capacity,
    0 AS capacity,
    d.id AS department_id,
    (SELECT id FROM staff WHERE role = 'NURSE' ORDER BY RAND(d.id) LIMIT 1) AS nurse_id
FROM department d
         CROSS JOIN (SELECT 'Ward A' AS name UNION ALL SELECT 'Ward B' UNION ALL SELECT 'ICU') w_names
         CROSS JOIN (SELECT 1 AS sec UNION ALL SELECT 2 AS sec) w_sec
LIMIT 70;

--- ==========================================
--- 5. SEED PATIENTS (2,000 rows) - NO DUPLICATE NAMES
--- ==========================================
INSERT INTO patient (first_name, last_name, oib, birth_date, status, mbo, hospital_id, assigned_doctor_id, ward_id)
SELECT
    CASE CAST(FLOOR(RAND() * 10) + 1 AS INT)
        WHEN 1 THEN 'Ivan' WHEN 2 THEN 'Luka' WHEN 3 THEN 'Marko' WHEN 4 THEN 'Ana' WHEN 5 THEN 'Marija'
        WHEN 6 THEN 'Petra' WHEN 7 THEN 'Josip' WHEN 8 THEN 'Stjepan' WHEN 9 THEN 'Ivana' ELSE 'Martina'
        END AS first_name,
    -- Appending X guarantees that every patient profile name is completely unique
    CONCAT(
            CASE CAST(FLOOR(RAND() * 10) + 1 AS INT)
                WHEN 1 THEN 'Horvat'
                WHEN 2 THEN 'Kovačević'
                WHEN 3 THEN 'Babić'
                WHEN 4 THEN 'Marić'
                WHEN 5 THEN 'Jurić'
                WHEN 6 THEN 'Novak'
                WHEN 7 THEN 'Kovačić'
                WHEN 8 THEN 'Knežević'
                WHEN 9 THEN 'Vuković'
                ELSE 'Marković'
                END, ' ', X
    )                                                                     AS last_name,
    CAST(20000000000 + X AS VARCHAR) AS oib,
    DATEADD('DAY', -CAST(FLOOR(RAND() * 28000 + 365) AS INT), CURRENT_DATE) AS birth_date,
    CASE CAST(FLOOR(RAND() * 5) + 1 AS INT)
        WHEN 1 THEN 'PENDING' WHEN 2 THEN 'APPROVED' WHEN 3 THEN 'REJECTED' WHEN 4 THEN 'CANCELLED' ELSE 'COMPLETED'
        END AS status,
    CAST(900000000 + X AS VARCHAR) AS mbo,
    (SELECT id FROM hospital ORDER BY RAND(X) LIMIT 1)                    AS hospital_id,
    (SELECT id FROM staff WHERE role = 'DOCTOR' ORDER BY RAND(X) LIMIT 1) AS assigned_doctor_id,
    (SELECT id FROM ward ORDER BY RAND(X) LIMIT 1)                        AS ward_id
FROM SYSTEM_RANGE(1, 2000) AS target(X);

--- ==========================================
--- 6. CALIBRATE WARD CAPACITIES
--- ==========================================
MERGE INTO ward KEY(id)
    SELECT
        w.id,
        w.name,
        w.max_capacity,
        (SELECT COUNT(*) FROM patient p WHERE p.ward_id = w.id) AS capacity,
        w.department_id,
        w.nurse_id
    FROM ward w;

--- ==========================================
--- 7. SEED APPOINTMENTS (5,000 rows)
--- ==========================================
INSERT INTO appointment (doctor_id, patient_id, date_time)
SELECT (SELECT assigned_doctor_id FROM patient WHERE id = r.patient_id) AS doctor_id,
       r.patient_id,
    DATEADD('DAY', CAST(FLOOR(RAND() * 30 - 15) AS INT), CURRENT_TIMESTAMP) AS date_time
FROM (SELECT CAST(FLOOR(RAND() * 2000) + 1 AS INT) AS patient_id
      FROM SYSTEM_RANGE(1, 5000)) r
WHERE (SELECT assigned_doctor_id FROM patient WHERE id = r.patient_id) IS NOT NULL;

--- ==========================================
--- 8. SEED PRESCRIPTIONS (3,000 rows)
--- ==========================================
INSERT INTO prescription (id, name, description, doctor_id, patient_id, start_date, end_date)
SELECT CONCAT('RX-', r.X, '-', CAST(FLOOR(RAND() * 10000) AS INT))      AS id,
    CASE CAST(FLOOR(RAND() * 6) + 1 AS INT)
        WHEN 1 THEN 'Ibuprofen 400mg' WHEN 2 THEN 'Amoxicillin 500mg' WHEN 3 THEN 'Paracetamol 500mg'
        WHEN 4 THEN 'Lisinopril 10mg' WHEN 5 THEN 'Atorvastatin 20mg' ELSE 'Metformin 850mg'
        END AS name,
    'Take once daily after meals.' AS description,
       (SELECT assigned_doctor_id FROM patient WHERE id = r.patient_id) AS doctor_id,
       r.patient_id,
    DATEADD('DAY', -CAST(FLOOR(RAND() * 10) + 1 AS INT), CURRENT_DATE) AS start_date,
    DATEADD('DAY', CAST(FLOOR(RAND() * 30) + 5 AS INT), CURRENT_DATE) AS end_date
FROM (SELECT X, CAST(FLOOR(RAND() * 2000) + 1 AS INT) AS patient_id
      FROM SYSTEM_RANGE(1, 3000)) r
WHERE (SELECT assigned_doctor_id FROM patient WHERE id = r.patient_id) IS NOT NULL;

--- ==========================================
--- 9. SEED MANUAL ADMINS - ISOLATED UNIQUE NAMES
--- ==========================================
INSERT INTO staff (first_name, last_name, oib, birth_date, role, permissions, email, salary, phone_number, address,
                   hospital_id)
VALUES ('Goran', 'Radić', '12345678901', '1985-04-12', 'ADMIN', 'FULL', 'goran.radic@hospital.com', 2500.00,
        '+385 91 123 4567', 'Ilica 10, Zagreb', 1),
       ('Elena', 'Brkić', '23456789012', '1990-08-23', 'ADMIN', 'FULL', 'elena.brkic@hospital.com', 2500.00,
        '+385 92 987 6543', 'Vukovarska 45, Split', 1),
       ('Davor', 'Blažević', '34567890123', '1978-11-02', 'ADMIN', 'FULL', 'davor.blazeveic@hospital.com', 2700.00,
        '+385 95 444 5555', 'Strossmayerova 12, Osijek', 1),
       ('Zrinka', 'Krpan', '45678901234', '1993-01-15', 'ADMIN', 'FULL', 'zrinka.krpan@hospital.com', 2400.00,
        '+385 98 777 8888', 'Korzo 4, Rijeka', 1);