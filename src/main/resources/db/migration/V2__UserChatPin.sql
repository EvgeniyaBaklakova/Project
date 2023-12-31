create sequence user_chat_pin_seq start 1 increment 1;


create table user_chat_pin (
                            id int8 not null,
                            persist_date timestamp not null,
                            chat_id int8 not null,
                            user_id int8 not null,
                            primary key (id)
);

alter table user_chat_pin
    add constraint FK_user_chat_pin_chat
        foreign key (chat_id)
            references chat;

alter table user_chat_pin
    add constraint FK_user_chat_pin_user
        foreign key (user_id)
            references user_entity;