CREATE SEQUENCE user_id_seq;
-- Create table
create table "user"
(
  USER_ID           BIGINT not null DEFAULT nextval('user_id_seq'),
  USER_NAME         VARCHAR(36) not null,
  E_MAIL            VARCHAR(100) not null,
  PHONE             VARCHAR(20) not null,
  ENCRYTED_PASSWORD VARCHAR(128) not null,
  ENABLED           BOOLEAN  not null
) ;
--
alter table "user"
  add constraint USER_PK primary key (USER_ID);

alter table "user"
  add constraint USER_UK unique (USER_NAME);


-- Create table
CREATE SEQUENCE role_id_seq;

create table ROLE
(
  ROLE_ID   BIGINT not null DEFAULT nextval('role_id_seq'),
  ROLE_NAME VARCHAR(30) not null
) ;
--
alter table ROLE
  add constraint ROLE_PK primary key (ROLE_ID);

alter table ROLE
  add constraint ROLE_UK unique (ROLE_NAME);


-- Create table
CREATE SEQUENCE user_role_id_seq;

create table USER_ROLE
(
  ID      BIGINT not null DEFAULT nextval('user_role_id_seq'),
  USER_ID BIGINT not null,
  ROLE_ID BIGINT not null
);
--
alter table USER_ROLE
  add constraint USER_ROLE_PK primary key (ID);

alter table USER_ROLE
  add constraint USER_ROLE_UK unique (USER_ID, ROLE_ID);

alter table USER_ROLE
  add constraint USER_ROLE_FK1 foreign key (USER_ID)
  references "user" (USER_ID);

alter table USER_ROLE
  add constraint USER_ROLE_FK2 foreign key (ROLE_ID)
  references ROLE (ROLE_ID);



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
  references "user" (USER_ID);

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

insert into "user" ( USER_NAME,E_MAIL,PHONE, ENCRYTED_PASSWORD, ENABLED)
values ( 'admin1', 'maksru@mail.ru' ,'+79632463534','$2a$10$xvVCsGs9H0sxf19Y3wJ3L.N08rqofZhbJX2MErDzywRUKzV6n9DI6', true);

insert into "user" ( USER_NAME,E_MAIL,PHONE, ENCRYTED_PASSWORD, ENABLED)
values ( 'admin', 'maksru@mail.ru' ,'+79632463534', '$2a$10$xvVCsGs9H0sxf19Y3wJ3L.N08rqofZhbJX2MErDzywRUKzV6n9DI6', true);

---

insert into role ( ROLE_NAME)
values ( 'ADMIN');

insert into role (ROLE_NAME)
values ('USER');

---

insert into user_role ( USER_ID, ROLE_ID)
values ( 1, 1);

insert into user_role ( USER_ID, ROLE_ID)
values ( 1, 2);

insert into user_role (USER_ID, ROLE_ID)
values ( 2, 2);

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