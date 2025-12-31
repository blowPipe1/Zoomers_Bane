
INSERT INTO users (name, surname, age, email, password, phone_number, avatar, account_type ) VALUES
    ('Billy', 'Herryngton', 37, 'gachi@muchi.com', '$2a$10$avWCrVgC7PCS8zAXqqaxaOa13ZFmiXtcD07GgbZotou9j1owrQErG', '1234567890', '', 'applicant'),
    ('Van', 'Darkholme', 45, 'boss@ofgym.com', '$2a$10$V9TnBj/rUGamIItHColuM.7b7yw.wSCboX.ouCxG0c28V.IDhcSxi', '0987654321', '', 'employer'),
    ('Nicola', 'Kovac', 27, 'deagle@miss.com', '$2a$10$sOZq7q.F2DnUxpywL/go8u0g9byXn7w2Eond3Ww7SWMqLaMPIDsLu', '996999000888', '', 'applicant');

INSERT INTO categories (name) VALUES ('IT'), ('Finances'), ('Education');
INSERT INTO categories (name, parent_id) VALUES ('Software Development', (select id from categories where name = 'IT')), ('Analytics', (select id from categories where name = 'Finances'));

INSERT INTO contact_types (type) VALUES ('Phone'), ('Email'), ('Telegram');

INSERT INTO resumes (applicant_id, name, category_id, salary, is_active, created_date, update_time) VALUES
    ((select id from USERS where EMAIL = 'gachi@muchi.com'), 'Java the Right Version ♂', (select id from categories where name = 'Software Development'), 15000.00, TRUE, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),
    ((select id from USERS where EMAIL = 'gachi@muchi.com'), 'Kotlin the Right Version ♂', (select id from categories where name = 'IT'), 12400.00, TRUE, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),
    ((select id from USERS where EMAIL = 'deagle@miss.com'), 'Couch', (select id from categories where name = 'Education'), 17500.00, TRUE, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());


INSERT INTO contact_info (resume_id, type_id, value) VALUES
    ((select id from RESUMES where APPLICANT_ID = (select id from USERS where EMAIL = 'gachi@muchi.com') and name = 'Java the Right Version ♂'), (select id from CONTACT_TYPES where TYPE = 'Phone'), '1234567890'),
    ((select id from RESUMES where APPLICANT_ID = (select id from USERS where EMAIL = 'gachi@muchi.com') and name = 'Java the Right Version ♂'), (select id from CONTACT_TYPES where TYPE = 'Email'), 'gachi@muchi.com');


INSERT INTO vacancies (name, description, category_id, salary, exp_from, is_active, author_id, created_date, update_time) VALUES
    ('Java Developer', 'need someone with straight hands', ((select id from categories where name = 'Software Development')), 180000.00, 3, TRUE, (select id from USERS where EMAIL = 'boss@ofgym.com'), CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),
    ('Kotlin Developer', 'need someone with straight hands', ((select id from categories where name = 'IT')), 90000.00, 1, TRUE, (select id from USERS where EMAIL = 'boss@ofgym.com'), CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),
    ('Banking Expert', 'gotta manage taxes', ((select id from categories where name = 'Finances')), 45000.00, 0, TRUE, (select id from USERS where EMAIL = 'boss@ofgym.com'), CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());

INSERT INTO education_info (resume_id, institution, program, start_date, end_date, degree) VALUES
    (((select id from RESUMES where APPLICANT_ID = (select id from USERS where EMAIL = 'gachi@muchi.com') and name = 'Java the Right Version ♂')), 'University', 'Computer Science', '2010-09-01', '2015-06-25', 'Professor'),
    (((select id from RESUMES where APPLICANT_ID = (select id from USERS where EMAIL = 'gachi@muchi.com') and name = 'Kotlin the Right Version ♂')), 'University', 'Advanced Kotlin', '2015-08-15', '2018-12-01', 'Expert');

INSERT INTO work_experience_info (resume_id, years,  company_name, position, responsibilities) VALUES
    (((select id from RESUMES where APPLICANT_ID = (select id from USERS where EMAIL = 'gachi@muchi.com') and name = 'Java the Right Version ♂')), 2, 'Right Version Company','Junior', 'Support & Assist');

INSERT INTO responded_applicants (resume_id, vacancy_id, confirmation) VALUES
    ( ((select id from RESUMES where APPLICANT_ID = (select id from USERS where EMAIL = 'gachi@muchi.com') and name = 'Java the Right Version ♂')), ((select id from VACANCIES where AUTHOR_ID = (select id from USERS where EMAIL = 'boss@ofgym.com') and name = 'Java Developer')), TRUE),
    ( ((select id from RESUMES where APPLICANT_ID = (select id from USERS where EMAIL = 'gachi@muchi.com') and name = 'Kotlin the Right Version ♂')), ((select id from VACANCIES where AUTHOR_ID = (select id from USERS where EMAIL = 'boss@ofgym.com') and name = 'Kotlin Developer')), TRUE),
    ( ((select id from RESUMES where APPLICANT_ID = (select id from USERS where EMAIL = 'deagle@miss.com') and name = 'Couch')), ((select id from VACANCIES where AUTHOR_ID = (select id from USERS where EMAIL = 'boss@ofgym.com') and name = 'Banking Expert')), TRUE);

INSERT INTO messages (responded_applicants_id, content, timestamp) VALUES
    ( select id from responded_applicants where RESUME_ID = ((select id from RESUMES where APPLICANT_ID = (select id from USERS where EMAIL = 'gachi@muchi.com') and name = 'Java the Right Version ♂')) and VACANCY_ID = ((select id from VACANCIES where AUTHOR_ID = (select id from USERS where EMAIL = 'boss@ofgym.com') and name = 'Java Developer')), 'Im interested in vacancy you have posted', CURRENT_TIMESTAMP());