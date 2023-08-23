create sequence message_star_seq start 1 increment 1;

create table message_star (
    id int8 not null,
    user_id int8 not null,
    message_id int8 not null,
    persist_date timestamp,
    primary key(id)
);
