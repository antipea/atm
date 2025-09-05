DROP TABLE IF EXISTS cards CASCADE
;

DROP TABLE IF EXISTS m2m_cards_payments CASCADE
;

DROP TABLE IF EXISTS payments CASCADE
;

DROP TABLE IF EXISTS rates CASCADE
;

DROP TABLE IF EXISTS users CASCADE
;

/* Create Tables */

CREATE TABLE cards
(
    c_id uuid NOT NULL,
    c_number varchar(50) NOT NULL,
    c_pincode smallint NOT NULL,
    c_currency varchar(50) NOT NULL,
    с_date_expire date NOT NULL,
    u_id uuid NOT NULL
)
;

CREATE TABLE m2m_cards_payments
(
    c_id uuid NOT NULL,
    p_id serial NOT NULL,
    pm_amount numeric NOT NULL,
    pm_date date NOT NULL
)
;

CREATE TABLE payments
(
    p_id serial NOT NULL,
    p_name varchar(50) NOT NULL,
    p_description text NULL,
    p_code integer NOT NULL,
    p_is_adding boolean NOT NULL
)
;

CREATE TABLE rates
(
    r_id serial NOT NULL,
    r_usd_byn numeric NULL,
    r_byn_usd numeric NULL,
    r_eur_byn numeric NULL,
    r_byn_eur numeric NULL,
    r_rub_byn numeric NULL,
    r_byn_rub numeric NULL,
    r_date date NULL
)
;

CREATE TABLE users
(
    u_id uuid NOT NULL,
    u_first_name varchar(50) NOT NULL,
    u_last_name varchar(50) NOT NULL,
    u_date_birth date NULL,
    u_number_passport varchar(50) NOT NULL
)
;

/* Create Primary Keys, Indexes, Uniques, Checks */

ALTER TABLE cards ADD CONSTRAINT "PK_cards"
    PRIMARY KEY (c_id)
;

ALTER TABLE cards
    ADD CONSTRAINT "UQ_cardes_c_number" UNIQUE (c_number)
;

CREATE INDEX "IXFK_cards_users" ON cards (u_id ASC)
;

ALTER TABLE m2m_cards_payments ADD CONSTRAINT "PK_m2m_cards_payments"
    PRIMARY KEY (c_id,p_id,pm_amount,pm_date)
;

CREATE INDEX "IXFK_m2m_cards_payments_cards" ON m2m_cards_payments (c_id ASC)
;

CREATE INDEX "IXFK_m2m_cards_payments_payments" ON m2m_cards_payments (p_id ASC)
;

ALTER TABLE payments ADD CONSTRAINT "PK_payments"
    PRIMARY KEY (p_id)
;

ALTER TABLE rates ADD CONSTRAINT "PK_rates"
    PRIMARY KEY (r_id)
;

ALTER TABLE users ADD CONSTRAINT "PK_users"
    PRIMARY KEY (u_id)
;

/* Create Foreign Key Constraints */

ALTER TABLE cards ADD CONSTRAINT "FK_cards_users"
    FOREIGN KEY (u_id) REFERENCES users (u_id) ON DELETE Cascade ON UPDATE No Action
;

ALTER TABLE m2m_cards_payments ADD CONSTRAINT "FK_m2m_cards_payments_cards"
    FOREIGN KEY (c_id) REFERENCES cards (c_id) ON DELETE No Action ON UPDATE No Action
;
