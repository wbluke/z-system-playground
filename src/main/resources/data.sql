drop table if exists document;

create table document
(
    id             bigint not null auto_increment primary key,
    title          varchar(255),
    category       varchar(50),
    contents       varchar(255),
    approval_state text
);

insert into document(title, category, contents, approval_state)
values ('팀 운영비 사용 정산의 건', 'OPERATING_EXPENSES', '운영비 사용 내역입니다.', 'WAITING')

insert into document(title, category, contents, approval_state)
values ('도서지원비 정산의 건', 'EDUCATION', '도서지원비 사용 내역입니다.', 'WAITING')
