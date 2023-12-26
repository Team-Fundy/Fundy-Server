create table user_authority
(
    user_id binary(16),
    authority varchar(16) not null,
    foreign key (user_id) references fundy_user(id) on delete cascade
);