# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table news_reminder (
  id                        integer primary key AUTOINCREMENT,
  news_id                   integer,
  user_id                   integer,
  stock_id                  integer,
  create_date               timestamp,
  remind_date               timestamp,
  message                   varchar(255))
;

create table user (
  id                        integer primary key AUTOINCREMENT,
  email                     varchar(255),
  password                  varchar(255))
;




# --- !Downs

PRAGMA foreign_keys = OFF;

drop table news_reminder;

drop table user;

PRAGMA foreign_keys = ON;

