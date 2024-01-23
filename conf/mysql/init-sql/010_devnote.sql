create table devnote
(
    id bigint not null auto_increment primary key ,
    title varchar(255) not null,
    content varchar(255) not null,
    thumbnail varchar(255) not null,
    created_at datetime not null default current_timestamp
);