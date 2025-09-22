/*DROP TYPE IF EXISTS currency_code CASCADE;*/
DROP TYPE IF EXISTS transaction_type CASCADE;
DROP TYPE IF EXISTS transaction_status CASCADE;

/* Create Tables */

/*CREATE TYPE currency_code AS ENUM('BYN', 'USD', 'EUR', 'RUB');*/
CREATE TYPE transaction_type AS ENUM('DEPOSIT', 'WITHDRAWAL', 'TRANSFER', 'PAYMENT');
CREATE TYPE transaction_status AS ENUM('COMPLETED', 'FAILED');

CREATE TABLE IF NOT EXISTS users
(
    u_id SERIAL PRIMARY KEY,
    u_first_name varchar(50) NOT NULL,
    u_last_name varchar(50) NOT NULL,
    u_date_birth date NOT NULL,
    u_number_passport varchar(50) UNIQUE NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS cards
(
    c_id SERIAL PRIMARY KEY,
    c_number varchar(16) UNIQUE NOT NULL,
    c_pin_code smallint NOT NULL,
    c_currency varchar(10) NOT NULL DEFAULT 'BYN',
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    expires_at DATE NOT NULL,
    c_balance DECIMAL(15,2) NOT NULL,
    c_user_id INTEGER REFERENCES users(u_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS payments
(
    p_id SERIAL PRIMARY KEY,
    p_name varchar(100) NOT NULL,
    p_description text,
    p_code integer UNIQUE NOT NULL,
    is_active BOOLEAN DEFAULT TRUE
);

CREATE TABLE IF NOT EXISTS rates
(
    r_id serial PRIMARY KEY,
    r_base_currency varchar(10) NOT NULL,
    r_target_currency varchar(10) NOT NULL,
    r_rate DECIMAL(15,4) NOT NULL,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE (r_base_currency, r_target_currency)
);

CREATE TABLE IF NOT EXISTS transactions (
      t_id SERIAL PRIMARY KEY,
      t_card_id INTEGER REFERENCES cards(c_id),
      t_type transaction_type NOT NULL,
      t_amount DECIMAL(15,2) NOT NULL,
      t_currency varchar(10) NOT NULL,
      t_base_amount DECIMAL(15,2) NOT NULL,
      t_base_currency varchar(10) NOT NULL,
      t_rate DECIMAL(15,6),
      t_description TEXT,
      t_target_card_number VARCHAR(16),
      t_payment_code VARCHAR(10),
      status transaction_status DEFAULT 'COMPLETED',
      created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);


/* Create Primary Keys, Indexes, Uniques, Checks */

CREATE INDEX IF NOT EXISTS idx_cards_c_number ON cards(c_number);
CREATE INDEX IF NOT EXISTS idx_rates_pair ON rates(r_base_currency, r_target_currency);
CREATE INDEX IF NOT EXISTS idx_transactions_created_at ON transactions(created_at);
CREATE INDEX IF NOT EXISTS idx_transactions_card_id ON transactions(t_card_id);

ALTER TABLE payments ADD COLUMN IF NOT EXISTS created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE payments ADD COLUMN IF NOT EXISTS updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP;

-- Add check for rates
ALTER TABLE rates ADD CHECK (r_base_currency != r_target_currency);

-- Add checks for cards
ALTER TABLE cards ADD CHECK (c_balance >= 0);
ALTER TABLE cards ADD CHECK (c_pin_code BETWEEN 0 AND 9999);

-- Add checks for transactions
ALTER TABLE transactions ADD CHECK (t_amount > 0);
ALTER TABLE transactions ADD CHECK (t_base_amount > 0);

-- Add indexes for increase producing
CREATE INDEX IF NOT EXISTS idx_cards_user_id ON cards(c_user_id);
CREATE INDEX IF NOT EXISTS idx_cards_currency ON cards(c_currency);
CREATE INDEX IF NOT EXISTS idx_transactions_type ON transactions(t_type);
CREATE INDEX IF NOT EXISTS idx_transactions_currency ON transactions(t_currency);
CREATE INDEX IF NOT EXISTS idx_transactions_status ON transactions(status);
CREATE INDEX IF NOT EXISTS idx_payments_code ON payments(p_code);