-- Feel free to augment or modify these schemas (and the corresponding data) as you see fit!
DROP TABLE IF EXISTS visits;
DROP TABLE IF EXISTS patients;
DROP TABLE IF EXISTS hospitals;
DROP TABLE IF EXISTS security_users;

CREATE TABLE patients
(
    id       IDENTITY,
    name     VARCHAR                NOT NULL,
    username varchar_ignorecase(50) NOT NULL
);

CREATE TABLE hospitals
(
    id   IDENTITY,
    name VARCHAR NOT NULL
);

CREATE TABLE visits
(
    id          IDENTITY,
    hospital_id NUMBER,
    patient_id  NUMBER,
    -- DEFAULT value to NOW is just for illustrative purpose. Of course it is inappropriate
    -- for production code. Anyway, for production we should have defined available timeslots,
    -- based on the doctors work schedule, etc.
    datetime    TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW()
);

create table security_users
(
    username varchar_ignorecase(50)  not null primary key,
    password varchar_ignorecase(150) not null,
    enabled  boolean                 not null
);


