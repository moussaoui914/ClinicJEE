-- ============================================================
-- ClinicManager - Script d'initialisation PostgreSQL
-- ============================================================

-- ⚠️ À exécuter dans pgAdmin sur la base "postgres" d'abord :
-- CREATE USER clinic_admin WITH PASSWORD 'clinic_pass_2024';
-- CREATE DATABASE clinic_manager_db OWNER clinic_admin;
-- GRANT ALL PRIVILEGES ON DATABASE clinic_manager_db TO clinic_admin;

-- ============================================================
-- À exécuter dans clinic_manager_db
-- ============================================================

CREATE TABLE IF NOT EXISTS users (
                                     id              BIGSERIAL PRIMARY KEY,
                                     nom             VARCHAR(100) NOT NULL,
    prenom          VARCHAR(100) NOT NULL,
    email           VARCHAR(150) NOT NULL UNIQUE,
    telephone       VARCHAR(20),
    mot_de_passe    VARCHAR(255) NOT NULL,
    role            VARCHAR(20) NOT NULL,
    actif           BOOLEAN NOT NULL DEFAULT TRUE,
    date_creation   TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
    );

CREATE TABLE IF NOT EXISTS departments (
                                           id              BIGSERIAL PRIMARY KEY,
                                           nom             VARCHAR(100) NOT NULL UNIQUE,
    description     VARCHAR(255)
    );

CREATE TABLE IF NOT EXISTS specialties (
                                           id              BIGSERIAL PRIMARY KEY,
                                           nom             VARCHAR(100) NOT NULL UNIQUE,
    description     VARCHAR(255),
    department_id   BIGINT NOT NULL,
    CONSTRAINT fk_specialty_department
    FOREIGN KEY (department_id) REFERENCES departments(id)
    );

CREATE TABLE IF NOT EXISTS patients (
                                        user_id         BIGINT PRIMARY KEY,
                                        cin             VARCHAR(20) UNIQUE,
    date_naissance  DATE,
    genre           VARCHAR(10),
    adresse         VARCHAR(255),
    groupe_sanguin  VARCHAR(5),
    CONSTRAINT fk_patient_user
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
    );

CREATE TABLE IF NOT EXISTS doctors (
                                       user_id         BIGINT PRIMARY KEY,
                                       matricule       VARCHAR(50) NOT NULL UNIQUE,
    titre           VARCHAR(50),
    specialty_id    BIGINT,
    department_id   BIGINT,
    CONSTRAINT fk_doctor_user
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT fk_doctor_specialty
    FOREIGN KEY (specialty_id) REFERENCES specialties(id),
    CONSTRAINT fk_doctor_department
    FOREIGN KEY (department_id) REFERENCES departments(id)
    );

CREATE TABLE IF NOT EXISTS availabilities (
                                              id              BIGSERIAL PRIMARY KEY,
                                              doctor_id       BIGINT NOT NULL,
                                              jour            VARCHAR(20) NOT NULL,
    heure_debut     TIME NOT NULL,
    heure_fin       TIME NOT NULL,
    statut          VARCHAR(20) NOT NULL DEFAULT 'ACTIVE',
    date_debut      DATE,
    date_fin        DATE,
    CONSTRAINT fk_availability_doctor
    FOREIGN KEY (doctor_id) REFERENCES doctors(user_id) ON DELETE CASCADE
    );

CREATE TABLE IF NOT EXISTS appointments (
                                            id              BIGSERIAL PRIMARY KEY,
                                            patient_id      BIGINT NOT NULL,
                                            doctor_id       BIGINT NOT NULL,
                                            date_rdv        TIMESTAMP NOT NULL,
                                            duree_minutes   INTEGER NOT NULL DEFAULT 30,
                                            type            VARCHAR(20) NOT NULL,
    statut          VARCHAR(20) NOT NULL DEFAULT 'PLANNED',
    motif           TEXT,
    CONSTRAINT fk_appointment_patient
    FOREIGN KEY (patient_id) REFERENCES patients(user_id),
    CONSTRAINT fk_appointment_doctor
    FOREIGN KEY (doctor_id) REFERENCES doctors(user_id)
    );

CREATE TABLE IF NOT EXISTS medical_notes (
                                             id              BIGSERIAL PRIMARY KEY,
                                             doctor_id       BIGINT NOT NULL,
                                             appointment_id  BIGINT NOT NULL UNIQUE,
                                             diagnostic      TEXT,
                                             contenu         TEXT,
                                             date_note       TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                             validee         BOOLEAN NOT NULL DEFAULT FALSE,
                                             CONSTRAINT fk_note_doctor
                                             FOREIGN KEY (doctor_id) REFERENCES doctors(user_id),
    CONSTRAINT fk_note_appointment
    FOREIGN KEY (appointment_id) REFERENCES appointments(id) ON DELETE CASCADE
    );

CREATE INDEX IF NOT EXISTS idx_users_email          ON users(email);
CREATE INDEX IF NOT EXISTS idx_appointments_date    ON appointments(date_rdv);
CREATE INDEX IF NOT EXISTS idx_appointments_doctor  ON appointments(doctor_id);
CREATE INDEX IF NOT EXISTS idx_appointments_patient ON appointments(patient_id);

-- ============================================================
-- DONNÉES DE TEST
-- ============================================================
INSERT INTO departments (nom, description) VALUES
                                               ('Médecine',  'Médecine générale'),
                                               ('Chirurgie', 'Chirurgie'),
                                               ('Pédiatrie', 'Pédiatrie'),
                                               ('Urgences',  'Urgences')
    ON CONFLICT DO NOTHING;

INSERT INTO specialties (nom, description, department_id) VALUES
                                                              ('Cardiologie',  'Maladies du cœur',            1),
                                                              ('Dermatologie', 'Maladies de la peau',         1),
                                                              ('Neurologie',   'Maladies du système nerveux', 1),
                                                              ('Pédiatrie',    'Médecine des enfants',        3)
    ON CONFLICT DO NOTHING;

-- Admin (mot de passe = admin123)
INSERT INTO users (nom, prenom, email, telephone, mot_de_passe, role, actif) VALUES
    ('Admin', 'Super', 'admin@clinic.com', '0600000000',
     '$2a$12$LQv3c1yqBWVHxkd0LHAkCOYz6TtxMQJqhN8/LewY5GyYqVr/if9Hy',
     'ADMIN', TRUE)
    ON CONFLICT DO NOTHING;