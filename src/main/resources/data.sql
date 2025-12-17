CREATE TABLE if not exists users (
    id IDENTITY PRIMARY KEY,
    name TEXT NOT NULL,
    surname TEXT,
    age INTEGER,
    email TEXT NOT NULL,
    password TEXT NOT NULL,
    phone_number VARCHAR(55),
    avatar TEXT,
    account_type VARCHAR(50)
);

CREATE TABLE if not exists categories (
    id IDENTITY PRIMARY KEY,
    name TEXT NOT NULL,
    parent_id INTEGER,
        FOREIGN KEY (parent_id) REFERENCES categories(id)
);

CREATE TABLE if not exists contact_types (
    id IDENTITY PRIMARY KEY,
    type TEXT NOT NULL
);


CREATE TABLE if not exists resumes (
    id IDENTITY PRIMARY KEY,
    applicant_id INTEGER NOT NULL,
    name TEXT NOT NULL,
    category_id INTEGER NOT NULL,
    salary REAL,
    is_active BOOLEAN NOT NULL,
    created_date TIMESTAMP NOT NULL,
    update_time TIMESTAMP NOT NULL,
        FOREIGN KEY (applicant_id) REFERENCES users(id),
        FOREIGN KEY (category_id) REFERENCES categories(id)
);

CREATE TABLE if not exists contact_info (
    id IDENTITY PRIMARY KEY,
    resume_id INTEGER NOT NULL,
    type_id INTEGER NOT NULL,
    "value" TEXT NOT NULL,
        FOREIGN KEY (resume_id) REFERENCES resumes(id),
        FOREIGN KEY (type_id) REFERENCES contact_types(id)
);

CREATE TABLE if not exists education_info (
    id IDENTITY PRIMARY KEY,
    resume_id INTEGER NOT NULL,
    institution TEXT NOT NULL,
    program TEXT NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE,
    degree TEXT,
        FOREIGN KEY (resume_id) REFERENCES resumes(id)
);

CREATE TABLE if not exists work_experience_info (
    id IDENTITY PRIMARY KEY,
    resume_id INTEGER NOT NULL,
    years INTEGER,
    company_name TEXT NOT NULL,
    position TEXT NOT NULL,
    responsibilities TEXT,
        FOREIGN KEY (resume_id) REFERENCES resumes(id)
);

CREATE TABLE if not exists vacancies (
    id IDENTITY PRIMARY KEY,
    name TEXT NOT NULL,
    description TEXT NOT NULL,
    category_id INTEGER NOT NULL,
    salary REAL,
    exp_from INTEGER,
    exp_to INTEGER,
    is_active BOOLEAN NOT NULL,
    author_id INTEGER NOT NULL,
    created_date TIMESTAMP NOT NULL,
    update_time TIMESTAMP NOT NULL,
        FOREIGN KEY (category_id) REFERENCES categories(id),
        FOREIGN KEY (author_id) REFERENCES users(id)
);

CREATE TABLE if not exists responded_applicants (
    id IDENTITY PRIMARY KEY,
    resume_id INTEGER NOT NULL,
    vacancy_id INTEGER NOT NULL,
    confirmation BOOLEAN,
        FOREIGN KEY (resume_id) REFERENCES resumes(id),
        FOREIGN KEY (vacancy_id) REFERENCES vacancies(id)
);

CREATE TABLE if not exists messages (
    id IDENTITY PRIMARY KEY,
    responded_applicants_id INTEGER NOT NULL,
    content TEXT NOT NULL,
    timestamp TIMESTAMP NOT NULL,
    FOREIGN KEY (responded_applicants_id) REFERENCES responded_applicants(id)
);


INSERT INTO users (name, surname, age, email, password, phone_number, avatar, account_type ) VALUES
    ('Billy', 'Herryngton', 37, 'gachi@muchi.com', 'password123', '1234567890', '', 'applicant'),
    ('Van', 'Darkholme', 45, 'boss@ofgym.com', 'secure', '0987654321', '', 'employer');

INSERT INTO categories (name) VALUES ('IT'), ('Finances'), ('Education');
INSERT INTO categories (name, parent_id) VALUES ('Software Development', 1), ('Analytics', 2);

INSERT INTO contact_types (type) VALUES ('Phone'), ('Email'), ('Telegram');

INSERT INTO resumes (applicant_id, name, category_id, salary, is_active, created_date, update_time) VALUES
    (1, 'Java the Right Version ♂', 4, 15000.00, TRUE, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),
    (2, 'Kotlin the Right Version ♂', 1, 12400.00, TRUE, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());

INSERT INTO contact_info (resume_id, type_id, "value") VALUES
    (1, 1, '1234567890'),
    (1, 2, 'gachi@muchi.com');


INSERT INTO vacancies (name, description, category_id, salary, exp_from, is_active, author_id, created_date, update_time) VALUES
    ('Java Developer', 'need someone with straight hands', 4, 180000.00, 3, TRUE, 2, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),
    ('Kotlin Developer', 'need someone with straight hands', 1, 90000.00, 1, TRUE, 2, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());

INSERT INTO education_info (resume_id, institution, program, start_date, end_date, degree) VALUES
    (1, 'University', 'Computer Science', '2010-09-01', '2015-06-25', 'Professor');

INSERT INTO work_experience_info (resume_id, years,  company_name, position, responsibilities) VALUES
    (1, 2, 'Right Version Company','Junior', 'Support & Assist');

INSERT INTO responded_applicants (resume_id, vacancy_id, confirmation) VALUES
    (1, 1, TRUE);

INSERT INTO messages (responded_applicants_id, content, timestamp) VALUES
    (1, 'Im interested in vacancy you have posted', CURRENT_TIMESTAMP());