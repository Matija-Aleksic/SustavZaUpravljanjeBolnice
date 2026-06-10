-- ========================================
-- HOSPITALS
-- ========================================

INSERT INTO hospital (name, address, phone_number, email)
VALUES ('University Hospital Zagreb', 'Kišpatićeva 12, Zagreb', '+38512345601', 'info@uhz.hr'),
       ('General Hospital Split', 'Spinčićeva 1, Split', '+38512345602', 'info@obs.hr'),
       ('Clinical Hospital Rijeka', 'Krešimirova 42, Rijeka', '+38512345603', 'info@kbr.hr');

-- ========================================
-- DEPARTMENTS
-- ========================================

INSERT INTO department (name, hospital_id)
VALUES ('Cardiology', 1),
       ('Neurology', 1),
       ('Emergency', 1),
       ('Oncology', 1),

       ('Cardiology', 2),
       ('Emergency', 2),
       ('Orthopedics', 2),

       ('Pediatrics', 3),
       ('Neurology', 3),
       ('Radiology', 3);

-- ========================================
-- WARDS
-- ========================================

INSERT INTO ward (name, max_capacity, capacity, department_id)
VALUES ('Ward A1', 40, 23, 1),
       ('Ward A2', 30, 17, 1),
       ('Ward B1', 25, 10, 2),
       ('Ward C1', 50, 42, 3),
       ('Ward D1', 20, 15, 4),

       ('Ward E1', 35, 18, 5),
       ('Ward F1', 45, 27, 6),
       ('Ward G1', 40, 11, 7),

       ('Ward H1', 30, 21, 8),
       ('Ward I1', 30, 16, 9),
       ('Ward J1', 25, 9, 10);

-- ========================================
-- STAFF
-- ========================================

INSERT INTO staff
(first_name, last_name, oib, birth_date, role, permissions, email, salary, hospital_id)
VALUES ('Ivan', 'Horvat', '10000000001', '1980-05-10', 'DOCTOR', NULL, 'ivan.horvat@uhz.hr', 3500, 1),
       ('Marko', 'Kovač', '10000000002', '1975-03-11', 'DOCTOR', NULL, 'marko.kovac@uhz.hr', 4200, 1),
       ('Ana', 'Marić', '10000000003', '1988-09-15', 'DOCTOR', NULL, 'ana.maric@uhz.hr', 3700, 1),
       ('Petra', 'Novak', '10000000004', '1984-06-20', 'DOCTOR', NULL, 'petra.novak@uhz.hr', 3900, 1),

       ('Josip', 'Babić', '10000000005', '1982-02-22', 'DOCTOR', NULL, 'josip.babic@obs.hr', 3600, 2),
       ('Lucija', 'Matić', '10000000006', '1990-12-01', 'DOCTOR', NULL, 'lucija.matic@obs.hr', 3400, 2),
       ('Nikola', 'Jurić', '10000000007', '1981-04-17', 'DOCTOR', NULL, 'nikola.juric@obs.hr', 4100, 2),

       ('Maja', 'Grgić', '10000000008', '1987-08-19', 'DOCTOR', NULL, 'maja.grgic@kbr.hr', 3800, 3),
       ('Tomislav', 'Perić', '10000000009', '1979-01-30', 'DOCTOR', NULL, 'tomislav.peric@kbr.hr', 4300, 3),

       ('Sara', 'Božić', '10000000010', '1992-07-13', 'NURSE', NULL, 'sara.bozic@uhz.hr', 1600, 1),
       ('Karla', 'Šimić', '10000000011', '1994-08-12', 'NURSE', NULL, 'karla.simic@uhz.hr', 1650, 1),
       ('Mia', 'Knežević', '10000000012', '1993-11-09', 'NURSE', NULL, 'mia.knezevic@obs.hr', 1700, 2),
       ('Lea', 'Vuković', '10000000013', '1991-05-28', 'NURSE', NULL, 'lea.vukovic@kbr.hr', 1750, 3),

       ('Antonio', 'Radić', '10000000014', '1985-10-10', 'RECEPTIONIST', NULL, 'antonio.radic@uhz.hr', 1400, 1),
       ('Iva', 'Pavlović', '10000000015', '1986-09-22', 'RECEPTIONIST', NULL, 'iva.pavlovic@obs.hr', 1450, 2),

       ('Admin', 'System', '10000000016', '1970-01-01', 'RECEPTIONIST', 'FULL', 'admin@hospital.hr', 5000, 1);

-- ========================================
-- PATIENTS
-- ========================================

INSERT INTO patient
(first_name, last_name, oib, birth_date, status, mbo, hospital_id, assigned_doctor_id)
VALUES ('Luka', 'Horvat', '20000000001', '2001-01-10', 'APPROVED', 'MBO000001', 1, 1),
       ('Matej', 'Kovačić', '20000000002', '1998-02-11', 'PENDING', 'MBO000002', 1, 1),
       ('Filip', 'Marić', '20000000003', '1987-03-15', 'COMPLETED', 'MBO000003', 1, 2),
       ('Petar', 'Novak', '20000000004', '1978-07-20', 'APPROVED', 'MBO000004', 1, 2),
       ('Ivana', 'Klarić', '20000000005', '1993-08-09', 'APPROVED', 'MBO000005', 1, 3),
       ('Marina', 'Jurić', '20000000006', '1968-12-22', 'REJECTED', 'MBO000006', 1, 3),
       ('Katarina', 'Matić', '20000000007', '2000-05-05', 'APPROVED', 'MBO000007', 1, 4),
       ('Dario', 'Šimić', '20000000008', '1999-06-14', 'PENDING', 'MBO000008', 1, 4),

       ('Bruno', 'Barišić', '20000000009', '1988-03-04', 'APPROVED', 'MBO000009', 2, 5),
       ('Kristina', 'Božić', '20000000010', '1995-11-18', 'APPROVED', 'MBO000010', 2, 5),
       ('Ena', 'Perković', '20000000011', '1981-10-30', 'COMPLETED', 'MBO000011', 2, 6),
       ('Marijan', 'Lončar', '20000000012', '1970-04-16', 'APPROVED', 'MBO000012', 2, 6),
       ('Toni', 'Rukavina', '20000000013', '2003-02-21', 'PENDING', 'MBO000013', 2, 7),
       ('Vanja', 'Prpić', '20000000014', '1992-09-07', 'APPROVED', 'MBO000014', 2, 7),

       ('Nina', 'Blažević', '20000000015', '1990-01-12', 'APPROVED', 'MBO000015', 3, 8),
       ('Helena', 'Križan', '20000000016', '1984-06-01', 'COMPLETED', 'MBO000016', 3, 8),
       ('Robert', 'Cindrić', '20000000017', '1976-07-09', 'APPROVED', 'MBO000017', 3, 9),
       ('Mario', 'Burić', '20000000018', '1991-10-11', 'CANCELLED', 'MBO000018', 3, 9),
       ('Ana', 'Lukić', '20000000019', '2002-04-20', 'APPROVED', 'MBO000019', 3, 8),
       ('Tanja', 'Zorić', '20000000020', '1986-12-17', 'PENDING', 'MBO000020', 3, 9);

-- ========================================
-- APPOINTMENTS
-- ========================================

INSERT INTO appointment
    (doctor_id, patient_id, date_time)
VALUES (1, 1, '2026-06-15 09:00:00'),
       (1, 2, '2026-06-15 09:30:00'),
       (1, 1, '2026-07-10 11:00:00'),
       (2, 3, '2026-06-16 10:00:00'),
       (2, 4, '2026-06-16 10:30:00'),
       (3, 5, '2026-06-17 08:30:00'),
       (3, 6, '2026-06-17 09:00:00'),
       (4, 7, '2026-06-18 12:00:00'),
       (4, 8, '2026-06-18 12:30:00'),

       (5, 9, '2026-06-19 10:00:00'),
       (5, 10, '2026-06-19 10:30:00'),
       (6, 11, '2026-06-20 11:00:00'),
       (6, 12, '2026-06-20 11:30:00'),
       (7, 13, '2026-06-21 09:00:00'),
       (7, 14, '2026-06-21 09:30:00'),

       (8, 15, '2026-06-22 13:00:00'),
       (8, 16, '2026-06-22 13:30:00'),
       (8, 19, '2026-06-22 14:00:00'),
       (9, 17, '2026-06-23 15:00:00'),
       (9, 18, '2026-06-23 15:30:00'),
       (9, 20, '2026-06-23 16:00:00');

-- ========================================
-- PRESCRIPTIONS
-- ========================================

INSERT INTO prescription
    (id, name, description, doctor_id, patient_id, start_date, end_date)
VALUES ('RX001', 'Amoxicillin', 'Antibiotic treatment', 1, 1, '2026-06-01', '2026-06-10'),
       ('RX002', 'Ibuprofen', 'Pain relief', 1, 2, '2026-06-01', '2026-06-07'),
       ('RX003', 'Metformin', 'Diabetes treatment', 2, 3, '2026-05-20', '2026-08-20'),
       ('RX004', 'Atorvastatin', 'Cholesterol reduction', 2, 4, '2026-05-01', '2026-11-01'),
       ('RX005', 'Paracetamol', 'Fever management', 3, 5, '2026-06-02', '2026-06-09'),
       ('RX006', 'Losartan', 'Blood pressure control', 3, 6, '2026-05-15', '2026-12-15'),
       ('RX007', 'Omeprazole', 'Acid reflux treatment', 4, 7, '2026-06-03', '2026-07-03'),
       ('RX008', 'Cetirizine', 'Allergy treatment', 4, 8, '2026-06-01', '2026-06-14'),

       ('RX009', 'Amoxicillin', 'Antibiotic treatment', 5, 9, '2026-06-01', '2026-06-10'),
       ('RX010', 'Ibuprofen', 'Pain relief', 5, 10, '2026-06-01', '2026-06-07'),
       ('RX011', 'Metformin', 'Diabetes treatment', 6, 11, '2026-05-20', '2026-08-20'),
       ('RX012', 'Atorvastatin', 'Cholesterol reduction', 6, 12, '2026-05-01', '2026-11-01'),
       ('RX013', 'Paracetamol', 'Fever management', 7, 13, '2026-06-02', '2026-06-09'),
       ('RX014', 'Losartan', 'Blood pressure control', 7, 14, '2026-05-15', '2026-12-15'),

       ('RX015', 'Omeprazole', 'Acid reflux treatment', 8, 15, '2026-06-03', '2026-07-03'),
       ('RX016', 'Cetirizine', 'Allergy treatment', 8, 16, '2026-06-01', '2026-06-14'),
       ('RX017', 'Metformin', 'Diabetes treatment', 9, 17, '2026-05-20', '2026-08-20'),
       ('RX018', 'Atorvastatin', 'Cholesterol reduction', 9, 18, '2026-05-01', '2026-11-01'),
       ('RX019', 'Paracetamol', 'Pain management', 8, 19, '2026-06-05', '2026-06-12'),
       ('RX020', 'Losartan', 'Hypertension treatment', 9, 20, '2026-04-01', '2026-10-01');
