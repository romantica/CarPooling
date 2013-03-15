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

create table pickup_point (
  id                        integer auto_increment not null,
  name                      varchar(255),
  description               varchar(255),
  address                   varchar(255),
  constraint pk_pickup_point primary key (id))
;

create table proposal (
  id                        integer auto_increment not null,
  km_cost                   float,
  available_seats           integer,
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




# --- !Downs

SET FOREIGN_KEY_CHECKS=0;

drop table assessment;

drop table car;

drop table composition;

drop table coordinate;

drop table itinerary;

drop table pickup_point;

drop table proposal;

drop table request;

drop table traject;

drop table user;

SET FOREIGN_KEY_CHECKS=1;

