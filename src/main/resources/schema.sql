-- Hospitals table
CREATE TABLE IF NOT EXISTS hospitals
(
    id
    INT
    PRIMARY
    KEY,
    name
    VARCHAR
(
    255
) NOT NULL
    );

-- Doctors table
CREATE TABLE IF NOT EXISTS doctors
(
    id
    INT
    PRIMARY
    KEY,
    first_name
    VARCHAR
(
    255
) NOT NULL,
    last_name VARCHAR
(
    255
) NOT NULL,
    date_of_birth DATE NOT NULL,
    specialization VARCHAR
(
    255
),
    base_salary DOUBLE
    );

-- Patients table
CREATE TABLE IF NOT EXISTS patients
(
    id
    INT
    PRIMARY
    KEY,
    first_name
    VARCHAR
(
    255
) NOT NULL,
    last_name VARCHAR
(
    255
) NOT NULL,
    date_of_birth DATE NOT NULL,
    condition VARCHAR
(
    50
),
    insurance_number VARCHAR
(
    100
)
    );

-- Appointments table
CREATE TABLE IF NOT EXISTS appointments
(
    id
    INT
    PRIMARY
    KEY,
    doctor_id
    INT
    NOT
    NULL,
    patient_id
    INT
    NOT
    NULL,
    date_time
    TIMESTAMP
    NOT
    NULL,
    FOREIGN
    KEY
(
    doctor_id
) REFERENCES doctors
(
    id
),
    FOREIGN KEY
(
    patient_id
) REFERENCES patients
(
    id
)
    );
