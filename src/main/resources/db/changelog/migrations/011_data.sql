
INSERT INTO users (name, surname, age, email, password, phone_number, avatar, account_type ) VALUES
    ('Billy', 'Herryngton', 37, 'gachi@muchi.com', 'password123', '1234567890', '', 'applicant'),
    ('Van', 'Darkholme', 45, 'boss@ofgym.com', 'secure', '0987654321', '', 'employer'),
    ('Nicola', 'Kovac', 27, 'deagle@miss.com', 'qwerty', '996999000888', '', 'applicant');

INSERT INTO categories (name) VALUES ('IT'), ('Finances'), ('Education');
INSERT INTO categories (name, parent_id) VALUES ('Software Development', 1), ('Analytics', 2);

INSERT INTO contact_types (id, type) VALUES (1,'Phone'), (2,'Email'), (3,'Telegram');

INSERT INTO resumes (applicant_id, name, category_id, salary, is_active, created_date, update_time) VALUES
    (1, 'Java the Right Version ♂', 4, 15000.00, TRUE, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),
    (1, 'Kotlin the Right Version ♂', 1, 12400.00, TRUE, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),
    (3, 'Couch', 3, 17500.00, TRUE, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());


INSERT INTO contact_info (resume_id, type_id, value) VALUES
    (1, 1, '1234567890'),
    (1, 2, 'gachi@muchi.com');


INSERT INTO vacancies (name, description, category_id, salary, exp_from, is_active, author_id, created_date, update_time) VALUES
    ('Java Developer', 'need someone with straight hands', 4, 180000.00, 3, TRUE, 2, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),
    ('Kotlin Developer', 'need someone with straight hands', 1, 90000.00, 1, TRUE, 2, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),
    ('Banking Expert', 'gotta manage taxes', 2, 45000.00, 0, TRUE, 2, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());

INSERT INTO education_info (resume_id, institution, program, start_date, end_date, degree) VALUES
    (1, 'University', 'Computer Science', '2010-09-01', '2015-06-25', 'Professor'),
    (2, 'University', 'Advanced Kotlin', '2015-08-15', '2018-12-01', 'Expert');

INSERT INTO work_experience_info (resume_id, years,  company_name, position, responsibilities) VALUES
    (1, 2, 'Right Version Company','Junior', 'Support & Assist');

INSERT INTO responded_applicants (resume_id, vacancy_id, confirmation) VALUES
    ( 1, 1, TRUE),
    ( 2, 2, TRUE),
    ( 3, 3, TRUE);

INSERT INTO messages (responded_applicants_id, content, timestamp) VALUES
    ( 1, 'Im interested in vacancy you have posted', CURRENT_TIMESTAMP());