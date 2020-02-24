DELETE FROM user_roles;
DELETE FROM users;

DELETE FROM meals;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password) VALUES
  ('User', 'user@yandex.ru', 'password'),
  ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id) VALUES
  ('ROLE_USER', 100000),
  ('ROLE_ADMIN', 100001);
INSERT INTO  meals (dateTime, description, calories, user_id ) VALUES


('2016-06-22 19:10','diner',1222, 100000),
('2017-06-22 12:10','lunch',1000,100001),
('2018-06-22 13:10','diner',1000,100000),
('2019-06-22 11:10','lunch',1000,100001);

