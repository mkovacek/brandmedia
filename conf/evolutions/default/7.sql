# --- !Ups
ALTER TABLE keyword ADD active INT;
# --- !Downs
alter table keyword DROP active;