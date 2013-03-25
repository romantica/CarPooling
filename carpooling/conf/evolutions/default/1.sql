# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table Assessment (
  id                        integer auto_increment not null,
  rating                    integer,
  comment                   varchar(255),
  type                      tinyint(1) default 0,
  constraint pk_Assessment primary key (id))
;

create table Car (
  id                        integer auto_increment not null,
  plate_number              varchar(255),
  model                     varchar(255),
  color                     varchar(255),
  constraint pk_Car primary key (id))
;

create table Composition (
  id                        integer auto_increment not null,
  type                      tinyint(1) default 0,
  time                      datetime,
  constraint pk_Composition primary key (id))
;

create table coordinate (
  id                        integer auto_increment not null,
  x                         double,
  y                         double,
  constraint pk_coordinate primary key (id))
;

create table Itinerary (
  id                        integer auto_increment not null,
  departure_time            datetime,
  arrival_time              datetime,
  constraint pk_Itinerary primary key (id))
;

create table PickupPoint (
  id                        integer auto_increment not null,
  name                      varchar(255),
  description               varchar(255),
  address                   varchar(255),
  coordinatex               double,
  coordinatey               double,
  constraint pk_PickupPoint primary key (id))
;

create table Proposal (
  id                        integer auto_increment not null,
  km_cost                   float,
  available_seats           integer,
  user_id                   integer,
  constraint pk_Proposal primary key (id))
;

create table Request (
  id                        integer auto_increment not null,
  departure_address         varchar(255),
  arrival_address           varchar(255),
  arrival_time              datetime,
  necessary_seats           integer,
  tolerance_time            integer,
  tolerance_walk_distance   integer,
  tolerance_price           float,
  constraint pk_Request primary key (id))
;

create table Traject (
  id                        integer auto_increment not null,
  reserved_seats            integer,
  total_cost                float,
  constraint pk_Traject primary key (id))
;

create table User (
  id                        integer auto_increment not null,
  login                     varchar(255),
  first_name                varchar(255),
  name                      varchar(255),
  email                     varchar(255),
  phone_number              varchar(255),
  balance                   integer,
  constraint pk_User primary key (id))
;


create table Proposal_Traject (
  Proposal_id                    integer not null,
  Traject_id                     integer not null,
  constraint pk_Proposal_Traject primary key (Proposal_id, Traject_id))
;

create table User_Car (
  User_id                        integer not null,
  Car_id                         integer not null,
  constraint pk_User_Car primary key (User_id, Car_id))
;

create table User_Proposal (
  User_id                        integer not null,
  Proposal_id                    integer not null,
  constraint pk_User_Proposal primary key (User_id, Proposal_id))
;

create table User_Traject (
  User_id                        integer not null,
  Traject_id                     integer not null,
  constraint pk_User_Traject primary key (User_id, Traject_id))
;

create table User_Request (
  User_id                        integer not null,
  Request_id                     integer not null,
  constraint pk_User_Request primary key (User_id, Request_id))
;
alter table Proposal add constraint fk_Proposal_user_1 foreign key (user_id) references User (id) on delete restrict on update restrict;
create index ix_Proposal_user_1 on Proposal (user_id);



alter table Proposal_Traject add constraint fk_Proposal_Traject_Proposal_01 foreign key (Proposal_id) references Proposal (id) on delete restrict on update restrict;

alter table Proposal_Traject add constraint fk_Proposal_Traject_Traject_02 foreign key (Traject_id) references Traject (id) on delete restrict on update restrict;

alter table User_Car add constraint fk_User_Car_User_01 foreign key (User_id) references User (id) on delete restrict on update restrict;

alter table User_Car add constraint fk_User_Car_Car_02 foreign key (Car_id) references Car (id) on delete restrict on update restrict;

alter table User_Proposal add constraint fk_User_Proposal_User_01 foreign key (User_id) references User (id) on delete restrict on update restrict;

alter table User_Proposal add constraint fk_User_Proposal_Proposal_02 foreign key (Proposal_id) references Proposal (id) on delete restrict on update restrict;

alter table User_Traject add constraint fk_User_Traject_User_01 foreign key (User_id) references User (id) on delete restrict on update restrict;

alter table User_Traject add constraint fk_User_Traject_Traject_02 foreign key (Traject_id) references Traject (id) on delete restrict on update restrict;

alter table User_Request add constraint fk_User_Request_User_01 foreign key (User_id) references User (id) on delete restrict on update restrict;

alter table User_Request add constraint fk_User_Request_Request_02 foreign key (Request_id) references Request (id) on delete restrict on update restrict;

# --- !Downs

SET FOREIGN_KEY_CHECKS=0;

drop table Assessment;

drop table Car;

drop table Composition;

drop table coordinate;

drop table Itinerary;

drop table PickupPoint;

drop table Proposal;

drop table Proposal_Traject;

drop table Request;

drop table Traject;

drop table User;

drop table User_Car;

drop table User_Proposal;

drop table User_Traject;

drop table User_Request;

SET FOREIGN_KEY_CHECKS=1;

