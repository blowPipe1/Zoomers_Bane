
INSERT INTO users (id, name, surname, age, email, password, phone_number, avatar, account_type ) VALUES
    (1,'Billy', 'Herryngton', 37, 'gachi@muchi.com', 'password123', '1234567890', '', 'applicant'),
    (2,'Van', 'Darkholme', 45, 'boss@ofgym.com', 'secure', '0987654321', '', 'employer'),
    (3,'Nicola', 'Kovac', 27, 'deagle@miss.com', 'qwerty', '996999000888', '', 'applicant');

INSERT INTO categories (id, name) VALUES (1,'IT'), (2,'Finances'), (3,'Education');
INSERT INTO categories (id, name, parent_id) VALUES (4,'Software Development', 1), (5,'Analytics', 2);

INSERT INTO contact_types (id, type) VALUES (1,'Phone'), (2,'Email'), (3,'Telegram');

INSERT INTO resumes (id, applicant_id, name, category_id, salary, is_active, created_date, update_time) VALUES
    (1, 1, 'Java the Right Version ♂', 4, 15000.00, TRUE, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),
    (2,1, 'Kotlin the Right Version ♂', 1, 12400.00, TRUE, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),
    (3,3, 'Couch', 3, 17500.00, TRUE, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());


INSERT INTO contact_info (id, resume_id, type_id, value) VALUES
    (1,1, 1, '1234567890'),
    (2,1, 2, 'gachi@muchi.com');


INSERT INTO vacancies (id, name, description, category_id, salary, exp_from, is_active, author_id, created_date, update_time) VALUES
    (1,'Java Developer', 'need someone with straight hands', 4, 180000.00, 3, TRUE, 2, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),
    (2,'Kotlin Developer', 'need someone with straight hands', 1, 90000.00, 1, TRUE, 2, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),
    (3, 'Banking Expert', 'gotta manage taxes', 2, 45000.00, 0, TRUE, 2, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());

INSERT INTO education_info (id, resume_id, institution, program, start_date, end_date, degree) VALUES
    (1,1, 'University', 'Computer Science', '2010-09-01', '2015-06-25', 'Professor'),
    (2,2, 'University', 'Advanced Kotlin', '2015-08-15', '2018-12-01', 'Expert');

INSERT INTO work_experience_info (id, resume_id, years,  company_name, position, responsibilities) VALUES
    (1,1, 2, 'Right Version Company','Junior', 'Support & Assist');

INSERT INTO responded_applicants (id, resume_id, vacancy_id, confirmation) VALUES
    (1, 1, 1, TRUE),
    (2, 2, 2, TRUE),
    (3, 3, 3, TRUE);

INSERT INTO messages (id, responded_applicants_id, content, timestamp) VALUES
    (1, 1, 'Im interested in vacancy you have posted', CURRENT_TIMESTAMP());