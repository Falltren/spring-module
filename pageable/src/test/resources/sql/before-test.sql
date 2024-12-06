INSERT INTO authors (id, first_name, last_name)
VALUES (100, 'John', 'Doe'),
       (101, 'Jacoco', null);

INSERT INTO books (id, title, publication_date, genre, author_id)
VALUES (100, 'Title1', '1990-10-20', 'ROMANCE', 100),
       (101, 'Title2', '1992-06-15', 'THRILLER', 100),
       (102, 'Title3', '1993-04-22', 'ROMANCE', 100),
       (103, 'Title4', '1995-10-10', 'MYSTERY', 101),
       (104, 'Title5', '2000-07-14', 'HORROR', 101);

