INSERT INTO genre (genre_id, tittle) VALUES (1, 'genre1');
INSERT INTO genre (genre_id, tittle) VALUES (2, 'genre2');
INSERT INTO genre (genre_id, tittle) VALUES (3, 'genre3');

INSERT INTO actor (actor_id, name, year) VALUES (1, 'actor1', '20');
INSERT INTO actor (actor_id, name, year) VALUES (2, 'actor2', '23');
INSERT INTO actor (actor_id, name, year) VALUES (3, 'actor3', '25');

INSERT INTO film (film_id, tittle, date) VALUES (1, 'film1', '2009-03-28');
INSERT INTO film (film_id, tittle, date) VALUES (2, 'film2', '2009-03-28');
INSERT INTO film (film_id, tittle, date) VALUES (3, 'film3', '2009-03-28');

INSERT INTO director (director_id, name, year) VALUES (1, 'director1', '20');
INSERT INTO director (director_id, name, year) VALUES (2, 'director2', '35');
INSERT INTO director (director_id, name, year) VALUES (3, 'director3', '30');

INSERT INTO film_genre (genre_id, film_id) VALUES (1, 1);
INSERT INTO film_genre (genre_id, film_id) VALUES (2, 1);
INSERT INTO film_genre (genre_id, film_id) VALUES (3, 1);

INSERT INTO film_director (film_id, director_id) VALUES (1, 1);
INSERT INTO film_director (film_id, director_id) VALUES (1, 2);
INSERT INTO film_director (film_id, director_id) VALUES (2, 3);

INSERT INTO film_actor (film_id, actor_id) VALUES (1, 1);
INSERT INTO film_actor (film_id, actor_id) VALUES (1, 2);
INSERT INTO film_actor (film_id, actor_id) VALUES (2, 3);

--INSERT INTO user (user_id, username, password, email) VALUES (1, 'user1', '$2a$10$iQ1oGcgr8TFOHgXIYtBGb.AGdEY38/Jd77MsOd7dQvpF6FmolrD1C', 'user1@mail.com');
--INSERT INTO user (user_id, username, password, email) VALUES (2, 'admin1', '$2a$10$BSi916s9DsdYYTq7asxfA.00RRDi4IYwNwgraCgzku5AjwxwXZcDW', 'admin1@mail.com');
--
--INSERT INTO role (role_id, name) VALUES (1, 'ADMIN');
--INSERT INTO role (role_id, name) VALUES (2, 'USER');
--
--INSERT INTO user_role (user_id, role_id) VALUES (1, 2);
--INSERT INTO user_role (user_id, role_id) VALUES (2, 1);
--INSERT INTO user_role (user_id, role_id) VALUES (2, 1);