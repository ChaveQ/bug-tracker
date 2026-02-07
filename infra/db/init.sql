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


INSERT INTO bug_severities (id, name)
VALUES (1, 'CRITICAL'), (2, 'HIGH'), (3, 'MEDIUM'), (4, 'LOW');

INSERT INTO bug_statuses (id, name)
VALUES (1, 'OPEN'), (2, 'IN_PROGRESS'), (3, 'RESOLVED'), (4, 'REOPENED'),  (5, 'CLOSED');

INSERT INTO bugs (title, description, severity_id, status_id)
VALUES ('Initial System Bug', 'Database schema finalized and tested.', 2, 1),
       ('TEST BUG', 'Checking if the DB saves anything at all', 4, 1),
       ('Button "Save" not working', 'I press and nothing happens. The console is quiet', 1, 2),
       ('Typo in footer', 'Stays "Copyright 2022" but it is 2026 now', 4, 1),
       ('Critical crash on profile', 'Critical crash when clicking profile icon 5 times', 1, 1),
       ('Search is slow', 'Takes like 5 seconds to find one bug. Indexing problem?', 2, 5),
       ('Logo too big', 'Looks huge on mobile screens', 3, 1),
       ('CSS issue on login page', 'Inputs are not aligned. Fix borders.', 4, 1),
       ('Another test', 'ignore this', 4, 1),
       ('SQL Error in logs', 'Found "Syntax error near WHERE" in server logs', 1, 3),
       ('Null pointer on logout', 'Randomly happens. Hard to reproduce.', 2, 1),
       ('Dark mode too dark', 'Can barely see the text on black background', 3, 3),
       ('Link in footer is 404', 'Privacy Policy link leads to nowhere', 2, 2),
       ('Validation fails', 'Can enter 1000000 characters in title field', 2, 1),
       ('Double click bug', 'Double clicking submit creates 2 identical bugs', 3, 1),
       ('Icon alignment', 'Move the bug icon 5px to the left', 4, 1),
       ('Font size on mobile', 'Too small to read on iPhone SE', 4, 1),
       ('About page content error', 'The About page still has placeholder text', 3, 4),
       ('Delete button no confirm', 'It just deletes. Should ask "Are you sure?"', 2, 5),
       ('Wrong date format', 'Shows 1970-01-01 for some reason.', 2, 1),
       ('Email not sent', 'Reset password email never arrives in inbox', 1, 1),
       ('Mobile menu stuck', 'Cant close the burger menu on Android Chrome', 2, 1),
       ('API returns 500', 'Endpoint /api/v1/bugs/all is broken', 1, 1),
       ('Needs more icons', 'Add some emoji support maybe?', 3, 1),
       ('Final test before commit', 'Checking foreign key constraints one last time', 3, 5),
       ('Login broken', 'Admin cannot log in with correct password', 1, 3);
