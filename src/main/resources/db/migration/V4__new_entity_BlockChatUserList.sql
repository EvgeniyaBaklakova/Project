create sequence block_chat_user_list_seq start 1 increment 1;


create table block_chat_user_list (
                               id int8 not null,
                               persist_date timestamp not null,
                               user_profile int8 not null,
                               user_blocked int8 not null,
                               primary key (id)
);

alter table block_chat_user_list
    add constraint fk_block_chat_user_list_user_profile
        foreign key (user_profile)
            references user_entity;

alter table block_chat_user_list
    add constraint fk_block_chat_user_list_user_blocked
        foreign key (user_blocked)
            references user_entity;