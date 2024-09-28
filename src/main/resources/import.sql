USE employee;

-- Create table roles
CREATE TABLE IF NOT EXISTS roles (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

INSERT INTO employee.roles (id, name) VALUES (1, "ADMIN");
INSERT INTO employee.roles (id, name) VALUES (2, "USER");