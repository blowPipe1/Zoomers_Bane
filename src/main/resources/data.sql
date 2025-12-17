create table if not exists USERS(
                                    id identity primary key,
                                    name varchar(45),
                                    surName varchar(45),
                                    age int,
                                    email varchar(255) not null,
                                    password varchar(255) not null,
                                    phoneNumber varchar(15),
                                    avatar varchar,
                                    accountType varchar
);

insert into USERS(name, surName, age, email, password,phoneNumber, avatar, accountType)
values('Giorno', 'Giovanno', 17, 'goldenwind@mudamuda', 'mudamuda', '123','', 'EMPLOYER'),
      ('Josuke', 'Higashikata', 18, 'crazyDia@dora', 'doradora', '6543', '', 'EMPLOYEE');



create table if not exists VACANCIES(
                                        id identity primary key,
                                        name varchar(45),
                                        description varchar(255),
                                        category varchar(45),
                                        salary double,
                                        expFrom int,
                                        expTo int,
                                        isActive boolean,
                                        author int,
                                        createdDate timestamp,
                                        updateTime timestamp
);

insert into VACANCIES(name, description, category, salary, expFrom,expTo, isActive, author, createdDate)
values ( 'Plumber', 'need a plumber', 'Labor', 250, 0,3,true,1, LOCALTIMESTAMP()),
       ( 'Cook', 'meth', 'Mental', 2450, 3,6,true,1, LOCALTIMESTAMP());

create table if not exists RESUMES(
                                      id identity primary key,
                                      author int,
                                      name varchar(45),
                                      category varchar(45),
                                      salary double,
                                      isActive boolean,
                                      createdDate timestamp,
                                      updateTime timestamp
);

insert into RESUMES(author, name, category, salary,  isActive,  createdDate)
values ( 2, 'Pro Plumber', 'Labor', 150, true, LOCALTIMESTAMP() ),
       ( 2, 'Pro Cook', 'Mental', 1502, false, LOCALTIMESTAMP() );