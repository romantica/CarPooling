# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table Assessment (
  id                        integer not null,
  rating                    integer,
  comment                   varchar(255),
  type                      boolean,
  constraint pk_Assessment primary key (id))
;

create table Car (
  plate_number              varchar(255) not null,
  model                     varchar(255),
  color                     varchar(255),
  constraint pk_Car primary key (plate_number))
;

create table Composition (
  id                        integer not null,
  type                      boolean,
  time                      timestamp,
  pickup_point_id           integer,
  constraint pk_Composition primary key (id))
;

create table coordinate (
  id                        integer not null,
  x                         double,
  y                         double,
  constraint pk_coordinate primary key (id))
;

create table Itinerary (
  id                        integer not null,
  departure_time            timestamp,
  arrival_time              timestamp,
  pickup_point_id           integer,
  constraint pk_Itinerary primary key (id))
;

create table PickupPoint (
  id                        integer not null,
  name                      varchar(255),
  description               varchar(255),
  address                   varchar(255),
  coordinatex               double,
  coordinatey               double,
  constraint pk_PickupPoint primary key (id))
;

create table Proposal (
  id                        integer not null,
  km_cost                   float,
  available_seats           integer,
  car_plate_number          varchar(255),
  user_id                   integer,
  last_update               timestamp not null,
  constraint pk_Proposal primary key (id))
;

create table Request (
  id                        integer not null,
  dep_coordinate_x          double,
  dep_coordinate_y          double,
  ar_coordinate_x           double,
  ar_coordinate_y           double,
  departure_address         varchar(255),
  arrival_address           varchar(255),
  arrival_time              timestamp,
  necessary_seats           integer,
  tolerance_time            integer,
  tolerance_walk_distance   integer,
  tolerance_price           float,
  user_id                   integer,
  traject_id                integer,
  last_update               timestamp not null,
  constraint pk_Request primary key (id))
;

create table Traject (
  id                        integer not null,
  reserved_seats            integer,
  total_cost                float,
  request_id                integer,
  user_id                   integer,
  departure_pp_id           integer,
  arrival_pp_id             integer,
  proposal_id               integer,
  last_update               timestamp not null,
  constraint pk_Traject primary key (id))
;

create table User (
  id                        integer not null,
  login                     varchar(255),
  first_name                varchar(255),
  name                      varchar(255),
  email                     varchar(255),
  phone_number              varchar(255),
  password                  varchar(255),
  balance                   integer,
  last_update               timestamp not null,
  constraint pk_User primary key (id))
;


create table Proposal_Traject (
  Proposal_id                    integer not null,
  Traject_id                     integer not null,
  constraint pk_Proposal_Traject primary key (Proposal_id, Traject_id))
;

create table Proposal_Itinerary (
  Proposal_id                    integer not null,
  Itinerary_id                   integer not null,
  constraint pk_Proposal_Itinerary primary key (Proposal_id, Itinerary_id))
;

create table User_Assessment (
  User_id                        integer not null,
  Assessment_id                  integer not null,
  constraint pk_User_Assessment primary key (User_id, Assessment_id))
;

create table User_Car (
  User_id                        integer not null,
  Car_plate_number               varchar(255) not null,
  constraint pk_User_Car primary key (User_id, Car_plate_number))
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
create sequence Assessment_seq;

create sequence Car_seq;

create sequence Composition_seq;

create sequence coordinate_seq;

create sequence Itinerary_seq;

create sequence PickupPoint_seq;

create sequence Proposal_seq;

create sequence Request_seq;

create sequence Traject_seq;

create sequence User_seq;

alter table Composition add constraint fk_Composition_pickupPoint_1 foreign key (pickup_point_id) references PickupPoint (id) on delete restrict on update restrict;
create index ix_Composition_pickupPoint_1 on Composition (pickup_point_id);
alter table Itinerary add constraint fk_Itinerary_pickupPoint_2 foreign key (pickup_point_id) references PickupPoint (id) on delete restrict on update restrict;
create index ix_Itinerary_pickupPoint_2 on Itinerary (pickup_point_id);
alter table Proposal add constraint fk_Proposal_car_3 foreign key (car_plate_number) references Car (plate_number) on delete restrict on update restrict;
create index ix_Proposal_car_3 on Proposal (car_plate_number);
alter table Proposal add constraint fk_Proposal_user_4 foreign key (user_id) references User (id) on delete restrict on update restrict;
create index ix_Proposal_user_4 on Proposal (user_id);
alter table Request add constraint fk_Request_user_5 foreign key (user_id) references User (id) on delete restrict on update restrict;
create index ix_Request_user_5 on Request (user_id);
alter table Request add constraint fk_Request_traject_6 foreign key (traject_id) references Traject (id) on delete restrict on update restrict;
create index ix_Request_traject_6 on Request (traject_id);
alter table Traject add constraint fk_Traject_request_7 foreign key (request_id) references Request (id) on delete restrict on update restrict;
create index ix_Traject_request_7 on Traject (request_id);
alter table Traject add constraint fk_Traject_user_8 foreign key (user_id) references User (id) on delete restrict on update restrict;
create index ix_Traject_user_8 on Traject (user_id);
alter table Traject add constraint fk_Traject_departurePP_9 foreign key (departure_pp_id) references Composition (id) on delete restrict on update restrict;
create index ix_Traject_departurePP_9 on Traject (departure_pp_id);
alter table Traject add constraint fk_Traject_arrivalPP_10 foreign key (arrival_pp_id) references Composition (id) on delete restrict on update restrict;
create index ix_Traject_arrivalPP_10 on Traject (arrival_pp_id);
alter table Traject add constraint fk_Traject_proposal_11 foreign key (proposal_id) references Proposal (id) on delete restrict on update restrict;
create index ix_Traject_proposal_11 on Traject (proposal_id);



alter table Proposal_Traject add constraint fk_Proposal_Traject_Proposal_01 foreign key (Proposal_id) references Proposal (id) on delete restrict on update restrict;

alter table Proposal_Traject add constraint fk_Proposal_Traject_Traject_02 foreign key (Traject_id) references Traject (id) on delete restrict on update restrict;

alter table Proposal_Itinerary add constraint fk_Proposal_Itinerary_Proposa_01 foreign key (Proposal_id) references Proposal (id) on delete restrict on update restrict;

alter table Proposal_Itinerary add constraint fk_Proposal_Itinerary_Itinera_02 foreign key (Itinerary_id) references Itinerary (id) on delete restrict on update restrict;

alter table User_Assessment add constraint fk_User_Assessment_User_01 foreign key (User_id) references User (id) on delete restrict on update restrict;

alter table User_Assessment add constraint fk_User_Assessment_Assessment_02 foreign key (Assessment_id) references Assessment (id) on delete restrict on update restrict;

alter table User_Car add constraint fk_User_Car_User_01 foreign key (User_id) references User (id) on delete restrict on update restrict;

alter table User_Car add constraint fk_User_Car_Car_02 foreign key (Car_plate_number) references Car (plate_number) on delete restrict on update restrict;

alter table User_Proposal add constraint fk_User_Proposal_User_01 foreign key (User_id) references User (id) on delete restrict on update restrict;

alter table User_Proposal add constraint fk_User_Proposal_Proposal_02 foreign key (Proposal_id) references Proposal (id) on delete restrict on update restrict;

alter table User_Traject add constraint fk_User_Traject_User_01 foreign key (User_id) references User (id) on delete restrict on update restrict;

alter table User_Traject add constraint fk_User_Traject_Traject_02 foreign key (Traject_id) references Traject (id) on delete restrict on update restrict;

alter table User_Request add constraint fk_User_Request_User_01 foreign key (User_id) references User (id) on delete restrict on update restrict;

alter table User_Request add constraint fk_User_Request_Request_02 foreign key (Request_id) references Request (id) on delete restrict on update restrict;

# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists Assessment;

drop table if exists Car;

drop table if exists Composition;

drop table if exists coordinate;

drop table if exists Itinerary;

drop table if exists PickupPoint;

drop table if exists Proposal;

drop table if exists Proposal_Traject;

drop table if exists Proposal_Itinerary;

drop table if exists Request;

drop table if exists Traject;

drop table if exists User;

drop table if exists User_Assessment;

drop table if exists User_Car;

drop table if exists User_Proposal;

drop table if exists User_Traject;

drop table if exists User_Request;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists Assessment_seq;

drop sequence if exists Car_seq;

drop sequence if exists Composition_seq;

drop sequence if exists coordinate_seq;

drop sequence if exists Itinerary_seq;

drop sequence if exists PickupPoint_seq;

drop sequence if exists Proposal_seq;

drop sequence if exists Request_seq;

drop sequence if exists Traject_seq;

drop sequence if exists User_seq;

