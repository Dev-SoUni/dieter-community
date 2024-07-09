-- 테스트용

create table member
(
    id         varchar(256)            not null
        constraint member_pk
            primary key,
    email      varchar(256)            not null,
    nickname   varchar(64)             not null,
    password   varchar(512)            not null,
    updated_at timestamp               not null,
    created_at timestamp default now() not null
);

comment on table member is '회원';

comment on column member.email is '이메일 주소';

comment on column member.nickname is '닉네임';

comment on column member.password is '비밀번호';

comment on column member.updated_at is '수정 날짜';

comment on column member.created_at is '등록 날짜';

create table tip
(
    id         varchar(256)            not null
        constraint tip_pk
            primary key,
    title      varchar(256)            not null,
    content    text                    not null,
    writer_id  varchar(256)            not null
        constraint tip_member_id_fk
            references member,
    updated_at timestamp               not null,
    created_at timestamp default now() not null
);

comment on table tip is '꿀팁 게시판';

comment on column tip.id is '꿀팁 ID';

comment on column tip.title is '꿀팁 제목';

comment on column tip.content is '꿀팁 내용';

comment on column tip.writer_id is '작성자 ID';

comment on column tip.updated_at is '수정 날짜';

comment on column tip.created_at is '등록 날짜';

create table tip_like
(
    id         varchar(256)            not null
        constraint tip_like_pk
            primary key,
    tip_id     varchar(256)            not null
        constraint tip_like_tip_id_fk
            references tip,
    member_id  varchar(256)            not null
        constraint tip_like_member_id_fk
            references member,
    created_at timestamp default now() not null
);

comment on table tip_like is '꿀팁 좋아요';

comment on column tip_like.tip_id is '꿀팁 게시물 ID';

comment on column tip_like.member_id is '사용자 ID';

comment on column tip_like.created_at is '등록 날짜';

create unique index tip_like_tip_id_member_id_uindex
    on tip_like (tip_id, member_id);

create unique index member_email_uindex
    on member (email);

