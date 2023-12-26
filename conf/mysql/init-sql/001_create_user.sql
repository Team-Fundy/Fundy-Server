create table fundy_user
(
    id       binary(16) default (UUID_TO_BIN(UUID(), 1)) primary key,
    email    varchar(60) not null unique,
    nickname varchar(30) not null unique,
    profile varchar(100) not null default "https://fundy-bucket.s3.ap-northeast-2.amazonaws.com/default/profileImage.png",
    password varchar(100) not null,
    phone varchar(9),
    creator_name varchar(30) unique,
    creator_description varchar(255) default "크리에이터 소개란입니다.",
    creator_profile varchar(100) default "https://fundy-bucket.s3.ap-northeast-2.amazonaws.com/default/profileImage.png",
    creator_background varchar(100) default "https://fundy-bucket.s3.ap-northeast-2.amazonaws.com/default/profileImage.png"
);