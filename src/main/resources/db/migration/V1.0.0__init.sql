create table users
(
    username             varchar(255)          not null primary key,
    password             varchar(255)          not null,
    enabled              boolean               not null default true
);

create table user_detail
(
    id                   integer               not null primary key,
    username             varchar(255)          not null,
    email                varchar(255)          not null,
    first_name           varchar(255)          not null,
    last_name            varchar(255)          not null,
    constraint FK__USER_DETAIL__USERNAME foreign key (username) references users (username),
    constraint UQ__EMAIL unique (email)
);

create table authorities
(
    username             varchar(255)          not null,
    authority            varchar(36)           not null,
    constraint FK__AUTHORITIES__USERNAME foreign key (username) references users (username)
);

create table post
(
    id                   integer               not null primary key,
    author               varchar(255)          not null,
    title                varchar(255)          not null,
    content              varchar(2000)         not null,
    enable_comments      boolean               not null default true,
    created_at           timestamp             not null,
    constraint FK__POST__AUTHOR foreign key (author) references users (username)
);

create table comment
(
    id                   integer               not null primary key,
    author               varchar(255)          not null,
    post_id              integer               not null,
    content              varchar(1000)         not null,
    created_at           timestamp             not null,
    constraint FK__COMMENT__AUTHOR foreign key (author) references users (username),
    constraint FK__COMMENT__POST_ID foreign key (post_id) references post (id)
);

create table notification
(
    id                   integer               not null primary key,
    username             varchar(255)          not null,
    post_id              integer               not null,
    content              varchar(255)          not null,
    created_at           timestamp             not null,
    constraint FK__NOTIFICATION__USERNAME foreign key (username) references users (username),
    constraint FK__NOTIFICATION__POST_ID foreign key (post_id) references post (id)
);

create table likes
(
    id                   integer               not null primary key,
    username             varchar(255)          not null,
    post_id              integer               not null,
    created_at           timestamp             not null,
    constraint FK__LIKES__USERNAME foreign key (username) references users (username),
    constraint FK__LIKES__POST_ID foreign key (post_id) references post (id)
);



