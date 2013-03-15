# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table assessment (
  id                        bigint auto_increment not null,
  rating                    integer,
  comment                   varchar(255),
  type                      tinyint(1) default 0,
  constraint pk_assessment primary key (id))
;




# --- !Downs

SET FOREIGN_KEY_CHECKS=0;

drop table assessment;

SET FOREIGN_KEY_CHECKS=1;

