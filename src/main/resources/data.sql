drop database if exists sk_example_db;
drop user if exists sk_example_user;
create database sk_example_db;
create user sk_example_user;

CREATE TABLE sk_example_table
(
    id SERIAL,
    obj JSONB NOT NULL,
    PRIMARY KEY (id)
);

INSERT INTO sk_example_table (obj) VALUES ('{"current": 0}'::JSONB);
