INSERT INTO roles (id, name) VALUES (1, 'ROLE_USER');
INSERT INTO roles (id, name) VALUES (2, 'ROLE_ADMIN');

INSERT INTO USERS (id, username, password, email) VALUES (1, 'gpro', '$2a$10$RgKfAYmkrhQp7UjlYWsUMucoCgxJlp0jqmVSdilxfDFdfDPWX3KnS','gpro@gmail.com');
INSERT INTO USERS (id, username, password, email) VALUES (2, 'admin', '$2a$10$RgKfAYmkrhQp7UjlYWsUMucoCgxJlp0jqmVSdilxfDFdfDPWX3KnS','admin@gmail.com');

INSERT INTO USERS_ROLES (user_id, role_id) VALUES (1, 1);
INSERT INTO USERS_ROLES (user_id, role_id) VALUES (2, 2);

INSERT INTO todos (id, title, description, is_completed) VALUES (1, 'Learn Spring Boot', 'Learn Spring Boot', false);
INSERT INTO todos (id, title, description, is_completed) VALUES (2, 'Spring Boot', 'Learn Spring Boot', false);
