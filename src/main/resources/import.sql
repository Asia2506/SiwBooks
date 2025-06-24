-- Questo file viene eseguito automaticamente da Hibernate/Spring Boot
-- all'avvio dell'applicazione.

-- PER IL CORRETTO FUNZIONAMENTO:
-- 1. Assicurati che in 'application.properties' (o 'application.yml'):
--    - 'spring.jpa.hibernate.ddl-auto' sia impostato su 'create-drop' o 'update'.
--    - 'spring.jpa.defer-datasource-initialization=true' sia impostato per versioni recenti di Spring Boot (consigliato).
-- 2. Hibernate creerà automaticamente le tabelle 'autore', 'libro', e 'autore_libri'
--    con le colonne 'id' configurate per la generazione automatica tramite sequenze (es. BIGSERIAL in PostgreSQL).


--Inserimento dati per la tabella IMMAGINE
INSERT INTO immagine (id,descrizione, path) VALUES (nextval('immagine_seq'),'Copertina di Cent''anni di solitudine', '/images/copertine/copertina100AnniSol.jpg');
INSERT INTO immagine (id,descrizione, path) VALUES (nextval('immagine_seq'),'Copertina de Le città invisibili', '/images/copertine/copertinaLeCittaInv.jpg');
INSERT INTO immagine (id,descrizione, path) VALUES (nextval('immagine_seq'),'Copertina de Il nome della rosa', '/images/copertine/copertinaIlNomeDellaRosa.jpg');
INSERT INTO immagine (id,descrizione, path) VALUES (nextval('immagine_seq'),'Copertina di Orgoglio e pregiudizio', '/images/copertine/copertinaOrgEPreg.jpg');
INSERT INTO immagine (id,descrizione, path) VALUES (nextval('immagine_seq'),'Copertina di Moby Dick', '/images/copertine/copertinaMobyDick.jpg');
INSERT INTO immagine (id,descrizione, path) VALUES (nextval('immagine_seq'),'Copertina di Mrs Dalloway', '/images/copertine/copertinaMrsDalloway.jpg');
INSERT INTO immagine (id,descrizione, path) VALUES (nextval('immagine_seq'),'Copertina di Delitto e castigo', '/images/copertine/copertinaDelECast.jpg');
INSERT INTO immagine (id,descrizione, path) VALUES (nextval('immagine_seq'),'Copertina di Dieci piccoli indiani', '/images/copertine/copertina10PiccoliInd.jpg');
INSERT INTO immagine (id,descrizione, path) VALUES (nextval('immagine_seq'),'Copertina de La metamorfosi', '/images/copertine/copertinaLaMetamorfosi.jpg');
INSERT INTO immagine (id,descrizione, path) VALUES (nextval('immagine_seq'),'Copertina de Il Signore degli Anelli', '/images/copertine/copertinaIlSignDegliAnelli.jpg');
INSERT INTO immagine (id,descrizione, path) VALUES (nextval('immagine_seq'),'Copertina de Lo Hobbit', '/images/copertine/copertinaLoHobbit.jpg');
INSERT INTO immagine (id,descrizione, path) VALUES (nextval('immagine_seq'),'Copertina di Guerra e pace', '/images/copertine/copertinaGuerraEPace.jpeg');
INSERT INTO immagine (id, descrizione, path) VALUES (nextval('immagine_seq'), 'Fotografia di Jane Austen', '/images/fotografie/austen.jpg');
INSERT INTO immagine (id, descrizione, path) VALUES (nextval('immagine_seq'), 'Fotografia di Italo Calvino', '/images/fotografie/calvino.jpg');
INSERT INTO immagine (id, descrizione, path) VALUES (nextval('immagine_seq'), 'Fotografia di Agatha Christie', '/images/fotografie/christie.jpg');
INSERT INTO immagine (id, descrizione, path) VALUES (nextval('immagine_seq'), 'Fotografia di Fëdor Dostoevskij', '/images/fotografie/dostoevskij.jpg');
INSERT INTO immagine (id, descrizione, path) VALUES (nextval('immagine_seq'), 'Fotografia di Umberto Eco', '/images/fotografie/eco.jpg');
INSERT INTO immagine (id, descrizione, path) VALUES (nextval('immagine_seq'), 'Fotografia di Gabriel García Márquez', '/images/fotografie/garciaMarquez.jpg');
INSERT INTO immagine (id, descrizione, path) VALUES (nextval('immagine_seq'), 'Fotografia di Franz Kafka', '/images/fotografie/kafka.jpg');
INSERT INTO immagine (id, descrizione, path) VALUES (nextval('immagine_seq'), 'Fotografia di Herman Melville', '/images/fotografie/melville.jpg');
INSERT INTO immagine (id, descrizione, path) VALUES (nextval('immagine_seq'), 'Fotografia di J.R.R. Tolkien', '/images/fotografie/tolkien.jpg');
INSERT INTO immagine (id, descrizione, path) VALUES (nextval('immagine_seq'), 'Fotografia di Virginia Woolf', '/images/fotografie/woolf.jpg');



-- Inserimento dati per la tabella AUTORE
-- Gli ID saranno generati automaticamente dal database (es. tramite sequenze)
INSERT INTO autore (id,nome, cognome, data_nascita, data_morte, nazionalita,fotografia_id) VALUES (nextval('autore_seq'),'Gabriel', 'Garcia Marquez', '1927-03-06', '2014-04-17', 'colombiana',(SELECT id FROM immagine WHERE descrizione='Fotografia di Gabriel García Márquez'));
INSERT INTO autore (id,nome, cognome, data_nascita, data_morte, nazionalita,fotografia_id) VALUES (nextval('autore_seq'),'Italo', 'Calvino', '1923-10-15', '1985-09-19', 'italiana',(SELECT id FROM immagine WHERE descrizione='Fotografia di Italo Calvino'));
INSERT INTO autore (id,nome, cognome, data_nascita, data_morte, nazionalita,fotografia_id) VALUES (nextval('autore_seq'),'Umberto', 'Eco', '1932-01-05', '2016-02-19', 'italiana',(SELECT id FROM immagine WHERE descrizione='Fotografia di Umberto Eco'));
INSERT INTO autore (id,nome, cognome, data_nascita, data_morte, nazionalita,fotografia_id) VALUES (nextval('autore_seq'),'Jane', 'Austen', '1775-12-16', '1817-07-18', 'britannica',(SELECT id FROM immagine WHERE descrizione='Fotografia di Jane Austen'));
INSERT INTO autore (id,nome, cognome, data_nascita, data_morte, nazionalita,fotografia_id) VALUES (nextval('autore_seq'),'Herman', 'Melville', '1819-08-01', '1891-09-28', 'americana',(SELECT id FROM immagine WHERE descrizione='Fotografia di Herman Melville'));
INSERT INTO autore (id,nome, cognome, data_nascita, data_morte, nazionalita,fotografia_id) VALUES (nextval('autore_seq'),'Virginia', 'Woolf', '1882-01-25', '1941-03-28', 'britannica',(SELECT id FROM immagine WHERE descrizione='Fotografia di Virginia Woolf'));
INSERT INTO autore (id,nome, cognome, data_nascita, data_morte, nazionalita,fotografia_id) VALUES (nextval('autore_seq'),'Fyodor', 'Dostoevsky', '1821-11-11', '1881-02-09', 'russa',(SELECT id FROM immagine WHERE descrizione='Fotografia di Fëdor Dostoevskij'));
INSERT INTO autore (id,nome, cognome, data_nascita, data_morte, nazionalita,fotografia_id) VALUES (nextval('autore_seq'),'Agatha', 'Christie', '1890-09-15', '1976-01-12', 'britannica',(SELECT id FROM immagine WHERE descrizione='Fotografia di Agatha Christie'));
INSERT INTO autore (id,nome, cognome, data_nascita, data_morte, nazionalita,fotografia_id) VALUES (nextval('autore_seq'),'Franz', 'Kafka', '1883-07-03', '1924-06-03', 'boema',(SELECT id FROM immagine WHERE descrizione='Fotografia di Franz Kafka'));
INSERT INTO autore (id,nome, cognome, data_nascita, data_morte, nazionalita,fotografia_id) VALUES (nextval('autore_seq'),'J.R.R.', 'Tolkien', '1892-01-03', '1973-09-02', 'britannica',(SELECT id FROM immagine WHERE descrizione='Fotografia di J.R.R. Tolkien'));


-- Inserimento dati per la tabella LIBRO
-- Gli ID saranno generati automaticamente dal database (es. tramite sequenze)
INSERT INTO libro (id,titolo, anno_pubblicazione) VALUES (nextval('libro_seq'),'Cent''anni di solitudine', 1967);
INSERT INTO libro (id,titolo, anno_pubblicazione) VALUES (nextval('libro_seq'),'Le città invisibili', 1972);
INSERT INTO libro (id,titolo, anno_pubblicazione) VALUES (nextval('libro_seq'),'Il nome della rosa', 1980);
INSERT INTO libro (id,titolo, anno_pubblicazione) VALUES (nextval('libro_seq'),'Orgoglio e pregiudizio', 1813);
INSERT INTO libro (id,titolo, anno_pubblicazione) VALUES (nextval('libro_seq'),'Moby Dick', 1851);
INSERT INTO libro (id,titolo, anno_pubblicazione) VALUES (nextval('libro_seq'),'Mrs Dalloway', 1925);
INSERT INTO libro (id,titolo, anno_pubblicazione) VALUES (nextval('libro_seq'),'Delitto e castigo', 1866);
INSERT INTO libro (id,titolo, anno_pubblicazione) VALUES (nextval('libro_seq'),'Dieci piccoli indiani', 1939);
INSERT INTO libro (id,titolo, anno_pubblicazione) VALUES (nextval('libro_seq'),'La metamorfosi', 1915);
INSERT INTO libro (id,titolo, anno_pubblicazione) VALUES (nextval('libro_seq'),'Il Signore degli Anelli', 1954);
INSERT INTO libro (id,titolo, anno_pubblicazione) VALUES (nextval('libro_seq'),'Lo Hobbit', 1937);
INSERT INTO libro (id,titolo, anno_pubblicazione) VALUES (nextval('libro_seq'),'Guerra e pace', 1869); -- Esempio di libro che potrebbe non avere un autore associato in questo set


-- Inserimento delle associazioni nella tabella autore_libri
-- Vengono utilizzate subquery per recuperare gli ID generati automaticamente.
-- Nota: La tabella di join dovrebbe chiamarsi 'autore_libri' e le colonne 'autore_id' e 'libri_id'
-- per riflettere la relazione ManyToMany tra 'Autore' e la lista 'libri' di Autore.

-- Gabriel Garcia Marquez - Cent'anni di solitudine
INSERT INTO autore_libri (autori_id, libri_id) VALUES ((SELECT id FROM autore WHERE nome='Gabriel' AND cognome='Garcia Marquez'),(SELECT id FROM libro WHERE titolo='Cent''anni di solitudine'));

-- Italo Calvino - Le città invisibili
INSERT INTO autore_libri (autori_id, libri_id) VALUES ((SELECT id FROM autore WHERE nome='Italo' AND cognome='Calvino'),(SELECT id FROM libro WHERE titolo='Le città invisibili'));

-- Umberto Eco - Il nome della rosa
INSERT INTO autore_libri (autori_id, libri_id) VALUES ((SELECT id FROM autore WHERE nome='Umberto' AND cognome='Eco'),(SELECT id FROM libro WHERE titolo='Il nome della rosa'));

-- Jane Austen - Orgoglio e pregiudizio
INSERT INTO autore_libri (autori_id, libri_id) VALUES ((SELECT id FROM autore WHERE nome='Jane' AND cognome='Austen'),(SELECT id FROM libro WHERE titolo='Orgoglio e pregiudizio'));

-- Herman Melville - Moby Dick
INSERT INTO autore_libri (autori_id, libri_id) VALUES ((SELECT id FROM autore WHERE nome='Herman' AND cognome='Melville'),(SELECT id FROM libro WHERE titolo='Moby Dick'));

-- Virginia Woolf - Mrs Dalloway
INSERT INTO autore_libri (autori_id, libri_id) VALUES ((SELECT id FROM autore WHERE nome='Virginia' AND cognome='Woolf'),(SELECT id FROM libro WHERE titolo='Mrs Dalloway'));

-- Fyodor Dostoevsky - Delitto e castigo
INSERT INTO autore_libri (autori_id, libri_id) VALUES ((SELECT id FROM autore WHERE nome='Fyodor' AND cognome='Dostoevsky'),(SELECT id FROM libro WHERE titolo='Delitto e castigo'));

-- Agatha Christie - Dieci piccoli indiani
INSERT INTO autore_libri (autori_id, libri_id) VALUES ((SELECT id FROM autore WHERE nome='Agatha' AND cognome='Christie'),(SELECT id FROM libro WHERE titolo='Dieci piccoli indiani'));

-- Franz Kafka - La metamorfosi
INSERT INTO autore_libri (autori_id, libri_id) VALUES ((SELECT id FROM autore WHERE nome='Franz' AND cognome='Kafka'),(SELECT id FROM libro WHERE titolo='La metamorfosi'));

-- J.R.R. Tolkien - Il Signore degli Anelli
INSERT INTO autore_libri (autori_id, libri_id) VALUES ((SELECT id FROM autore WHERE nome='J.R.R.' AND cognome='Tolkien'),(SELECT id FROM libro WHERE titolo='Il Signore degli Anelli'));
-- J.R.R. Tolkien - Lo Hobbit
INSERT INTO autore_libri (autori_id, libri_id) VALUES ((SELECT id FROM autore WHERE nome='J.R.R.' AND cognome='Tolkien'),(SELECT id FROM libro WHERE titolo='Lo Hobbit'));



INSERT INTO libro_immagini (immagini_id, libro_id) VALUES ((SELECT id FROM immagine WHERE descrizione='Copertina di Cent''anni di solitudine'),(SELECT id FROM libro WHERE titolo='Cent''anni di solitudine'));
INSERT INTO libro_immagini (immagini_id, libro_id) VALUES ((SELECT id FROM immagine WHERE descrizione='Copertina de Le città invisibili'),(SELECT id FROM libro WHERE titolo='Le città invisibili'));
INSERT INTO libro_immagini (immagini_id, libro_id) VALUES ((SELECT id FROM immagine WHERE descrizione='Copertina de Il nome della rosa'),(SELECT id FROM libro WHERE titolo='Il nome della rosa'));
INSERT INTO libro_immagini (immagini_id, libro_id) VALUES ((SELECT id FROM immagine WHERE descrizione='Copertina di Orgoglio e pregiudizio'),(SELECT id FROM libro WHERE titolo='Orgoglio e pregiudizio'));
INSERT INTO libro_immagini (immagini_id, libro_id) VALUES ((SELECT id FROM immagine WHERE descrizione='Copertina di Moby Dick'),(SELECT id FROM libro WHERE titolo='Moby Dick'));
INSERT INTO libro_immagini (immagini_id, libro_id) VALUES ((SELECT id FROM immagine WHERE descrizione='Copertina di Mrs Dalloway'),(SELECT id FROM libro WHERE titolo='Mrs Dalloway'));
INSERT INTO libro_immagini (immagini_id, libro_id) VALUES ((SELECT id FROM immagine WHERE descrizione='Copertina di Delitto e castigo'),(SELECT id FROM libro WHERE titolo='Delitto e castigo'));
INSERT INTO libro_immagini (immagini_id, libro_id) VALUES ((SELECT id FROM immagine WHERE descrizione='Copertina di Dieci piccoli indiani'),(SELECT id FROM libro WHERE titolo='Dieci piccoli indiani'));
INSERT INTO libro_immagini (immagini_id, libro_id) VALUES ((SELECT id FROM immagine WHERE descrizione='Copertina de La metamorfosi'),(SELECT id FROM libro WHERE titolo='La metamorfosi'));
INSERT INTO libro_immagini (immagini_id, libro_id) VALUES ((SELECT id FROM immagine WHERE descrizione='Copertina de Il Signore degli Anelli'),(SELECT id FROM libro WHERE titolo='Il Signore degli Anelli'));
INSERT INTO libro_immagini (immagini_id, libro_id) VALUES ((SELECT id FROM immagine WHERE descrizione='Copertina de Lo Hobbit'),(SELECT id FROM libro WHERE titolo='Lo Hobbit'));
INSERT INTO libro_immagini (immagini_id, libro_id) VALUES ((SELECT id FROM immagine WHERE descrizione='Copertina di Guerra e pace'),(SELECT id FROM libro WHERE titolo='Guerra e pace'));


--CREO L'ADMIN
INSERT INTO users (id, nome, cognome, data_nascita, luogo_nascita, email) VALUES (nextval('users_seq'), 'Asia', 'Fulvi', '25-06-2003', 'Roma', 'admin@gmail.com');
INSERT INTO credentials (id, username, password, role, user_id) VALUES (nextval('credentials_seq'), 'asia25', '$2a$10$3xz1InFVQCU3kbTE625NYuInyA74bnRLc9ycPtcv9A/rHerSDk2Iq', 'ADMIN', (SELECT id FROM users WHERE nome='Asia' AND cognome='Fulvi' AND email='admin@gmail.com'));
--$2a$10$3xz1InFVQCU3kbTE625NYuInyA74bnRLc9ycPtcv9A/rHerSDk2Iq
INSERT INTO users (id, nome, cognome, data_nascita, luogo_nascita, email) VALUES (nextval('users_seq'), 'Giada', 'Fulvi', '18-08-2005', 'Roma', 'giada@gmail.com');
INSERT INTO credentials (id, username, password, role, user_id) VALUES (nextval('credentials_seq'), 'giada18', '$2a$10$ArEYw3GeQ6frWeov2Gu88.jlflwpWUc/E.yzWY5DPS0r8tx5Ltr0m', 'USER', (SELECT id FROM users WHERE nome='Giada' AND cognome='Fulvi' AND email='giada@gmail.com'));

--$2a$10$ArEYw3GeQ6frWeov2Gu88.jlflwpWUc/E.yzWY5DPS0r8tx5Ltr0m



