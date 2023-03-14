create sequence answer_seq start 1 increment 1;
create sequence answer_vote_seq start 1 increment 1;
create sequence badge_seq start 1 increment 1;
create sequence chat_seq start 1 increment 1;
create sequence comment_seq start 1 increment 1;
create sequence ignore_tag_seq start 1 increment 1;
create sequence message_seq start 1 increment 1;
create sequence question_seq start 1 increment 1;
create sequence question_viewed_seq start 1 increment 1;
create sequence related_tag_seq start 1 increment 1;
create sequence reputation_seq start 1 increment 1;
create sequence role_seq start 1 increment 1;
create sequence tag_seq start 1 increment 1;
create sequence tracked_tag_seq start 1 increment 1;
create sequence user_seq start 1 increment 1;
create sequence user_badges_seq start 1 increment 1;
create sequence user_favorite_question_seq start 1 increment 1;
create sequence vote_question_seq start 1 increment 1;

create table answer (
                        id int8 not null,
                        date_accept_time timestamp,
                        html_body text not null,
                        is_deleted boolean not null,
                        is_deleted_by_moderator boolean not null,
                        is_helpful boolean not null,
                        persist_date timestamp,
                        update_date timestamp not null,
                        question_id int8 not null,
                        user_id int8 not null,
                        primary key (id)
);

create table badges (
                        id int8 not null,
                        badge_name varchar(255),
                        description varchar(255),
                        reputations_for_merit int4,
                        primary key (id)
);

create table bookmarks (
                           id  bigserial not null,
                           question_id int8 not null,
                           user_id int8 not null,
                           primary key (id)
);

create table chat (
                      id int8 not null,
                      chat_type int2,
                      persist_date timestamp,
                      title varchar(255),
                      primary key (id)
);

create table comment (
                         id int8 not null,
                         comment_type int2 not null,
                         last_redaction_date timestamp,
                         persist_date timestamp,
                         text varchar(255) not null,
                         user_id int8 not null,
                         primary key (id)
);

create table comment_answer (
                                comment_id int8 not null,
                                answer_id int8 not null,
                                primary key (comment_id)
);

create table comment_question (
                                  comment_id int8 not null,
                                  question_id int8 not null,
                                  primary key (comment_id)
);

create table group_chat (
                            chat_id int8 not null,
                            primary key (chat_id)
);

create table groupchat_has_users (
                                     chat_id int8 not null,
                                     user_id int8 not null,
                                     primary key (chat_id, user_id)
);

create table message (
                         id int8 not null,
                         last_redaction_date timestamp not null,
                         message text,
                         persist_date timestamp,
                         chat_id int8 not null,
                         user_sender_id int8 not null,
                         primary key (id)
);

create table question (
                          id int8 not null,
                          description text not null,
                          is_deleted boolean,
                          last_redaction_date timestamp not null,
                          persist_date timestamp,
                          title varchar(255) not null,
                          user_id int8 not null,
                          primary key (id)
);

create table question_has_tag (
                                  question_id int8 not null,
                                  tag_id int8 not null
);

create table question_viewed (
                                 id int8 not null,
                                 persist_date timestamp,
                                 question_id int8,
                                 user_id int8,
                                 primary key (id)
);

create table related_tag (
                             id int8 not null,
                             child_tag int8 not null,
                             main_tag int8 not null,
                             primary key (id)
);

create table reputation (
                            id int8 not null,
                            count int4,
                            persist_date timestamp,
                            type int4 not null,
                            answer_id int8,
                            author_id int8 not null,
                            question_id int8,
                            sender_id int8,
                            primary key (id)
);

create table role (
                      id int8 not null,
                      name varchar(255),
                      primary key (id)
);

create table single_chat (
                             chat_id int8 not null,
                             use_two_id int8 not null,
                             user_one_id int8 not null,
                             primary key (chat_id)
);

create table tag (
                     id int8 not null,
                     description varchar(255),
                     name varchar(255) not null,
                     persist_date timestamp,
                     primary key (id)
);

create table tag_ignore (
                            id int8 not null,
                            persist_date timestamp,
                            ignored_tag_id int8 not null,
                            user_id int8 not null,
                            primary key (id)
);

create table tag_tracked (
                             id int8 not null,
                             persist_date timestamp,
                             tracked_tag_id int8 not null,
                             user_id int8 not null,
                             primary key (id)
);

create table user_badges (
                             id int8 not null,
                             ready boolean,
                             badges_id int8,
                             user_id int8,
                             primary key (id)
);

create table user_entity (
                             id int8 not null,
                             about varchar(255),
                             city varchar(255),
                             email varchar(255),
                             full_name varchar(255),
                             image_link varchar(255),
                             is_deleted boolean,
                             is_enabled boolean,
                             last_redaction_date timestamp not null,
                             link_github varchar(255),
                             link_site varchar(255),
                             link_vk varchar(255),
                             nickname varchar(255),
                             password varchar(255),
                             persist_date timestamp,
                             role_id int8 not null,
                             primary key (id)
);

create table user_favorite_question (
                                        id int8 not null,
                                        persist_date timestamp not null,
                                        question_id int8 not null,
                                        user_id int8 not null,
                                        primary key (id)
);

create table votes_on_answers (
                                  id int8 not null,
                                  persist_date timestamp,
                                  vote varchar(255),
                                  answer_id int8 not null,
                                  user_id int8 not null,
                                  primary key (id)
);

create table votes_on_questions (
                                    id int8 not null,
                                    persist_date timestamp,
                                    vote varchar(255),
                                    question_id int8,
                                    user_id int8,
                                    primary key (id)
);

alter table answer
    add constraint FK8frr4bcabmmeyyu60qt7iiblo
        foreign key (question_id)
            references question;

alter table answer
    add constraint FK2k36bpxtxus6sxyg8mlof5js1
        foreign key (user_id)
            references user_entity;

alter table bookmarks
    add constraint FKmge2txjhd0hq1ji859u0ylwyw
        foreign key (question_id)
            references question;

alter table bookmarks
    add constraint FKlo0de495ruc6wr4j8uim9fc2e
        foreign key (user_id)
            references user_entity;

alter table comment
    add constraint FKl4xlhaqa07wrvf446sjwngh8j
        foreign key (user_id)
            references user_entity;

alter table comment_answer
    add constraint FKdqdik3krvw5227fq8oiei7vn4
        foreign key (answer_id)
            references answer;

alter table comment_answer
    add constraint FKoe2xyxejln3qgk05w6y3pih6j
        foreign key (comment_id)
            references comment;

alter table comment_question
    add constraint FK2ob5qywtuo1093g7ql5kfx3rk
        foreign key (comment_id)
            references comment;

alter table comment_question
    add constraint FKup4pc0f37svsrid4lwxkeqf2
        foreign key (question_id)
            references question;

alter table group_chat
    add constraint FKhwhjdn057057sx83g73m0iag5
        foreign key (chat_id)
            references chat;

alter table groupchat_has_users
    add constraint FKd1tr2cxaudn9q2ea52ydi3sea
        foreign key (user_id)
            references user_entity;

alter table groupchat_has_users
    add constraint FK2rtdyfisemfv23xnmsxopptng
        foreign key (chat_id)
            references group_chat;

alter table message
    add constraint FKmejd0ykokrbuekwwgd5a5xt8a
        foreign key (chat_id)
            references chat;

alter table message
    add constraint FKlgr15f5qgibclatnawcadb4x9
        foreign key (user_sender_id)
            references user_entity;

alter table question
    add constraint FKtg7oxllrdp5tf8isrfmory7yo
        foreign key (user_id)
            references user_entity;

alter table question_has_tag
    add constraint FKrg80k8r5dshnsufpxu1p0d8gp
        foreign key (tag_id)
            references tag;

alter table question_has_tag
    add constraint FK95qkp3v84rs97v7alcfk77fdr
        foreign key (question_id)
            references question;

alter table question_viewed
    add constraint FKqeg29r7uu1g1flepcpm4mv1g2
        foreign key (question_id)
            references question;

alter table question_viewed
    add constraint FKnw7lp8384y34tnaulsvmmfnub
        foreign key (user_id)
            references user_entity;

alter table related_tag
    add constraint FKk5320uu5wbdyg2xku67qp8aqe
        foreign key (child_tag)
            references tag;

alter table related_tag
    add constraint FKnb1cmjyv0amiv491xlx4xi6w1
        foreign key (main_tag)
            references tag;

alter table reputation
    add constraint FKsqo308es5w7bvg914o1mwrn2m
        foreign key (answer_id)
            references answer;

alter table reputation
    add constraint FKlndsyfrqg3b6g87gplymx8y6r
        foreign key (author_id)
            references user_entity;

alter table reputation
    add constraint FKolofwdfwvpjawwa9gpfvtycwf
        foreign key (question_id)
            references question;

alter table reputation
    add constraint FKqm7j2hn9g8f57qjvw5r1vebcs
        foreign key (sender_id)
            references user_entity;

alter table single_chat
    add constraint FKent72vkye8jx5pivbn0fk0h2m
        foreign key (chat_id)
            references chat;

alter table single_chat
    add constraint FK3n1idibr3yw114ogpftw7tc6o
        foreign key (use_two_id)
            references user_entity;

alter table single_chat
    add constraint FKld25t5j2cqwg9mgp2njmu31xo
        foreign key (user_one_id)
            references user_entity;

alter table tag_ignore
    add constraint FK7wnisc677h8s3wb6o4mv2oqkf
        foreign key (ignored_tag_id)
            references tag;

alter table tag_ignore
    add constraint FKjjdrwp57ok5c1ckuwqsft1qxf
        foreign key (user_id)
            references user_entity;

alter table tag_tracked
    add constraint FKpn5ysi8igj9rmnun213hg5uf
        foreign key (tracked_tag_id)
            references tag;

alter table tag_tracked
    add constraint FKi60fykn402bntk377q2t9ybde
        foreign key (user_id)
            references user_entity;

alter table user_badges
    add constraint FK337cc9ux9pqlb991y6w43to3j
        foreign key (badges_id)
            references badges;

alter table user_badges
    add constraint FKfb1c6pv1fesnielua9xtgci76
        foreign key (user_id)
            references user_entity;

alter table user_entity
    add constraint FKpostrnt7qdgc4m56i71qlkl61
        foreign key (role_id)
            references role;

alter table user_favorite_question
    add constraint FKlp6cfgrasgyon52khtdjjgi02
        foreign key (question_id)
            references question;

alter table user_favorite_question
    add constraint FKnhawqa1q89ai58etpxjbvlt3c
        foreign key (user_id)
            references user_entity;

alter table votes_on_answers
    add constraint FKjyyufwvq2xtar6hhcajuv73f9
        foreign key (answer_id)
            references answer;

alter table votes_on_answers
    add constraint FKi8gxkndohkt08w93qkx756d8e
        foreign key (user_id)
            references user_entity;

alter table votes_on_questions
    add constraint FKiu40gq78m9r2n8nmwnefjxyry
        foreign key (question_id)
            references question;

alter table votes_on_questions
    add constraint FK5rc898att1153186h64v2lym2
        foreign key (user_id)
            references user_entity;