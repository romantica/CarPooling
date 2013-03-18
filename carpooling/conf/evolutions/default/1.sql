# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table assessment (
  id                        integer auto_increment not null,
  rating                    integer,
  comment                   varchar(255),
  type                      tinyint(1) default 0,
  constraint pk_assessment primary key (id))
;

create table car (
  id                        integer auto_increment not null,
  plate_number              varchar(255),
  model                     varchar(255),
  color                     varchar(255),
  constraint pk_car primary key (id))
;

create table composition (
  id                        integer auto_increment not null,
  type                      tinyint(1) default 0,
  time                      datetime,
  constraint pk_composition primary key (id))
;

create table coordinate (
  id                        integer auto_increment not null,
  x                         double,
  y                         double,
  constraint pk_coordinate primary key (id))
;

create table itinerary (
  id                        integer auto_increment not null,
  departure_time            datetime,
  arrival_time              datetime,
  constraint pk_itinerary primary key (id))
;

create table PickupPoint (
  id                        integer auto_increment not null,
  name                      varchar(255),
  description               varchar(255),
  address                   varchar(255),
  constraint pk_PickupPoint primary key (id))
;

create table proposal (
  id                        integer auto_increment not null,
  km_cost                   float,
  available_seats           integer,
  user_id                   integer,
  constraint pk_proposal primary key (id))
;

create table request (
  id                        integer auto_increment not null,
  departure_address         varchar(255),
  arrival_address           varchar(255),
  arrival_time              datetime,
  necessary_seats           integer,
  tolerance_time            integer,
  tolerance_walk_distance   integer,
  tolerance_price           float,
  constraint pk_request primary key (id))
;

create table traject (
  id                        integer auto_increment not null,
  reserved_seats            integer,
  total_cost                float,
  constraint pk_traject primary key (id))
;

create table user (
  id                        integer auto_increment not null,
  login                     varchar(255),
  first_name                varchar(255),
  name                      varchar(255),
  email                     varchar(255),
  phone_number              varchar(255),
  balance                   integer,
  constraint pk_user primary key (id))
;


create table proposal_traject (
  proposal_id                    integer not null,
  traject_id                     integer not null,
  constraint pk_proposal_traject primary key (proposal_id, traject_id))
;

create table user_car (
  user_id                        integer not null,
  car_id                         integer not null,
  constraint pk_user_car primary key (user_id, car_id))
;

create table user_proposal (
  user_id                        integer not null,
  proposal_id                    integer not null,
  constraint pk_user_proposal primary key (user_id, proposal_id))
;

create table user_traject (
  user_id                        integer not null,
  traject_id                     integer not null,
  constraint pk_user_traject primary key (user_id, traject_id))
;

create table user_request (
  user_id                        integer not null,
  request_id                     integer not null,
  constraint pk_user_request primary key (user_id, request_id))
;
alter table proposal add constraint fk_proposal_user_1 foreign key (user_id) references user (id) on delete restrict on update restrict;
create index ix_proposal_user_1 on proposal (user_id);



alter table proposal_traject add constraint fk_proposal_traject_proposal_01 foreign key (proposal_id) references proposal (id) on delete restrict on update restrict;

alter table proposal_traject add constraint fk_proposal_traject_traject_02 foreign key (traject_id) references traject (id) on delete restrict on update restrict;

alter table user_car add constraint fk_user_car_user_01 foreign key (user_id) references user (id) on delete restrict on update restrict;

alter table user_car add constraint fk_user_car_car_02 foreign key (car_id) references car (id) on delete restrict on update restrict;

alter table user_proposal add constraint fk_user_proposal_user_01 foreign key (user_id) references user (id) on delete restrict on update restrict;

alter table user_proposal add constraint fk_user_proposal_proposal_02 foreign key (proposal_id) references proposal (id) on delete restrict on update restrict;

alter table user_traject add constraint fk_user_traject_user_01 foreign key (user_id) references user (id) on delete restrict on update restrict;

alter table user_traject add constraint fk_user_traject_traject_02 foreign key (traject_id) references traject (id) on delete restrict on update restrict;

alter table user_request add constraint fk_user_request_user_01 foreign key (user_id) references user (id) on delete restrict on update restrict;

alter table user_request add constraint fk_user_request_request_02 foreign key (request_id) references request (id) on delete restrict on update restrict;

# --- !Downs

SET FOREIGN_KEY_CHECKS=0;

drop table assessment;

drop table car;

drop table composition;

drop table coordinate;

drop table itinerary;

drop table PickupPoint;

drop table proposal;

drop table proposal_traject;

drop table request;

drop table traject;

drop table user;

drop table user_car;

drop table user_proposal;

drop table user_traject;

drop table user_request;

SET FOREIGN_KEY_CHECKS=1;

