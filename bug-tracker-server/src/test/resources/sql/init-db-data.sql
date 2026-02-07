INSERT INTO bug_severities (id, name) VALUES (1, 'CRITICAL'), (2, 'HIGH'), (3, 'MEDIUM'), (4, 'LOW');
INSERT INTO bug_statuses (id, name) VALUES (1, 'OPEN'), (2, 'IN_PROGRESS'), (3, 'CLOSED');

INSERT INTO bugs (id, title, description, severity_id, status_id)
VALUES (1, 'Critical Error', 'System crash', 1, 1),
       (2, 'High Risk UI', 'Login button overlaps', 2, 1),
       (3, 'Medium minor', 'Wrong color', 3, 1),
       (4, 'Low text', 'Typo in help', 4, 1),
       (5, 'Another Critical', 'Data leak', 1, 2);
