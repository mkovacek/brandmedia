# --- !Ups
INSERT INTO user_type(name) VALUE("ADMIN");
INSERT INTO user_type(name) VALUE("USER");

# --- !Downs
DELETE FROM user_type WHERE name="ADMIN";
DELETE FROM user_type WHERE name="USER";