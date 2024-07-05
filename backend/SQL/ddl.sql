create table tip
(
    id         varchar(256)            not null
        constraint tip_pk
            primary key,
    title      varchar(256)            not null,
    content    text                    not null,
    writer_id  bigint,
    updated_at timestamp               not null,
    created_at timestamp default now() not null
);

comment on table tip is '꿀팁 게시판';

comment on column tip.id is '꿀팁 id';

comment on column tip.title is '꿀팁 게시판 제목';

comment on column tip.content is '꿀팁 내용';

comment on column tip.writer_id is '작성자 id';

comment on column tip.updated_at is '수정 날짜';

comment on column tip.created_at is '등록 날짜';

alter table tip
    owner to dev;

create table tip_like
(
    id         varchar(256)            not null
        constraint tip_like_pk
            primary key,
    tip_id     varchar(256)
        constraint tip_like_tip_id_fk
            references tip,
    member_id  varchar(256)            not null,
    created_at timestamp default now() not null
);

comment on table tip_like is '꿀팁 좋아요';

comment on column tip_like.tip_id is '꿀팁 아이디';

comment on column tip_like.member_id is '회원 아이디';

comment on column tip_like.created_at is '등록 일자';

alter table tip_like
    owner to dev;

create unique index tip_like_tip_id_member_id_uindex
    on tip_like (tip_id, member_id);

