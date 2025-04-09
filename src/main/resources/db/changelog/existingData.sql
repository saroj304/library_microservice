-- liquibase formatted sql

-- changeset webguardianx:1744220911778-1
INSERT INTO "book" ("id", "published_date", "author", "genre", "status", "title") VALUES (1, '1955-09-01', 'Walter Lord', 'HISTORY', 'UNAVAILABLE', 'Titanic');
INSERT INTO "book" ("id", "published_date", "author", "genre", "status", "title") VALUES (102, '1951-07-16', 'J.D. Salinger', 'FICTION', 'AVAILABLE', 'The Catcher in the Rye');
INSERT INTO "book" ("id", "published_date", "author", "genre", "status", "title") VALUES (202, '1851-10-18', 'Herman Melville', 'BIOGRAPHY', 'AVAILABLE', 'Moby-Dick');
INSERT INTO "book" ("id", "published_date", "author", "genre", "status", "title") VALUES (352, '1949-06-08', 'GeorgeOrwell', 'BIOGRAPHY', 'UNAVAILABLE', '1984');
INSERT INTO "book" ("id", "published_date", "author", "genre", "status", "title") VALUES (502, '1949-06-01', 'hari', 'FANTASY', 'AVAILABLE', '1987');
INSERT INTO "book" ("id", "published_date", "author", "genre", "status", "title") VALUES (103, '1960-07-11', 'Harper Lee', 'FICTION', 'UNAVAILABLE', 'To Kill a Mockingbird');
INSERT INTO "book" ("id", "published_date", "author", "genre", "status", "title") VALUES (552, '1925-04-10', 'F. Scott Fitzgerald', 'FICTION', 'AVAILABLE', 'The Great Gatsby');
INSERT INTO "book" ("id", "published_date", "author", "genre", "status", "title") VALUES (602, '1960-07-11', 'Harper Lee', 'MYSTERY', 'AVAILABLE', 'To Kill a Mockingbird');
INSERT INTO "book" ("id", "published_date", "author", "genre", "status", "title") VALUES (652, '1851-10-18', 'saroj khatiwada', 'BIOGRAPHY', 'AVAILABLE', 'jonny deep');
INSERT INTO "book" ("id", "published_date", "author", "genre", "status", "title") VALUES (702, '1949-06-01', 'kalpana', 'FANTASY', 'AVAILABLE', 'horrot');

-- changeset webguardianx:1744220911778-2
INSERT INTO "loan" ("book_id", "loan_date", "member_id", "return_date", "id", "status") VALUES (1, '2024-11-20', 2, '2024-11-19', 52, 'OVERDUE');
INSERT INTO "loan" ("book_id", "loan_date", "member_id", "return_date", "id", "status") VALUES (103, '2024-11-21', 3, '2024-11-19', 153, 'OVERDUE');
INSERT INTO "loan" ("book_id", "loan_date", "member_id", "return_date", "id", "status") VALUES (202, '2024-11-21', 3, '2024-11-19', 252, 'OVERDUE');
INSERT INTO "loan" ("book_id", "loan_date", "member_id", "return_date", "id", "status") VALUES (102, '2024-11-21', 3, '2024-11-22', 152, 'OVERDUE');
INSERT INTO "loan" ("book_id", "loan_date", "member_id", "return_date", "id", "status") VALUES (702, '2025-04-09', 452, '2024-11-30', 752, 'ISSUED');

-- changeset webguardianx:1744220911778-3
INSERT INTO "member" ("id", "member_ship_date", "email", "name") VALUES (2, '2024-11-20 21:37:46.268', 'sarojkhatiwada1999@gmail.com', 'saroj khatiwada');
INSERT INTO "member" ("id", "member_ship_date", "email", "name") VALUES (3, '2024-11-20 21:38:21.032', 'Ramkhatiwada1999@gmail.com', 'Ram khatiwada');
INSERT INTO "member" ("id", "member_ship_date", "email", "name") VALUES (302, '2024-11-21 17:41:33.5', 'ajaywagle1999@gmail.com', 'ajay');
INSERT INTO "member" ("id", "member_ship_date", "email", "name") VALUES (402, '2024-11-21 23:07:35.716', 'ajaywagle1999@gmail.com', 'ajay');
INSERT INTO "member" ("id", "member_ship_date", "email", "name") VALUES (452, '2024-11-21 23:12:20.579', 'kalpanakhatiwada1999@gmail.com', 'kalpana');

