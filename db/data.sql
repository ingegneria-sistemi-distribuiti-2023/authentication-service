-- authorization service
-- script sql di dati fittizzi usati per il test

-- users

insert into authdb.users(username, password, enabled)
values ('admin', 'admin', 0),
('admin-docker', 'admin', 0);

-- balances

insert into authdb.balances(user_id, cashable, bonus)
values (1, 100, 100),
(2, 100, 100);

-- transactions

insert into authdb.transactions(user_id, circuit,amount, status)
values (1, "mastercard", 100, "CLOSED"),
(2, "mastercard", 100, "CLOSED");