create table users
(
    id                   serial                not null primary key,
    username             varchar(255)          not null,
    password             varchar(255)          not null,
    email                varchar(255)          not null,
    first_name           varchar(255)          not null,
    last_name            varchar(255)          not null,
    created_at           timestamp             not null,
    constraint UQ__USERNAME unique (username),
    constraint UQ__EMAIL unique (email)
);

create table post
(
    id                   serial                not null primary key,
    author_id            integer               not null,
    title                varchar(255)          not null,
    content              varchar(2000)         not null,
    enable_comments      boolean               not null default true,
    created_at           timestamp             not null,
    constraint FK__POST__AUTHOR_ID foreign key (author_id) references users (id)
);

create table comment
(
    id                   serial                not null primary key,
    author_id            integer               not null,
    post_id              integer               not null,
    content              varchar(1000)         not null,
    created_at           timestamp             not null,
    constraint FK__COMMENT__AUTHOR_ID foreign key (author_id) references users (id),
    constraint FK__COMMENT__POST_ID foreign key (post_id) references post (id)
);

create table notification
(
    id                   serial                not null primary key,
    sender_id            integer               not null,
    receiver_id          integer               not null,
    post_id              integer               not null,
    content              varchar(255)          not null,
    created_at           timestamp             not null,
    constraint FK__NOTIFICATION__SENDER_ID foreign key (sender_id) references users (id),
    constraint FK__NOTIFICATION__RECEIVER_ID foreign key (receiver_id) references users (id),
    constraint FK__NOTIFICATION__POST_ID foreign key (post_id) references post (id)
);

create table likes
(
    id                   serial                not null primary key,
    user_id              integer               not null,
    post_id              integer               not null,
    created_at           timestamp             not null,
    constraint FK__LIKES__USER_ID foreign key (user_id) references users (id),
    constraint FK__LIKES__POST_ID foreign key (post_id) references post (id)
);



