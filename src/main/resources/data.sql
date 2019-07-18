INSERT INTO customer (id, first_name, last_name) VALUES (1, 'ROOT', 'BANK');
INSERT INTO customer (first_name, last_name) VALUES ('Vasif', 'Mustafayev');

INSERT INTO account (customer_id, id, currency, balance)
VALUES (1, '111111-2222-3333-4444-556642440000', 'EUR', 999999999);
INSERT INTO account (customer_id, id, currency, balance)
VALUES (1, '222222-2222-3333-4444-556642440000', 'USD', 999999999);
INSERT INTO account (customer_id, id, currency, balance)
VALUES (1, '333333-2222-3333-4444-556642440000', 'PLN', 999999999);

COMMIT;