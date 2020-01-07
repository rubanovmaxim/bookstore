create extension "uuid-ossp";


CREATE SEQUENCE user_id_seq;

CREATE TABLE users (
  id  BIGINT not null DEFAULT nextval('user_id_seq'),
  username VARCHAR(50) NOT NULL UNIQUE,
  password VARCHAR(100) NOT NULL,
  e_mail  VARCHAR(100) not null,
  phone   VARCHAR(20) not null,
  enabled BOOLEAN  not null,
  PRIMARY KEY (id)
);


CREATE SEQUENCE authority_id_seq;

CREATE TABLE authorities (
  ID BIGINT not null DEFAULT nextval('authority_id_seq'),
  username VARCHAR(50) NOT NULL,
  authority VARCHAR(50) NOT NULL,
  FOREIGN KEY (username) REFERENCES users(username)
);

CREATE UNIQUE INDEX ix_auth_username
  on authorities (username,authority);



insert into "users" ( username,e_mail,phone, password, enabled)
values ( 'maks', 'maksru@mail.ru' ,'+79632463534','$2a$10$xvVCsGs9H0sxf19Y3wJ3L.N08rqofZhbJX2MErDzywRUKzV6n9DI6', true);

insert into "users" ( username,e_mail,phone, password, enabled)
values ( 'admin', 'maksru@mail.ru' ,'+79632463534', '$2a$10$xvVCsGs9H0sxf19Y3wJ3L.N08rqofZhbJX2MErDzywRUKzV6n9DI6', true);

insert into "users" ( username,e_mail,phone, password, enabled)
values ( 'test', 'rubanov.test@gmail.com' ,'+79632463534', '$2a$10$xvVCsGs9H0sxf19Y3wJ3L.N08rqofZhbJX2MErDzywRUKzV6n9DI6', true);


insert into authorities (username,authority)
values ('admin','ADMIN');

insert into authorities ( username,authority)
values ( 'maks','USER');

insert into authorities ( username,authority)
values ( 'test','USER');


-------------------------------------------------



-- Used by Spring Remember Me API.
CREATE TABLE Persistent_Logins (

    username varchar(64) not null,
    series varchar(64) not null,
    token varchar(64) not null,
    last_used timestamp not null,
    PRIMARY KEY (series)

);


-- Create table

CREATE SEQUENCE book_id_seq;

create table BOOK
(
  ID           BIGINT not null DEFAULT nextval('book_id_seq'),
  P_HOUSE_ID   BIGINT not null,
  NAME         VARCHAR(300) not null,
  AUTHOR VARCHAR(128) not null,
  PRICE DOUBLE PRECISION not null

) ;
--
alter table BOOK
  add constraint BOOK_PK primary key (ID);



-- Create table
CREATE SEQUENCE publishing_house_id_seq;

create table PUBLISHING_HOUSE
(
  ID  BIGINT not null DEFAULT nextval('publishing_house_id_seq'),
  NAME VARCHAR(30) not null,
  ADDRESS VARCHAR(200) not null,
  PHONE VARCHAR(15) not null
) ;
--
alter table  PUBLISHING_HOUSE
  add constraint PUBLISHING_HOUSE_PK primary key (ID);


-- Create table
CREATE SEQUENCE order_id_seq;

create table "order"
(
  ID  BIGINT not null DEFAULT nextval('order_id_seq'),
  USER_ID  BIGINT not null,
  STATUS VARCHAR(30) not null
);

alter table  "order"
  add constraint ORDER_PK primary key (ID);

alter table "order"
  add constraint ORDER_FK1 foreign key (USER_ID)
  references "users" (id);

-- Create table
CREATE SEQUENCE order_content_id_seq;

create table ORDER_CONTENT
(
  ID  BIGINT not null DEFAULT nextval('order_content_id_seq'),
  ORDER_ID BIGINT not null,
  BOOK_ID  BIGINT not null
);


alter table ORDER_CONTENT
  add constraint ORDER_CONTENT_FK1 foreign key (ORDER_ID)
  references "order" (ID);

alter table ORDER_CONTENT
  add constraint ORDER_CONTENT_FK2 foreign key (BOOK_ID)
  references BOOK (ID);


-- -- Create table
-- create table BASKET
-- (
--   USER_ID BIGINT not null,
--   BOOK_ID  BIGINT not null
-- );
-- --

alter table BOOK
  add constraint BOOK_FK1 foreign key (P_HOUSE_ID)
  references PUBLISHING_HOUSE (ID);


-- alter table BASKET
--   add constraint BASKET_FK1 foreign key (USER_ID)
--   references "user" (USER_ID);
--
-- alter table BASKET
--   add constraint BASKET_FK2 foreign key (BOOK_ID)
--   references BOOK (ID);


--------------------------------------



insert into PUBLISHING_HOUSE ( NAME, ADDRESS, PHONE)
values ( 'Москва', 'Россия , г.Москва, Кремль', '8-905-208-15-22');

insert into PUBLISHING_HOUSE ( NAME, ADDRESS, PHONE)
values ( 'Питер', 'Россия ,г. СПб, пр. КОролева 50', '8-911-408-15-57');

---


insert into BOOK ( P_HOUSE_ID, NAME, AUTHOR, PRICE)
values ( 1, 'Руслан и Людмила', 'Пушкин А.С.',200);

insert into BOOK ( P_HOUSE_ID, NAME, AUTHOR, PRICE)
values ( 2, 'Война и мир', 'Толстой Л.Н',300);

---
Commit;