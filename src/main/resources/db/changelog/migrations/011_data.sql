
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



INSERT INTO users (name, surname, age, email, password, phone_number, avatar, account_type ) VALUES
                                                                                                 ('Phil', 'Collins', 55, 'phil.c@music.com', '$2a$10$HASHEDPASSWORD001', '1112223334', '', 'applicant'),
                                                                                                 ('John', 'Doe', 32, 'john.doe@example.com', '$2a$10$HASHEDPASSWORD002', '1112223335', '', 'applicant'),
                                                                                                 ('Jane', 'Smith', 28, 'jane.smith@example.com', '$2a$10$HASHEDPASSWORD003', '1112223336', '', 'applicant'),
                                                                                                 ('Bruce', 'Wayne', 40, 'bruce.w@corp.com', '$2a$10$HASHEDPASSWORD004', '1112223337', '', 'employer'),
                                                                                                 ('Clark', 'Kent', 35, 'clark.k@daily.com', '$2a$10$HASHEDPASSWORD005', '1112223338', '', 'applicant'),
                                                                                                 ('Diana', 'Prince', 30, 'diana.p@museum.com', '$2a$10$HASHEDPASSWORD006', '1112223339', '', 'applicant'),
                                                                                                 ('Peter', 'Parker', 24, 'peter.p@photo.com', '$2a$10$HASHEDPASSWORD007', '1112223340', '', 'applicant'),
                                                                                                 ('Mary', 'Jane', 25, 'mj.watson@art.com', '$2a$10$HASHEDPASSWORD008', '1112223341', '', 'applicant'),
                                                                                                 ('Lex', 'Luthor', 50, 'lex.l@corp.com', '$2a$10$HASHEDPASSWORD009', '1112223342', '', 'employer'),
                                                                                                 ('Barry', 'Allen', 29, 'barry.a@police.com', '$2a$10$HASHEDPASSWORD010', '1112223343', '', 'applicant');


-- Insert 15 additional resumes

-- Resumes for Billy Herryngton (existing user)
INSERT INTO resumes (applicant_id, name, category_id, salary, is_active, created_date, update_time) VALUES
    ((select id from USERS where EMAIL = 'gachi@muchi.com'), 'PHP Developer', (select id from categories where name = 'Software Development'), 10000.00, TRUE, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
-- Experience/Education/Contacts for PHP Developer
INSERT INTO work_experience_info (resume_id, years, company_name, position, responsibilities) VALUES
    ((select id from RESUMES where APPLICANT_ID = (select id from USERS where EMAIL = 'gachi@muchi.com') and name = 'PHP Developer'), 3, 'Old Guard Co.', 'Mid-Level Dev', 'Legacy code maintenance');
INSERT INTO education_info (resume_id, institution, program, start_date, end_date, degree) VALUES
    ((select id from RESUMES where APPLICANT_ID = (select id from USERS where EMAIL = 'gachi@muchi.com') and name = 'PHP Developer'), 'Tech School', 'Web Development', '2016-01-01', '2017-12-31', 'Associate');
INSERT INTO contact_info (resume_id, type_id, value) VALUES
    ((select id from RESUMES where APPLICANT_ID = (select id from USERS where EMAIL = 'gachi@muchi.com') and name = 'PHP Developer'), (select id from CONTACT_TYPES where TYPE = 'Email'), 'gachi@muchi.com');



-- Resumes for Nicola Kovac (existing user)
INSERT INTO resumes (applicant_id, name, category_id, salary, is_active, created_date, update_time) VALUES
    ((select id from USERS where EMAIL = 'deagle@miss.com'), 'Financial Analyst', (select id from categories where name = 'Analytics'), 20000.00, TRUE, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),
    ((select id from USERS where EMAIL = 'deagle@miss.com'), 'Data Entry Clerk', (select id from categories where name = 'Finances'), 8000.00, FALSE, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
-- Experience/Education/Contacts for Financial Analyst
INSERT INTO work_experience_info (resume_id, years, company_name, position, responsibilities) VALUES
    ((select id from RESUMES where APPLICANT_ID = (select id from USERS where EMAIL = 'deagle@miss.com') and name = 'Financial Analyst'), 5, 'Big Bank Inc.', 'Junior Analyst', 'Spreadsheets and reports');
INSERT INTO education_info (resume_id, institution, program, start_date, end_date, degree) VALUES
    ((select id from RESUMES where APPLICANT_ID = (select id from USERS where EMAIL = 'deagle@miss.com') and name = 'Financial Analyst'), 'Business Uni', 'Finance', '2010-01-01', '2014-06-01', 'Master');
INSERT INTO contact_info (resume_id, type_id, value) VALUES
    ((select id from RESUMES where APPLICANT_ID = (select id from USERS where EMAIL = 'deagle@miss.com') and name = 'Financial Analyst'), (select id from CONTACT_TYPES where TYPE = 'Email'), 'deagle@miss.com');

-- Resumes for Phil Collins (new user)
INSERT INTO resumes (applicant_id, name, category_id, salary, is_active, created_date, update_time) VALUES
    ((select id from USERS where EMAIL = 'phil.c@music.com'), 'Musician/Drummer', (select id from categories where name = 'Education'), 30000.00, TRUE, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
INSERT INTO work_experience_info (resume_id, years, company_name, position, responsibilities) VALUES
    ((select id from RESUMES where APPLICANT_ID = (select id from USERS where EMAIL = 'phil.c@music.com') and name = 'Musician/Drummer'), 40, 'Genesis', 'Drummer/Vocalist', 'Making great music');
INSERT INTO contact_info (resume_id, type_id, value) VALUES
    ((select id from RESUMES where APPLICANT_ID = (select id from USERS where EMAIL = 'phil.c@music.com') and name = 'Musician/Drummer'), (select id from CONTACT_TYPES where TYPE = 'Phone'), '1112223334');


-- Resumes for John Doe (new user)
INSERT INTO resumes (applicant_id, name, category_id, salary, is_active, created_date, update_time) VALUES
    ((select id from USERS where EMAIL = 'john.doe@example.com'), 'Junior Java Dev', (select id from categories where name = 'Software Development'), 12000.00, TRUE, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
INSERT INTO education_info (resume_id, institution, program, start_date, end_date, degree) VALUES
    ((select id from RESUMES where APPLICANT_ID = (select id from USERS where EMAIL = 'john.doe@example.com') and name = 'Junior Java Dev'), 'Local College', 'IT Basics', '2020-01-01', '2022-01-01', 'Diploma');
INSERT INTO contact_info (resume_id, type_id, value) VALUES
    ((select id from RESUMES where APPLICANT_ID = (select id from USERS where EMAIL = 'john.doe@example.com') and name = 'Junior Java Dev'), (select id from CONTACT_TYPES where TYPE = 'Email'), 'john.doe@example.com');


-- Resumes for Jane Smith (new user)
INSERT INTO resumes (applicant_id, name, category_id, salary, is_active, created_date, update_time) VALUES
                                                                                                        ((select id from USERS where EMAIL = 'jane.smith@example.com'), 'Python Engineer', (select id from categories where name = 'Software Development'), 14000.00, TRUE, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),
                                                                                                        ((select id from USERS where EMAIL = 'jane.smith@example.com'), 'Math Tutor', (select id from categories where name = 'Education'), 5000.00, TRUE, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
INSERT INTO work_experience_info (resume_id, years, company_name, position, responsibilities) VALUES
    ((select id from RESUMES where APPLICANT_ID = (select id from USERS where EMAIL = 'jane.smith@example.com') and name = 'Python Engineer'), 2, 'Data Co.', 'Junior Engineer', 'ML models training');
INSERT INTO education_info (resume_id, institution, program, start_date, end_date, degree) VALUES
    ((select id from RESUMES where APPLICANT_ID = (select id from USERS where EMAIL = 'jane.smith@example.com') and name = 'Python Engineer'), 'State Uni', 'Computer Science', '2016-09-01', '2020-06-01', 'Bachelor');
INSERT INTO contact_info (resume_id, type_id, value) VALUES
    ((select id from RESUMES where APPLICANT_ID = (select id from USERS where EMAIL = 'jane.smith@example.com') and name = 'Python Engineer'), (select id from CONTACT_TYPES where TYPE = 'Email'), 'jane.smith@example.com');


-- Resumes for Clark Kent (new user)
INSERT INTO resumes (applicant_id, name, category_id, salary, is_active, created_date, update_time) VALUES
    ((select id from USERS where EMAIL = 'clark.k@daily.com'), 'Journalist', (select id from categories where name = 'Education'), 9000.00, TRUE, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
INSERT INTO work_experience_info (resume_id, years, company_name, position, responsibilities) VALUES
    ((select id from RESUMES where APPLICANT_ID = (select id from USERS where EMAIL = 'clark.k@daily.com') and name = 'Journalist'), 10, 'Daily Planet', 'Reporter', 'Writing news');
INSERT INTO contact_info (resume_id, type_id, value) VALUES
    ((select id from RESUMES where APPLICANT_ID = (select id from USERS where EMAIL = 'clark.k@daily.com') and name = 'Journalist'), (select id from CONTACT_TYPES where TYPE = 'Phone'), '1112223338');


-- Resumes for Diana Prince (new user)
INSERT INTO resumes (applicant_id, name, category_id, salary, is_active, created_date, update_time) VALUES
    ((select id from USERS where EMAIL = 'diana.p@museum.com'), 'Curator Assistant', (select id from categories where name = 'Education'), 7000.00, TRUE, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());


-- Resumes for Peter Parker (new user)
INSERT INTO resumes (applicant_id, name, category_id, salary, is_active, created_date, update_time) VALUES
    ((select id from USERS where EMAIL = 'peter.p@photo.com'), 'Photographer', (select id from categories where name = 'Education'), 6000.00, TRUE, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
INSERT INTO work_experience_info (resume_id, years, company_name, position, responsibilities) VALUES
    ((select id from RESUMES where APPLICANT_ID = (select id from USERS where EMAIL = 'peter.p@photo.com') and name = 'Photographer'), 5, 'Daily Bugle', 'Freelance Photo', 'Photos of Spider-Man');
INSERT INTO contact_info (resume_id, type_id, value) VALUES
    ((select id from RESUMES where APPLICANT_ID = (select id from USERS where EMAIL = 'peter.p@photo.com') and name = 'Photographer'), (select id from CONTACT_TYPES where TYPE = 'Email'), 'peter.p@photo.com');


-- Resumes for Mary Jane (new user)
INSERT INTO resumes (applicant_id, name, category_id, salary, is_active, created_date, update_time) VALUES
    ((select id from USERS where EMAIL = 'mj.watson@art.com'), 'Waitress', (select id from categories where name = 'Education'), 4000.00, FALSE, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());


-- Resumes for Barry Allen (new user)
INSERT INTO resumes (applicant_id, name, category_id, salary, is_active, created_date, update_time) VALUES
                                                                                                        ((select id from USERS where EMAIL = 'barry.a@police.com'), 'Forensic Scientist', (select id from categories where name = 'Education'), 11000.00, TRUE, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),
                                                                                                        ((select id from USERS where EMAIL = 'barry.a@police.com'), 'Chemist', (select id from categories where name = 'Education'), 10000.00, TRUE, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
INSERT INTO education_info (resume_id, institution, program, start_date, end_date, degree) VALUES
    ((select id from RESUMES where APPLICANT_ID = (select id from USERS where EMAIL = 'barry.a@police.com') and name = 'Forensic Scientist'), 'Central Uni', 'Forensics', '2010-01-01', '2014-06-01', 'Master');
INSERT INTO contact_info (resume_id, type_id, value) VALUES
    ((select id from RESUMES where APPLICANT_ID = (select id from USERS where EMAIL = 'barry.a@police.com') and name = 'Forensic Scientist'), (select id from CONTACT_TYPES where TYPE = 'Phone'), '1112223343');


-- Insert 15 additional vacancies

-- Vacancies for Van Darkholme (existing employer)
INSERT INTO vacancies (name, description, category_id, salary, exp_from, is_active, author_id, created_date, update_time) VALUES
    ('React Developer', 'Need front-end expert', ((select id from categories where name = 'Software Development')), 150000.00, 2, TRUE, (select id from USERS where EMAIL = 'boss@ofgym.com'), CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),
    ('UX Designer', 'Make it look good', ((select id from categories where name = 'IT')), 70000.00, 1, TRUE, (select id from USERS where EMAIL = 'boss@ofgym.com'), CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),
    ('Auditor', 'Count the money', ((select id from categories where name = 'Finances')), 60000.00, 3, TRUE, (select id from USERS where EMAIL = 'boss@ofgym.com'), CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());

-- Vacancies for Bruce Wayne (new employer)
INSERT INTO vacancies (name, description, category_id, salary, exp_from, is_active, author_id, created_date, update_time) VALUES
    ('Security Specialist', 'Must work nights', ((select id from categories where name = 'IT')), 200000.00, 5, TRUE, (select id from USERS where EMAIL = 'bruce.w@corp.com'), CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),
    ('Accountant', 'Handle the books', ((select id from categories where name = 'Finances')), 90000.00, 4, TRUE, (select id from USERS where EMAIL = 'bruce.w@corp.com'), CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),
    ('Data Scientist', 'Analyze everything', ((select id from categories where name = 'Analytics')), 180000.00, 3, TRUE, (select id from USERS where EMAIL = 'bruce.w@corp.com'), CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());

-- Vacancies for Lex Luthor (new employer)
INSERT INTO vacancies (name, description, category_id, salary, exp_from, is_active, author_id, created_date, update_time) VALUES
    ('Nuclear Engineer', 'Need top mind', ((select id from categories where name = 'Education')), 300000.00, 10, TRUE, (select id from USERS where EMAIL = 'lex.l@corp.com'), CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),
    ('Software Architect', 'Build the future', ((select id from categories where name = 'Software Development')), 250000.00, 8, TRUE, (select id from USERS where EMAIL = 'lex.l@corp.com'), CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),
    ('PR Specialist', 'Improve image', ((select id from categories where name = 'Education')), 80000.00, 5, TRUE, (select id from USERS where EMAIL = 'lex.l@corp.com'), CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());

-- More Vacancies for Van Darkholme (existing employer)
INSERT INTO vacancies (name, description, category_id, salary, exp_from, is_active, author_id, created_date, update_time) VALUES
    ('Database Admin', 'Manage the SQL', ((select id from categories where name = 'IT')), 110000.00, 4, TRUE, (select id from USERS where EMAIL = 'boss@ofgym.com'), CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),
    ('Project Manager', 'Keep guys in line', ((select id from categories where name = 'IT')), 130000.00, 6, TRUE, (select id from USERS where EMAIL = 'boss@ofgym.com'), CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),
    ('Frontend Intern', 'Learn React', ((select id from categories where name = 'Software Development')), 30000.00, 0, TRUE, (select id from USERS where EMAIL = 'boss@ofgym.com'), CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),
    ('CFO Assistant', 'Help manage finances', ((select id from categories where name = 'Finances')), 40000.00, 1, TRUE, (select id from USERS where EMAIL = 'boss@ofgym.com'), CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());

-- More Vacancies for Bruce Wayne (new employer)
INSERT INTO vacancies (name, description, category_id, salary, exp_from, is_active, author_id, created_date, update_time) VALUES
      ('Hardware Engineer', 'Gadget specialist', ((select id from categories where name = 'IT')), 160000.00, 4, TRUE, (select id from USERS where EMAIL = 'bruce.w@corp.com'), CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),
      ('Legal Counsel', 'Review contracts', ((select id from categories where name = 'Education')), 150000.00, 7, TRUE, (select id from USERS where EMAIL = 'bruce.w@corp.com'), CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());