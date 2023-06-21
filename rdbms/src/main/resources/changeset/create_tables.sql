CREATE TABLE account1
(
    id      BIGSERIAL CONSTRAINT account1_pk PRIMARY KEY,
    amount  INT CHECK (amount >= 0),
    version INT
);

INSERT INTO account1(amount, version)
VALUES (100, 0),
       (200, 0),
       (300, 0),
       (400, 0),
       (500, 0),
       (600, 0),
       (700, 0),
       (800, 0),
       (900000, 0),
       (0, 0);

CREATE TABLE account2
(
    id      BIGSERIAL CONSTRAINT account2_pk PRIMARY KEY,
    amount  INT,
    version INT
);

INSERT INTO account2(amount, version)
VALUES (100, 0),
       (200, 0),
       (300, 0),
       (400, 0),
       (500, 0),
       (600, 0),
       (700, 0),
       (800, 0),
       (900000, 0),
       (0, 0);

