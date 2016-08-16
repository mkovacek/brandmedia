# --- !Ups
ALTER TABLE user ADD INDEX user_index_1 (email);
ALTER TABLE user ADD INDEX user_index_2 (email,password,active);
ALTER TABLE user ADD INDEX user_index_3 (type_id);
ALTER TABLE user ADD INDEX user_index_4 (details_id);
ALTER TABLE keyword ADD INDEX keyword_index_1 (keyword);
ALTER TABLE keyword ADD INDEX keyword_index_2 (user_id);
ALTER TABLE keyword ADD INDEX keyword_index_3 (user_id,active,keyword);
ALTER TABLE mention ADD INDEX mention_index_1 (keyword_id);
ALTER TABLE mention ADD INDEX mention_index_2 (created);
ALTER TABLE mention ADD INDEX mention_index_3 (user_location);

# --- !Downs
ALTER TABLE user DROP INDEX user_index_1;
ALTER TABLE user DROP INDEX user_index_2;
ALTER TABLE user DROP INDEX user_index_3;
ALTER TABLE user DROP INDEX user_index_4;
ALTER TABLE keyword DROP INDEX keyword_index_1;
ALTER TABLE keyword DROP INDEX keyword_index_2;
ALTER TABLE keyword DROP INDEX keyword_index_3;
ALTER TABLE mention DROP INDEX mention_index_1;
ALTER TABLE mention DROP INDEX mention_index_2;
ALTER TABLE mention DROP INDEX mention_index_3;