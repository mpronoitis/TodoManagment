DROP TABLE IF EXISTS roles;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS users_roles;
DROP TABLE IF EXISTS todos;

CREATE TABLE roles (
                       id INTEGER,
                       name VARCHAR(50) NOT NULL,
                       PRIMARY KEY (id)
);

CREATE TABLE users (
                       id INTEGER,
                       username VARCHAR(50) NOT NULL,
                       password VARCHAR(255) NOT NULL,
                       email VARCHAR(50) NOT NULL,
                       PRIMARY KEY (id)
);

CREATE TABLE users_roles (
                             user_id INTEGER NOT NULL,
                             role_id INTEGER NOT NULL,
                             PRIMARY KEY (user_id, role_id)
);

CREATE TABLE todos (
                        id INTEGER,
                        title VARCHAR(255) NOT NULL,
                        description VARCHAR(255) NOT NULL,
                        is_completed BOOLEAN NOT NULL,
                        PRIMARY KEY (id)
    );
