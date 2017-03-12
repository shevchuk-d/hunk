CREATE DATABASE hunk;

drop TABLE IF EXISTS lockers_neighbors;
drop TABLE IF EXISTS lockers;
drop TABLE IF EXISTS visits;
drop TABLE IF EXISTS clients;

CREATE TABLE clients (
  client_id bigserial,
	NAME VARCHAR(255),
  sex VARCHAR(7),
  primary key (client_id)
) ;

CREATE TABLE lockers (
  locker_id bigserial,
	number INT,
  primary key (locker_id)
) ;

CREATE TABLE visits (
	visit_id bigserial,
	start DATE,
	finish DATE,
	locker_id BIGINT,
  client_id BIGINT,
  primary key (visit_id),
	foreign key (locker_id) references lockers(locker_id),
	foreign key (client_id) references clients(client_id)
) ;

CREATE TABLE lockers_neighbors (
  locker_id BIGINT,
  neighbor_id BIGINT,
  FOREIGN KEY (locker_id) REFERENCES lockers(locker_id),
  FOREIGN KEY (neighbor_id) REFERENCES lockers(locker_id)
) ;