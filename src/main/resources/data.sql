insert into USERS (ID, LOGIN, PASSWORD, ROLE)
VALUES(1, 'root', '$2a$10$.3eIK96dbDtcQnIy3kAQf.Bn/nWLOlJUZ51goAQfY.s0BXYkzfQ7C', 0);
insert into users (ID, LOGIN, PASSWORD, ROLE)
VALUES(2, 'user', '$2a$10$.3eIK96dbDtcQnIy3kAQf.Bn/nWLOlJUZ51goAQfY.s0BXYkzfQ7C', 1);

insert into SCORE (ID, DESCRIPTION, MAX, MIN)
values(1, 'Insuficiente', 200, 0);
insert into SCORE (ID, DESCRIPTION, MAX, MIN)
VALUES(2, 'Inaceitável', 500, 201);
insert into SCORE (ID, DESCRIPTION, MAX, MIN)
values(3, 'Aceitável', 700, 501);
insert into SCORE (ID, DESCRIPTION, MAX, MIN)
values(4, 'Recomendável', 1000, 701);