
-- ========================================
-- HOSPITALS
-- ========================================
INSERT INTO hospital (id, name, address, phone_number, email)
VALUES (1, 'University Hospital Zagreb', 'Kišpatićeva 12, Zagreb', '+38512345601', 'info@uhz.hr'),
       (2, 'General Hospital Split', 'Spinčićeva 1, Split', '+38512345602', 'info@obs.hr'),
       (3, 'Clinical Hospital Rijeka', 'Krešimirova 42, Rijeka', '+38512345603', 'info@kbr.hr');

-- ========================================
-- DEPARTMENTS
-- ========================================
INSERT INTO department (id, name, hospital_id)
VALUES (1, 'Cardiology', 1),
       (2, 'Neurology', 1),
       (3, 'Emergency', 1),
       (4, 'Oncology', 1),
       (5, 'Psychiatry', 1), -- Expanded

       (6, 'Cardiology', 2),
       (7, 'Emergency', 2),
       (8, 'Orthopedics', 2),
       (9, 'Pediatrics', 2), -- Expanded

       (10, 'Pediatrics', 3),
       (11, 'Neurology', 3),
       (12, 'Radiology', 3),
       (13, 'Dermatology', 3);
-- Expanded

-- ========================================
-- WARDS
-- ========================================
INSERT INTO ward (id, name, max_capacity, capacity, department_id)
VALUES (1, 'Ward A1 - Intensive Cardio', 40, 23, 1),
       (2, 'Ward A2 - General Cardio', 30, 17, 1),
       (3, 'Ward B1 - Stroke Unit', 25, 10, 2),
       (4, 'Ward C1 - Triage', 50, 42, 3),
       (5, 'Ward D1 - Chemotherapy', 20, 15, 4),
       (6, 'Ward E1 - Acute Psych', 15, 8, 5),

       (7, 'Ward F1 - Cardio Care', 35, 18, 6),
       (8, 'Ward G1 - ER Observation', 45, 27, 7),
       (9, 'Ward H1 - Trauma & Ortho', 40, 11, 8),
       (10, 'Ward I1 - Neonatal ICU', 15, 6, 9),

       (11, 'Ward J1 - General Peds', 30, 21, 10),
       (12, 'Ward K1 - Neuro Rehab', 30, 16, 11),
       (13, 'Ward L1 - Diagnostics', 25, 9, 12),
       (14, 'Ward M1 - Skin & Allergy', 20, 5, 13);

-- ========================================
-- STAFF
-- ========================================
INSERT INTO staff (id, first_name, last_name, oib, birth_date, role, permissions, email, salary, phone_number, address,
                   hospital_id)
VALUES
-- Hospital 1 Staff (Zagreb)
(1, 'Ivan', 'Horvat', '10000000001', '1980-05-10', 'DOCTOR', NULL, 'ivan.horvat@uhz.hr', 3500, '+385915550001',
 'Ilica 45, Zagreb', 1),
(2, 'Marko', 'Kovač', '10000000002', '1975-03-11', 'DOCTOR', NULL, 'marko.kovac@uhz.hr', 4200, '+385915550002',
 'Maksimirska 10, Zagreb', 1),
(3, 'Ana', 'Marić', '10000000003', '1988-09-15', 'DOCTOR', NULL, 'ana.maric@uhz.hr', 3700, '+385915550003',
 'Vukovarska 88, Zagreb', 1),
(4, 'Petra', 'Novak', '10000000004', '1984-06-20', 'DOCTOR', NULL, 'petra.novak@uhz.hr', 3900, '+385915550004',
 'Savska 120, Zagreb', 1),
(5, 'Stjepan', 'Radić', '10000000017', '1979-11-02', 'DOCTOR', NULL, 'stjepan.radic@uhz.hr', 4100, '+385915550017',
 'Dubrava 14, Zagreb', 1),
(6, 'Sara', 'Božić', '10000000010', '1992-07-13', 'NURSE', NULL, 'sara.bozic@uhz.hr', 1600, '+385915550010',
 'Zvonimirova 3, Zagreb', 1),
(7, 'Karla', 'Šimić', '10000000011', '1994-08-12', 'NURSE', NULL, 'karla.simic@uhz.hr', 1650, '+385915550011',
 'Ozaljska 54, Zagreb', 1),
(8, 'Antonio', 'Radić', '10000000014', '1985-10-10', 'RECEPTIONIST', NULL, 'antonio.radic@uhz.hr', 1400,
 '+385915550014', 'Horvaćanska 22, Zagreb', 1),
(9, 'Admin', 'System', '10000000016', '1970-01-01', 'ADMIN', 'FULL', 'admin@hospital.hr', 5000, '+385915550016',
 'Kišpatićeva 12, Zagreb', 1),

-- Hospital 2 Staff (Split)
(10, 'Josip', 'Babić', '10000000005', '1982-02-22', 'DOCTOR', NULL, 'josip.babic@obs.hr', 3600, '+385925550005',
 'Riva 3, Split', 2),
(11, 'Lucija', 'Matić', '10000000006', '1990-12-01', 'DOCTOR', NULL, 'lucija.matic@obs.hr', 3400, '+385925550006',
 'Poljička 15, Split', 2),
(12, 'Nikola', 'Jurić', '10000000007', '1981-04-17', 'DOCTOR', NULL, 'nikola.juric@obs.hr', 4100, '+385925550007',
 'Vukovarska 40, Split', 2),
(13, 'Mia', 'Knežević', '10000000012', '1993-11-09', 'NURSE', NULL, 'mia.knezevic@obs.hr', 1700, '+385925550012',
 'Domovinskog rata 22, Split', 2),
(14, 'Iva', 'Pavlović', '10000000015', '1986-09-22', 'RECEPTIONIST', NULL, 'iva.pavlovic@obs.hr', 1450, '+385925550015',
 'Zoranićeva 5, Split', 2),

-- Hospital 3 Staff (Rijeka)
(15, 'Maja', 'Grgić', '10000000008', '1987-08-19', 'DOCTOR', NULL, 'maja.grgic@kbr.hr', 3800, '+385935550008',
 'Korzo 12, Rijeka', 3),
(16, 'Tomislav', 'Perić', '10000000009', '1979-01-30', 'DOCTOR', NULL, 'tomislav.peric@kbr.hr', 4300, '+385935550009',
 'Fiorello la Guardia 4, Rijeka', 3),
(17, 'Elena', 'Vranić', '10000000018', '1983-04-25', 'DOCTOR', NULL, 'elena.vranic@kbr.hr', 4000, '+385935550018',
 'Opatijska 32, Rijeka', 3),
(18, 'Lea', 'Vuković', '10000000013', '1991-05-28', 'NURSE', NULL, 'lea.vukovic@kbr.hr', 1750, '+385935550013',
 'Vukovarska 19, Rijeka', 3);

-- ========================================
-- PATIENTS
-- ========================================
INSERT INTO patient (id, first_name, last_name, oib, birth_date, status, mbo, hospital_id, assigned_doctor_id)
VALUES
-- Hospital 1 Patients
(1, 'Luka', 'Horvat', '20000000001', '2001-01-10', 'APPROVED', 'MBO000001', 1, 1),
(2, 'Matej', 'Kovačić', '20000000002', '1998-02-11', 'PENDING', 'MBO000002', 1, 1),
(3, 'Filip', 'Marić', '20000000003', '1987-03-15', 'COMPLETED', 'MBO000003', 1, 2),
(4, 'Petar', 'Novak', '20000000004', '1978-07-20', 'APPROVED', 'MBO000004', 1, 2),
(5, 'Ivana', 'Klarić', '20000000005', '1993-08-09', 'APPROVED', 'MBO000005', 1, 3),
(6, 'Marina', 'Jurić', '20000000006', '1968-12-22', 'REJECTED', 'MBO000006', 1, 3),
(7, 'Katarina', 'Matić', '20000000007', '2000-05-05', 'APPROVED', 'MBO000007', 1, 4),
(8, 'Dario', 'Šimić', '20000000008', '1999-06-14', 'PENDING', 'MBO000008', 1, 4),
(9, 'Borist', 'Vukić', '20000000021', '1965-11-30', 'APPROVED', 'MBO000021', 1, 5),

-- Hospital 2 Patients
(10, 'Bruno', 'Barišić', '20000000009', '1988-03-04', 'APPROVED', 'MBO000009', 2, 10),
(11, 'Kristina', 'Božić', '20000000010', '1995-11-18', 'APPROVED', 'MBO000010', 2, 10),
(12, 'Ena', 'Perković', '20000000011', '1981-10-30', 'COMPLETED', 'MBO000011', 2, 11),
(13, 'Marijan', 'Lončar', '20000000012', '1970-04-16', 'APPROVED', 'MBO000012', 2, 11),
(14, 'Toni', 'Rukavina', '20000000013', '2003-02-21', 'PENDING', 'MBO000013', 2, 12),
(15, 'Vanja', 'Prpić', '20000000014', '1992-09-07', 'APPROVED', 'MBO000014', 2, 12),

-- Hospital 3 Patients
(16, 'Nina', 'Blažević', '20000000015', '1990-01-12', 'APPROVED', 'MBO000015', 3, 15),
(17, 'Helena', 'Križan', '20000000016', '1984-06-01', 'COMPLETED', 'MBO000016', 3, 15),
(18, 'Robert', 'Cindrić', '20000000017', '1976-07-09', 'APPROVED', 'MBO000017', 3, 16),
(19, 'Mario', 'Burić', '20000000018', '1991-10-11', 'CANCELLED', 'MBO000018', 3, 16),
(20, 'Ana', 'Lukić', '20000000019', '2002-04-20', 'APPROVED', 'MBO000019', 3, 15),
(21, 'Tanja', 'Zorić', '20000000020', '1986-12-17', 'PENDING', 'MBO000020', 3, 16),
(22, 'Goran', 'Karan', '20000000022', '1973-05-14', 'APPROVED', 'MBO000022', 3, 17);

-- ========================================
-- APPOINTMENTS
-- ========================================
INSERT INTO appointment (id, doctor_id, patient_id, date_time)
VALUES
-- Hospital 1 Appointments
(1, 1, 1, '2026-06-15 09:00:00'),
(2, 1, 2, '2026-06-15 09:30:00'),
(3, 1, 1, '2026-07-10 11:00:00'),
(4, 2, 3, '2026-06-16 10:00:00'),
(5, 2, 4, '2026-06-16 10:30:00'),
(6, 3, 5, '2026-06-17 08:30:00'),
(7, 3, 6, '2026-06-17 09:00:00'),
(8, 4, 7, '2026-06-18 12:00:00'),
(9, 4, 8, '2026-06-18 12:30:00'),
(10, 5, 9, '2026-06-18 14:00:00'),

-- Hospital 2 Appointments
(11, 10, 10, '2026-06-19 10:00:00'),
(12, 10, 11, '2026-06-19 10:30:00'),
(13, 11, 12, '2026-06-20 11:00:00'),
(14, 11, 13, '2026-06-20 11:30:00'),
(15, 12, 14, '2026-06-21 09:00:00'),
(16, 12, 15, '2026-06-21 09:30:00'),

-- Hospital 3 Appointments
(17, 15, 16, '2026-06-22 13:00:00'),
(18, 15, 17, '2026-06-22 13:30:00'),
(19, 15, 20, '2026-06-22 14:00:00'),
(20, 16, 18, '2026-06-23 15:00:00'),
(21, 16, 19, '2026-06-23 15:30:00'),
(22, 16, 21, '2026-06-23 16:00:00'),
(23, 17, 22, '2026-06-24 10:00:00');

-- ========================================
-- PRESCRIPTIONS
-- ========================================
INSERT INTO prescription (id, name, description, doctor_id, patient_id, start_date, end_date)
VALUES ('RX001', 'Amoxicillin', 'Antibiotic treatment', 1, 1, '2026-06-01', '2026-06-10'),
       ('RX002', 'Ibuprofen', 'Pain relief', 1, 2, '2026-06-01', '2026-06-07'),
       ('RX003', 'Metformin', 'Diabetes treatment', 2, 3, '2026-05-20', '2026-08-20'),
       ('RX004', 'Atorvastatin', 'Cholesterol reduction', 2, 4, '2026-05-01', '2026-11-01'),
       ('RX005', 'Paracetamol', 'Fever management', 3, 5, '2026-06-02', '2026-06-09'),
       ('RX006', 'Losartan', 'Blood pressure control', 3, 6, '2026-05-15', '2026-12-15'),
       ('RX007', 'Omeprazole', 'Acid reflux treatment', 4, 7, '2026-06-03', '2026-07-03'),
       ('RX008', 'Cetirizine', 'Allergy treatment', 4, 8, '2026-06-01', '2026-06-14'),
       ('RX021', 'Sertraline', 'Anxiety management', 5, 9, '2026-06-05', '2026-12-05'),

       ('RX009', 'Amoxicillin', 'Antibiotic treatment', 10, 10, '2026-06-01', '2026-06-10'),
       ('RX010', 'Ibuprofen', 'Pain relief', 10, 11, '2026-06-01', '2026-06-07'),
       ('RX011', 'Metformin', 'Diabetes treatment', 11, 12, '2026-05-20', '2026-08-20'),
       ('RX012', 'Atorvastatin', 'Cholesterol reduction', 11, 13, '2026-05-01', '2026-11-01'),
       ('RX013', 'Paracetamol', 'Fever management', 12, 14, '2026-06-02', '2026-06-09'),
       ('RX014', 'Losartan', 'Blood pressure control', 12, 15, '2026-05-15', '2026-12-15'),

       ('RX015', 'Omeprazole', 'Acid reflux treatment', 15, 16, '2026-06-03', '2026-07-03'),
       ('RX016', 'Cetirizine', 'Allergy treatment', 15, 17, '2026-06-01', '2026-06-14'),
       ('RX017', 'Metformin', 'Diabetes treatment', 16, 18, '2026-05-20', '2026-08-20'),
       ('RX018', 'Atorvastatin', 'Cholesterol reduction', 16, 19, '2026-05-01', '2026-11-01'),
       ('RX019', 'Paracetamol', 'Pain management', 15, 20, '2026-06-05', '2026-06-12'),
       ('RX020', 'Losartan', 'Hypertension treatment', 16, 21, '2026-04-01', '2026-10-01'),
       ('RX022', 'Hydrocortisone', 'Topical eczema cream', 17, 22, '2026-06-09', '2026-06-23');
