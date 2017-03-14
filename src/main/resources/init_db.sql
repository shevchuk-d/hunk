create database hunk;

drop table if exists lockers_neighbors;
drop table if exists visits;
drop table if exists lockers;
drop table if exists clients;

create table clients (
  client_id bigserial,
	name varchar(255),
  sex varchar(7),
  primary key (client_id)
) ;

create table lockers (
  locker_id bigserial,
	number int,
  primary key (locker_id)
) ;

create table visits (
	visit_id bigserial,
	start date,
	finish date,
	locker_id bigint,
  client_id bigint,
  primary key (visit_id),
	foreign key (locker_id) references lockers(locker_id),
	foreign key (client_id) references clients(client_id)
) ;

create table lockers_neighbors (
  locker_id bigint,
  neighbor_id bigint,
  foreign key (locker_id) references lockers(locker_id),
  foreign key (neighbor_id) references lockers(locker_id)
) ;