CREATE TABLE credit_card

SELECT a.id_customer_address, a.street, a.city, a.state_, a.postal_code, a.country, a.district, a.customer_id,
       b.id_customer, b.full_name, b.cpf, b.gender, b.birthday
FROM customer_address as a
         INNER JOIN customer as b
                    ON a.id_customer_address = b.id_customer;

CREATE TABLE credit_card (
                             id_credit_card BIGSERIAL PRIMARY KEY NOT NULL,
                             number_card INT NOT NULL,
                             CVV VARCHAR(3) NOT NULL,
                             expiration_date DATE NOT NULL,
                             active BOOLEAN NOT NULL,
                             name_card VARCHAR(255) NOT NULL
);

CREATE TABLE password_credit_card(
                                     id_password_credit_card BIGSERIAL PRIMARY KEY NOT NULL,
                                     password_card VARCHAR(255) NOT NULL
);

CREATE TABLE limit_card(
                           id_limit_card BIGSERIAL PRIMARY KEY NOT NULL,
                           limit_card MONEY NOT NULL
);

CREATE TABLE credit_card_client(
                                   id_credit_card_client BIGSERIAL PRIMARY KEY NOT NULL,
                                   credit_card INT,
                                   password_credit_card INT,
                                   limit_card INT,
                                   customer_id INT,
                                   FOREIGN KEY (credit_card) REFERENCES credit_card(id_credit_card),
                                   FOREIGN KEY (password_credit_card) REFERENCES password_credit_card(id_password_credit_card),
                                   FOREIGN KEY (limit_card) REFERENCES limit_card(id_limit_card),
                                   FOREIGN KEY (customer_id) REFERENCES customer(id_customer)
);


INSERT INTO credit_card (number_card, CVV, expiration_date, active, name_card)
VALUES
    (12345678, '123', '2024-11-06', true, 'Rodrigo S');

INSERT INTO password_credit_card(password_card) VALUES
    ('12345678');

INSERT INTO limit_card(limit_card) VALUES
    (400.34);

INSERT INTO credit_card_client(credit_card, password_credit_card, limit_card, customer_id) VALUES
    (1,1,1,1);



SELECT a.id_customer_address, a.street, a.city, a.state_, a.postal_code, a.country, a.district, a.customer_id,
       b.id_customer, b.full_name, b.cpf, b.gender, b.birthday
FROM customer_address as a
         INNER JOIN customer as b
                    ON a.id_customer_address = b.id_customer;


SELECT
    main.id_credit_card_client,
    main.password_credit_card,
    main.limit_card,
    main.customer_id,
    a.id_credit_card,
    a.number_card,
    a.CVV,
    a.active,
    a.name_card,
    b.password_card,
    c.limit_card,
    d.full_name
FROM
    credit_card_client AS main
        INNER JOIN
    credit_card AS a ON main.id_credit_card_client = a.id_credit_card
        INNER JOIN
    password_credit_card AS b ON main.password_credit_card = b.id_password_credit_card
        INNER JOIN
    limit_card AS c ON main.limit_card = c.id_limit_card
        INNER JOIN
    customer AS d ON main.customer_id = d.id_customer;





UPDATE limit_card
SET limit_card = 450.43
WHERE id_limit_card = 1;

SELECT
    a.id_credit_card,
    a.number_card,
    a.CVV,
    a.expiration_date,
    a.active,
    a.name_card,
    b.password_card,
    c.limit_card,
    d.full_name
FROM
    credit_card_client AS main
        INNER JOIN
    credit_card AS a ON main.id_credit_card_client = a.id_credit_card
        INNER JOIN
    password_credit_card AS b ON main.password_credit_card = b.id_password_credit_card
        INNER JOIN
    limit_card AS c ON main.limit_card = c.id_limit_card
        INNER JOIN
    customer AS d ON main.customer_id = d.id_customer
WHERE
    main.customer_id = 1;



CREATE TABLE invoices(
                         id_invoices BIGSERIAL NOT NULL PRIMARY KEY,
                         total_value MONEY NOT NULL,
                         payment_date DATE NOT NULL,
                         credit_card_id INT,
                         FOREIGN KEY (credit_card_id) REFERENCES credit_card(id_credit_card)
);

CREATE TABLE status_invoice(
                               id_status_invoices BIGSERIAL NOT NULL PRIMARY KEY,
                               status_invoice BOOLEAN NOT NULL,
                               invoices INT,
                               FOREIGN KEY (invoices) REFERENCES invoices(id_invoices)
);

INSERT INTO invoices(total_value, payment_date, credit_card_id) VALUES
                                                                    (120.23, '2024-11-26', 1),
                                                                    (140.23, '2024-11-26', 1);

INSERT INTO status_invoice(status_invoice,invoices) VALUES
                                                        (false, 1),
                                                        (false, 2),
                                                        (false, 3);



UPDATE status_invoice
SET status_invoice = 'false'
WHERE invoices = 3;

UPDATE status_invoice
SET status_invoice = 'true'
WHERE invoices = 3;

SELECT
    a.id_invoices,
    a.total_value,
    SUM(a.total_value) OVER () AS total_sum ,
        b.id_status_invoices,
    b.status_invoice,
    d.full_name,
    c.number_card
FROM
    invoices AS a
        INNER JOIN
    status_invoice AS b ON a.id_invoices = b.invoices
        INNER JOIN
    credit_card AS c ON a.credit_card_id = c.id_credit_card
        INNER JOIN
    customer AS d ON c.id_credit_card = d.id_customer
WHERE
    d.id_customer = 1;





SELECT
    a.id_invoices,
    a.total_value,
    a.payment_date,
    b.id_status_invoices,
    b.status_invoice,
    d.full_name,
    c.number_card
FROM
    invoices AS a
        INNER JOIN
    status_invoice AS b ON a.id_invoices = b.invoices
        INNER JOIN
    credit_card AS c ON a.credit_card_id = c.id_credit_card
        INNER JOIN
    customer AS d ON c.id_credit_card = d.id_customer
WHERE
    d.id_customer = 1;


SELECT
    a.id_invoices,
    a.total_value,
    a.payment_date,
    b.id_status_invoices,
    b.status_invoice,
    d.full_name,
    c.number_card,
    (SELECT
         SUM(CASE WHEN b2.status_invoice = false THEN a2.total_value ELSE 0::money END)
     FROM
         invoices AS a2
             INNER JOIN
         status_invoice AS b2 ON a2.id_invoices = b2.invoices
     WHERE
         b2.status_invoice = false
       AND a2.credit_card_id = a.credit_card_id
    ) AS total_value_sum
FROM
    invoices AS a
        INNER JOIN
    status_invoice AS b ON a.id_invoices = b.invoices
        INNER JOIN
    credit_card AS c ON a.credit_card_id = c.id_credit_card
        INNER JOIN
    customer AS d ON c.id_credit_card = d.id_customer
WHERE
    d.id_customer = 1;




SELECT
    SUM(CASE WHEN b.status_invoice = false THEN a.total_value::NUMERIC ELSE 0 END) AS total_sum
FROM
    invoices AS a
        INNER JOIN
    status_invoice AS b ON a.id_invoices = b.invoices
        INNER JOIN
    credit_card AS c ON a.credit_card_id = c.id_credit_card
        INNER JOIN
    customer AS d ON c.id_credit_card = d.id_customer
WHERE
    d.id_customer = 1;





INSERT INTO credit_card (number_card, CVV, expiration_date, active, name_card)
VALUES
    (987654321, '321', '2024-11-06', true, 'Ana S');

INSERT INTO password_credit_card(password_card) VALUES
    ('12345678');

INSERT INTO limit_card(limit_card) VALUES
    (500.34);

INSERT INTO credit_card_client(credit_card, password_credit_card, limit_card, customer_id) VALUES
    (2,2,2,2);










