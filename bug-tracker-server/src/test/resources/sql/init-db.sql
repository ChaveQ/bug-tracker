CREATE TABLE IF NOT EXISTS bug_statuses (
        id INT AUTO_INCREMENT PRIMARY KEY,
        name VARCHAR(50) NOT NULL UNIQUE);

CREATE TABLE IF NOT EXISTS bug_severities (
        id INT AUTO_INCREMENT PRIMARY KEY,
        name VARCHAR(20) NOT NULL UNIQUE);

CREATE TABLE IF NOT EXISTS bugs (
        id BIGINT AUTO_INCREMENT PRIMARY KEY,
        title VARCHAR(255) NOT NULL,
        description TEXT,
        severity_id INT NOT NULL,
        status_id INT NOT NULL,
        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
        updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
        CONSTRAINT fk_bugs_severity FOREIGN KEY (severity_id) REFERENCES bug_severities(id),
        CONSTRAINT fk_bug_status FOREIGN KEY (status_id) REFERENCES bug_statuses(id));
