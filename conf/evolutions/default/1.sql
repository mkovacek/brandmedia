# --- !Ups

create table user_type(
  id                         bigint auto_increment not null,
  name                       varchar(50),
  constraint user_type_pk primary key (id)
);

create table user_details(
  id                         bigint auto_increment not null,
  name                       varchar(255),
  surname                    varchar(255),
  constraint user_details_pk primary key (id)
);

create table user(
  id                         bigint auto_increment not null,
  email                      varchar(255),
  password                   varchar(255),
  active                     int,
  type_id                    bigint,
  details_id                 bigint,
  constraint user_pk primary key (id),
  constraint user_user_type_fk FOREIGN KEY (type_id) REFERENCES user_type (id) ON DELETE CASCADE ON UPDATE CASCADE,
  constraint user_user_details_fk FOREIGN KEY (details_id) REFERENCES user_details (id) ON DELETE CASCADE ON UPDATE CASCADE
);

create table keyword(
  id                         bigint auto_increment not null,
  keyword                    varchar(255),
  constraint keyword_pk primary key (id)
);

create table user_keyword (
  user_id    BIGINT NOT NULL,
  keyword_id BIGINT NOT NULL,
  active     INT,
  constraint user_keyword_pk primary key (user_id,keyword_id),
  constraint user_keyword_user_fk FOREIGN KEY (user_id) REFERENCES user (id) ON DELETE CASCADE ON UPDATE CASCADE,
  constraint user_keyword_keywords_fk FOREIGN KEY (keyword_id) REFERENCES keyword (id) ON DELETE CASCADE ON UPDATE CASCADE
);

create table mention(
  id                         bigint auto_increment not null,
  text                       varchar(1000),
  created                    datetime,
  user_name                  VARCHAR(255),
  user_username              VARCHAR(255),
  user_location              VARCHAR(255),
  user_image                 VARCHAR(255),
  retweet_count              bigint,
  favorite_count             bigint,
  keyword_id                 bigint,
  constraint mention_pk primary key (id),
  constraint mention_keyword_fk FOREIGN KEY (keyword_id) REFERENCES keyword (id) ON DELETE CASCADE ON UPDATE CASCADE
);

# --- !Downs
SET FOREIGN_KEY_CHECKS=0;

drop table user_type;

drop table user_details;

drop table user;

drop table keyword;

drop table user_keywords;

drop table mention;

SET FOREIGN_KEY_CHECKS=1;