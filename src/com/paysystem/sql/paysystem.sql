CREATE DATABASE IF NOT EXISTS payment_system;
USE payment_system;

CREATE TABLE IF NOT EXISTS users (
    id BINARY(16) PRIMARY KEY,
    firstName VARCHAR(30) NOT NULL,
    lastName VARCHAR(30) NOT NULL,
    email VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(60) NOT NULL,
    role enum('director', 'agent', 'responsible') DEFAULT 'agent',
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS departments (
    id BINARY(16) PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE,
    responsible_id BINARY(16) NOT NULL ,
    FOREIGN KEY (responsible_id) REFERENCES users (id)
);

CREATE TABLE IF NOT EXISTS payments (
    id BINARY(16) PRIMARY KEY,
    amount DECIMAL(10,2) NOT NULL,
    date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    reason TEXT,
    agent_id BINARY(16) NOT NULL,
    FOREIGN KEY (agent_id) REFERENCES users (id)
);

-- Insert new users
INSERT INTO users (id, firstName, lastName, email, password, role)
VALUES
    (UUID_TO_BIN('f47ac10b-58cc-4372-a567-0e02b2c3d479'), 'Ali', 'Yara', 'ali.yara@example.com', 'hashedpassword1', 'agent'),
    (UUID_TO_BIN('9a3b2c1d-7e5f-4a9b-81c2-3f4d5e6f7a8b'), 'Sara', 'Moussa', 'sara.moussa@example.com', 'hashedpassword2', 'responsible'),
    (UUID_TO_BIN('c1d2e3f4-5678-90ab-cdef-1234567890ab'), 'Khalid', 'Ben', 'khalid.ben@example.com', 'hashedpassword3', 'director');

-- Insert new departments (make sure responsible_id matches an existing user)
INSERT INTO departments (id, name, responsible_id)
VALUES
    (UUID_TO_BIN('11111111-2222-3333-4444-555555555555'), 'Finance', UUID_TO_BIN('9a3b2c1d-7e5f-4a9b-81c2-3f4d5e6f7a8b')),
    (UUID_TO_BIN('66666666-7777-8888-9999-000000000000'), 'HR', UUID_TO_BIN('f47ac10b-58cc-4372-a567-0e02b2c3d479'));

-- Insert payments (make sure agent_id matches an existing user)
INSERT INTO payments (id, amount, reason, agent_id)
VALUES
    (UUID_TO_BIN('aaaaaaaa-bbbb-cccc-dddd-eeeeeeeeeeee'), 2500.00, 'Monthly salary', UUID_TO_BIN('f47ac10b-58cc-4372-a567-0e02b2c3d479')),
    (UUID_TO_BIN('ffffffff-1111-2222-3333-444444444444'), 500.50, 'Project bonus', UUID_TO_BIN('f47ac10b-58cc-4372-a567-0e02b2c3d479'));
