# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table document (
  id                        bigint not null,
  name                      varchar(255),
  plain_text                text,
  localization              varchar(255),
  created_at                timestamp,
  constraint pk_document primary key (id))
;

create sequence document_seq;




# --- !Downs

drop table if exists document cascade;

drop sequence if exists document_seq;

