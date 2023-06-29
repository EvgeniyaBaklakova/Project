create sequence group_bookmark_seq start 1 increment 1;

create table group_bookmark (
    id int8 not null ,
    title varchar not null ,
    primary key(id)
);

create table group_has_bookmark (
    group_bookmark_id int8 not null,
    bookmarks_id int8 not null
);

alter table group_has_bookmark
    add constraint fk_group_has_bookmarks_group_bookmark
        foreign key (group_bookmark_id)
            references group_bookmark;

alter table group_has_bookmark
    add constraint fk_group_has_bookmarks_bookmarks
        foreign key (bookmarks_id)
            references bookmarks;