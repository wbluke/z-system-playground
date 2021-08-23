drop table if exists document;
drop table if exists user;

create table user
(
    id       bigint not null auto_increment primary key,
    email    varchar(255),
    password varchar(255),
    name     varchar(255)
);

create table document
(
    id             bigint not null auto_increment primary key,
    title          varchar(255),
    category       varchar(50),
    contents       varchar(255),
    approval_state text,
    drafter_id     bigint
);

insert into user(id, email, password, name)
values (1, 'wbluke@gmail.com', '1234', '박우빈');

insert into document(id, title, category, contents, approval_state, drafter_id)
values (1, '팀 운영비 사용 정산의 건', 'OPERATING_EXPENSES', '운영비 사용 내역입니다.', 'DRAFTING', 1);

insert into document(id, title, category, contents, approval_state, drafter_id)
values (2, '도서지원비 정산의 건', 'EDUCATION', '도서지원비 사용 내역입니다.', 'DRAFTING', 1);
